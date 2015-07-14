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
import com.itapteka.transport.dbcontroller.HttpInfo;
import com.itapteka.transport.dbcontroller.ServerClientConfig;
import com.itapteka.transport.jpacontroller.exceptions.IllegalOrphanException;
import com.itapteka.transport.jpacontroller.exceptions.NonexistentEntityException;
import com.itapteka.transport.jpacontroller.exceptions.PreexistingEntityException;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Ponomarev
 */
public class ServerClientConfigJpaController implements Serializable {

    public ServerClientConfigJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(ServerClientConfig serverClientConfig) throws IllegalOrphanException, PreexistingEntityException, Exception {
        List<String> illegalOrphanMessages = null;
        HttpInfo httpInfoOrphanCheck = serverClientConfig.getHttpInfo();
        if (httpInfoOrphanCheck != null) {
            ServerClientConfig oldServerClientConfigOfHttpInfo = httpInfoOrphanCheck.getServerClientConfig();
            if (oldServerClientConfigOfHttpInfo != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("The HttpInfo " + httpInfoOrphanCheck + " already has an item of type ServerClientConfig whose httpInfo column cannot be null. Please make another selection for the httpInfo field.");
            }
        }
        if (illegalOrphanMessages != null) {
            throw new IllegalOrphanException(illegalOrphanMessages);
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            HttpInfo httpInfo = serverClientConfig.getHttpInfo();
            if (httpInfo != null) {
                httpInfo = em.getReference(httpInfo.getClass(), httpInfo.getId());
                serverClientConfig.setHttpInfo(httpInfo);
            }
            em.persist(serverClientConfig);
            if (httpInfo != null) {
                httpInfo.setServerClientConfig(serverClientConfig);
                httpInfo = em.merge(httpInfo);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findServerClientConfig(serverClientConfig.getId()) != null) {
                throw new PreexistingEntityException("ServerClientConfig " + serverClientConfig + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(ServerClientConfig serverClientConfig) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            ServerClientConfig persistentServerClientConfig = em.find(ServerClientConfig.class, serverClientConfig.getId());
            HttpInfo httpInfoOld = persistentServerClientConfig.getHttpInfo();
            HttpInfo httpInfoNew = serverClientConfig.getHttpInfo();
            List<String> illegalOrphanMessages = null;
            if (httpInfoNew != null && !httpInfoNew.equals(httpInfoOld)) {
                ServerClientConfig oldServerClientConfigOfHttpInfo = httpInfoNew.getServerClientConfig();
                if (oldServerClientConfigOfHttpInfo != null) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("The HttpInfo " + httpInfoNew + " already has an item of type ServerClientConfig whose httpInfo column cannot be null. Please make another selection for the httpInfo field.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (httpInfoNew != null) {
                httpInfoNew = em.getReference(httpInfoNew.getClass(), httpInfoNew.getId());
                serverClientConfig.setHttpInfo(httpInfoNew);
            }
            serverClientConfig = em.merge(serverClientConfig);
            if (httpInfoOld != null && !httpInfoOld.equals(httpInfoNew)) {
                httpInfoOld.setServerClientConfig(null);
                httpInfoOld = em.merge(httpInfoOld);
            }
            if (httpInfoNew != null && !httpInfoNew.equals(httpInfoOld)) {
                httpInfoNew.setServerClientConfig(serverClientConfig);
                httpInfoNew = em.merge(httpInfoNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = serverClientConfig.getId();
                if (findServerClientConfig(id) == null) {
                    throw new NonexistentEntityException("The serverClientConfig with id " + id + " no longer exists.");
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
            ServerClientConfig serverClientConfig;
            try {
                serverClientConfig = em.getReference(ServerClientConfig.class, id);
                serverClientConfig.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The serverClientConfig with id " + id + " no longer exists.", enfe);
            }
            HttpInfo httpInfo = serverClientConfig.getHttpInfo();
            if (httpInfo != null) {
                httpInfo.setServerClientConfig(null);
                httpInfo = em.merge(httpInfo);
            }
            em.remove(serverClientConfig);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<ServerClientConfig> findServerClientConfigEntities() {
        return findServerClientConfigEntities(true, -1, -1);
    }

    public List<ServerClientConfig> findServerClientConfigEntities(int maxResults, int firstResult) {
        return findServerClientConfigEntities(false, maxResults, firstResult);
    }

    private List<ServerClientConfig> findServerClientConfigEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(ServerClientConfig.class));
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

    public ServerClientConfig findServerClientConfig(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(ServerClientConfig.class, id);
        } finally {
            em.close();
        }
    }

    public int getServerClientConfigCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<ServerClientConfig> rt = cq.from(ServerClientConfig.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
