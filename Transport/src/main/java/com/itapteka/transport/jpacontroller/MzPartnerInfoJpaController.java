/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.itapteka.transport.jpacontroller;

import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.itapteka.transport.dbcontroller.Client;
import com.itapteka.transport.dbcontroller.MzPartnerInfo;
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
public class MzPartnerInfoJpaController implements Serializable {

    public MzPartnerInfoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(MzPartnerInfo mzPartnerInfo) throws IllegalOrphanException, PreexistingEntityException, Exception {
        List<String> illegalOrphanMessages = null;
        Client clientOrphanCheck = mzPartnerInfo.getClient();
        if (clientOrphanCheck != null) {
            MzPartnerInfo oldMzPartnerInfoOfClient = clientOrphanCheck.getMzPartnerInfo();
            if (oldMzPartnerInfoOfClient != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("The Client " + clientOrphanCheck + " already has an item of type MzPartnerInfo whose client column cannot be null. Please make another selection for the client field.");
            }
        }
        if (illegalOrphanMessages != null) {
            throw new IllegalOrphanException(illegalOrphanMessages);
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Client client = mzPartnerInfo.getClient();
            if (client != null) {
                client = em.getReference(client.getClass(), client.getId());
                mzPartnerInfo.setClient(client);
            }
            em.persist(mzPartnerInfo);
            if (client != null) {
                client.setMzPartnerInfo(mzPartnerInfo);
                client = em.merge(client);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findMzPartnerInfo(mzPartnerInfo.getId()) != null) {
                throw new PreexistingEntityException("MzPartnerInfo " + mzPartnerInfo + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(MzPartnerInfo mzPartnerInfo) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            MzPartnerInfo persistentMzPartnerInfo = em.find(MzPartnerInfo.class, mzPartnerInfo.getId());
            Client clientOld = persistentMzPartnerInfo.getClient();
            Client clientNew = mzPartnerInfo.getClient();
            List<String> illegalOrphanMessages = null;
            if (clientNew != null && !clientNew.equals(clientOld)) {
                MzPartnerInfo oldMzPartnerInfoOfClient = clientNew.getMzPartnerInfo();
                if (oldMzPartnerInfoOfClient != null) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("The Client " + clientNew + " already has an item of type MzPartnerInfo whose client column cannot be null. Please make another selection for the client field.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (clientNew != null) {
                clientNew = em.getReference(clientNew.getClass(), clientNew.getId());
                mzPartnerInfo.setClient(clientNew);
            }
            mzPartnerInfo = em.merge(mzPartnerInfo);
            if (clientOld != null && !clientOld.equals(clientNew)) {
                clientOld.setMzPartnerInfo(null);
                clientOld = em.merge(clientOld);
            }
            if (clientNew != null && !clientNew.equals(clientOld)) {
                clientNew.setMzPartnerInfo(mzPartnerInfo);
                clientNew = em.merge(clientNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = mzPartnerInfo.getId();
                if (findMzPartnerInfo(id) == null) {
                    throw new NonexistentEntityException("The mzPartnerInfo with id " + id + " no longer exists.");
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
            MzPartnerInfo mzPartnerInfo;
            try {
                mzPartnerInfo = em.getReference(MzPartnerInfo.class, id);
                mzPartnerInfo.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The mzPartnerInfo with id " + id + " no longer exists.", enfe);
            }
            Client client = mzPartnerInfo.getClient();
            if (client != null) {
                client.setMzPartnerInfo(null);
                client = em.merge(client);
            }
            em.remove(mzPartnerInfo);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<MzPartnerInfo> findMzPartnerInfoEntities() {
        return findMzPartnerInfoEntities(true, -1, -1);
    }

    public List<MzPartnerInfo> findMzPartnerInfoEntities(int maxResults, int firstResult) {
        return findMzPartnerInfoEntities(false, maxResults, firstResult);
    }

    private List<MzPartnerInfo> findMzPartnerInfoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(MzPartnerInfo.class));
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

    public MzPartnerInfo findMzPartnerInfo(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(MzPartnerInfo.class, id);
        } finally {
            em.close();
        }
    }

    public int getMzPartnerInfoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<MzPartnerInfo> rt = cq.from(MzPartnerInfo.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
