/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.itapteka.transport.jpacontroller;

import com.itapteka.transport.dbcontroller.ClientTraffic;
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
public class ClientTrafficJpaController implements Serializable {

    public ClientTrafficJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(ClientTraffic clientTraffic) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            HttpInfo httpInfoId = clientTraffic.getHttpInfoId();
            if (httpInfoId != null) {
                httpInfoId = em.getReference(httpInfoId.getClass(), httpInfoId.getId());
                clientTraffic.setHttpInfoId(httpInfoId);
            }
            em.persist(clientTraffic);
            if (httpInfoId != null) {
                httpInfoId.getClientTrafficCollection().add(clientTraffic);
                httpInfoId = em.merge(httpInfoId);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(ClientTraffic clientTraffic) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            ClientTraffic persistentClientTraffic = em.find(ClientTraffic.class, clientTraffic.getId());
            HttpInfo httpInfoIdOld = persistentClientTraffic.getHttpInfoId();
            HttpInfo httpInfoIdNew = clientTraffic.getHttpInfoId();
            if (httpInfoIdNew != null) {
                httpInfoIdNew = em.getReference(httpInfoIdNew.getClass(), httpInfoIdNew.getId());
                clientTraffic.setHttpInfoId(httpInfoIdNew);
            }
            clientTraffic = em.merge(clientTraffic);
            if (httpInfoIdOld != null && !httpInfoIdOld.equals(httpInfoIdNew)) {
                httpInfoIdOld.getClientTrafficCollection().remove(clientTraffic);
                httpInfoIdOld = em.merge(httpInfoIdOld);
            }
            if (httpInfoIdNew != null && !httpInfoIdNew.equals(httpInfoIdOld)) {
                httpInfoIdNew.getClientTrafficCollection().add(clientTraffic);
                httpInfoIdNew = em.merge(httpInfoIdNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = clientTraffic.getId();
                if (findClientTraffic(id) == null) {
                    throw new NonexistentEntityException("The clientTraffic with id " + id + " no longer exists.");
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
            ClientTraffic clientTraffic;
            try {
                clientTraffic = em.getReference(ClientTraffic.class, id);
                clientTraffic.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The clientTraffic with id " + id + " no longer exists.", enfe);
            }
            HttpInfo httpInfoId = clientTraffic.getHttpInfoId();
            if (httpInfoId != null) {
                httpInfoId.getClientTrafficCollection().remove(clientTraffic);
                httpInfoId = em.merge(httpInfoId);
            }
            em.remove(clientTraffic);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<ClientTraffic> findClientTrafficEntities() {
        return findClientTrafficEntities(true, -1, -1);
    }

    public List<ClientTraffic> findClientTrafficEntities(int maxResults, int firstResult) {
        return findClientTrafficEntities(false, maxResults, firstResult);
    }

    private List<ClientTraffic> findClientTrafficEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(ClientTraffic.class));
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

    public ClientTraffic findClientTraffic(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(ClientTraffic.class, id);
        } finally {
            em.close();
        }
    }

    public int getClientTrafficCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<ClientTraffic> rt = cq.from(ClientTraffic.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
