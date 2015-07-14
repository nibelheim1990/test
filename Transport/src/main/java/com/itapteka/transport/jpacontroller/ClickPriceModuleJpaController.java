/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.itapteka.transport.jpacontroller;

import com.itapteka.transport.dbcontroller.ClickPriceModule;
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
public class ClickPriceModuleJpaController implements Serializable {

    public ClickPriceModuleJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(ClickPriceModule clickPriceModule) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(clickPriceModule);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(ClickPriceModule clickPriceModule) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            clickPriceModule = em.merge(clickPriceModule);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = clickPriceModule.getId();
                if (findClickPriceModule(id) == null) {
                    throw new NonexistentEntityException("The clickPriceModule with id " + id + " no longer exists.");
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
            ClickPriceModule clickPriceModule;
            try {
                clickPriceModule = em.getReference(ClickPriceModule.class, id);
                clickPriceModule.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The clickPriceModule with id " + id + " no longer exists.", enfe);
            }
            em.remove(clickPriceModule);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<ClickPriceModule> findClickPriceModuleEntities() {
        return findClickPriceModuleEntities(true, -1, -1);
    }

    public List<ClickPriceModule> findClickPriceModuleEntities(int maxResults, int firstResult) {
        return findClickPriceModuleEntities(false, maxResults, firstResult);
    }

    private List<ClickPriceModule> findClickPriceModuleEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(ClickPriceModule.class));
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

    public ClickPriceModule findClickPriceModule(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(ClickPriceModule.class, id);
        } finally {
            em.close();
        }
    }

    public int getClickPriceModuleCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<ClickPriceModule> rt = cq.from(ClickPriceModule.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
