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
import com.itapteka.transport.dbcontroller.EnaklMetadata;
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
public class EnaklMetadataJpaController implements Serializable {

    public EnaklMetadataJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(EnaklMetadata enaklMetadata) throws IllegalOrphanException, PreexistingEntityException, Exception {
        List<String> illegalOrphanMessages = null;
        Document documentOrphanCheck = enaklMetadata.getDocument();
        if (documentOrphanCheck != null) {
            EnaklMetadata oldEnaklMetadataOfDocument = documentOrphanCheck.getEnaklMetadata();
            if (oldEnaklMetadataOfDocument != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("The Document " + documentOrphanCheck + " already has an item of type EnaklMetadata whose document column cannot be null. Please make another selection for the document field.");
            }
        }
        if (illegalOrphanMessages != null) {
            throw new IllegalOrphanException(illegalOrphanMessages);
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Document document = enaklMetadata.getDocument();
            if (document != null) {
                document = em.getReference(document.getClass(), document.getId());
                enaklMetadata.setDocument(document);
            }
            em.persist(enaklMetadata);
            if (document != null) {
                document.setEnaklMetadata(enaklMetadata);
                document = em.merge(document);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findEnaklMetadata(enaklMetadata.getId()) != null) {
                throw new PreexistingEntityException("EnaklMetadata " + enaklMetadata + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(EnaklMetadata enaklMetadata) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            EnaklMetadata persistentEnaklMetadata = em.find(EnaklMetadata.class, enaklMetadata.getId());
            Document documentOld = persistentEnaklMetadata.getDocument();
            Document documentNew = enaklMetadata.getDocument();
            List<String> illegalOrphanMessages = null;
            if (documentNew != null && !documentNew.equals(documentOld)) {
                EnaklMetadata oldEnaklMetadataOfDocument = documentNew.getEnaklMetadata();
                if (oldEnaklMetadataOfDocument != null) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("The Document " + documentNew + " already has an item of type EnaklMetadata whose document column cannot be null. Please make another selection for the document field.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (documentNew != null) {
                documentNew = em.getReference(documentNew.getClass(), documentNew.getId());
                enaklMetadata.setDocument(documentNew);
            }
            enaklMetadata = em.merge(enaklMetadata);
            if (documentOld != null && !documentOld.equals(documentNew)) {
                documentOld.setEnaklMetadata(null);
                documentOld = em.merge(documentOld);
            }
            if (documentNew != null && !documentNew.equals(documentOld)) {
                documentNew.setEnaklMetadata(enaklMetadata);
                documentNew = em.merge(documentNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = enaklMetadata.getId();
                if (findEnaklMetadata(id) == null) {
                    throw new NonexistentEntityException("The enaklMetadata with id " + id + " no longer exists.");
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
            EnaklMetadata enaklMetadata;
            try {
                enaklMetadata = em.getReference(EnaklMetadata.class, id);
                enaklMetadata.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The enaklMetadata with id " + id + " no longer exists.", enfe);
            }
            Document document = enaklMetadata.getDocument();
            if (document != null) {
                document.setEnaklMetadata(null);
                document = em.merge(document);
            }
            em.remove(enaklMetadata);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<EnaklMetadata> findEnaklMetadataEntities() {
        return findEnaklMetadataEntities(true, -1, -1);
    }

    public List<EnaklMetadata> findEnaklMetadataEntities(int maxResults, int firstResult) {
        return findEnaklMetadataEntities(false, maxResults, firstResult);
    }

    private List<EnaklMetadata> findEnaklMetadataEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(EnaklMetadata.class));
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

    public EnaklMetadata findEnaklMetadata(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(EnaklMetadata.class, id);
        } finally {
            em.close();
        }
    }

    public int getEnaklMetadataCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<EnaklMetadata> rt = cq.from(EnaklMetadata.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
