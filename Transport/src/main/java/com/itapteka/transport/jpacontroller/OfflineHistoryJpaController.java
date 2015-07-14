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
import com.itapteka.transport.dbcontroller.OfflineHistory;
import com.itapteka.transport.jpacontroller.exceptions.NonexistentEntityException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Ponomarev
 */
public class OfflineHistoryJpaController implements Serializable {

    public OfflineHistoryJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(OfflineHistory offlineHistory) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Client clientId = offlineHistory.getClientId();
            if (clientId != null) {
                clientId = em.getReference(clientId.getClass(), clientId.getId());
                offlineHistory.setClientId(clientId);
            }
            em.persist(offlineHistory);
            if (clientId != null) {
                clientId.getOfflineHistoryCollection().add(offlineHistory);
                clientId = em.merge(clientId);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(OfflineHistory offlineHistory) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            OfflineHistory persistentOfflineHistory = em.find(OfflineHistory.class, offlineHistory.getId());
            Client clientIdOld = persistentOfflineHistory.getClientId();
            Client clientIdNew = offlineHistory.getClientId();
            if (clientIdNew != null) {
                clientIdNew = em.getReference(clientIdNew.getClass(), clientIdNew.getId());
                offlineHistory.setClientId(clientIdNew);
            }
            offlineHistory = em.merge(offlineHistory);
            if (clientIdOld != null && !clientIdOld.equals(clientIdNew)) {
                clientIdOld.getOfflineHistoryCollection().remove(offlineHistory);
                clientIdOld = em.merge(clientIdOld);
            }
            if (clientIdNew != null && !clientIdNew.equals(clientIdOld)) {
                clientIdNew.getOfflineHistoryCollection().add(offlineHistory);
                clientIdNew = em.merge(clientIdNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = offlineHistory.getId();
                if (findOfflineHistory(id) == null) {
                    throw new NonexistentEntityException("The offlineHistory with id " + id + " no longer exists.");
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
            OfflineHistory offlineHistory;
            try {
                offlineHistory = em.getReference(OfflineHistory.class, id);
                offlineHistory.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The offlineHistory with id " + id + " no longer exists.", enfe);
            }
            Client clientId = offlineHistory.getClientId();
            if (clientId != null) {
                clientId.getOfflineHistoryCollection().remove(offlineHistory);
                clientId = em.merge(clientId);
            }
            em.remove(offlineHistory);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<OfflineHistory> findOfflineHistoryEntities() {
        return findOfflineHistoryEntities(true, -1, -1);
    }

    public List<OfflineHistory> findOfflineHistoryEntities(int maxResults, int firstResult) {
        return findOfflineHistoryEntities(false, maxResults, firstResult);
    }

    private List<OfflineHistory> findOfflineHistoryEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(OfflineHistory.class));
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

    public OfflineHistory findOfflineHistory(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(OfflineHistory.class, id);
        } finally {
            em.close();
        }
    }

    public int getOfflineHistoryCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<OfflineHistory> rt = cq.from(OfflineHistory.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
