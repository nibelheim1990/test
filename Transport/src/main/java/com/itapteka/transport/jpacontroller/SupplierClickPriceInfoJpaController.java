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
import com.itapteka.transport.dbcontroller.ClickPriceInfo;
import com.itapteka.transport.dbcontroller.ClientSendSettings;
import com.itapteka.transport.dbcontroller.ClickPriceFormatInfo;
import com.itapteka.transport.dbcontroller.SupplierClickPriceInfo;
import com.itapteka.transport.jpacontroller.exceptions.IllegalOrphanException;
import com.itapteka.transport.jpacontroller.exceptions.NonexistentEntityException;
import com.itapteka.transport.jpacontroller.exceptions.PreexistingEntityException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Ponomarev
 */
public class SupplierClickPriceInfoJpaController implements Serializable {

    public SupplierClickPriceInfoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(SupplierClickPriceInfo supplierClickPriceInfo) throws IllegalOrphanException, PreexistingEntityException, Exception {
        if (supplierClickPriceInfo.getClickPriceFormatInfoCollection() == null) {
            supplierClickPriceInfo.setClickPriceFormatInfoCollection(new ArrayList<ClickPriceFormatInfo>());
        }
        List<String> illegalOrphanMessages = null;
        ClickPriceInfo clickPriceInfoOrphanCheck = supplierClickPriceInfo.getClickPriceInfo();
        if (clickPriceInfoOrphanCheck != null) {
            SupplierClickPriceInfo oldSupplierClickPriceInfoOfClickPriceInfo = clickPriceInfoOrphanCheck.getSupplierClickPriceInfo();
            if (oldSupplierClickPriceInfoOfClickPriceInfo != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("The ClickPriceInfo " + clickPriceInfoOrphanCheck + " already has an item of type SupplierClickPriceInfo whose clickPriceInfo column cannot be null. Please make another selection for the clickPriceInfo field.");
            }
        }
        if (illegalOrphanMessages != null) {
            throw new IllegalOrphanException(illegalOrphanMessages);
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            ClickPriceInfo clickPriceInfo = supplierClickPriceInfo.getClickPriceInfo();
            if (clickPriceInfo != null) {
                clickPriceInfo = em.getReference(clickPriceInfo.getClass(), clickPriceInfo.getId());
                supplierClickPriceInfo.setClickPriceInfo(clickPriceInfo);
            }
            ClientSendSettings sendSettingsId = supplierClickPriceInfo.getSendSettingsId();
            if (sendSettingsId != null) {
                sendSettingsId = em.getReference(sendSettingsId.getClass(), sendSettingsId.getId());
                supplierClickPriceInfo.setSendSettingsId(sendSettingsId);
            }
            Collection<ClickPriceFormatInfo> attachedClickPriceFormatInfoCollection = new ArrayList<ClickPriceFormatInfo>();
            for (ClickPriceFormatInfo clickPriceFormatInfoCollectionClickPriceFormatInfoToAttach : supplierClickPriceInfo.getClickPriceFormatInfoCollection()) {
                clickPriceFormatInfoCollectionClickPriceFormatInfoToAttach = em.getReference(clickPriceFormatInfoCollectionClickPriceFormatInfoToAttach.getClass(), clickPriceFormatInfoCollectionClickPriceFormatInfoToAttach.getId());
                attachedClickPriceFormatInfoCollection.add(clickPriceFormatInfoCollectionClickPriceFormatInfoToAttach);
            }
            supplierClickPriceInfo.setClickPriceFormatInfoCollection(attachedClickPriceFormatInfoCollection);
            em.persist(supplierClickPriceInfo);
            if (clickPriceInfo != null) {
                clickPriceInfo.setSupplierClickPriceInfo(supplierClickPriceInfo);
                clickPriceInfo = em.merge(clickPriceInfo);
            }
            if (sendSettingsId != null) {
                sendSettingsId.getSupplierClickPriceInfoCollection().add(supplierClickPriceInfo);
                sendSettingsId = em.merge(sendSettingsId);
            }
            for (ClickPriceFormatInfo clickPriceFormatInfoCollectionClickPriceFormatInfo : supplierClickPriceInfo.getClickPriceFormatInfoCollection()) {
                SupplierClickPriceInfo oldSupplierClickPriceInfoIdOfClickPriceFormatInfoCollectionClickPriceFormatInfo = clickPriceFormatInfoCollectionClickPriceFormatInfo.getSupplierClickPriceInfoId();
                clickPriceFormatInfoCollectionClickPriceFormatInfo.setSupplierClickPriceInfoId(supplierClickPriceInfo);
                clickPriceFormatInfoCollectionClickPriceFormatInfo = em.merge(clickPriceFormatInfoCollectionClickPriceFormatInfo);
                if (oldSupplierClickPriceInfoIdOfClickPriceFormatInfoCollectionClickPriceFormatInfo != null) {
                    oldSupplierClickPriceInfoIdOfClickPriceFormatInfoCollectionClickPriceFormatInfo.getClickPriceFormatInfoCollection().remove(clickPriceFormatInfoCollectionClickPriceFormatInfo);
                    oldSupplierClickPriceInfoIdOfClickPriceFormatInfoCollectionClickPriceFormatInfo = em.merge(oldSupplierClickPriceInfoIdOfClickPriceFormatInfoCollectionClickPriceFormatInfo);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findSupplierClickPriceInfo(supplierClickPriceInfo.getId()) != null) {
                throw new PreexistingEntityException("SupplierClickPriceInfo " + supplierClickPriceInfo + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(SupplierClickPriceInfo supplierClickPriceInfo) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            SupplierClickPriceInfo persistentSupplierClickPriceInfo = em.find(SupplierClickPriceInfo.class, supplierClickPriceInfo.getId());
            ClickPriceInfo clickPriceInfoOld = persistentSupplierClickPriceInfo.getClickPriceInfo();
            ClickPriceInfo clickPriceInfoNew = supplierClickPriceInfo.getClickPriceInfo();
            ClientSendSettings sendSettingsIdOld = persistentSupplierClickPriceInfo.getSendSettingsId();
            ClientSendSettings sendSettingsIdNew = supplierClickPriceInfo.getSendSettingsId();
            Collection<ClickPriceFormatInfo> clickPriceFormatInfoCollectionOld = persistentSupplierClickPriceInfo.getClickPriceFormatInfoCollection();
            Collection<ClickPriceFormatInfo> clickPriceFormatInfoCollectionNew = supplierClickPriceInfo.getClickPriceFormatInfoCollection();
            List<String> illegalOrphanMessages = null;
            if (clickPriceInfoNew != null && !clickPriceInfoNew.equals(clickPriceInfoOld)) {
                SupplierClickPriceInfo oldSupplierClickPriceInfoOfClickPriceInfo = clickPriceInfoNew.getSupplierClickPriceInfo();
                if (oldSupplierClickPriceInfoOfClickPriceInfo != null) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("The ClickPriceInfo " + clickPriceInfoNew + " already has an item of type SupplierClickPriceInfo whose clickPriceInfo column cannot be null. Please make another selection for the clickPriceInfo field.");
                }
            }
            for (ClickPriceFormatInfo clickPriceFormatInfoCollectionOldClickPriceFormatInfo : clickPriceFormatInfoCollectionOld) {
                if (!clickPriceFormatInfoCollectionNew.contains(clickPriceFormatInfoCollectionOldClickPriceFormatInfo)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain ClickPriceFormatInfo " + clickPriceFormatInfoCollectionOldClickPriceFormatInfo + " since its supplierClickPriceInfoId field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (clickPriceInfoNew != null) {
                clickPriceInfoNew = em.getReference(clickPriceInfoNew.getClass(), clickPriceInfoNew.getId());
                supplierClickPriceInfo.setClickPriceInfo(clickPriceInfoNew);
            }
            if (sendSettingsIdNew != null) {
                sendSettingsIdNew = em.getReference(sendSettingsIdNew.getClass(), sendSettingsIdNew.getId());
                supplierClickPriceInfo.setSendSettingsId(sendSettingsIdNew);
            }
            Collection<ClickPriceFormatInfo> attachedClickPriceFormatInfoCollectionNew = new ArrayList<ClickPriceFormatInfo>();
            for (ClickPriceFormatInfo clickPriceFormatInfoCollectionNewClickPriceFormatInfoToAttach : clickPriceFormatInfoCollectionNew) {
                clickPriceFormatInfoCollectionNewClickPriceFormatInfoToAttach = em.getReference(clickPriceFormatInfoCollectionNewClickPriceFormatInfoToAttach.getClass(), clickPriceFormatInfoCollectionNewClickPriceFormatInfoToAttach.getId());
                attachedClickPriceFormatInfoCollectionNew.add(clickPriceFormatInfoCollectionNewClickPriceFormatInfoToAttach);
            }
            clickPriceFormatInfoCollectionNew = attachedClickPriceFormatInfoCollectionNew;
            supplierClickPriceInfo.setClickPriceFormatInfoCollection(clickPriceFormatInfoCollectionNew);
            supplierClickPriceInfo = em.merge(supplierClickPriceInfo);
            if (clickPriceInfoOld != null && !clickPriceInfoOld.equals(clickPriceInfoNew)) {
                clickPriceInfoOld.setSupplierClickPriceInfo(null);
                clickPriceInfoOld = em.merge(clickPriceInfoOld);
            }
            if (clickPriceInfoNew != null && !clickPriceInfoNew.equals(clickPriceInfoOld)) {
                clickPriceInfoNew.setSupplierClickPriceInfo(supplierClickPriceInfo);
                clickPriceInfoNew = em.merge(clickPriceInfoNew);
            }
            if (sendSettingsIdOld != null && !sendSettingsIdOld.equals(sendSettingsIdNew)) {
                sendSettingsIdOld.getSupplierClickPriceInfoCollection().remove(supplierClickPriceInfo);
                sendSettingsIdOld = em.merge(sendSettingsIdOld);
            }
            if (sendSettingsIdNew != null && !sendSettingsIdNew.equals(sendSettingsIdOld)) {
                sendSettingsIdNew.getSupplierClickPriceInfoCollection().add(supplierClickPriceInfo);
                sendSettingsIdNew = em.merge(sendSettingsIdNew);
            }
            for (ClickPriceFormatInfo clickPriceFormatInfoCollectionNewClickPriceFormatInfo : clickPriceFormatInfoCollectionNew) {
                if (!clickPriceFormatInfoCollectionOld.contains(clickPriceFormatInfoCollectionNewClickPriceFormatInfo)) {
                    SupplierClickPriceInfo oldSupplierClickPriceInfoIdOfClickPriceFormatInfoCollectionNewClickPriceFormatInfo = clickPriceFormatInfoCollectionNewClickPriceFormatInfo.getSupplierClickPriceInfoId();
                    clickPriceFormatInfoCollectionNewClickPriceFormatInfo.setSupplierClickPriceInfoId(supplierClickPriceInfo);
                    clickPriceFormatInfoCollectionNewClickPriceFormatInfo = em.merge(clickPriceFormatInfoCollectionNewClickPriceFormatInfo);
                    if (oldSupplierClickPriceInfoIdOfClickPriceFormatInfoCollectionNewClickPriceFormatInfo != null && !oldSupplierClickPriceInfoIdOfClickPriceFormatInfoCollectionNewClickPriceFormatInfo.equals(supplierClickPriceInfo)) {
                        oldSupplierClickPriceInfoIdOfClickPriceFormatInfoCollectionNewClickPriceFormatInfo.getClickPriceFormatInfoCollection().remove(clickPriceFormatInfoCollectionNewClickPriceFormatInfo);
                        oldSupplierClickPriceInfoIdOfClickPriceFormatInfoCollectionNewClickPriceFormatInfo = em.merge(oldSupplierClickPriceInfoIdOfClickPriceFormatInfoCollectionNewClickPriceFormatInfo);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = supplierClickPriceInfo.getId();
                if (findSupplierClickPriceInfo(id) == null) {
                    throw new NonexistentEntityException("The supplierClickPriceInfo with id " + id + " no longer exists.");
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
            SupplierClickPriceInfo supplierClickPriceInfo;
            try {
                supplierClickPriceInfo = em.getReference(SupplierClickPriceInfo.class, id);
                supplierClickPriceInfo.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The supplierClickPriceInfo with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<ClickPriceFormatInfo> clickPriceFormatInfoCollectionOrphanCheck = supplierClickPriceInfo.getClickPriceFormatInfoCollection();
            for (ClickPriceFormatInfo clickPriceFormatInfoCollectionOrphanCheckClickPriceFormatInfo : clickPriceFormatInfoCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This SupplierClickPriceInfo (" + supplierClickPriceInfo + ") cannot be destroyed since the ClickPriceFormatInfo " + clickPriceFormatInfoCollectionOrphanCheckClickPriceFormatInfo + " in its clickPriceFormatInfoCollection field has a non-nullable supplierClickPriceInfoId field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            ClickPriceInfo clickPriceInfo = supplierClickPriceInfo.getClickPriceInfo();
            if (clickPriceInfo != null) {
                clickPriceInfo.setSupplierClickPriceInfo(null);
                clickPriceInfo = em.merge(clickPriceInfo);
            }
            ClientSendSettings sendSettingsId = supplierClickPriceInfo.getSendSettingsId();
            if (sendSettingsId != null) {
                sendSettingsId.getSupplierClickPriceInfoCollection().remove(supplierClickPriceInfo);
                sendSettingsId = em.merge(sendSettingsId);
            }
            em.remove(supplierClickPriceInfo);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<SupplierClickPriceInfo> findSupplierClickPriceInfoEntities() {
        return findSupplierClickPriceInfoEntities(true, -1, -1);
    }

    public List<SupplierClickPriceInfo> findSupplierClickPriceInfoEntities(int maxResults, int firstResult) {
        return findSupplierClickPriceInfoEntities(false, maxResults, firstResult);
    }

    private List<SupplierClickPriceInfo> findSupplierClickPriceInfoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(SupplierClickPriceInfo.class));
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

    public SupplierClickPriceInfo findSupplierClickPriceInfo(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(SupplierClickPriceInfo.class, id);
        } finally {
            em.close();
        }
    }

    public int getSupplierClickPriceInfoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<SupplierClickPriceInfo> rt = cq.from(SupplierClickPriceInfo.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
