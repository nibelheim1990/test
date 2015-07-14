/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.itapteka.transport.jpacontroller;

import com.itapteka.transport.dbcontroller.ClientSettings;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.itapteka.transport.dbcontroller.HttpInfo;
import com.itapteka.transport.jpacontroller.exceptions.NonexistentEntityException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Ponomarev
 */
public class ClientSettingsJpaController implements Serializable {

    public ClientSettingsJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(ClientSettings clientSettings) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            HttpInfo httpInfoId = clientSettings.getHttpInfoId();
            if (httpInfoId != null) {
                httpInfoId = em.getReference(httpInfoId.getClass(), httpInfoId.getId());
                clientSettings.setHttpInfoId(httpInfoId);
            }
            em.persist(clientSettings);
            if (httpInfoId != null) {
                httpInfoId.getClientSettingsCollection().add(clientSettings);
                httpInfoId = em.merge(httpInfoId);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(ClientSettings clientSettings) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            ClientSettings persistentClientSettings = em.find(ClientSettings.class, clientSettings.getId());
            HttpInfo httpInfoIdOld = persistentClientSettings.getHttpInfoId();
            HttpInfo httpInfoIdNew = clientSettings.getHttpInfoId();
            if (httpInfoIdNew != null) {
                httpInfoIdNew = em.getReference(httpInfoIdNew.getClass(), httpInfoIdNew.getId());
                clientSettings.setHttpInfoId(httpInfoIdNew);
            }
            clientSettings = em.merge(clientSettings);
            if (httpInfoIdOld != null && !httpInfoIdOld.equals(httpInfoIdNew)) {
                httpInfoIdOld.getClientSettingsCollection().remove(clientSettings);
                httpInfoIdOld = em.merge(httpInfoIdOld);
            }
            if (httpInfoIdNew != null && !httpInfoIdNew.equals(httpInfoIdOld)) {
                httpInfoIdNew.getClientSettingsCollection().add(clientSettings);
                httpInfoIdNew = em.merge(httpInfoIdNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = clientSettings.getId();
                if (findClientSettings(id) == null) {
                    throw new NonexistentEntityException("The clientSettings with id " + id + " no longer exists.");
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
            ClientSettings clientSettings;
            try {
                clientSettings = em.getReference(ClientSettings.class, id);
                clientSettings.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The clientSettings with id " + id + " no longer exists.", enfe);
            }
            HttpInfo httpInfoId = clientSettings.getHttpInfoId();
            if (httpInfoId != null) {
                httpInfoId.getClientSettingsCollection().remove(clientSettings);
                httpInfoId = em.merge(httpInfoId);
            }
            em.remove(clientSettings);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<ClientSettings> findClientSettingsEntities() {
        return findClientSettingsEntities(true, -1, -1);
    }

    public List<ClientSettings> findClientSettingsEntities(int maxResults, int firstResult) {
        return findClientSettingsEntities(false, maxResults, firstResult);
    }

    private List<ClientSettings> findClientSettingsEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(ClientSettings.class));
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

    public ClientSettings findClientSettings(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(ClientSettings.class, id);
        } finally {
            em.close();
        }
    }

    public int getClientSettingsCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<ClientSettings> rt = cq.from(ClientSettings.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
