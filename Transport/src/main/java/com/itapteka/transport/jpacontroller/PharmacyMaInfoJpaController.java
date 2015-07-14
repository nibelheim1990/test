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
import com.itapteka.transport.dbcontroller.PharmacyMaInfo;
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
public class PharmacyMaInfoJpaController implements Serializable {

    public PharmacyMaInfoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(PharmacyMaInfo pharmacyMaInfo) throws IllegalOrphanException, PreexistingEntityException, Exception {
        List<String> illegalOrphanMessages = null;
        Client clientOrphanCheck = pharmacyMaInfo.getClient();
        if (clientOrphanCheck != null) {
            PharmacyMaInfo oldPharmacyMaInfoOfClient = clientOrphanCheck.getPharmacyMaInfo();
            if (oldPharmacyMaInfoOfClient != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("The Client " + clientOrphanCheck + " already has an item of type PharmacyMaInfo whose client column cannot be null. Please make another selection for the client field.");
            }
        }
        if (illegalOrphanMessages != null) {
            throw new IllegalOrphanException(illegalOrphanMessages);
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Client client = pharmacyMaInfo.getClient();
            if (client != null) {
                client = em.getReference(client.getClass(), client.getId());
                pharmacyMaInfo.setClient(client);
            }
            em.persist(pharmacyMaInfo);
            if (client != null) {
                client.setPharmacyMaInfo(pharmacyMaInfo);
                client = em.merge(client);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findPharmacyMaInfo(pharmacyMaInfo.getId()) != null) {
                throw new PreexistingEntityException("PharmacyMaInfo " + pharmacyMaInfo + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(PharmacyMaInfo pharmacyMaInfo) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            PharmacyMaInfo persistentPharmacyMaInfo = em.find(PharmacyMaInfo.class, pharmacyMaInfo.getId());
            Client clientOld = persistentPharmacyMaInfo.getClient();
            Client clientNew = pharmacyMaInfo.getClient();
            List<String> illegalOrphanMessages = null;
            if (clientNew != null && !clientNew.equals(clientOld)) {
                PharmacyMaInfo oldPharmacyMaInfoOfClient = clientNew.getPharmacyMaInfo();
                if (oldPharmacyMaInfoOfClient != null) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("The Client " + clientNew + " already has an item of type PharmacyMaInfo whose client column cannot be null. Please make another selection for the client field.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (clientNew != null) {
                clientNew = em.getReference(clientNew.getClass(), clientNew.getId());
                pharmacyMaInfo.setClient(clientNew);
            }
            pharmacyMaInfo = em.merge(pharmacyMaInfo);
            if (clientOld != null && !clientOld.equals(clientNew)) {
                clientOld.setPharmacyMaInfo(null);
                clientOld = em.merge(clientOld);
            }
            if (clientNew != null && !clientNew.equals(clientOld)) {
                clientNew.setPharmacyMaInfo(pharmacyMaInfo);
                clientNew = em.merge(clientNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = pharmacyMaInfo.getId();
                if (findPharmacyMaInfo(id) == null) {
                    throw new NonexistentEntityException("The pharmacyMaInfo with id " + id + " no longer exists.");
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
            PharmacyMaInfo pharmacyMaInfo;
            try {
                pharmacyMaInfo = em.getReference(PharmacyMaInfo.class, id);
                pharmacyMaInfo.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The pharmacyMaInfo with id " + id + " no longer exists.", enfe);
            }
            Client client = pharmacyMaInfo.getClient();
            if (client != null) {
                client.setPharmacyMaInfo(null);
                client = em.merge(client);
            }
            em.remove(pharmacyMaInfo);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<PharmacyMaInfo> findPharmacyMaInfoEntities() {
        return findPharmacyMaInfoEntities(true, -1, -1);
    }

    public List<PharmacyMaInfo> findPharmacyMaInfoEntities(int maxResults, int firstResult) {
        return findPharmacyMaInfoEntities(false, maxResults, firstResult);
    }

    private List<PharmacyMaInfo> findPharmacyMaInfoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(PharmacyMaInfo.class));
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

    public PharmacyMaInfo findPharmacyMaInfo(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(PharmacyMaInfo.class, id);
        } finally {
            em.close();
        }
    }

    public int getPharmacyMaInfoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<PharmacyMaInfo> rt = cq.from(PharmacyMaInfo.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
