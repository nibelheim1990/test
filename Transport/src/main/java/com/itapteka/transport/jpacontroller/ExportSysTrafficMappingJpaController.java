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
import com.itapteka.transport.dbcontroller.Document;
import com.itapteka.transport.dbcontroller.ExportSysTrafficMapping;
import com.itapteka.transport.jpacontroller.exceptions.NonexistentEntityException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Ponomarev
 */
public class ExportSysTrafficMappingJpaController implements Serializable {

    public ExportSysTrafficMappingJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(ExportSysTrafficMapping exportSysTrafficMapping) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Document documentUniqueField = exportSysTrafficMapping.getDocumentUniqueField();
            if (documentUniqueField != null) {
                documentUniqueField = em.getReference(documentUniqueField.getClass(), documentUniqueField.getId());
                exportSysTrafficMapping.setDocumentUniqueField(documentUniqueField);
            }
            em.persist(exportSysTrafficMapping);
            if (documentUniqueField != null) {
                documentUniqueField.getExportSysTrafficMappingCollection().add(exportSysTrafficMapping);
                documentUniqueField = em.merge(documentUniqueField);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(ExportSysTrafficMapping exportSysTrafficMapping) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            ExportSysTrafficMapping persistentExportSysTrafficMapping = em.find(ExportSysTrafficMapping.class, exportSysTrafficMapping.getId());
            Document documentUniqueFieldOld = persistentExportSysTrafficMapping.getDocumentUniqueField();
            Document documentUniqueFieldNew = exportSysTrafficMapping.getDocumentUniqueField();
            if (documentUniqueFieldNew != null) {
                documentUniqueFieldNew = em.getReference(documentUniqueFieldNew.getClass(), documentUniqueFieldNew.getId());
                exportSysTrafficMapping.setDocumentUniqueField(documentUniqueFieldNew);
            }
            exportSysTrafficMapping = em.merge(exportSysTrafficMapping);
            if (documentUniqueFieldOld != null && !documentUniqueFieldOld.equals(documentUniqueFieldNew)) {
                documentUniqueFieldOld.getExportSysTrafficMappingCollection().remove(exportSysTrafficMapping);
                documentUniqueFieldOld = em.merge(documentUniqueFieldOld);
            }
            if (documentUniqueFieldNew != null && !documentUniqueFieldNew.equals(documentUniqueFieldOld)) {
                documentUniqueFieldNew.getExportSysTrafficMappingCollection().add(exportSysTrafficMapping);
                documentUniqueFieldNew = em.merge(documentUniqueFieldNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = exportSysTrafficMapping.getId();
                if (findExportSysTrafficMapping(id) == null) {
                    throw new NonexistentEntityException("The exportSysTrafficMapping with id " + id + " no longer exists.");
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
            ExportSysTrafficMapping exportSysTrafficMapping;
            try {
                exportSysTrafficMapping = em.getReference(ExportSysTrafficMapping.class, id);
                exportSysTrafficMapping.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The exportSysTrafficMapping with id " + id + " no longer exists.", enfe);
            }
            Document documentUniqueField = exportSysTrafficMapping.getDocumentUniqueField();
            if (documentUniqueField != null) {
                documentUniqueField.getExportSysTrafficMappingCollection().remove(exportSysTrafficMapping);
                documentUniqueField = em.merge(documentUniqueField);
            }
            em.remove(exportSysTrafficMapping);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<ExportSysTrafficMapping> findExportSysTrafficMappingEntities() {
        return findExportSysTrafficMappingEntities(true, -1, -1);
    }

    public List<ExportSysTrafficMapping> findExportSysTrafficMappingEntities(int maxResults, int firstResult) {
        return findExportSysTrafficMappingEntities(false, maxResults, firstResult);
    }

    private List<ExportSysTrafficMapping> findExportSysTrafficMappingEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(ExportSysTrafficMapping.class));
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

    public ExportSysTrafficMapping findExportSysTrafficMapping(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(ExportSysTrafficMapping.class, id);
        } finally {
            em.close();
        }
    }

    public int getExportSysTrafficMappingCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<ExportSysTrafficMapping> rt = cq.from(ExportSysTrafficMapping.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
