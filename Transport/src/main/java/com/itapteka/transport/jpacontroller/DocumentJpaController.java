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
import com.itapteka.transport.dbcontroller.Document;
import com.itapteka.transport.dbcontroller.EnaklMetadata;
import java.util.ArrayList;
import java.util.Collection;
import com.itapteka.transport.dbcontroller.ExportSysTrafficMapping;
import com.itapteka.transport.jpacontroller.exceptions.IllegalOrphanException;
import com.itapteka.transport.jpacontroller.exceptions.NonexistentEntityException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Ponomarev
 */
public class DocumentJpaController implements Serializable {

    public DocumentJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Document document) {
        if (document.getDocumentCollection() == null) {
            document.setDocumentCollection(new ArrayList<Document>());
        }
        if (document.getDocumentCollection1() == null) {
            document.setDocumentCollection1(new ArrayList<Document>());
        }
        if (document.getExportSysTrafficMappingCollection() == null) {
            document.setExportSysTrafficMappingCollection(new ArrayList<ExportSysTrafficMapping>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Client clientDstId = document.getClientDstId();
            if (clientDstId != null) {
                clientDstId = em.getReference(clientDstId.getClass(), clientDstId.getId());
                document.setClientDstId(clientDstId);
            }
            Client clientSrcId = document.getClientSrcId();
            if (clientSrcId != null) {
                clientSrcId = em.getReference(clientSrcId.getClass(), clientSrcId.getId());
                document.setClientSrcId(clientSrcId);
            }
            Document packId = document.getPackId();
            if (packId != null) {
                packId = em.getReference(packId.getClass(), packId.getId());
                document.setPackId(packId);
            }
            Document parentId = document.getParentId();
            if (parentId != null) {
                parentId = em.getReference(parentId.getClass(), parentId.getId());
                document.setParentId(parentId);
            }
            EnaklMetadata enaklMetadata = document.getEnaklMetadata();
            if (enaklMetadata != null) {
                enaklMetadata = em.getReference(enaklMetadata.getClass(), enaklMetadata.getId());
                document.setEnaklMetadata(enaklMetadata);
            }
            Collection<Document> attachedDocumentCollection = new ArrayList<Document>();
            for (Document documentCollectionDocumentToAttach : document.getDocumentCollection()) {
                documentCollectionDocumentToAttach = em.getReference(documentCollectionDocumentToAttach.getClass(), documentCollectionDocumentToAttach.getId());
                attachedDocumentCollection.add(documentCollectionDocumentToAttach);
            }
            document.setDocumentCollection(attachedDocumentCollection);
            Collection<Document> attachedDocumentCollection1 = new ArrayList<Document>();
            for (Document documentCollection1DocumentToAttach : document.getDocumentCollection1()) {
                documentCollection1DocumentToAttach = em.getReference(documentCollection1DocumentToAttach.getClass(), documentCollection1DocumentToAttach.getId());
                attachedDocumentCollection1.add(documentCollection1DocumentToAttach);
            }
            document.setDocumentCollection1(attachedDocumentCollection1);
            Collection<ExportSysTrafficMapping> attachedExportSysTrafficMappingCollection = new ArrayList<ExportSysTrafficMapping>();
            for (ExportSysTrafficMapping exportSysTrafficMappingCollectionExportSysTrafficMappingToAttach : document.getExportSysTrafficMappingCollection()) {
                exportSysTrafficMappingCollectionExportSysTrafficMappingToAttach = em.getReference(exportSysTrafficMappingCollectionExportSysTrafficMappingToAttach.getClass(), exportSysTrafficMappingCollectionExportSysTrafficMappingToAttach.getId());
                attachedExportSysTrafficMappingCollection.add(exportSysTrafficMappingCollectionExportSysTrafficMappingToAttach);
            }
            document.setExportSysTrafficMappingCollection(attachedExportSysTrafficMappingCollection);
            em.persist(document);
            if (clientDstId != null) {
                clientDstId.getDocumentCollection().add(document);
                clientDstId = em.merge(clientDstId);
            }
            if (clientSrcId != null) {
                clientSrcId.getDocumentCollection().add(document);
                clientSrcId = em.merge(clientSrcId);
            }
            if (packId != null) {
                packId.getDocumentCollection().add(document);
                packId = em.merge(packId);
            }
            if (parentId != null) {
                parentId.getDocumentCollection().add(document);
                parentId = em.merge(parentId);
            }
            if (enaklMetadata != null) {
                Document oldDocumentOfEnaklMetadata = enaklMetadata.getDocument();
                if (oldDocumentOfEnaklMetadata != null) {
                    oldDocumentOfEnaklMetadata.setEnaklMetadata(null);
                    oldDocumentOfEnaklMetadata = em.merge(oldDocumentOfEnaklMetadata);
                }
                enaklMetadata.setDocument(document);
                enaklMetadata = em.merge(enaklMetadata);
            }
            for (Document documentCollectionDocument : document.getDocumentCollection()) {
                Document oldPackIdOfDocumentCollectionDocument = documentCollectionDocument.getPackId();
                documentCollectionDocument.setPackId(document);
                documentCollectionDocument = em.merge(documentCollectionDocument);
                if (oldPackIdOfDocumentCollectionDocument != null) {
                    oldPackIdOfDocumentCollectionDocument.getDocumentCollection().remove(documentCollectionDocument);
                    oldPackIdOfDocumentCollectionDocument = em.merge(oldPackIdOfDocumentCollectionDocument);
                }
            }
            for (Document documentCollection1Document : document.getDocumentCollection1()) {
                Document oldParentIdOfDocumentCollection1Document = documentCollection1Document.getParentId();
                documentCollection1Document.setParentId(document);
                documentCollection1Document = em.merge(documentCollection1Document);
                if (oldParentIdOfDocumentCollection1Document != null) {
                    oldParentIdOfDocumentCollection1Document.getDocumentCollection1().remove(documentCollection1Document);
                    oldParentIdOfDocumentCollection1Document = em.merge(oldParentIdOfDocumentCollection1Document);
                }
            }
            for (ExportSysTrafficMapping exportSysTrafficMappingCollectionExportSysTrafficMapping : document.getExportSysTrafficMappingCollection()) {
                Document oldDocumentUniqueFieldOfExportSysTrafficMappingCollectionExportSysTrafficMapping = exportSysTrafficMappingCollectionExportSysTrafficMapping.getDocumentUniqueField();
                exportSysTrafficMappingCollectionExportSysTrafficMapping.setDocumentUniqueField(document);
                exportSysTrafficMappingCollectionExportSysTrafficMapping = em.merge(exportSysTrafficMappingCollectionExportSysTrafficMapping);
                if (oldDocumentUniqueFieldOfExportSysTrafficMappingCollectionExportSysTrafficMapping != null) {
                    oldDocumentUniqueFieldOfExportSysTrafficMappingCollectionExportSysTrafficMapping.getExportSysTrafficMappingCollection().remove(exportSysTrafficMappingCollectionExportSysTrafficMapping);
                    oldDocumentUniqueFieldOfExportSysTrafficMappingCollectionExportSysTrafficMapping = em.merge(oldDocumentUniqueFieldOfExportSysTrafficMappingCollectionExportSysTrafficMapping);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Document document) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Document persistentDocument = em.find(Document.class, document.getId());
            Client clientDstIdOld = persistentDocument.getClientDstId();
            Client clientDstIdNew = document.getClientDstId();
            Client clientSrcIdOld = persistentDocument.getClientSrcId();
            Client clientSrcIdNew = document.getClientSrcId();
            Document packIdOld = persistentDocument.getPackId();
            Document packIdNew = document.getPackId();
            Document parentIdOld = persistentDocument.getParentId();
            Document parentIdNew = document.getParentId();
            EnaklMetadata enaklMetadataOld = persistentDocument.getEnaklMetadata();
            EnaklMetadata enaklMetadataNew = document.getEnaklMetadata();
            Collection<Document> documentCollectionOld = persistentDocument.getDocumentCollection();
            Collection<Document> documentCollectionNew = document.getDocumentCollection();
            Collection<Document> documentCollection1Old = persistentDocument.getDocumentCollection1();
            Collection<Document> documentCollection1New = document.getDocumentCollection1();
            Collection<ExportSysTrafficMapping> exportSysTrafficMappingCollectionOld = persistentDocument.getExportSysTrafficMappingCollection();
            Collection<ExportSysTrafficMapping> exportSysTrafficMappingCollectionNew = document.getExportSysTrafficMappingCollection();
            List<String> illegalOrphanMessages = null;
            if (enaklMetadataOld != null && !enaklMetadataOld.equals(enaklMetadataNew)) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("You must retain EnaklMetadata " + enaklMetadataOld + " since its document field is not nullable.");
            }
            for (ExportSysTrafficMapping exportSysTrafficMappingCollectionOldExportSysTrafficMapping : exportSysTrafficMappingCollectionOld) {
                if (!exportSysTrafficMappingCollectionNew.contains(exportSysTrafficMappingCollectionOldExportSysTrafficMapping)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain ExportSysTrafficMapping " + exportSysTrafficMappingCollectionOldExportSysTrafficMapping + " since its documentUniqueField field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (clientDstIdNew != null) {
                clientDstIdNew = em.getReference(clientDstIdNew.getClass(), clientDstIdNew.getId());
                document.setClientDstId(clientDstIdNew);
            }
            if (clientSrcIdNew != null) {
                clientSrcIdNew = em.getReference(clientSrcIdNew.getClass(), clientSrcIdNew.getId());
                document.setClientSrcId(clientSrcIdNew);
            }
            if (packIdNew != null) {
                packIdNew = em.getReference(packIdNew.getClass(), packIdNew.getId());
                document.setPackId(packIdNew);
            }
            if (parentIdNew != null) {
                parentIdNew = em.getReference(parentIdNew.getClass(), parentIdNew.getId());
                document.setParentId(parentIdNew);
            }
            if (enaklMetadataNew != null) {
                enaklMetadataNew = em.getReference(enaklMetadataNew.getClass(), enaklMetadataNew.getId());
                document.setEnaklMetadata(enaklMetadataNew);
            }
            Collection<Document> attachedDocumentCollectionNew = new ArrayList<Document>();
            for (Document documentCollectionNewDocumentToAttach : documentCollectionNew) {
                documentCollectionNewDocumentToAttach = em.getReference(documentCollectionNewDocumentToAttach.getClass(), documentCollectionNewDocumentToAttach.getId());
                attachedDocumentCollectionNew.add(documentCollectionNewDocumentToAttach);
            }
            documentCollectionNew = attachedDocumentCollectionNew;
            document.setDocumentCollection(documentCollectionNew);
            Collection<Document> attachedDocumentCollection1New = new ArrayList<Document>();
            for (Document documentCollection1NewDocumentToAttach : documentCollection1New) {
                documentCollection1NewDocumentToAttach = em.getReference(documentCollection1NewDocumentToAttach.getClass(), documentCollection1NewDocumentToAttach.getId());
                attachedDocumentCollection1New.add(documentCollection1NewDocumentToAttach);
            }
            documentCollection1New = attachedDocumentCollection1New;
            document.setDocumentCollection1(documentCollection1New);
            Collection<ExportSysTrafficMapping> attachedExportSysTrafficMappingCollectionNew = new ArrayList<ExportSysTrafficMapping>();
            for (ExportSysTrafficMapping exportSysTrafficMappingCollectionNewExportSysTrafficMappingToAttach : exportSysTrafficMappingCollectionNew) {
                exportSysTrafficMappingCollectionNewExportSysTrafficMappingToAttach = em.getReference(exportSysTrafficMappingCollectionNewExportSysTrafficMappingToAttach.getClass(), exportSysTrafficMappingCollectionNewExportSysTrafficMappingToAttach.getId());
                attachedExportSysTrafficMappingCollectionNew.add(exportSysTrafficMappingCollectionNewExportSysTrafficMappingToAttach);
            }
            exportSysTrafficMappingCollectionNew = attachedExportSysTrafficMappingCollectionNew;
            document.setExportSysTrafficMappingCollection(exportSysTrafficMappingCollectionNew);
            document = em.merge(document);
            if (clientDstIdOld != null && !clientDstIdOld.equals(clientDstIdNew)) {
                clientDstIdOld.getDocumentCollection().remove(document);
                clientDstIdOld = em.merge(clientDstIdOld);
            }
            if (clientDstIdNew != null && !clientDstIdNew.equals(clientDstIdOld)) {
                clientDstIdNew.getDocumentCollection().add(document);
                clientDstIdNew = em.merge(clientDstIdNew);
            }
            if (clientSrcIdOld != null && !clientSrcIdOld.equals(clientSrcIdNew)) {
                clientSrcIdOld.getDocumentCollection().remove(document);
                clientSrcIdOld = em.merge(clientSrcIdOld);
            }
            if (clientSrcIdNew != null && !clientSrcIdNew.equals(clientSrcIdOld)) {
                clientSrcIdNew.getDocumentCollection().add(document);
                clientSrcIdNew = em.merge(clientSrcIdNew);
            }
            if (packIdOld != null && !packIdOld.equals(packIdNew)) {
                packIdOld.getDocumentCollection().remove(document);
                packIdOld = em.merge(packIdOld);
            }
            if (packIdNew != null && !packIdNew.equals(packIdOld)) {
                packIdNew.getDocumentCollection().add(document);
                packIdNew = em.merge(packIdNew);
            }
            if (parentIdOld != null && !parentIdOld.equals(parentIdNew)) {
                parentIdOld.getDocumentCollection().remove(document);
                parentIdOld = em.merge(parentIdOld);
            }
            if (parentIdNew != null && !parentIdNew.equals(parentIdOld)) {
                parentIdNew.getDocumentCollection().add(document);
                parentIdNew = em.merge(parentIdNew);
            }
            if (enaklMetadataNew != null && !enaklMetadataNew.equals(enaklMetadataOld)) {
                Document oldDocumentOfEnaklMetadata = enaklMetadataNew.getDocument();
                if (oldDocumentOfEnaklMetadata != null) {
                    oldDocumentOfEnaklMetadata.setEnaklMetadata(null);
                    oldDocumentOfEnaklMetadata = em.merge(oldDocumentOfEnaklMetadata);
                }
                enaklMetadataNew.setDocument(document);
                enaklMetadataNew = em.merge(enaklMetadataNew);
            }
            for (Document documentCollectionOldDocument : documentCollectionOld) {
                if (!documentCollectionNew.contains(documentCollectionOldDocument)) {
                    documentCollectionOldDocument.setPackId(null);
                    documentCollectionOldDocument = em.merge(documentCollectionOldDocument);
                }
            }
            for (Document documentCollectionNewDocument : documentCollectionNew) {
                if (!documentCollectionOld.contains(documentCollectionNewDocument)) {
                    Document oldPackIdOfDocumentCollectionNewDocument = documentCollectionNewDocument.getPackId();
                    documentCollectionNewDocument.setPackId(document);
                    documentCollectionNewDocument = em.merge(documentCollectionNewDocument);
                    if (oldPackIdOfDocumentCollectionNewDocument != null && !oldPackIdOfDocumentCollectionNewDocument.equals(document)) {
                        oldPackIdOfDocumentCollectionNewDocument.getDocumentCollection().remove(documentCollectionNewDocument);
                        oldPackIdOfDocumentCollectionNewDocument = em.merge(oldPackIdOfDocumentCollectionNewDocument);
                    }
                }
            }
            for (Document documentCollection1OldDocument : documentCollection1Old) {
                if (!documentCollection1New.contains(documentCollection1OldDocument)) {
                    documentCollection1OldDocument.setParentId(null);
                    documentCollection1OldDocument = em.merge(documentCollection1OldDocument);
                }
            }
            for (Document documentCollection1NewDocument : documentCollection1New) {
                if (!documentCollection1Old.contains(documentCollection1NewDocument)) {
                    Document oldParentIdOfDocumentCollection1NewDocument = documentCollection1NewDocument.getParentId();
                    documentCollection1NewDocument.setParentId(document);
                    documentCollection1NewDocument = em.merge(documentCollection1NewDocument);
                    if (oldParentIdOfDocumentCollection1NewDocument != null && !oldParentIdOfDocumentCollection1NewDocument.equals(document)) {
                        oldParentIdOfDocumentCollection1NewDocument.getDocumentCollection1().remove(documentCollection1NewDocument);
                        oldParentIdOfDocumentCollection1NewDocument = em.merge(oldParentIdOfDocumentCollection1NewDocument);
                    }
                }
            }
            for (ExportSysTrafficMapping exportSysTrafficMappingCollectionNewExportSysTrafficMapping : exportSysTrafficMappingCollectionNew) {
                if (!exportSysTrafficMappingCollectionOld.contains(exportSysTrafficMappingCollectionNewExportSysTrafficMapping)) {
                    Document oldDocumentUniqueFieldOfExportSysTrafficMappingCollectionNewExportSysTrafficMapping = exportSysTrafficMappingCollectionNewExportSysTrafficMapping.getDocumentUniqueField();
                    exportSysTrafficMappingCollectionNewExportSysTrafficMapping.setDocumentUniqueField(document);
                    exportSysTrafficMappingCollectionNewExportSysTrafficMapping = em.merge(exportSysTrafficMappingCollectionNewExportSysTrafficMapping);
                    if (oldDocumentUniqueFieldOfExportSysTrafficMappingCollectionNewExportSysTrafficMapping != null && !oldDocumentUniqueFieldOfExportSysTrafficMappingCollectionNewExportSysTrafficMapping.equals(document)) {
                        oldDocumentUniqueFieldOfExportSysTrafficMappingCollectionNewExportSysTrafficMapping.getExportSysTrafficMappingCollection().remove(exportSysTrafficMappingCollectionNewExportSysTrafficMapping);
                        oldDocumentUniqueFieldOfExportSysTrafficMappingCollectionNewExportSysTrafficMapping = em.merge(oldDocumentUniqueFieldOfExportSysTrafficMappingCollectionNewExportSysTrafficMapping);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = document.getId();
                if (findDocument(id) == null) {
                    throw new NonexistentEntityException("The document with id " + id + " no longer exists.");
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
            Document document;
            try {
                document = em.getReference(Document.class, id);
                document.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The document with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            EnaklMetadata enaklMetadataOrphanCheck = document.getEnaklMetadata();
            if (enaklMetadataOrphanCheck != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Document (" + document + ") cannot be destroyed since the EnaklMetadata " + enaklMetadataOrphanCheck + " in its enaklMetadata field has a non-nullable document field.");
            }
            Collection<ExportSysTrafficMapping> exportSysTrafficMappingCollectionOrphanCheck = document.getExportSysTrafficMappingCollection();
            for (ExportSysTrafficMapping exportSysTrafficMappingCollectionOrphanCheckExportSysTrafficMapping : exportSysTrafficMappingCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Document (" + document + ") cannot be destroyed since the ExportSysTrafficMapping " + exportSysTrafficMappingCollectionOrphanCheckExportSysTrafficMapping + " in its exportSysTrafficMappingCollection field has a non-nullable documentUniqueField field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Client clientDstId = document.getClientDstId();
            if (clientDstId != null) {
                clientDstId.getDocumentCollection().remove(document);
                clientDstId = em.merge(clientDstId);
            }
            Client clientSrcId = document.getClientSrcId();
            if (clientSrcId != null) {
                clientSrcId.getDocumentCollection().remove(document);
                clientSrcId = em.merge(clientSrcId);
            }
            Document packId = document.getPackId();
            if (packId != null) {
                packId.getDocumentCollection().remove(document);
                packId = em.merge(packId);
            }
            Document parentId = document.getParentId();
            if (parentId != null) {
                parentId.getDocumentCollection().remove(document);
                parentId = em.merge(parentId);
            }
            Collection<Document> documentCollection = document.getDocumentCollection();
            for (Document documentCollectionDocument : documentCollection) {
                documentCollectionDocument.setPackId(null);
                documentCollectionDocument = em.merge(documentCollectionDocument);
            }
            Collection<Document> documentCollection1 = document.getDocumentCollection1();
            for (Document documentCollection1Document : documentCollection1) {
                documentCollection1Document.setParentId(null);
                documentCollection1Document = em.merge(documentCollection1Document);
            }
            em.remove(document);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Document> findDocumentEntities() {
        return findDocumentEntities(true, -1, -1);
    }

    public List<Document> findDocumentEntities(int maxResults, int firstResult) {
        return findDocumentEntities(false, maxResults, firstResult);
    }

    private List<Document> findDocumentEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Document.class));
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

    public Document findDocument(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Document.class, id);
        } finally {
            em.close();
        }
    }

    public int getDocumentCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Document> rt = cq.from(Document.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
