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
import com.itapteka.transport.dbcontroller.SupplierSettings;
import com.itapteka.transport.dbcontroller.TransportFormatInfo;
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
public class SupplierSettingsJpaController implements Serializable {

    public SupplierSettingsJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(SupplierSettings supplierSettings) throws IllegalOrphanException, PreexistingEntityException, Exception {
        if (supplierSettings.getTransportFormatInfoCollection() == null) {
            supplierSettings.setTransportFormatInfoCollection(new ArrayList<TransportFormatInfo>());
        }
        List<String> illegalOrphanMessages = null;
        Client clientOrphanCheck = supplierSettings.getClient();
        if (clientOrphanCheck != null) {
            SupplierSettings oldSupplierSettingsOfClient = clientOrphanCheck.getSupplierSettings();
            if (oldSupplierSettingsOfClient != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("The Client " + clientOrphanCheck + " already has an item of type SupplierSettings whose client column cannot be null. Please make another selection for the client field.");
            }
        }
        if (illegalOrphanMessages != null) {
            throw new IllegalOrphanException(illegalOrphanMessages);
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Client client = supplierSettings.getClient();
            if (client != null) {
                client = em.getReference(client.getClass(), client.getId());
                supplierSettings.setClient(client);
            }
            Collection<TransportFormatInfo> attachedTransportFormatInfoCollection = new ArrayList<TransportFormatInfo>();
            for (TransportFormatInfo transportFormatInfoCollectionTransportFormatInfoToAttach : supplierSettings.getTransportFormatInfoCollection()) {
                transportFormatInfoCollectionTransportFormatInfoToAttach = em.getReference(transportFormatInfoCollectionTransportFormatInfoToAttach.getClass(), transportFormatInfoCollectionTransportFormatInfoToAttach.getId());
                attachedTransportFormatInfoCollection.add(transportFormatInfoCollectionTransportFormatInfoToAttach);
            }
            supplierSettings.setTransportFormatInfoCollection(attachedTransportFormatInfoCollection);
            em.persist(supplierSettings);
            if (client != null) {
                client.setSupplierSettings(supplierSettings);
                client = em.merge(client);
            }
            for (TransportFormatInfo transportFormatInfoCollectionTransportFormatInfo : supplierSettings.getTransportFormatInfoCollection()) {
                SupplierSettings oldSupplierSettingsIdOfTransportFormatInfoCollectionTransportFormatInfo = transportFormatInfoCollectionTransportFormatInfo.getSupplierSettingsId();
                transportFormatInfoCollectionTransportFormatInfo.setSupplierSettingsId(supplierSettings);
                transportFormatInfoCollectionTransportFormatInfo = em.merge(transportFormatInfoCollectionTransportFormatInfo);
                if (oldSupplierSettingsIdOfTransportFormatInfoCollectionTransportFormatInfo != null) {
                    oldSupplierSettingsIdOfTransportFormatInfoCollectionTransportFormatInfo.getTransportFormatInfoCollection().remove(transportFormatInfoCollectionTransportFormatInfo);
                    oldSupplierSettingsIdOfTransportFormatInfoCollectionTransportFormatInfo = em.merge(oldSupplierSettingsIdOfTransportFormatInfoCollectionTransportFormatInfo);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findSupplierSettings(supplierSettings.getId()) != null) {
                throw new PreexistingEntityException("SupplierSettings " + supplierSettings + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(SupplierSettings supplierSettings) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            SupplierSettings persistentSupplierSettings = em.find(SupplierSettings.class, supplierSettings.getId());
            Client clientOld = persistentSupplierSettings.getClient();
            Client clientNew = supplierSettings.getClient();
            Collection<TransportFormatInfo> transportFormatInfoCollectionOld = persistentSupplierSettings.getTransportFormatInfoCollection();
            Collection<TransportFormatInfo> transportFormatInfoCollectionNew = supplierSettings.getTransportFormatInfoCollection();
            List<String> illegalOrphanMessages = null;
            if (clientNew != null && !clientNew.equals(clientOld)) {
                SupplierSettings oldSupplierSettingsOfClient = clientNew.getSupplierSettings();
                if (oldSupplierSettingsOfClient != null) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("The Client " + clientNew + " already has an item of type SupplierSettings whose client column cannot be null. Please make another selection for the client field.");
                }
            }
            for (TransportFormatInfo transportFormatInfoCollectionOldTransportFormatInfo : transportFormatInfoCollectionOld) {
                if (!transportFormatInfoCollectionNew.contains(transportFormatInfoCollectionOldTransportFormatInfo)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain TransportFormatInfo " + transportFormatInfoCollectionOldTransportFormatInfo + " since its supplierSettingsId field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (clientNew != null) {
                clientNew = em.getReference(clientNew.getClass(), clientNew.getId());
                supplierSettings.setClient(clientNew);
            }
            Collection<TransportFormatInfo> attachedTransportFormatInfoCollectionNew = new ArrayList<TransportFormatInfo>();
            for (TransportFormatInfo transportFormatInfoCollectionNewTransportFormatInfoToAttach : transportFormatInfoCollectionNew) {
                transportFormatInfoCollectionNewTransportFormatInfoToAttach = em.getReference(transportFormatInfoCollectionNewTransportFormatInfoToAttach.getClass(), transportFormatInfoCollectionNewTransportFormatInfoToAttach.getId());
                attachedTransportFormatInfoCollectionNew.add(transportFormatInfoCollectionNewTransportFormatInfoToAttach);
            }
            transportFormatInfoCollectionNew = attachedTransportFormatInfoCollectionNew;
            supplierSettings.setTransportFormatInfoCollection(transportFormatInfoCollectionNew);
            supplierSettings = em.merge(supplierSettings);
            if (clientOld != null && !clientOld.equals(clientNew)) {
                clientOld.setSupplierSettings(null);
                clientOld = em.merge(clientOld);
            }
            if (clientNew != null && !clientNew.equals(clientOld)) {
                clientNew.setSupplierSettings(supplierSettings);
                clientNew = em.merge(clientNew);
            }
            for (TransportFormatInfo transportFormatInfoCollectionNewTransportFormatInfo : transportFormatInfoCollectionNew) {
                if (!transportFormatInfoCollectionOld.contains(transportFormatInfoCollectionNewTransportFormatInfo)) {
                    SupplierSettings oldSupplierSettingsIdOfTransportFormatInfoCollectionNewTransportFormatInfo = transportFormatInfoCollectionNewTransportFormatInfo.getSupplierSettingsId();
                    transportFormatInfoCollectionNewTransportFormatInfo.setSupplierSettingsId(supplierSettings);
                    transportFormatInfoCollectionNewTransportFormatInfo = em.merge(transportFormatInfoCollectionNewTransportFormatInfo);
                    if (oldSupplierSettingsIdOfTransportFormatInfoCollectionNewTransportFormatInfo != null && !oldSupplierSettingsIdOfTransportFormatInfoCollectionNewTransportFormatInfo.equals(supplierSettings)) {
                        oldSupplierSettingsIdOfTransportFormatInfoCollectionNewTransportFormatInfo.getTransportFormatInfoCollection().remove(transportFormatInfoCollectionNewTransportFormatInfo);
                        oldSupplierSettingsIdOfTransportFormatInfoCollectionNewTransportFormatInfo = em.merge(oldSupplierSettingsIdOfTransportFormatInfoCollectionNewTransportFormatInfo);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = supplierSettings.getId();
                if (findSupplierSettings(id) == null) {
                    throw new NonexistentEntityException("The supplierSettings with id " + id + " no longer exists.");
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
            SupplierSettings supplierSettings;
            try {
                supplierSettings = em.getReference(SupplierSettings.class, id);
                supplierSettings.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The supplierSettings with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<TransportFormatInfo> transportFormatInfoCollectionOrphanCheck = supplierSettings.getTransportFormatInfoCollection();
            for (TransportFormatInfo transportFormatInfoCollectionOrphanCheckTransportFormatInfo : transportFormatInfoCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This SupplierSettings (" + supplierSettings + ") cannot be destroyed since the TransportFormatInfo " + transportFormatInfoCollectionOrphanCheckTransportFormatInfo + " in its transportFormatInfoCollection field has a non-nullable supplierSettingsId field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Client client = supplierSettings.getClient();
            if (client != null) {
                client.setSupplierSettings(null);
                client = em.merge(client);
            }
            em.remove(supplierSettings);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<SupplierSettings> findSupplierSettingsEntities() {
        return findSupplierSettingsEntities(true, -1, -1);
    }

    public List<SupplierSettings> findSupplierSettingsEntities(int maxResults, int firstResult) {
        return findSupplierSettingsEntities(false, maxResults, firstResult);
    }

    private List<SupplierSettings> findSupplierSettingsEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(SupplierSettings.class));
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

    public SupplierSettings findSupplierSettings(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(SupplierSettings.class, id);
        } finally {
            em.close();
        }
    }

    public int getSupplierSettingsCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<SupplierSettings> rt = cq.from(SupplierSettings.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
