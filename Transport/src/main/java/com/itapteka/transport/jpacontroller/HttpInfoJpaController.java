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
import com.itapteka.transport.dbcontroller.TransportInfo;
import com.itapteka.transport.dbcontroller.ServerClientConfig;
import com.itapteka.transport.dbcontroller.ClientSettings;
import java.util.ArrayList;
import java.util.Collection;
import com.itapteka.transport.dbcontroller.ClientLog;
import com.itapteka.transport.dbcontroller.ClientTraffic;
import com.itapteka.transport.dbcontroller.HttpInfo;
import com.itapteka.transport.jpacontroller.exceptions.IllegalOrphanException;
import com.itapteka.transport.jpacontroller.exceptions.NonexistentEntityException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Ponomarev
 */
public class HttpInfoJpaController implements Serializable {

    public HttpInfoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(HttpInfo httpInfo) {
        if (httpInfo.getClientSettingsCollection() == null) {
            httpInfo.setClientSettingsCollection(new ArrayList<ClientSettings>());
        }
        if (httpInfo.getClientLogCollection() == null) {
            httpInfo.setClientLogCollection(new ArrayList<ClientLog>());
        }
        if (httpInfo.getClientTrafficCollection() == null) {
            httpInfo.setClientTrafficCollection(new ArrayList<ClientTraffic>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            TransportInfo transportInfoId = httpInfo.getTransportInfoId();
            if (transportInfoId != null) {
                transportInfoId = em.getReference(transportInfoId.getClass(), transportInfoId.getId());
                httpInfo.setTransportInfoId(transportInfoId);
            }
            ServerClientConfig serverClientConfig = httpInfo.getServerClientConfig();
            if (serverClientConfig != null) {
                serverClientConfig = em.getReference(serverClientConfig.getClass(), serverClientConfig.getId());
                httpInfo.setServerClientConfig(serverClientConfig);
            }
            Collection<ClientSettings> attachedClientSettingsCollection = new ArrayList<ClientSettings>();
            for (ClientSettings clientSettingsCollectionClientSettingsToAttach : httpInfo.getClientSettingsCollection()) {
                clientSettingsCollectionClientSettingsToAttach = em.getReference(clientSettingsCollectionClientSettingsToAttach.getClass(), clientSettingsCollectionClientSettingsToAttach.getId());
                attachedClientSettingsCollection.add(clientSettingsCollectionClientSettingsToAttach);
            }
            httpInfo.setClientSettingsCollection(attachedClientSettingsCollection);
            Collection<ClientLog> attachedClientLogCollection = new ArrayList<ClientLog>();
            for (ClientLog clientLogCollectionClientLogToAttach : httpInfo.getClientLogCollection()) {
                clientLogCollectionClientLogToAttach = em.getReference(clientLogCollectionClientLogToAttach.getClass(), clientLogCollectionClientLogToAttach.getId());
                attachedClientLogCollection.add(clientLogCollectionClientLogToAttach);
            }
            httpInfo.setClientLogCollection(attachedClientLogCollection);
            Collection<ClientTraffic> attachedClientTrafficCollection = new ArrayList<ClientTraffic>();
            for (ClientTraffic clientTrafficCollectionClientTrafficToAttach : httpInfo.getClientTrafficCollection()) {
                clientTrafficCollectionClientTrafficToAttach = em.getReference(clientTrafficCollectionClientTrafficToAttach.getClass(), clientTrafficCollectionClientTrafficToAttach.getId());
                attachedClientTrafficCollection.add(clientTrafficCollectionClientTrafficToAttach);
            }
            httpInfo.setClientTrafficCollection(attachedClientTrafficCollection);
            em.persist(httpInfo);
            if (transportInfoId != null) {
                transportInfoId.getHttpInfoCollection().add(httpInfo);
                transportInfoId = em.merge(transportInfoId);
            }
            if (serverClientConfig != null) {
                HttpInfo oldHttpInfoOfServerClientConfig = serverClientConfig.getHttpInfo();
                if (oldHttpInfoOfServerClientConfig != null) {
                    oldHttpInfoOfServerClientConfig.setServerClientConfig(null);
                    oldHttpInfoOfServerClientConfig = em.merge(oldHttpInfoOfServerClientConfig);
                }
                serverClientConfig.setHttpInfo(httpInfo);
                serverClientConfig = em.merge(serverClientConfig);
            }
            for (ClientSettings clientSettingsCollectionClientSettings : httpInfo.getClientSettingsCollection()) {
                HttpInfo oldHttpInfoIdOfClientSettingsCollectionClientSettings = clientSettingsCollectionClientSettings.getHttpInfoId();
                clientSettingsCollectionClientSettings.setHttpInfoId(httpInfo);
                clientSettingsCollectionClientSettings = em.merge(clientSettingsCollectionClientSettings);
                if (oldHttpInfoIdOfClientSettingsCollectionClientSettings != null) {
                    oldHttpInfoIdOfClientSettingsCollectionClientSettings.getClientSettingsCollection().remove(clientSettingsCollectionClientSettings);
                    oldHttpInfoIdOfClientSettingsCollectionClientSettings = em.merge(oldHttpInfoIdOfClientSettingsCollectionClientSettings);
                }
            }
            for (ClientLog clientLogCollectionClientLog : httpInfo.getClientLogCollection()) {
                HttpInfo oldHttpInfoIdOfClientLogCollectionClientLog = clientLogCollectionClientLog.getHttpInfoId();
                clientLogCollectionClientLog.setHttpInfoId(httpInfo);
                clientLogCollectionClientLog = em.merge(clientLogCollectionClientLog);
                if (oldHttpInfoIdOfClientLogCollectionClientLog != null) {
                    oldHttpInfoIdOfClientLogCollectionClientLog.getClientLogCollection().remove(clientLogCollectionClientLog);
                    oldHttpInfoIdOfClientLogCollectionClientLog = em.merge(oldHttpInfoIdOfClientLogCollectionClientLog);
                }
            }
            for (ClientTraffic clientTrafficCollectionClientTraffic : httpInfo.getClientTrafficCollection()) {
                HttpInfo oldHttpInfoIdOfClientTrafficCollectionClientTraffic = clientTrafficCollectionClientTraffic.getHttpInfoId();
                clientTrafficCollectionClientTraffic.setHttpInfoId(httpInfo);
                clientTrafficCollectionClientTraffic = em.merge(clientTrafficCollectionClientTraffic);
                if (oldHttpInfoIdOfClientTrafficCollectionClientTraffic != null) {
                    oldHttpInfoIdOfClientTrafficCollectionClientTraffic.getClientTrafficCollection().remove(clientTrafficCollectionClientTraffic);
                    oldHttpInfoIdOfClientTrafficCollectionClientTraffic = em.merge(oldHttpInfoIdOfClientTrafficCollectionClientTraffic);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(HttpInfo httpInfo) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            HttpInfo persistentHttpInfo = em.find(HttpInfo.class, httpInfo.getId());
            TransportInfo transportInfoIdOld = persistentHttpInfo.getTransportInfoId();
            TransportInfo transportInfoIdNew = httpInfo.getTransportInfoId();
            ServerClientConfig serverClientConfigOld = persistentHttpInfo.getServerClientConfig();
            ServerClientConfig serverClientConfigNew = httpInfo.getServerClientConfig();
            Collection<ClientSettings> clientSettingsCollectionOld = persistentHttpInfo.getClientSettingsCollection();
            Collection<ClientSettings> clientSettingsCollectionNew = httpInfo.getClientSettingsCollection();
            Collection<ClientLog> clientLogCollectionOld = persistentHttpInfo.getClientLogCollection();
            Collection<ClientLog> clientLogCollectionNew = httpInfo.getClientLogCollection();
            Collection<ClientTraffic> clientTrafficCollectionOld = persistentHttpInfo.getClientTrafficCollection();
            Collection<ClientTraffic> clientTrafficCollectionNew = httpInfo.getClientTrafficCollection();
            List<String> illegalOrphanMessages = null;
            if (serverClientConfigOld != null && !serverClientConfigOld.equals(serverClientConfigNew)) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("You must retain ServerClientConfig " + serverClientConfigOld + " since its httpInfo field is not nullable.");
            }
            for (ClientSettings clientSettingsCollectionOldClientSettings : clientSettingsCollectionOld) {
                if (!clientSettingsCollectionNew.contains(clientSettingsCollectionOldClientSettings)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain ClientSettings " + clientSettingsCollectionOldClientSettings + " since its httpInfoId field is not nullable.");
                }
            }
            for (ClientLog clientLogCollectionOldClientLog : clientLogCollectionOld) {
                if (!clientLogCollectionNew.contains(clientLogCollectionOldClientLog)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain ClientLog " + clientLogCollectionOldClientLog + " since its httpInfoId field is not nullable.");
                }
            }
            for (ClientTraffic clientTrafficCollectionOldClientTraffic : clientTrafficCollectionOld) {
                if (!clientTrafficCollectionNew.contains(clientTrafficCollectionOldClientTraffic)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain ClientTraffic " + clientTrafficCollectionOldClientTraffic + " since its httpInfoId field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (transportInfoIdNew != null) {
                transportInfoIdNew = em.getReference(transportInfoIdNew.getClass(), transportInfoIdNew.getId());
                httpInfo.setTransportInfoId(transportInfoIdNew);
            }
            if (serverClientConfigNew != null) {
                serverClientConfigNew = em.getReference(serverClientConfigNew.getClass(), serverClientConfigNew.getId());
                httpInfo.setServerClientConfig(serverClientConfigNew);
            }
            Collection<ClientSettings> attachedClientSettingsCollectionNew = new ArrayList<ClientSettings>();
            for (ClientSettings clientSettingsCollectionNewClientSettingsToAttach : clientSettingsCollectionNew) {
                clientSettingsCollectionNewClientSettingsToAttach = em.getReference(clientSettingsCollectionNewClientSettingsToAttach.getClass(), clientSettingsCollectionNewClientSettingsToAttach.getId());
                attachedClientSettingsCollectionNew.add(clientSettingsCollectionNewClientSettingsToAttach);
            }
            clientSettingsCollectionNew = attachedClientSettingsCollectionNew;
            httpInfo.setClientSettingsCollection(clientSettingsCollectionNew);
            Collection<ClientLog> attachedClientLogCollectionNew = new ArrayList<ClientLog>();
            for (ClientLog clientLogCollectionNewClientLogToAttach : clientLogCollectionNew) {
                clientLogCollectionNewClientLogToAttach = em.getReference(clientLogCollectionNewClientLogToAttach.getClass(), clientLogCollectionNewClientLogToAttach.getId());
                attachedClientLogCollectionNew.add(clientLogCollectionNewClientLogToAttach);
            }
            clientLogCollectionNew = attachedClientLogCollectionNew;
            httpInfo.setClientLogCollection(clientLogCollectionNew);
            Collection<ClientTraffic> attachedClientTrafficCollectionNew = new ArrayList<ClientTraffic>();
            for (ClientTraffic clientTrafficCollectionNewClientTrafficToAttach : clientTrafficCollectionNew) {
                clientTrafficCollectionNewClientTrafficToAttach = em.getReference(clientTrafficCollectionNewClientTrafficToAttach.getClass(), clientTrafficCollectionNewClientTrafficToAttach.getId());
                attachedClientTrafficCollectionNew.add(clientTrafficCollectionNewClientTrafficToAttach);
            }
            clientTrafficCollectionNew = attachedClientTrafficCollectionNew;
            httpInfo.setClientTrafficCollection(clientTrafficCollectionNew);
            httpInfo = em.merge(httpInfo);
            if (transportInfoIdOld != null && !transportInfoIdOld.equals(transportInfoIdNew)) {
                transportInfoIdOld.getHttpInfoCollection().remove(httpInfo);
                transportInfoIdOld = em.merge(transportInfoIdOld);
            }
            if (transportInfoIdNew != null && !transportInfoIdNew.equals(transportInfoIdOld)) {
                transportInfoIdNew.getHttpInfoCollection().add(httpInfo);
                transportInfoIdNew = em.merge(transportInfoIdNew);
            }
            if (serverClientConfigNew != null && !serverClientConfigNew.equals(serverClientConfigOld)) {
                HttpInfo oldHttpInfoOfServerClientConfig = serverClientConfigNew.getHttpInfo();
                if (oldHttpInfoOfServerClientConfig != null) {
                    oldHttpInfoOfServerClientConfig.setServerClientConfig(null);
                    oldHttpInfoOfServerClientConfig = em.merge(oldHttpInfoOfServerClientConfig);
                }
                serverClientConfigNew.setHttpInfo(httpInfo);
                serverClientConfigNew = em.merge(serverClientConfigNew);
            }
            for (ClientSettings clientSettingsCollectionNewClientSettings : clientSettingsCollectionNew) {
                if (!clientSettingsCollectionOld.contains(clientSettingsCollectionNewClientSettings)) {
                    HttpInfo oldHttpInfoIdOfClientSettingsCollectionNewClientSettings = clientSettingsCollectionNewClientSettings.getHttpInfoId();
                    clientSettingsCollectionNewClientSettings.setHttpInfoId(httpInfo);
                    clientSettingsCollectionNewClientSettings = em.merge(clientSettingsCollectionNewClientSettings);
                    if (oldHttpInfoIdOfClientSettingsCollectionNewClientSettings != null && !oldHttpInfoIdOfClientSettingsCollectionNewClientSettings.equals(httpInfo)) {
                        oldHttpInfoIdOfClientSettingsCollectionNewClientSettings.getClientSettingsCollection().remove(clientSettingsCollectionNewClientSettings);
                        oldHttpInfoIdOfClientSettingsCollectionNewClientSettings = em.merge(oldHttpInfoIdOfClientSettingsCollectionNewClientSettings);
                    }
                }
            }
            for (ClientLog clientLogCollectionNewClientLog : clientLogCollectionNew) {
                if (!clientLogCollectionOld.contains(clientLogCollectionNewClientLog)) {
                    HttpInfo oldHttpInfoIdOfClientLogCollectionNewClientLog = clientLogCollectionNewClientLog.getHttpInfoId();
                    clientLogCollectionNewClientLog.setHttpInfoId(httpInfo);
                    clientLogCollectionNewClientLog = em.merge(clientLogCollectionNewClientLog);
                    if (oldHttpInfoIdOfClientLogCollectionNewClientLog != null && !oldHttpInfoIdOfClientLogCollectionNewClientLog.equals(httpInfo)) {
                        oldHttpInfoIdOfClientLogCollectionNewClientLog.getClientLogCollection().remove(clientLogCollectionNewClientLog);
                        oldHttpInfoIdOfClientLogCollectionNewClientLog = em.merge(oldHttpInfoIdOfClientLogCollectionNewClientLog);
                    }
                }
            }
            for (ClientTraffic clientTrafficCollectionNewClientTraffic : clientTrafficCollectionNew) {
                if (!clientTrafficCollectionOld.contains(clientTrafficCollectionNewClientTraffic)) {
                    HttpInfo oldHttpInfoIdOfClientTrafficCollectionNewClientTraffic = clientTrafficCollectionNewClientTraffic.getHttpInfoId();
                    clientTrafficCollectionNewClientTraffic.setHttpInfoId(httpInfo);
                    clientTrafficCollectionNewClientTraffic = em.merge(clientTrafficCollectionNewClientTraffic);
                    if (oldHttpInfoIdOfClientTrafficCollectionNewClientTraffic != null && !oldHttpInfoIdOfClientTrafficCollectionNewClientTraffic.equals(httpInfo)) {
                        oldHttpInfoIdOfClientTrafficCollectionNewClientTraffic.getClientTrafficCollection().remove(clientTrafficCollectionNewClientTraffic);
                        oldHttpInfoIdOfClientTrafficCollectionNewClientTraffic = em.merge(oldHttpInfoIdOfClientTrafficCollectionNewClientTraffic);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = httpInfo.getId();
                if (findHttpInfo(id) == null) {
                    throw new NonexistentEntityException("The httpInfo with id " + id + " no longer exists.");
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
            HttpInfo httpInfo;
            try {
                httpInfo = em.getReference(HttpInfo.class, id);
                httpInfo.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The httpInfo with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            ServerClientConfig serverClientConfigOrphanCheck = httpInfo.getServerClientConfig();
            if (serverClientConfigOrphanCheck != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This HttpInfo (" + httpInfo + ") cannot be destroyed since the ServerClientConfig " + serverClientConfigOrphanCheck + " in its serverClientConfig field has a non-nullable httpInfo field.");
            }
            Collection<ClientSettings> clientSettingsCollectionOrphanCheck = httpInfo.getClientSettingsCollection();
            for (ClientSettings clientSettingsCollectionOrphanCheckClientSettings : clientSettingsCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This HttpInfo (" + httpInfo + ") cannot be destroyed since the ClientSettings " + clientSettingsCollectionOrphanCheckClientSettings + " in its clientSettingsCollection field has a non-nullable httpInfoId field.");
            }
            Collection<ClientLog> clientLogCollectionOrphanCheck = httpInfo.getClientLogCollection();
            for (ClientLog clientLogCollectionOrphanCheckClientLog : clientLogCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This HttpInfo (" + httpInfo + ") cannot be destroyed since the ClientLog " + clientLogCollectionOrphanCheckClientLog + " in its clientLogCollection field has a non-nullable httpInfoId field.");
            }
            Collection<ClientTraffic> clientTrafficCollectionOrphanCheck = httpInfo.getClientTrafficCollection();
            for (ClientTraffic clientTrafficCollectionOrphanCheckClientTraffic : clientTrafficCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This HttpInfo (" + httpInfo + ") cannot be destroyed since the ClientTraffic " + clientTrafficCollectionOrphanCheckClientTraffic + " in its clientTrafficCollection field has a non-nullable httpInfoId field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            TransportInfo transportInfoId = httpInfo.getTransportInfoId();
            if (transportInfoId != null) {
                transportInfoId.getHttpInfoCollection().remove(httpInfo);
                transportInfoId = em.merge(transportInfoId);
            }
            em.remove(httpInfo);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<HttpInfo> findHttpInfoEntities() {
        return findHttpInfoEntities(true, -1, -1);
    }

    public List<HttpInfo> findHttpInfoEntities(int maxResults, int firstResult) {
        return findHttpInfoEntities(false, maxResults, firstResult);
    }

    private List<HttpInfo> findHttpInfoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(HttpInfo.class));
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

    public HttpInfo findHttpInfo(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(HttpInfo.class, id);
        } finally {
            em.close();
        }
    }

    public int getHttpInfoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<HttpInfo> rt = cq.from(HttpInfo.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
