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
import com.itapteka.transport.dbcontroller.FtpInfo;
import com.itapteka.transport.jpacontroller.exceptions.NonexistentEntityException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Ponomarev
 */
public class FtpInfoJpaController implements Serializable {

    public FtpInfoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(FtpInfo ftpInfo) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Client clientId = ftpInfo.getClientId();
            if (clientId != null) {
                clientId = em.getReference(clientId.getClass(), clientId.getId());
                ftpInfo.setClientId(clientId);
            }
            em.persist(ftpInfo);
            if (clientId != null) {
                clientId.getFtpInfoCollection().add(ftpInfo);
                clientId = em.merge(clientId);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(FtpInfo ftpInfo) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            FtpInfo persistentFtpInfo = em.find(FtpInfo.class, ftpInfo.getId());
            Client clientIdOld = persistentFtpInfo.getClientId();
            Client clientIdNew = ftpInfo.getClientId();
            if (clientIdNew != null) {
                clientIdNew = em.getReference(clientIdNew.getClass(), clientIdNew.getId());
                ftpInfo.setClientId(clientIdNew);
            }
            ftpInfo = em.merge(ftpInfo);
            if (clientIdOld != null && !clientIdOld.equals(clientIdNew)) {
                clientIdOld.getFtpInfoCollection().remove(ftpInfo);
                clientIdOld = em.merge(clientIdOld);
            }
            if (clientIdNew != null && !clientIdNew.equals(clientIdOld)) {
                clientIdNew.getFtpInfoCollection().add(ftpInfo);
                clientIdNew = em.merge(clientIdNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = ftpInfo.getId();
                if (findFtpInfo(id) == null) {
                    throw new NonexistentEntityException("The ftpInfo with id " + id + " no longer exists.");
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
            FtpInfo ftpInfo;
            try {
                ftpInfo = em.getReference(FtpInfo.class, id);
                ftpInfo.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The ftpInfo with id " + id + " no longer exists.", enfe);
            }
            Client clientId = ftpInfo.getClientId();
            if (clientId != null) {
                clientId.getFtpInfoCollection().remove(ftpInfo);
                clientId = em.merge(clientId);
            }
            em.remove(ftpInfo);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<FtpInfo> findFtpInfoEntities() {
        return findFtpInfoEntities(true, -1, -1);
    }

    public List<FtpInfo> findFtpInfoEntities(int maxResults, int firstResult) {
        return findFtpInfoEntities(false, maxResults, firstResult);
    }

    private List<FtpInfo> findFtpInfoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(FtpInfo.class));
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

    public FtpInfo findFtpInfo(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(FtpInfo.class, id);
        } finally {
            em.close();
        }
    }

    public int getFtpInfoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<FtpInfo> rt = cq.from(FtpInfo.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
