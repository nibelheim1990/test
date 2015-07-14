/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.itapteka.transport.jpacontroller;

import com.itapteka.transport.dbcontroller.TransportClientInfo;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.itapteka.transport.dbcontroller.TransportInfo;
import com.itapteka.transport.jpacontroller.exceptions.IllegalOrphanException;
import com.itapteka.transport.jpacontroller.exceptions.NonexistentEntityException;
import com.itapteka.transport.jpacontroller.exceptions.PreexistingEntityException;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Ponomarev
 */
public class TransportClientInfoJpaController implements Serializable {

    public TransportClientInfoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(TransportClientInfo transportClientInfo) throws IllegalOrphanException, PreexistingEntityException, Exception {
        List<String> illegalOrphanMessages = null;
        TransportInfo transportInfoOrphanCheck = transportClientInfo.getTransportInfo();
        if (transportInfoOrphanCheck != null) {
            TransportClientInfo oldTransportClientInfoOfTransportInfo = transportInfoOrphanCheck.getTransportClientInfo();
            if (oldTransportClientInfoOfTransportInfo != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("The TransportInfo " + transportInfoOrphanCheck + " already has an item of type TransportClientInfo whose transportInfo column cannot be null. Please make another selection for the transportInfo field.");
            }
        }
        if (illegalOrphanMessages != null) {
            throw new IllegalOrphanException(illegalOrphanMessages);
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            TransportInfo transportInfo = transportClientInfo.getTransportInfo();
            if (transportInfo != null) {
                transportInfo = em.getReference(transportInfo.getClass(), transportInfo.getId());
                transportClientInfo.setTransportInfo(transportInfo);
            }
            em.persist(transportClientInfo);
            if (transportInfo != null) {
                transportInfo.setTransportClientInfo(transportClientInfo);
                transportInfo = em.merge(transportInfo);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findTransportClientInfo(transportClientInfo.getId()) != null) {
                throw new PreexistingEntityException("TransportClientInfo " + transportClientInfo + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(TransportClientInfo transportClientInfo) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            TransportClientInfo persistentTransportClientInfo = em.find(TransportClientInfo.class, transportClientInfo.getId());
            TransportInfo transportInfoOld = persistentTransportClientInfo.getTransportInfo();
            TransportInfo transportInfoNew = transportClientInfo.getTransportInfo();
            List<String> illegalOrphanMessages = null;
            if (transportInfoNew != null && !transportInfoNew.equals(transportInfoOld)) {
                TransportClientInfo oldTransportClientInfoOfTransportInfo = transportInfoNew.getTransportClientInfo();
                if (oldTransportClientInfoOfTransportInfo != null) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("The TransportInfo " + transportInfoNew + " already has an item of type TransportClientInfo whose transportInfo column cannot be null. Please make another selection for the transportInfo field.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (transportInfoNew != null) {
                transportInfoNew = em.getReference(transportInfoNew.getClass(), transportInfoNew.getId());
                transportClientInfo.setTransportInfo(transportInfoNew);
            }
            transportClientInfo = em.merge(transportClientInfo);
            if (transportInfoOld != null && !transportInfoOld.equals(transportInfoNew)) {
                transportInfoOld.setTransportClientInfo(null);
                transportInfoOld = em.merge(transportInfoOld);
            }
            if (transportInfoNew != null && !transportInfoNew.equals(transportInfoOld)) {
                transportInfoNew.setTransportClientInfo(transportClientInfo);
                transportInfoNew = em.merge(transportInfoNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = transportClientInfo.getId();
                if (findTransportClientInfo(id) == null) {
                    throw new NonexistentEntityException("The transportClientInfo with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Long id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            TransportClientInfo transportClientInfo;
            try {
                transportClientInfo = em.getReference(TransportClientInfo.class, id);
                transportClientInfo.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The transportClientInfo with id " + id + " no longer exists.", enfe);
            }
            TransportInfo transportInfo = transportClientInfo.getTransportInfo();
            if (transportInfo != null) {
                transportInfo.setTransportClientInfo(null);
                transportInfo = em.merge(transportInfo);
            }
            em.remove(transportClientInfo);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<TransportClientInfo> findTransportClientInfoEntities() {
        return findTransportClientInfoEntities(true, -1, -1);
    }

    public List<TransportClientInfo> findTransportClientInfoEntities(int maxResults, int firstResult) {
        return findTransportClientInfoEntities(false, maxResults, firstResult);
    }

    private List<TransportClientInfo> findTransportClientInfoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(TransportClientInfo.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public TransportClientInfo findTransportClientInfo(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(TransportClientInfo.class, id);
        } finally {
            em.close();
        }
    }

    public int getTransportClientInfoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<TransportClientInfo> rt = cq.from(TransportClientInfo.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
