/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.itapteka.transport.jpacontroller;

import com.itapteka.transport.dbcontroller.CleanerException;
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
public class CleanerExceptionJpaController implements Serializable {

    public CleanerExceptionJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(CleanerException cleanerException) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(cleanerException);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(CleanerException cleanerException) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            cleanerException = em.merge(cleanerException);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = cleanerException.getId();
                if (findCleanerException(id) == null) {
                    throw new NonexistentEntityException("The cleanerException with id " + id + " no longer exists.");
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
            CleanerException cleanerException;
            try {
                cleanerException = em.getReference(CleanerException.class, id);
                cleanerException.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The cleanerException with id " + id + " no longer exists.", enfe);
            }
            em.remove(cleanerException);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<CleanerException> findCleanerExceptionEntities() {
        return findCleanerExceptionEntities(true, -1, -1);
    }

    public List<CleanerException> findCleanerExceptionEntities(int maxResults, int firstResult) {
        return findCleanerExceptionEntities(false, maxResults, firstResult);
    }

    private List<CleanerException> findCleanerExceptionEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(CleanerException.class));
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

    public CleanerException findCleanerException(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(CleanerException.class, id);
        } finally {
            em.close();
        }
    }

    public int getCleanerExceptionCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<CleanerException> rt = cq.from(CleanerException.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
