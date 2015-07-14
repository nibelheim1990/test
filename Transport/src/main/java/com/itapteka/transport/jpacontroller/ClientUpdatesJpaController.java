/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.itapteka.transport.jpacontroller;

import com.itapteka.transport.dbcontroller.ClientUpdates;
import com.itapteka.transport.jpacontroller.exceptions.NonexistentEntityException;
import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 *
 * @author Ponomarev
 */
public class ClientUpdatesJpaController implements Serializable {

    public ClientUpdatesJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(ClientUpdates clientUpdates) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(clientUpdates);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(ClientUpdates clientUpdates) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            clientUpdates = em.merge(clientUpdates);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = clientUpdates.getId();
                if (findClientUpdates(id) == null) {
                    throw new NonexistentEntityException("The clientUpdates with id " + id + " no longer exists.");
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
            ClientUpdates clientUpdates;
            try {
                clientUpdates = em.getReference(ClientUpdates.class, id);
                clientUpdates.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The clientUpdates with id " + id + " no longer exists.", enfe);
            }
            em.remove(clientUpdates);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<ClientUpdates> findClientUpdatesEntities() {
        return findClientUpdatesEntities(true, -1, -1);
    }

    public List<ClientUpdates> findClientUpdatesEntities(int maxResults, int firstResult) {
        return findClientUpdatesEntities(false, maxResults, firstResult);
    }

    private List<ClientUpdates> findClientUpdatesEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(ClientUpdates.class));
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

    public ClientUpdates findClientUpdates(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(ClientUpdates.class, id);
        } finally {
            em.close();
        }
    }

    public int getClientUpdatesCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<ClientUpdates> rt = cq.from(ClientUpdates.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
