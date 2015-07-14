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
import com.itapteka.transport.dbcontroller.FormatInfo;
import com.itapteka.transport.dbcontroller.SupplierSettings;
import com.itapteka.transport.dbcontroller.TransportFormatInfo;
import com.itapteka.transport.jpacontroller.exceptions.NonexistentEntityException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Ponomarev
 */
public class TransportFormatInfoJpaController implements Serializable {

    public TransportFormatInfoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(TransportFormatInfo transportFormatInfo) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            FormatInfo formatInfoId = transportFormatInfo.getFormatInfoId();
            if (formatInfoId != null) {
                formatInfoId = em.getReference(formatInfoId.getClass(), formatInfoId.getId());
                transportFormatInfo.setFormatInfoId(formatInfoId);
            }
            SupplierSettings supplierSettingsId = transportFormatInfo.getSupplierSettingsId();
            if (supplierSettingsId != null) {
                supplierSettingsId = em.getReference(supplierSettingsId.getClass(), supplierSettingsId.getId());
                transportFormatInfo.setSupplierSettingsId(supplierSettingsId);
            }
            em.persist(transportFormatInfo);
            if (formatInfoId != null) {
                formatInfoId.getTransportFormatInfoCollection().add(transportFormatInfo);
                formatInfoId = em.merge(formatInfoId);
            }
            if (supplierSettingsId != null) {
                supplierSettingsId.getTransportFormatInfoCollection().add(transportFormatInfo);
                supplierSettingsId = em.merge(supplierSettingsId);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(TransportFormatInfo transportFormatInfo) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            TransportFormatInfo persistentTransportFormatInfo = em.find(TransportFormatInfo.class, transportFormatInfo.getId());
            FormatInfo formatInfoIdOld = persistentTransportFormatInfo.getFormatInfoId();
            FormatInfo formatInfoIdNew = transportFormatInfo.getFormatInfoId();
            SupplierSettings supplierSettingsIdOld = persistentTransportFormatInfo.getSupplierSettingsId();
            SupplierSettings supplierSettingsIdNew = transportFormatInfo.getSupplierSettingsId();
            if (formatInfoIdNew != null) {
                formatInfoIdNew = em.getReference(formatInfoIdNew.getClass(), formatInfoIdNew.getId());
                transportFormatInfo.setFormatInfoId(formatInfoIdNew);
            }
            if (supplierSettingsIdNew != null) {
                supplierSettingsIdNew = em.getReference(supplierSettingsIdNew.getClass(), supplierSettingsIdNew.getId());
                transportFormatInfo.setSupplierSettingsId(supplierSettingsIdNew);
            }
            transportFormatInfo = em.merge(transportFormatInfo);
            if (formatInfoIdOld != null && !formatInfoIdOld.equals(formatInfoIdNew)) {
                formatInfoIdOld.getTransportFormatInfoCollection().remove(transportFormatInfo);
                formatInfoIdOld = em.merge(formatInfoIdOld);
            }
            if (formatInfoIdNew != null && !formatInfoIdNew.equals(formatInfoIdOld)) {
                formatInfoIdNew.getTransportFormatInfoCollection().add(transportFormatInfo);
                formatInfoIdNew = em.merge(formatInfoIdNew);
            }
            if (supplierSettingsIdOld != null && !supplierSettingsIdOld.equals(supplierSettingsIdNew)) {
                supplierSettingsIdOld.getTransportFormatInfoCollection().remove(transportFormatInfo);
                supplierSettingsIdOld = em.merge(supplierSettingsIdOld);
            }
            if (supplierSettingsIdNew != null && !supplierSettingsIdNew.equals(supplierSettingsIdOld)) {
                supplierSettingsIdNew.getTransportFormatInfoCollection().add(transportFormatInfo);
                supplierSettingsIdNew = em.merge(supplierSettingsIdNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = transportFormatInfo.getId();
                if (findTransportFormatInfo(id) == null) {
                    throw new NonexistentEntityException("The transportFormatInfo with id " + id + " no longer exists.");
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
            TransportFormatInfo transportFormatInfo;
            try {
                transportFormatInfo = em.getReference(TransportFormatInfo.class, id);
                transportFormatInfo.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The transportFormatInfo with id " + id + " no longer exists.", enfe);
            }
            FormatInfo formatInfoId = transportFormatInfo.getFormatInfoId();
            if (formatInfoId != null) {
                formatInfoId.getTransportFormatInfoCollection().remove(transportFormatInfo);
                formatInfoId = em.merge(formatInfoId);
            }
            SupplierSettings supplierSettingsId = transportFormatInfo.getSupplierSettingsId();
            if (supplierSettingsId != null) {
                supplierSettingsId.getTransportFormatInfoCollection().remove(transportFormatInfo);
                supplierSettingsId = em.merge(supplierSettingsId);
            }
            em.remove(transportFormatInfo);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<TransportFormatInfo> findTransportFormatInfoEntities() {
        return findTransportFormatInfoEntities(true, -1, -1);
    }

    public List<TransportFormatInfo> findTransportFormatInfoEntities(int maxResults, int firstResult) {
        return findTransportFormatInfoEntities(false, maxResults, firstResult);
    }

    private List<TransportFormatInfo> findTransportFormatInfoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(TransportFormatInfo.class));
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

    public TransportFormatInfo findTransportFormatInfo(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(TransportFormatInfo.class, id);
        } finally {
            em.close();
        }
    }

    public int getTransportFormatInfoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<TransportFormatInfo> rt = cq.from(TransportFormatInfo.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
