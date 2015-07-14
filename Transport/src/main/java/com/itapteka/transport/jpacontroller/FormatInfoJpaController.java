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
import com.itapteka.transport.dbcontroller.ClickPriceFormatInfo;
import com.itapteka.transport.dbcontroller.FormatInfo;
import java.util.ArrayList;
import java.util.Collection;
import com.itapteka.transport.dbcontroller.TransportFormatInfo;
import com.itapteka.transport.jpacontroller.exceptions.IllegalOrphanException;
import com.itapteka.transport.jpacontroller.exceptions.NonexistentEntityException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Ponomarev
 */
public class FormatInfoJpaController implements Serializable {

    public FormatInfoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(FormatInfo formatInfo) {
        if (formatInfo.getClickPriceFormatInfoCollection() == null) {
            formatInfo.setClickPriceFormatInfoCollection(new ArrayList<ClickPriceFormatInfo>());
        }
        if (formatInfo.getTransportFormatInfoCollection() == null) {
            formatInfo.setTransportFormatInfoCollection(new ArrayList<TransportFormatInfo>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<ClickPriceFormatInfo> attachedClickPriceFormatInfoCollection = new ArrayList<ClickPriceFormatInfo>();
            for (ClickPriceFormatInfo clickPriceFormatInfoCollectionClickPriceFormatInfoToAttach : formatInfo.getClickPriceFormatInfoCollection()) {
                clickPriceFormatInfoCollectionClickPriceFormatInfoToAttach = em.getReference(clickPriceFormatInfoCollectionClickPriceFormatInfoToAttach.getClass(), clickPriceFormatInfoCollectionClickPriceFormatInfoToAttach.getId());
                attachedClickPriceFormatInfoCollection.add(clickPriceFormatInfoCollectionClickPriceFormatInfoToAttach);
            }
            formatInfo.setClickPriceFormatInfoCollection(attachedClickPriceFormatInfoCollection);
            Collection<TransportFormatInfo> attachedTransportFormatInfoCollection = new ArrayList<TransportFormatInfo>();
            for (TransportFormatInfo transportFormatInfoCollectionTransportFormatInfoToAttach : formatInfo.getTransportFormatInfoCollection()) {
                transportFormatInfoCollectionTransportFormatInfoToAttach = em.getReference(transportFormatInfoCollectionTransportFormatInfoToAttach.getClass(), transportFormatInfoCollectionTransportFormatInfoToAttach.getId());
                attachedTransportFormatInfoCollection.add(transportFormatInfoCollectionTransportFormatInfoToAttach);
            }
            formatInfo.setTransportFormatInfoCollection(attachedTransportFormatInfoCollection);
            em.persist(formatInfo);
            for (ClickPriceFormatInfo clickPriceFormatInfoCollectionClickPriceFormatInfo : formatInfo.getClickPriceFormatInfoCollection()) {
                FormatInfo oldFormatInfoIdOfClickPriceFormatInfoCollectionClickPriceFormatInfo = clickPriceFormatInfoCollectionClickPriceFormatInfo.getFormatInfoId();
                clickPriceFormatInfoCollectionClickPriceFormatInfo.setFormatInfoId(formatInfo);
                clickPriceFormatInfoCollectionClickPriceFormatInfo = em.merge(clickPriceFormatInfoCollectionClickPriceFormatInfo);
                if (oldFormatInfoIdOfClickPriceFormatInfoCollectionClickPriceFormatInfo != null) {
                    oldFormatInfoIdOfClickPriceFormatInfoCollectionClickPriceFormatInfo.getClickPriceFormatInfoCollection().remove(clickPriceFormatInfoCollectionClickPriceFormatInfo);
                    oldFormatInfoIdOfClickPriceFormatInfoCollectionClickPriceFormatInfo = em.merge(oldFormatInfoIdOfClickPriceFormatInfoCollectionClickPriceFormatInfo);
                }
            }
            for (TransportFormatInfo transportFormatInfoCollectionTransportFormatInfo : formatInfo.getTransportFormatInfoCollection()) {
                FormatInfo oldFormatInfoIdOfTransportFormatInfoCollectionTransportFormatInfo = transportFormatInfoCollectionTransportFormatInfo.getFormatInfoId();
                transportFormatInfoCollectionTransportFormatInfo.setFormatInfoId(formatInfo);
                transportFormatInfoCollectionTransportFormatInfo = em.merge(transportFormatInfoCollectionTransportFormatInfo);
                if (oldFormatInfoIdOfTransportFormatInfoCollectionTransportFormatInfo != null) {
                    oldFormatInfoIdOfTransportFormatInfoCollectionTransportFormatInfo.getTransportFormatInfoCollection().remove(transportFormatInfoCollectionTransportFormatInfo);
                    oldFormatInfoIdOfTransportFormatInfoCollectionTransportFormatInfo = em.merge(oldFormatInfoIdOfTransportFormatInfoCollectionTransportFormatInfo);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(FormatInfo formatInfo) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            FormatInfo persistentFormatInfo = em.find(FormatInfo.class, formatInfo.getId());
            Collection<ClickPriceFormatInfo> clickPriceFormatInfoCollectionOld = persistentFormatInfo.getClickPriceFormatInfoCollection();
            Collection<ClickPriceFormatInfo> clickPriceFormatInfoCollectionNew = formatInfo.getClickPriceFormatInfoCollection();
            Collection<TransportFormatInfo> transportFormatInfoCollectionOld = persistentFormatInfo.getTransportFormatInfoCollection();
            Collection<TransportFormatInfo> transportFormatInfoCollectionNew = formatInfo.getTransportFormatInfoCollection();
            List<String> illegalOrphanMessages = null;
            for (ClickPriceFormatInfo clickPriceFormatInfoCollectionOldClickPriceFormatInfo : clickPriceFormatInfoCollectionOld) {
                if (!clickPriceFormatInfoCollectionNew.contains(clickPriceFormatInfoCollectionOldClickPriceFormatInfo)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain ClickPriceFormatInfo " + clickPriceFormatInfoCollectionOldClickPriceFormatInfo + " since its formatInfoId field is not nullable.");
                }
            }
            for (TransportFormatInfo transportFormatInfoCollectionOldTransportFormatInfo : transportFormatInfoCollectionOld) {
                if (!transportFormatInfoCollectionNew.contains(transportFormatInfoCollectionOldTransportFormatInfo)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain TransportFormatInfo " + transportFormatInfoCollectionOldTransportFormatInfo + " since its formatInfoId field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<ClickPriceFormatInfo> attachedClickPriceFormatInfoCollectionNew = new ArrayList<ClickPriceFormatInfo>();
            for (ClickPriceFormatInfo clickPriceFormatInfoCollectionNewClickPriceFormatInfoToAttach : clickPriceFormatInfoCollectionNew) {
                clickPriceFormatInfoCollectionNewClickPriceFormatInfoToAttach = em.getReference(clickPriceFormatInfoCollectionNewClickPriceFormatInfoToAttach.getClass(), clickPriceFormatInfoCollectionNewClickPriceFormatInfoToAttach.getId());
                attachedClickPriceFormatInfoCollectionNew.add(clickPriceFormatInfoCollectionNewClickPriceFormatInfoToAttach);
            }
            clickPriceFormatInfoCollectionNew = attachedClickPriceFormatInfoCollectionNew;
            formatInfo.setClickPriceFormatInfoCollection(clickPriceFormatInfoCollectionNew);
            Collection<TransportFormatInfo> attachedTransportFormatInfoCollectionNew = new ArrayList<TransportFormatInfo>();
            for (TransportFormatInfo transportFormatInfoCollectionNewTransportFormatInfoToAttach : transportFormatInfoCollectionNew) {
                transportFormatInfoCollectionNewTransportFormatInfoToAttach = em.getReference(transportFormatInfoCollectionNewTransportFormatInfoToAttach.getClass(), transportFormatInfoCollectionNewTransportFormatInfoToAttach.getId());
                attachedTransportFormatInfoCollectionNew.add(transportFormatInfoCollectionNewTransportFormatInfoToAttach);
            }
            transportFormatInfoCollectionNew = attachedTransportFormatInfoCollectionNew;
            formatInfo.setTransportFormatInfoCollection(transportFormatInfoCollectionNew);
            formatInfo = em.merge(formatInfo);
            for (ClickPriceFormatInfo clickPriceFormatInfoCollectionNewClickPriceFormatInfo : clickPriceFormatInfoCollectionNew) {
                if (!clickPriceFormatInfoCollectionOld.contains(clickPriceFormatInfoCollectionNewClickPriceFormatInfo)) {
                    FormatInfo oldFormatInfoIdOfClickPriceFormatInfoCollectionNewClickPriceFormatInfo = clickPriceFormatInfoCollectionNewClickPriceFormatInfo.getFormatInfoId();
                    clickPriceFormatInfoCollectionNewClickPriceFormatInfo.setFormatInfoId(formatInfo);
                    clickPriceFormatInfoCollectionNewClickPriceFormatInfo = em.merge(clickPriceFormatInfoCollectionNewClickPriceFormatInfo);
                    if (oldFormatInfoIdOfClickPriceFormatInfoCollectionNewClickPriceFormatInfo != null && !oldFormatInfoIdOfClickPriceFormatInfoCollectionNewClickPriceFormatInfo.equals(formatInfo)) {
                        oldFormatInfoIdOfClickPriceFormatInfoCollectionNewClickPriceFormatInfo.getClickPriceFormatInfoCollection().remove(clickPriceFormatInfoCollectionNewClickPriceFormatInfo);
                        oldFormatInfoIdOfClickPriceFormatInfoCollectionNewClickPriceFormatInfo = em.merge(oldFormatInfoIdOfClickPriceFormatInfoCollectionNewClickPriceFormatInfo);
                    }
                }
            }
            for (TransportFormatInfo transportFormatInfoCollectionNewTransportFormatInfo : transportFormatInfoCollectionNew) {
                if (!transportFormatInfoCollectionOld.contains(transportFormatInfoCollectionNewTransportFormatInfo)) {
                    FormatInfo oldFormatInfoIdOfTransportFormatInfoCollectionNewTransportFormatInfo = transportFormatInfoCollectionNewTransportFormatInfo.getFormatInfoId();
                    transportFormatInfoCollectionNewTransportFormatInfo.setFormatInfoId(formatInfo);
                    transportFormatInfoCollectionNewTransportFormatInfo = em.merge(transportFormatInfoCollectionNewTransportFormatInfo);
                    if (oldFormatInfoIdOfTransportFormatInfoCollectionNewTransportFormatInfo != null && !oldFormatInfoIdOfTransportFormatInfoCollectionNewTransportFormatInfo.equals(formatInfo)) {
                        oldFormatInfoIdOfTransportFormatInfoCollectionNewTransportFormatInfo.getTransportFormatInfoCollection().remove(transportFormatInfoCollectionNewTransportFormatInfo);
                        oldFormatInfoIdOfTransportFormatInfoCollectionNewTransportFormatInfo = em.merge(oldFormatInfoIdOfTransportFormatInfoCollectionNewTransportFormatInfo);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = formatInfo.getId();
                if (findFormatInfo(id) == null) {
                    throw new NonexistentEntityException("The formatInfo with id " + id + " no longer exists.");
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
            FormatInfo formatInfo;
            try {
                formatInfo = em.getReference(FormatInfo.class, id);
                formatInfo.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The formatInfo with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<ClickPriceFormatInfo> clickPriceFormatInfoCollectionOrphanCheck = formatInfo.getClickPriceFormatInfoCollection();
            for (ClickPriceFormatInfo clickPriceFormatInfoCollectionOrphanCheckClickPriceFormatInfo : clickPriceFormatInfoCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This FormatInfo (" + formatInfo + ") cannot be destroyed since the ClickPriceFormatInfo " + clickPriceFormatInfoCollectionOrphanCheckClickPriceFormatInfo + " in its clickPriceFormatInfoCollection field has a non-nullable formatInfoId field.");
            }
            Collection<TransportFormatInfo> transportFormatInfoCollectionOrphanCheck = formatInfo.getTransportFormatInfoCollection();
            for (TransportFormatInfo transportFormatInfoCollectionOrphanCheckTransportFormatInfo : transportFormatInfoCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This FormatInfo (" + formatInfo + ") cannot be destroyed since the TransportFormatInfo " + transportFormatInfoCollectionOrphanCheckTransportFormatInfo + " in its transportFormatInfoCollection field has a non-nullable formatInfoId field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(formatInfo);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<FormatInfo> findFormatInfoEntities() {
        return findFormatInfoEntities(true, -1, -1);
    }

    public List<FormatInfo> findFormatInfoEntities(int maxResults, int firstResult) {
        return findFormatInfoEntities(false, maxResults, firstResult);
    }

    private List<FormatInfo> findFormatInfoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(FormatInfo.class));
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

    public FormatInfo findFormatInfo(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(FormatInfo.class, id);
        } finally {
            em.close();
        }
    }

    public int getFormatInfoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<FormatInfo> rt = cq.from(FormatInfo.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
