/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.itapteka.transport.jpacontroller;

import com.itapteka.transport.dbcontroller.ClientSendSettings;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.itapteka.transport.dbcontroller.SupplierClickPriceInfo;
import com.itapteka.transport.jpacontroller.exceptions.NonexistentEntityException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Ponomarev
 */
public class ClientSendSettingsJpaController implements Serializable {

    public ClientSendSettingsJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(ClientSendSettings clientSendSettings) {
        if (clientSendSettings.getSupplierClickPriceInfoCollection() == null) {
            clientSendSettings.setSupplierClickPriceInfoCollection(new ArrayList<SupplierClickPriceInfo>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<SupplierClickPriceInfo> attachedSupplierClickPriceInfoCollection = new ArrayList<SupplierClickPriceInfo>();
            for (SupplierClickPriceInfo supplierClickPriceInfoCollectionSupplierClickPriceInfoToAttach : clientSendSettings.getSupplierClickPriceInfoCollection()) {
                supplierClickPriceInfoCollectionSupplierClickPriceInfoToAttach = em.getReference(supplierClickPriceInfoCollectionSupplierClickPriceInfoToAttach.getClass(), supplierClickPriceInfoCollectionSupplierClickPriceInfoToAttach.getId());
                attachedSupplierClickPriceInfoCollection.add(supplierClickPriceInfoCollectionSupplierClickPriceInfoToAttach);
            }
            clientSendSettings.setSupplierClickPriceInfoCollection(attachedSupplierClickPriceInfoCollection);
            em.persist(clientSendSettings);
            for (SupplierClickPriceInfo supplierClickPriceInfoCollectionSupplierClickPriceInfo : clientSendSettings.getSupplierClickPriceInfoCollection()) {
                ClientSendSettings oldSendSettingsIdOfSupplierClickPriceInfoCollectionSupplierClickPriceInfo = supplierClickPriceInfoCollectionSupplierClickPriceInfo.getSendSettingsId();
                supplierClickPriceInfoCollectionSupplierClickPriceInfo.setSendSettingsId(clientSendSettings);
                supplierClickPriceInfoCollectionSupplierClickPriceInfo = em.merge(supplierClickPriceInfoCollectionSupplierClickPriceInfo);
                if (oldSendSettingsIdOfSupplierClickPriceInfoCollectionSupplierClickPriceInfo != null) {
                    oldSendSettingsIdOfSupplierClickPriceInfoCollectionSupplierClickPriceInfo.getSupplierClickPriceInfoCollection().remove(supplierClickPriceInfoCollectionSupplierClickPriceInfo);
                    oldSendSettingsIdOfSupplierClickPriceInfoCollectionSupplierClickPriceInfo = em.merge(oldSendSettingsIdOfSupplierClickPriceInfoCollectionSupplierClickPriceInfo);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(ClientSendSettings clientSendSettings) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            ClientSendSettings persistentClientSendSettings = em.find(ClientSendSettings.class, clientSendSettings.getId());
            Collection<SupplierClickPriceInfo> supplierClickPriceInfoCollectionOld = persistentClientSendSettings.getSupplierClickPriceInfoCollection();
            Collection<SupplierClickPriceInfo> supplierClickPriceInfoCollectionNew = clientSendSettings.getSupplierClickPriceInfoCollection();
            Collection<SupplierClickPriceInfo> attachedSupplierClickPriceInfoCollectionNew = new ArrayList<SupplierClickPriceInfo>();
            for (SupplierClickPriceInfo supplierClickPriceInfoCollectionNewSupplierClickPriceInfoToAttach : supplierClickPriceInfoCollectionNew) {
                supplierClickPriceInfoCollectionNewSupplierClickPriceInfoToAttach = em.getReference(supplierClickPriceInfoCollectionNewSupplierClickPriceInfoToAttach.getClass(), supplierClickPriceInfoCollectionNewSupplierClickPriceInfoToAttach.getId());
                attachedSupplierClickPriceInfoCollectionNew.add(supplierClickPriceInfoCollectionNewSupplierClickPriceInfoToAttach);
            }
            supplierClickPriceInfoCollectionNew = attachedSupplierClickPriceInfoCollectionNew;
            clientSendSettings.setSupplierClickPriceInfoCollection(supplierClickPriceInfoCollectionNew);
            clientSendSettings = em.merge(clientSendSettings);
            for (SupplierClickPriceInfo supplierClickPriceInfoCollectionOldSupplierClickPriceInfo : supplierClickPriceInfoCollectionOld) {
                if (!supplierClickPriceInfoCollectionNew.contains(supplierClickPriceInfoCollectionOldSupplierClickPriceInfo)) {
                    supplierClickPriceInfoCollectionOldSupplierClickPriceInfo.setSendSettingsId(null);
                    supplierClickPriceInfoCollectionOldSupplierClickPriceInfo = em.merge(supplierClickPriceInfoCollectionOldSupplierClickPriceInfo);
                }
            }
            for (SupplierClickPriceInfo supplierClickPriceInfoCollectionNewSupplierClickPriceInfo : supplierClickPriceInfoCollectionNew) {
                if (!supplierClickPriceInfoCollectionOld.contains(supplierClickPriceInfoCollectionNewSupplierClickPriceInfo)) {
                    ClientSendSettings oldSendSettingsIdOfSupplierClickPriceInfoCollectionNewSupplierClickPriceInfo = supplierClickPriceInfoCollectionNewSupplierClickPriceInfo.getSendSettingsId();
                    supplierClickPriceInfoCollectionNewSupplierClickPriceInfo.setSendSettingsId(clientSendSettings);
                    supplierClickPriceInfoCollectionNewSupplierClickPriceInfo = em.merge(supplierClickPriceInfoCollectionNewSupplierClickPriceInfo);
                    if (oldSendSettingsIdOfSupplierClickPriceInfoCollectionNewSupplierClickPriceInfo != null && !oldSendSettingsIdOfSupplierClickPriceInfoCollectionNewSupplierClickPriceInfo.equals(clientSendSettings)) {
                        oldSendSettingsIdOfSupplierClickPriceInfoCollectionNewSupplierClickPriceInfo.getSupplierClickPriceInfoCollection().remove(supplierClickPriceInfoCollectionNewSupplierClickPriceInfo);
                        oldSendSettingsIdOfSupplierClickPriceInfoCollectionNewSupplierClickPriceInfo = em.merge(oldSendSettingsIdOfSupplierClickPriceInfoCollectionNewSupplierClickPriceInfo);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = clientSendSettings.getId();
                if (findClientSendSettings(id) == null) {
                    throw new NonexistentEntityException("The clientSendSettings with id " + id + " no longer exists.");
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
            ClientSendSettings clientSendSettings;
            try {
                clientSendSettings = em.getReference(ClientSendSettings.class, id);
                clientSendSettings.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The clientSendSettings with id " + id + " no longer exists.", enfe);
            }
            Collection<SupplierClickPriceInfo> supplierClickPriceInfoCollection = clientSendSettings.getSupplierClickPriceInfoCollection();
            for (SupplierClickPriceInfo supplierClickPriceInfoCollectionSupplierClickPriceInfo : supplierClickPriceInfoCollection) {
                supplierClickPriceInfoCollectionSupplierClickPriceInfo.setSendSettingsId(null);
                supplierClickPriceInfoCollectionSupplierClickPriceInfo = em.merge(supplierClickPriceInfoCollectionSupplierClickPriceInfo);
            }
            em.remove(clientSendSettings);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<ClientSendSettings> findClientSendSettingsEntities() {
        return findClientSendSettingsEntities(true, -1, -1);
    }

    public List<ClientSendSettings> findClientSendSettingsEntities(int maxResults, int firstResult) {
        return findClientSendSettingsEntities(false, maxResults, firstResult);
    }

    private List<ClientSendSettings> findClientSendSettingsEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(ClientSendSettings.class));
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

    public ClientSendSettings findClientSendSettings(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(ClientSendSettings.class, id);
        } finally {
            em.close();
        }
    }

    public int getClientSendSettingsCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<ClientSendSettings> rt = cq.from(ClientSendSettings.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
