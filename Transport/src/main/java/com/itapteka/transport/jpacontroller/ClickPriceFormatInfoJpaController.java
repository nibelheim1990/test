/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.itapteka.transport.jpacontroller;

import com.itapteka.transport.dbcontroller.ClickPriceFormatInfo;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.itapteka.transport.dbcontroller.FormatInfo;
import com.itapteka.transport.dbcontroller.SupplierClickPriceInfo;
import com.itapteka.transport.jpacontroller.exceptions.NonexistentEntityException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Ponomarev
 */
public class ClickPriceFormatInfoJpaController implements Serializable {

    public ClickPriceFormatInfoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(ClickPriceFormatInfo clickPriceFormatInfo) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            FormatInfo formatInfoId = clickPriceFormatInfo.getFormatInfoId();
            if (formatInfoId != null) {
                formatInfoId = em.getReference(formatInfoId.getClass(), formatInfoId.getId());
                clickPriceFormatInfo.setFormatInfoId(formatInfoId);
            }
            SupplierClickPriceInfo supplierClickPriceInfoId = clickPriceFormatInfo.getSupplierClickPriceInfoId();
            if (supplierClickPriceInfoId != null) {
                supplierClickPriceInfoId = em.getReference(supplierClickPriceInfoId.getClass(), supplierClickPriceInfoId.getId());
                clickPriceFormatInfo.setSupplierClickPriceInfoId(supplierClickPriceInfoId);
            }
            em.persist(clickPriceFormatInfo);
            if (formatInfoId != null) {
                formatInfoId.getClickPriceFormatInfoCollection().add(clickPriceFormatInfo);
                formatInfoId = em.merge(formatInfoId);
            }
            if (supplierClickPriceInfoId != null) {
                supplierClickPriceInfoId.getClickPriceFormatInfoCollection().add(clickPriceFormatInfo);
                supplierClickPriceInfoId = em.merge(supplierClickPriceInfoId);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(ClickPriceFormatInfo clickPriceFormatInfo) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            ClickPriceFormatInfo persistentClickPriceFormatInfo = em.find(ClickPriceFormatInfo.class, clickPriceFormatInfo.getId());
            FormatInfo formatInfoIdOld = persistentClickPriceFormatInfo.getFormatInfoId();
            FormatInfo formatInfoIdNew = clickPriceFormatInfo.getFormatInfoId();
            SupplierClickPriceInfo supplierClickPriceInfoIdOld = persistentClickPriceFormatInfo.getSupplierClickPriceInfoId();
            SupplierClickPriceInfo supplierClickPriceInfoIdNew = clickPriceFormatInfo.getSupplierClickPriceInfoId();
            if (formatInfoIdNew != null) {
                formatInfoIdNew = em.getReference(formatInfoIdNew.getClass(), formatInfoIdNew.getId());
                clickPriceFormatInfo.setFormatInfoId(formatInfoIdNew);
            }
            if (supplierClickPriceInfoIdNew != null) {
                supplierClickPriceInfoIdNew = em.getReference(supplierClickPriceInfoIdNew.getClass(), supplierClickPriceInfoIdNew.getId());
                clickPriceFormatInfo.setSupplierClickPriceInfoId(supplierClickPriceInfoIdNew);
            }
            clickPriceFormatInfo = em.merge(clickPriceFormatInfo);
            if (formatInfoIdOld != null && !formatInfoIdOld.equals(formatInfoIdNew)) {
                formatInfoIdOld.getClickPriceFormatInfoCollection().remove(clickPriceFormatInfo);
                formatInfoIdOld = em.merge(formatInfoIdOld);
            }
            if (formatInfoIdNew != null && !formatInfoIdNew.equals(formatInfoIdOld)) {
                formatInfoIdNew.getClickPriceFormatInfoCollection().add(clickPriceFormatInfo);
                formatInfoIdNew = em.merge(formatInfoIdNew);
            }
            if (supplierClickPriceInfoIdOld != null && !supplierClickPriceInfoIdOld.equals(supplierClickPriceInfoIdNew)) {
                supplierClickPriceInfoIdOld.getClickPriceFormatInfoCollection().remove(clickPriceFormatInfo);
                supplierClickPriceInfoIdOld = em.merge(supplierClickPriceInfoIdOld);
            }
            if (supplierClickPriceInfoIdNew != null && !supplierClickPriceInfoIdNew.equals(supplierClickPriceInfoIdOld)) {
                supplierClickPriceInfoIdNew.getClickPriceFormatInfoCollection().add(clickPriceFormatInfo);
                supplierClickPriceInfoIdNew = em.merge(supplierClickPriceInfoIdNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = clickPriceFormatInfo.getId();
                if (findClickPriceFormatInfo(id) == null) {
                    throw new NonexistentEntityException("The clickPriceFormatInfo with id " + id + " no longer exists.");
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
            ClickPriceFormatInfo clickPriceFormatInfo;
            try {
                clickPriceFormatInfo = em.getReference(ClickPriceFormatInfo.class, id);
                clickPriceFormatInfo.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The clickPriceFormatInfo with id " + id + " no longer exists.", enfe);
            }
            FormatInfo formatInfoId = clickPriceFormatInfo.getFormatInfoId();
            if (formatInfoId != null) {
                formatInfoId.getClickPriceFormatInfoCollection().remove(clickPriceFormatInfo);
                formatInfoId = em.merge(formatInfoId);
            }
            SupplierClickPriceInfo supplierClickPriceInfoId = clickPriceFormatInfo.getSupplierClickPriceInfoId();
            if (supplierClickPriceInfoId != null) {
                supplierClickPriceInfoId.getClickPriceFormatInfoCollection().remove(clickPriceFormatInfo);
                supplierClickPriceInfoId = em.merge(supplierClickPriceInfoId);
            }
            em.remove(clickPriceFormatInfo);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<ClickPriceFormatInfo> findClickPriceFormatInfoEntities() {
        return findClickPriceFormatInfoEntities(true, -1, -1);
    }

    public List<ClickPriceFormatInfo> findClickPriceFormatInfoEntities(int maxResults, int firstResult) {
        return findClickPriceFormatInfoEntities(false, maxResults, firstResult);
    }

    private List<ClickPriceFormatInfo> findClickPriceFormatInfoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(ClickPriceFormatInfo.class));
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

    public ClickPriceFormatInfo findClickPriceFormatInfo(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(ClickPriceFormatInfo.class, id);
        } finally {
            em.close();
        }
    }

    public int getClickPriceFormatInfoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<ClickPriceFormatInfo> rt = cq.from(ClickPriceFormatInfo.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
