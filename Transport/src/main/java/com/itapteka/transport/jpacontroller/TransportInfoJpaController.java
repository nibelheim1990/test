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
import com.itapteka.transport.dbcontroller.TransportClientInfo;
import com.itapteka.transport.dbcontroller.Client;
import com.itapteka.transport.dbcontroller.Schedule;
import com.itapteka.transport.dbcontroller.TransportInfo;
import java.util.ArrayList;
import java.util.Collection;
import com.itapteka.transport.dbcontroller.HttpInfo;
import com.itapteka.transport.jpacontroller.exceptions.IllegalOrphanException;
import com.itapteka.transport.jpacontroller.exceptions.NonexistentEntityException;
import com.itapteka.transport.jpacontroller.exceptions.PreexistingEntityException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Ponomarev
 */
public class TransportInfoJpaController implements Serializable {

    public TransportInfoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(TransportInfo transportInfo) throws IllegalOrphanException, PreexistingEntityException, Exception {
        if (transportInfo.getTransportInfoCollection() == null) {
            transportInfo.setTransportInfoCollection(new ArrayList<TransportInfo>());
        }
        if (transportInfo.getHttpInfoCollection() == null) {
            transportInfo.setHttpInfoCollection(new ArrayList<HttpInfo>());
        }
        List<String> illegalOrphanMessages = null;
        Client clientOrphanCheck = transportInfo.getClient();
        if (clientOrphanCheck != null) {
            TransportInfo oldTransportInfoOfClient = clientOrphanCheck.getTransportInfo();
            if (oldTransportInfoOfClient != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("The Client " + clientOrphanCheck + " already has an item of type TransportInfo whose client column cannot be null. Please make another selection for the client field.");
            }
        }
        if (illegalOrphanMessages != null) {
            throw new IllegalOrphanException(illegalOrphanMessages);
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            TransportClientInfo transportClientInfo = transportInfo.getTransportClientInfo();
            if (transportClientInfo != null) {
                transportClientInfo = em.getReference(transportClientInfo.getClass(), transportClientInfo.getId());
                transportInfo.setTransportClientInfo(transportClientInfo);
            }
            Client client = transportInfo.getClient();
            if (client != null) {
                client = em.getReference(client.getClass(), client.getId());
                transportInfo.setClient(client);
            }
            Schedule scheduleId = transportInfo.getScheduleId();
            if (scheduleId != null) {
                scheduleId = em.getReference(scheduleId.getClass(), scheduleId.getId());
                transportInfo.setScheduleId(scheduleId);
            }
            TransportInfo officeId = transportInfo.getOfficeId();
            if (officeId != null) {
                officeId = em.getReference(officeId.getClass(), officeId.getId());
                transportInfo.setOfficeId(officeId);
            }
            Collection<TransportInfo> attachedTransportInfoCollection = new ArrayList<TransportInfo>();
            for (TransportInfo transportInfoCollectionTransportInfoToAttach : transportInfo.getTransportInfoCollection()) {
                transportInfoCollectionTransportInfoToAttach = em.getReference(transportInfoCollectionTransportInfoToAttach.getClass(), transportInfoCollectionTransportInfoToAttach.getId());
                attachedTransportInfoCollection.add(transportInfoCollectionTransportInfoToAttach);
            }
            transportInfo.setTransportInfoCollection(attachedTransportInfoCollection);
            Collection<HttpInfo> attachedHttpInfoCollection = new ArrayList<HttpInfo>();
            for (HttpInfo httpInfoCollectionHttpInfoToAttach : transportInfo.getHttpInfoCollection()) {
                httpInfoCollectionHttpInfoToAttach = em.getReference(httpInfoCollectionHttpInfoToAttach.getClass(), httpInfoCollectionHttpInfoToAttach.getId());
                attachedHttpInfoCollection.add(httpInfoCollectionHttpInfoToAttach);
            }
            transportInfo.setHttpInfoCollection(attachedHttpInfoCollection);
            em.persist(transportInfo);
            if (transportClientInfo != null) {
                TransportInfo oldTransportInfoOfTransportClientInfo = transportClientInfo.getTransportInfo();
                if (oldTransportInfoOfTransportClientInfo != null) {
                    oldTransportInfoOfTransportClientInfo.setTransportClientInfo(null);
                    oldTransportInfoOfTransportClientInfo = em.merge(oldTransportInfoOfTransportClientInfo);
                }
                transportClientInfo.setTransportInfo(transportInfo);
                transportClientInfo = em.merge(transportClientInfo);
            }
            if (client != null) {
                client.setTransportInfo(transportInfo);
                client = em.merge(client);
            }
            if (scheduleId != null) {
                scheduleId.getTransportInfoCollection().add(transportInfo);
                scheduleId = em.merge(scheduleId);
            }
            if (officeId != null) {
                officeId.getTransportInfoCollection().add(transportInfo);
                officeId = em.merge(officeId);
            }
            for (TransportInfo transportInfoCollectionTransportInfo : transportInfo.getTransportInfoCollection()) {
                TransportInfo oldOfficeIdOfTransportInfoCollectionTransportInfo = transportInfoCollectionTransportInfo.getOfficeId();
                transportInfoCollectionTransportInfo.setOfficeId(transportInfo);
                transportInfoCollectionTransportInfo = em.merge(transportInfoCollectionTransportInfo);
                if (oldOfficeIdOfTransportInfoCollectionTransportInfo != null) {
                    oldOfficeIdOfTransportInfoCollectionTransportInfo.getTransportInfoCollection().remove(transportInfoCollectionTransportInfo);
                    oldOfficeIdOfTransportInfoCollectionTransportInfo = em.merge(oldOfficeIdOfTransportInfoCollectionTransportInfo);
                }
            }
            for (HttpInfo httpInfoCollectionHttpInfo : transportInfo.getHttpInfoCollection()) {
                TransportInfo oldTransportInfoIdOfHttpInfoCollectionHttpInfo = httpInfoCollectionHttpInfo.getTransportInfoId();
                httpInfoCollectionHttpInfo.setTransportInfoId(transportInfo);
                httpInfoCollectionHttpInfo = em.merge(httpInfoCollectionHttpInfo);
                if (oldTransportInfoIdOfHttpInfoCollectionHttpInfo != null) {
                    oldTransportInfoIdOfHttpInfoCollectionHttpInfo.getHttpInfoCollection().remove(httpInfoCollectionHttpInfo);
                    oldTransportInfoIdOfHttpInfoCollectionHttpInfo = em.merge(oldTransportInfoIdOfHttpInfoCollectionHttpInfo);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findTransportInfo(transportInfo.getId()) != null) {
                throw new PreexistingEntityException("TransportInfo " + transportInfo + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(TransportInfo transportInfo) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            TransportInfo persistentTransportInfo = em.find(TransportInfo.class, transportInfo.getId());
            TransportClientInfo transportClientInfoOld = persistentTransportInfo.getTransportClientInfo();
            TransportClientInfo transportClientInfoNew = transportInfo.getTransportClientInfo();
            Client clientOld = persistentTransportInfo.getClient();
            Client clientNew = transportInfo.getClient();
            Schedule scheduleIdOld = persistentTransportInfo.getScheduleId();
            Schedule scheduleIdNew = transportInfo.getScheduleId();
            TransportInfo officeIdOld = persistentTransportInfo.getOfficeId();
            TransportInfo officeIdNew = transportInfo.getOfficeId();
            Collection<TransportInfo> transportInfoCollectionOld = persistentTransportInfo.getTransportInfoCollection();
            Collection<TransportInfo> transportInfoCollectionNew = transportInfo.getTransportInfoCollection();
            Collection<HttpInfo> httpInfoCollectionOld = persistentTransportInfo.getHttpInfoCollection();
            Collection<HttpInfo> httpInfoCollectionNew = transportInfo.getHttpInfoCollection();
            List<String> illegalOrphanMessages = null;
            if (transportClientInfoOld != null && !transportClientInfoOld.equals(transportClientInfoNew)) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("You must retain TransportClientInfo " + transportClientInfoOld + " since its transportInfo field is not nullable.");
            }
            if (clientNew != null && !clientNew.equals(clientOld)) {
                TransportInfo oldTransportInfoOfClient = clientNew.getTransportInfo();
                if (oldTransportInfoOfClient != null) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("The Client " + clientNew + " already has an item of type TransportInfo whose client column cannot be null. Please make another selection for the client field.");
                }
            }
            for (HttpInfo httpInfoCollectionOldHttpInfo : httpInfoCollectionOld) {
                if (!httpInfoCollectionNew.contains(httpInfoCollectionOldHttpInfo)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain HttpInfo " + httpInfoCollectionOldHttpInfo + " since its transportInfoId field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (transportClientInfoNew != null) {
                transportClientInfoNew = em.getReference(transportClientInfoNew.getClass(), transportClientInfoNew.getId());
                transportInfo.setTransportClientInfo(transportClientInfoNew);
            }
            if (clientNew != null) {
                clientNew = em.getReference(clientNew.getClass(), clientNew.getId());
                transportInfo.setClient(clientNew);
            }
            if (scheduleIdNew != null) {
                scheduleIdNew = em.getReference(scheduleIdNew.getClass(), scheduleIdNew.getId());
                transportInfo.setScheduleId(scheduleIdNew);
            }
            if (officeIdNew != null) {
                officeIdNew = em.getReference(officeIdNew.getClass(), officeIdNew.getId());
                transportInfo.setOfficeId(officeIdNew);
            }
            Collection<TransportInfo> attachedTransportInfoCollectionNew = new ArrayList<TransportInfo>();
            for (TransportInfo transportInfoCollectionNewTransportInfoToAttach : transportInfoCollectionNew) {
                transportInfoCollectionNewTransportInfoToAttach = em.getReference(transportInfoCollectionNewTransportInfoToAttach.getClass(), transportInfoCollectionNewTransportInfoToAttach.getId());
                attachedTransportInfoCollectionNew.add(transportInfoCollectionNewTransportInfoToAttach);
            }
            transportInfoCollectionNew = attachedTransportInfoCollectionNew;
            transportInfo.setTransportInfoCollection(transportInfoCollectionNew);
            Collection<HttpInfo> attachedHttpInfoCollectionNew = new ArrayList<HttpInfo>();
            for (HttpInfo httpInfoCollectionNewHttpInfoToAttach : httpInfoCollectionNew) {
                httpInfoCollectionNewHttpInfoToAttach = em.getReference(httpInfoCollectionNewHttpInfoToAttach.getClass(), httpInfoCollectionNewHttpInfoToAttach.getId());
                attachedHttpInfoCollectionNew.add(httpInfoCollectionNewHttpInfoToAttach);
            }
            httpInfoCollectionNew = attachedHttpInfoCollectionNew;
            transportInfo.setHttpInfoCollection(httpInfoCollectionNew);
            transportInfo = em.merge(transportInfo);
            if (transportClientInfoNew != null && !transportClientInfoNew.equals(transportClientInfoOld)) {
                TransportInfo oldTransportInfoOfTransportClientInfo = transportClientInfoNew.getTransportInfo();
                if (oldTransportInfoOfTransportClientInfo != null) {
                    oldTransportInfoOfTransportClientInfo.setTransportClientInfo(null);
                    oldTransportInfoOfTransportClientInfo = em.merge(oldTransportInfoOfTransportClientInfo);
                }
                transportClientInfoNew.setTransportInfo(transportInfo);
                transportClientInfoNew = em.merge(transportClientInfoNew);
            }
            if (clientOld != null && !clientOld.equals(clientNew)) {
                clientOld.setTransportInfo(null);
                clientOld = em.merge(clientOld);
            }
            if (clientNew != null && !clientNew.equals(clientOld)) {
                clientNew.setTransportInfo(transportInfo);
                clientNew = em.merge(clientNew);
            }
            if (scheduleIdOld != null && !scheduleIdOld.equals(scheduleIdNew)) {
                scheduleIdOld.getTransportInfoCollection().remove(transportInfo);
                scheduleIdOld = em.merge(scheduleIdOld);
            }
            if (scheduleIdNew != null && !scheduleIdNew.equals(scheduleIdOld)) {
                scheduleIdNew.getTransportInfoCollection().add(transportInfo);
                scheduleIdNew = em.merge(scheduleIdNew);
            }
            if (officeIdOld != null && !officeIdOld.equals(officeIdNew)) {
                officeIdOld.getTransportInfoCollection().remove(transportInfo);
                officeIdOld = em.merge(officeIdOld);
            }
            if (officeIdNew != null && !officeIdNew.equals(officeIdOld)) {
                officeIdNew.getTransportInfoCollection().add(transportInfo);
                officeIdNew = em.merge(officeIdNew);
            }
            for (TransportInfo transportInfoCollectionOldTransportInfo : transportInfoCollectionOld) {
                if (!transportInfoCollectionNew.contains(transportInfoCollectionOldTransportInfo)) {
                    transportInfoCollectionOldTransportInfo.setOfficeId(null);
                    transportInfoCollectionOldTransportInfo = em.merge(transportInfoCollectionOldTransportInfo);
                }
            }
            for (TransportInfo transportInfoCollectionNewTransportInfo : transportInfoCollectionNew) {
                if (!transportInfoCollectionOld.contains(transportInfoCollectionNewTransportInfo)) {
                    TransportInfo oldOfficeIdOfTransportInfoCollectionNewTransportInfo = transportInfoCollectionNewTransportInfo.getOfficeId();
                    transportInfoCollectionNewTransportInfo.setOfficeId(transportInfo);
                    transportInfoCollectionNewTransportInfo = em.merge(transportInfoCollectionNewTransportInfo);
                    if (oldOfficeIdOfTransportInfoCollectionNewTransportInfo != null && !oldOfficeIdOfTransportInfoCollectionNewTransportInfo.equals(transportInfo)) {
                        oldOfficeIdOfTransportInfoCollectionNewTransportInfo.getTransportInfoCollection().remove(transportInfoCollectionNewTransportInfo);
                        oldOfficeIdOfTransportInfoCollectionNewTransportInfo = em.merge(oldOfficeIdOfTransportInfoCollectionNewTransportInfo);
                    }
                }
            }
            for (HttpInfo httpInfoCollectionNewHttpInfo : httpInfoCollectionNew) {
                if (!httpInfoCollectionOld.contains(httpInfoCollectionNewHttpInfo)) {
                    TransportInfo oldTransportInfoIdOfHttpInfoCollectionNewHttpInfo = httpInfoCollectionNewHttpInfo.getTransportInfoId();
                    httpInfoCollectionNewHttpInfo.setTransportInfoId(transportInfo);
                    httpInfoCollectionNewHttpInfo = em.merge(httpInfoCollectionNewHttpInfo);
                    if (oldTransportInfoIdOfHttpInfoCollectionNewHttpInfo != null && !oldTransportInfoIdOfHttpInfoCollectionNewHttpInfo.equals(transportInfo)) {
                        oldTransportInfoIdOfHttpInfoCollectionNewHttpInfo.getHttpInfoCollection().remove(httpInfoCollectionNewHttpInfo);
                        oldTransportInfoIdOfHttpInfoCollectionNewHttpInfo = em.merge(oldTransportInfoIdOfHttpInfoCollectionNewHttpInfo);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = transportInfo.getId();
                if (findTransportInfo(id) == null) {
                    throw new NonexistentEntityException("The transportInfo with id " + id + " no longer exists.");
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
            TransportInfo transportInfo;
            try {
                transportInfo = em.getReference(TransportInfo.class, id);
                transportInfo.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The transportInfo with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            TransportClientInfo transportClientInfoOrphanCheck = transportInfo.getTransportClientInfo();
            if (transportClientInfoOrphanCheck != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This TransportInfo (" + transportInfo + ") cannot be destroyed since the TransportClientInfo " + transportClientInfoOrphanCheck + " in its transportClientInfo field has a non-nullable transportInfo field.");
            }
            Collection<HttpInfo> httpInfoCollectionOrphanCheck = transportInfo.getHttpInfoCollection();
            for (HttpInfo httpInfoCollectionOrphanCheckHttpInfo : httpInfoCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This TransportInfo (" + transportInfo + ") cannot be destroyed since the HttpInfo " + httpInfoCollectionOrphanCheckHttpInfo + " in its httpInfoCollection field has a non-nullable transportInfoId field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Client client = transportInfo.getClient();
            if (client != null) {
                client.setTransportInfo(null);
                client = em.merge(client);
            }
            Schedule scheduleId = transportInfo.getScheduleId();
            if (scheduleId != null) {
                scheduleId.getTransportInfoCollection().remove(transportInfo);
                scheduleId = em.merge(scheduleId);
            }
            TransportInfo officeId = transportInfo.getOfficeId();
            if (officeId != null) {
                officeId.getTransportInfoCollection().remove(transportInfo);
                officeId = em.merge(officeId);
            }
            Collection<TransportInfo> transportInfoCollection = transportInfo.getTransportInfoCollection();
            for (TransportInfo transportInfoCollectionTransportInfo : transportInfoCollection) {
                transportInfoCollectionTransportInfo.setOfficeId(null);
                transportInfoCollectionTransportInfo = em.merge(transportInfoCollectionTransportInfo);
            }
            em.remove(transportInfo);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<TransportInfo> findTransportInfoEntities() {
        return findTransportInfoEntities(true, -1, -1);
    }

    public List<TransportInfo> findTransportInfoEntities(int maxResults, int firstResult) {
        return findTransportInfoEntities(false, maxResults, firstResult);
    }

    private List<TransportInfo> findTransportInfoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(TransportInfo.class));
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

    public TransportInfo findTransportInfo(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(TransportInfo.class, id);
        } finally {
            em.close();
        }
    }

    public int getTransportInfoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<TransportInfo> rt = cq.from(TransportInfo.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
