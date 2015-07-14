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
import com.itapteka.transport.dbcontroller.Schedule;
import com.itapteka.transport.dbcontroller.ScheduleDay;
import com.itapteka.transport.jpacontroller.exceptions.NonexistentEntityException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Ponomarev
 */
public class ScheduleDayJpaController implements Serializable {

    public ScheduleDayJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(ScheduleDay scheduleDay) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Schedule scheduleId = scheduleDay.getScheduleId();
            if (scheduleId != null) {
                scheduleId = em.getReference(scheduleId.getClass(), scheduleId.getId());
                scheduleDay.setScheduleId(scheduleId);
            }
            em.persist(scheduleDay);
            if (scheduleId != null) {
                scheduleId.getScheduleDayCollection().add(scheduleDay);
                scheduleId = em.merge(scheduleId);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(ScheduleDay scheduleDay) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            ScheduleDay persistentScheduleDay = em.find(ScheduleDay.class, scheduleDay.getId());
            Schedule scheduleIdOld = persistentScheduleDay.getScheduleId();
            Schedule scheduleIdNew = scheduleDay.getScheduleId();
            if (scheduleIdNew != null) {
                scheduleIdNew = em.getReference(scheduleIdNew.getClass(), scheduleIdNew.getId());
                scheduleDay.setScheduleId(scheduleIdNew);
            }
            scheduleDay = em.merge(scheduleDay);
            if (scheduleIdOld != null && !scheduleIdOld.equals(scheduleIdNew)) {
                scheduleIdOld.getScheduleDayCollection().remove(scheduleDay);
                scheduleIdOld = em.merge(scheduleIdOld);
            }
            if (scheduleIdNew != null && !scheduleIdNew.equals(scheduleIdOld)) {
                scheduleIdNew.getScheduleDayCollection().add(scheduleDay);
                scheduleIdNew = em.merge(scheduleIdNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = scheduleDay.getId();
                if (findScheduleDay(id) == null) {
                    throw new NonexistentEntityException("The scheduleDay with id " + id + " no longer exists.");
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
            ScheduleDay scheduleDay;
            try {
                scheduleDay = em.getReference(ScheduleDay.class, id);
                scheduleDay.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The scheduleDay with id " + id + " no longer exists.", enfe);
            }
            Schedule scheduleId = scheduleDay.getScheduleId();
            if (scheduleId != null) {
                scheduleId.getScheduleDayCollection().remove(scheduleDay);
                scheduleId = em.merge(scheduleId);
            }
            em.remove(scheduleDay);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<ScheduleDay> findScheduleDayEntities() {
        return findScheduleDayEntities(true, -1, -1);
    }

    public List<ScheduleDay> findScheduleDayEntities(int maxResults, int firstResult) {
        return findScheduleDayEntities(false, maxResults, firstResult);
    }

    private List<ScheduleDay> findScheduleDayEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(ScheduleDay.class));
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

    public ScheduleDay findScheduleDay(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(ScheduleDay.class, id);
        } finally {
            em.close();
        }
    }

    public int getScheduleDayCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<ScheduleDay> rt = cq.from(ScheduleDay.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
