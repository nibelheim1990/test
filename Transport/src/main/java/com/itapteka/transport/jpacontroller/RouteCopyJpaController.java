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
import com.itapteka.transport.dbcontroller.Route;
import com.itapteka.transport.dbcontroller.RouteCopy;
import com.itapteka.transport.jpacontroller.exceptions.NonexistentEntityException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Ponomarev
 */
public class RouteCopyJpaController implements Serializable {

    public RouteCopyJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(RouteCopy routeCopy) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Client clientId = routeCopy.getClientId();
            if (clientId != null) {
                clientId = em.getReference(clientId.getClass(), clientId.getId());
                routeCopy.setClientId(clientId);
            }
            Route routeId = routeCopy.getRouteId();
            if (routeId != null) {
                routeId = em.getReference(routeId.getClass(), routeId.getId());
                routeCopy.setRouteId(routeId);
            }
            em.persist(routeCopy);
            if (clientId != null) {
                clientId.getRouteCopyCollection().add(routeCopy);
                clientId = em.merge(clientId);
            }
            if (routeId != null) {
                routeId.getRouteCopyCollection().add(routeCopy);
                routeId = em.merge(routeId);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(RouteCopy routeCopy) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            RouteCopy persistentRouteCopy = em.find(RouteCopy.class, routeCopy.getId());
            Client clientIdOld = persistentRouteCopy.getClientId();
            Client clientIdNew = routeCopy.getClientId();
            Route routeIdOld = persistentRouteCopy.getRouteId();
            Route routeIdNew = routeCopy.getRouteId();
            if (clientIdNew != null) {
                clientIdNew = em.getReference(clientIdNew.getClass(), clientIdNew.getId());
                routeCopy.setClientId(clientIdNew);
            }
            if (routeIdNew != null) {
                routeIdNew = em.getReference(routeIdNew.getClass(), routeIdNew.getId());
                routeCopy.setRouteId(routeIdNew);
            }
            routeCopy = em.merge(routeCopy);
            if (clientIdOld != null && !clientIdOld.equals(clientIdNew)) {
                clientIdOld.getRouteCopyCollection().remove(routeCopy);
                clientIdOld = em.merge(clientIdOld);
            }
            if (clientIdNew != null && !clientIdNew.equals(clientIdOld)) {
                clientIdNew.getRouteCopyCollection().add(routeCopy);
                clientIdNew = em.merge(clientIdNew);
            }
            if (routeIdOld != null && !routeIdOld.equals(routeIdNew)) {
                routeIdOld.getRouteCopyCollection().remove(routeCopy);
                routeIdOld = em.merge(routeIdOld);
            }
            if (routeIdNew != null && !routeIdNew.equals(routeIdOld)) {
                routeIdNew.getRouteCopyCollection().add(routeCopy);
                routeIdNew = em.merge(routeIdNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = routeCopy.getId();
                if (findRouteCopy(id) == null) {
                    throw new NonexistentEntityException("The routeCopy with id " + id + " no longer exists.");
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
            RouteCopy routeCopy;
            try {
                routeCopy = em.getReference(RouteCopy.class, id);
                routeCopy.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The routeCopy with id " + id + " no longer exists.", enfe);
            }
            Client clientId = routeCopy.getClientId();
            if (clientId != null) {
                clientId.getRouteCopyCollection().remove(routeCopy);
                clientId = em.merge(clientId);
            }
            Route routeId = routeCopy.getRouteId();
            if (routeId != null) {
                routeId.getRouteCopyCollection().remove(routeCopy);
                routeId = em.merge(routeId);
            }
            em.remove(routeCopy);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<RouteCopy> findRouteCopyEntities() {
        return findRouteCopyEntities(true, -1, -1);
    }

    public List<RouteCopy> findRouteCopyEntities(int maxResults, int firstResult) {
        return findRouteCopyEntities(false, maxResults, firstResult);
    }

    private List<RouteCopy> findRouteCopyEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(RouteCopy.class));
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

    public RouteCopy findRouteCopy(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(RouteCopy.class, id);
        } finally {
            em.close();
        }
    }

    public int getRouteCopyCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<RouteCopy> rt = cq.from(RouteCopy.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
