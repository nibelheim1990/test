/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.itapteka.transport.jpacontroller;

import com.itapteka.transport.dbcontroller.ClientLog;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.itapteka.transport.dbcontroller.HttpInfo;
import com.itapteka.transport.jpacontroller.exceptions.NonexistentEntityException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Ponomarev
 */
public class ClientLogJpaController implements Serializable {

    public ClientLogJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(ClientLog clientLog) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            HttpInfo httpInfoId = clientLog.getHttpInfoId();
            if (httpInfoId != null) {
                httpInfoId = em.getReference(httpInfoId.getClass(), httpInfoId.getId());
                clientLog.setHttpInfoId(httpInfoId);
            }
            em.persist(clientLog);
            if (httpInfoId != null) {
                httpInfoId.getClientLogCollection().add(clientLog);
                httpInfoId = em.merge(httpInfoId);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(ClientLog clientLog) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            ClientLog persistentClientLog = em.find(ClientLog.class, clientLog.getId());
            HttpInfo httpInfoIdOld = persistentClientLog.getHttpInfoId();
            HttpInfo httpInfoIdNew = clientLog.getHttpInfoId();
            if (httpInfoIdNew != null) {
                httpInfoIdNew = em.getReference(httpInfoIdNew.getClass(), httpInfoIdNew.getId());
                clientLog.setHttpInfoId(httpInfoIdNew);
            }
            clientLog = em.merge(clientLog);
            if (httpInfoIdOld != null && !httpInfoIdOld.equals(httpInfoIdNew)) {
                httpInfoIdOld.getClientLogCollection().remove(clientLog);
                httpInfoIdOld = em.merge(httpInfoIdOld);
            }
            if (httpInfoIdNew != null && !httpInfoIdNew.equals(httpInfoIdOld)) {
                httpInfoIdNew.getClientLogCollection().add(clientLog);
                httpInfoIdNew = em.merge(httpInfoIdNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = clientLog.getId();
                if (findClientLog(id) == null) {
                    throw new NonexistentEntityException("The clientLog with id " + id + " no longer exists.");
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
            ClientLog clientLog;
            try {
                clientLog = em.getReference(ClientLog.class, id);
                clientLog.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The clientLog with id " + id + " no longer exists.", enfe);
            }
            HttpInfo httpInfoId = clientLog.getHttpInfoId();
            if (httpInfoId != null) {
                httpInfoId.getClientLogCollection().remove(clientLog);
                httpInfoId = em.merge(httpInfoId);
            }
            em.remove(clientLog);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<ClientLog> findClientLogEntities() {
        return findClientLogEntities(true, -1, -1);
    }

    public List<ClientLog> findClientLogEntities(int maxResults, int firstResult) {
        return findClientLogEntities(false, maxResults, firstResult);
    }

    private List<ClientLog> findClientLogEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(ClientLog.class));
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

    public ClientLog findClientLog(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(ClientLog.class, id);
        } finally {
            em.close();
        }
    }

    public int getClientLogCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<ClientLog> rt = cq.from(ClientLog.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
