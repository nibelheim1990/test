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
import com.itapteka.transport.jpacontroller.exceptions.IllegalOrphanException;
import com.itapteka.transport.jpacontroller.exceptions.NonexistentEntityException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Ponomarev
 */
public class RouteJpaController implements Serializable {

    public RouteJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Route route) {
        if (route.getRouteCopyCollection() == null) {
            route.setRouteCopyCollection(new ArrayList<RouteCopy>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Client clientDstId = route.getClientDstId();
            if (clientDstId != null) {
                clientDstId = em.getReference(clientDstId.getClass(), clientDstId.getId());
                route.setClientDstId(clientDstId);
            }
            Client clientSrcId = route.getClientSrcId();
            if (clientSrcId != null) {
                clientSrcId = em.getReference(clientSrcId.getClass(), clientSrcId.getId());
                route.setClientSrcId(clientSrcId);
            }
            Collection<RouteCopy> attachedRouteCopyCollection = new ArrayList<RouteCopy>();
            for (RouteCopy routeCopyCollectionRouteCopyToAttach : route.getRouteCopyCollection()) {
                routeCopyCollectionRouteCopyToAttach = em.getReference(routeCopyCollectionRouteCopyToAttach.getClass(), routeCopyCollectionRouteCopyToAttach.getId());
                attachedRouteCopyCollection.add(routeCopyCollectionRouteCopyToAttach);
            }
            route.setRouteCopyCollection(attachedRouteCopyCollection);
            em.persist(route);
            if (clientDstId != null) {
                clientDstId.getRouteCollection().add(route);
                clientDstId = em.merge(clientDstId);
            }
            if (clientSrcId != null) {
                clientSrcId.getRouteCollection().add(route);
                clientSrcId = em.merge(clientSrcId);
            }
            for (RouteCopy routeCopyCollectionRouteCopy : route.getRouteCopyCollection()) {
                Route oldRouteIdOfRouteCopyCollectionRouteCopy = routeCopyCollectionRouteCopy.getRouteId();
                routeCopyCollectionRouteCopy.setRouteId(route);
                routeCopyCollectionRouteCopy = em.merge(routeCopyCollectionRouteCopy);
                if (oldRouteIdOfRouteCopyCollectionRouteCopy != null) {
                    oldRouteIdOfRouteCopyCollectionRouteCopy.getRouteCopyCollection().remove(routeCopyCollectionRouteCopy);
                    oldRouteIdOfRouteCopyCollectionRouteCopy = em.merge(oldRouteIdOfRouteCopyCollectionRouteCopy);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Route route) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Route persistentRoute = em.find(Route.class, route.getId());
            Client clientDstIdOld = persistentRoute.getClientDstId();
            Client clientDstIdNew = route.getClientDstId();
            Client clientSrcIdOld = persistentRoute.getClientSrcId();
            Client clientSrcIdNew = route.getClientSrcId();
            Collection<RouteCopy> routeCopyCollectionOld = persistentRoute.getRouteCopyCollection();
            Collection<RouteCopy> routeCopyCollectionNew = route.getRouteCopyCollection();
            List<String> illegalOrphanMessages = null;
            for (RouteCopy routeCopyCollectionOldRouteCopy : routeCopyCollectionOld) {
                if (!routeCopyCollectionNew.contains(routeCopyCollectionOldRouteCopy)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain RouteCopy " + routeCopyCollectionOldRouteCopy + " since its routeId field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (clientDstIdNew != null) {
                clientDstIdNew = em.getReference(clientDstIdNew.getClass(), clientDstIdNew.getId());
                route.setClientDstId(clientDstIdNew);
            }
            if (clientSrcIdNew != null) {
                clientSrcIdNew = em.getReference(clientSrcIdNew.getClass(), clientSrcIdNew.getId());
                route.setClientSrcId(clientSrcIdNew);
            }
            Collection<RouteCopy> attachedRouteCopyCollectionNew = new ArrayList<RouteCopy>();
            for (RouteCopy routeCopyCollectionNewRouteCopyToAttach : routeCopyCollectionNew) {
                routeCopyCollectionNewRouteCopyToAttach = em.getReference(routeCopyCollectionNewRouteCopyToAttach.getClass(), routeCopyCollectionNewRouteCopyToAttach.getId());
                attachedRouteCopyCollectionNew.add(routeCopyCollectionNewRouteCopyToAttach);
            }
            routeCopyCollectionNew = attachedRouteCopyCollectionNew;
            route.setRouteCopyCollection(routeCopyCollectionNew);
            route = em.merge(route);
            if (clientDstIdOld != null && !clientDstIdOld.equals(clientDstIdNew)) {
                clientDstIdOld.getRouteCollection().remove(route);
                clientDstIdOld = em.merge(clientDstIdOld);
            }
            if (clientDstIdNew != null && !clientDstIdNew.equals(clientDstIdOld)) {
                clientDstIdNew.getRouteCollection().add(route);
                clientDstIdNew = em.merge(clientDstIdNew);
            }
            if (clientSrcIdOld != null && !clientSrcIdOld.equals(clientSrcIdNew)) {
                clientSrcIdOld.getRouteCollection().remove(route);
                clientSrcIdOld = em.merge(clientSrcIdOld);
            }
            if (clientSrcIdNew != null && !clientSrcIdNew.equals(clientSrcIdOld)) {
                clientSrcIdNew.getRouteCollection().add(route);
                clientSrcIdNew = em.merge(clientSrcIdNew);
            }
            for (RouteCopy routeCopyCollectionNewRouteCopy : routeCopyCollectionNew) {
                if (!routeCopyCollectionOld.contains(routeCopyCollectionNewRouteCopy)) {
                    Route oldRouteIdOfRouteCopyCollectionNewRouteCopy = routeCopyCollectionNewRouteCopy.getRouteId();
                    routeCopyCollectionNewRouteCopy.setRouteId(route);
                    routeCopyCollectionNewRouteCopy = em.merge(routeCopyCollectionNewRouteCopy);
                    if (oldRouteIdOfRouteCopyCollectionNewRouteCopy != null && !oldRouteIdOfRouteCopyCollectionNewRouteCopy.equals(route)) {
                        oldRouteIdOfRouteCopyCollectionNewRouteCopy.getRouteCopyCollection().remove(routeCopyCollectionNewRouteCopy);
                        oldRouteIdOfRouteCopyCollectionNewRouteCopy = em.merge(oldRouteIdOfRouteCopyCollectionNewRouteCopy);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = route.getId();
                if (findRoute(id) == null) {
                    throw new NonexistentEntityException("The route with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Long id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Route route;
            try {
                route = em.getReference(Route.class, id);
                route.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The route with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<RouteCopy> routeCopyCollectionOrphanCheck = route.getRouteCopyCollection();
            for (RouteCopy routeCopyCollectionOrphanCheckRouteCopy : routeCopyCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Route (" + route + ") cannot be destroyed since the RouteCopy " + routeCopyCollectionOrphanCheckRouteCopy + " in its routeCopyCollection field has a non-nullable routeId field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Client clientDstId = route.getClientDstId();
            if (clientDstId != null) {
                clientDstId.getRouteCollection().remove(route);
                clientDstId = em.merge(clientDstId);
            }
            Client clientSrcId = route.getClientSrcId();
            if (clientSrcId != null) {
                clientSrcId.getRouteCollection().remove(route);
                clientSrcId = em.merge(clientSrcId);
            }
            em.remove(route);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Route> findRouteEntities() {
        return findRouteEntities(true, -1, -1);
    }

    public List<Route> findRouteEntities(int maxResults, int firstResult) {
        return findRouteEntities(false, maxResults, firstResult);
    }

    private List<Route> findRouteEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Route.class));
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

    public Route findRoute(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Route.class, id);
        } finally {
            em.close();
        }
    }

    public int getRouteCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Route> rt = cq.from(Route.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
