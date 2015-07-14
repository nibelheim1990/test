/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.itapteka.transport.jpacontroller;

import com.itapteka.transport.dbcontroller.Schedule;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.itapteka.transport.dbcontroller.TransportInfo;
import java.util.ArrayList;
import java.util.Collection;
import com.itapteka.transport.dbcontroller.ScheduleDay;
import com.itapteka.transport.jpacontroller.exceptions.IllegalOrphanException;
import com.itapteka.transport.jpacontroller.exceptions.NonexistentEntityException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Ponomarev
 */
public class ScheduleJpaController implements Serializable {

    public ScheduleJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Schedule schedule) {
        if (schedule.getTransportInfoCollection() == null) {
            schedule.setTransportInfoCollection(new ArrayList<TransportInfo>());
        }
        if (schedule.getScheduleDayCollection() == null) {
            schedule.setScheduleDayCollection(new ArrayList<ScheduleDay>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<TransportInfo> attachedTransportInfoCollection = new ArrayList<TransportInfo>();
            for (TransportInfo transportInfoCollectionTransportInfoToAttach : schedule.getTransportInfoCollection()) {
                transportInfoCollectionTransportInfoToAttach = em.getReference(transportInfoCollectionTransportInfoToAttach.getClass(), transportInfoCollectionTransportInfoToAttach.getId());
                attachedTransportInfoCollection.add(transportInfoCollectionTransportInfoToAttach);
            }
            schedule.setTransportInfoCollection(attachedTransportInfoCollection);
            Collection<ScheduleDay> attachedScheduleDayCollection = new ArrayList<ScheduleDay>();
            for (ScheduleDay scheduleDayCollectionScheduleDayToAttach : schedule.getScheduleDayCollection()) {
                scheduleDayCollectionScheduleDayToAttach = em.getReference(scheduleDayCollectionScheduleDayToAttach.getClass(), scheduleDayCollectionScheduleDayToAttach.getId());
                attachedScheduleDayCollection.add(scheduleDayCollectionScheduleDayToAttach);
            }
            schedule.setScheduleDayCollection(attachedScheduleDayCollection);
            em.persist(schedule);
            for (TransportInfo transportInfoCollectionTransportInfo : schedule.getTransportInfoCollection()) {
                Schedule oldScheduleIdOfTransportInfoCollectionTransportInfo = transportInfoCollectionTransportInfo.getScheduleId();
                transportInfoCollectionTransportInfo.setScheduleId(schedule);
                transportInfoCollectionTransportInfo = em.merge(transportInfoCollectionTransportInfo);
                if (oldScheduleIdOfTransportInfoCollectionTransportInfo != null) {
                    oldScheduleIdOfTransportInfoCollectionTransportInfo.getTransportInfoCollection().remove(transportInfoCollectionTransportInfo);
                    oldScheduleIdOfTransportInfoCollectionTransportInfo = em.merge(oldScheduleIdOfTransportInfoCollectionTransportInfo);
                }
            }
            for (ScheduleDay scheduleDayCollectionScheduleDay : schedule.getScheduleDayCollection()) {
                Schedule oldScheduleIdOfScheduleDayCollectionScheduleDay = scheduleDayCollectionScheduleDay.getScheduleId();
                scheduleDayCollectionScheduleDay.setScheduleId(schedule);
                scheduleDayCollectionScheduleDay = em.merge(scheduleDayCollectionScheduleDay);
                if (oldScheduleIdOfScheduleDayCollectionScheduleDay != null) {
                    oldScheduleIdOfScheduleDayCollectionScheduleDay.getScheduleDayCollection().remove(scheduleDayCollectionScheduleDay);
                    oldScheduleIdOfScheduleDayCollectionScheduleDay = em.merge(oldScheduleIdOfScheduleDayCollectionScheduleDay);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Schedule schedule) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Schedule persistentSchedule = em.find(Schedule.class, schedule.getId());
            Collection<TransportInfo> transportInfoCollectionOld = persistentSchedule.getTransportInfoCollection();
            Collection<TransportInfo> transportInfoCollectionNew = schedule.getTransportInfoCollection();
            Collection<ScheduleDay> scheduleDayCollectionOld = persistentSchedule.getScheduleDayCollection();
            Collection<ScheduleDay> scheduleDayCollectionNew = schedule.getScheduleDayCollection();
            List<String> illegalOrphanMessages = null;
            for (ScheduleDay scheduleDayCollectionOldScheduleDay : scheduleDayCollectionOld) {
                if (!scheduleDayCollectionNew.contains(scheduleDayCollectionOldScheduleDay)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain ScheduleDay " + scheduleDayCollectionOldScheduleDay + " since its scheduleId field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<TransportInfo> attachedTransportInfoCollectionNew = new ArrayList<TransportInfo>();
            for (TransportInfo transportInfoCollectionNewTransportInfoToAttach : transportInfoCollectionNew) {
                transportInfoCollectionNewTransportInfoToAttach = em.getReference(transportInfoCollectionNewTransportInfoToAttach.getClass(), transportInfoCollectionNewTransportInfoToAttach.getId());
                attachedTransportInfoCollectionNew.add(transportInfoCollectionNewTransportInfoToAttach);
            }
            transportInfoCollectionNew = attachedTransportInfoCollectionNew;
            schedule.setTransportInfoCollection(transportInfoCollectionNew);
            Collection<ScheduleDay> attachedScheduleDayCollectionNew = new ArrayList<ScheduleDay>();
            for (ScheduleDay scheduleDayCollectionNewScheduleDayToAttach : scheduleDayCollectionNew) {
                scheduleDayCollectionNewScheduleDayToAttach = em.getReference(scheduleDayCollectionNewScheduleDayToAttach.getClass(), scheduleDayCollectionNewScheduleDayToAttach.getId());
                attachedScheduleDayCollectionNew.add(scheduleDayCollectionNewScheduleDayToAttach);
            }
            scheduleDayCollectionNew = attachedScheduleDayCollectionNew;
            schedule.setScheduleDayCollection(scheduleDayCollectionNew);
            schedule = em.merge(schedule);
            for (TransportInfo transportInfoCollectionOldTransportInfo : transportInfoCollectionOld) {
                if (!transportInfoCollectionNew.contains(transportInfoCollectionOldTransportInfo)) {
                    transportInfoCollectionOldTransportInfo.setScheduleId(null);
                    transportInfoCollectionOldTransportInfo = em.merge(transportInfoCollectionOldTransportInfo);
                }
            }
            for (TransportInfo transportInfoCollectionNewTransportInfo : transportInfoCollectionNew) {
                if (!transportInfoCollectionOld.contains(transportInfoCollectionNewTransportInfo)) {
                    Schedule oldScheduleIdOfTransportInfoCollectionNewTransportInfo = transportInfoCollectionNewTransportInfo.getScheduleId();
                    transportInfoCollectionNewTransportInfo.setScheduleId(schedule);
                    transportInfoCollectionNewTransportInfo = em.merge(transportInfoCollectionNewTransportInfo);
                    if (oldScheduleIdOfTransportInfoCollectionNewTransportInfo != null && !oldScheduleIdOfTransportInfoCollectionNewTransportInfo.equals(schedule)) {
                        oldScheduleIdOfTransportInfoCollectionNewTransportInfo.getTransportInfoCollection().remove(transportInfoCollectionNewTransportInfo);
                        oldScheduleIdOfTransportInfoCollectionNewTransportInfo = em.merge(oldScheduleIdOfTransportInfoCollectionNewTransportInfo);
                    }
                }
            }
            for (ScheduleDay scheduleDayCollectionNewScheduleDay : scheduleDayCollectionNew) {
                if (!scheduleDayCollectionOld.contains(scheduleDayCollectionNewScheduleDay)) {
                    Schedule oldScheduleIdOfScheduleDayCollectionNewScheduleDay = scheduleDayCollectionNewScheduleDay.getScheduleId();
                    scheduleDayCollectionNewScheduleDay.setScheduleId(schedule);
                    scheduleDayCollectionNewScheduleDay = em.merge(scheduleDayCollectionNewScheduleDay);
                    if (oldScheduleIdOfScheduleDayCollectionNewScheduleDay != null && !oldScheduleIdOfScheduleDayCollectionNewScheduleDay.equals(schedule)) {
                        oldScheduleIdOfScheduleDayCollectionNewScheduleDay.getScheduleDayCollection().remove(scheduleDayCollectionNewScheduleDay);
                        oldScheduleIdOfScheduleDayCollectionNewScheduleDay = em.merge(oldScheduleIdOfScheduleDayCollectionNewScheduleDay);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = schedule.getId();
                if (findSchedule(id) == null) {
                    throw new NonexistentEntityException("The schedule with id " + id + " no longer exists.");
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
            Schedule schedule;
            try {
                schedule = em.getReference(Schedule.class, id);
                schedule.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The schedule with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<ScheduleDay> scheduleDayCollectionOrphanCheck = schedule.getScheduleDayCollection();
            for (ScheduleDay scheduleDayCollectionOrphanCheckScheduleDay : scheduleDayCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Schedule (" + schedule + ") cannot be destroyed since the ScheduleDay " + scheduleDayCollectionOrphanCheckScheduleDay + " in its scheduleDayCollection field has a non-nullable scheduleId field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<TransportInfo> transportInfoCollection = schedule.getTransportInfoCollection();
            for (TransportInfo transportInfoCollectionTransportInfo : transportInfoCollection) {
                transportInfoCollectionTransportInfo.setScheduleId(null);
                transportInfoCollectionTransportInfo = em.merge(transportInfoCollectionTransportInfo);
            }
            em.remove(schedule);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Schedule> findScheduleEntities() {
        return findScheduleEntities(true, -1, -1);
    }

    public List<Schedule> findScheduleEntities(int maxResults, int firstResult) {
        return findScheduleEntities(false, maxResults, firstResult);
    }

    private List<Schedule> findScheduleEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Schedule.class));
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

    public Schedule findSchedule(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Schedule.class, id);
        } finally {
            em.close();
        }
    }

    public int getScheduleCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Schedule> rt = cq.from(Schedule.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
