/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.itapteka.transport.jpacontroller;

import com.itapteka.transport.dbcontroller.ClickPriceInfo;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.itapteka.transport.dbcontroller.PharmacyClickPriceInfo;
import com.itapteka.transport.dbcontroller.SupplierClickPriceInfo;
import com.itapteka.transport.dbcontroller.Client;
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
public class ClickPriceInfoJpaController implements Serializable {

    public ClickPriceInfoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(ClickPriceInfo clickPriceInfo) throws IllegalOrphanException, PreexistingEntityException, Exception {
        List<String> illegalOrphanMessages = null;
        Client clientOrphanCheck = clickPriceInfo.getClient();
        if (clientOrphanCheck != null) {
            ClickPriceInfo oldClickPriceInfoOfClient = clientOrphanCheck.getClickPriceInfo();
            if (oldClickPriceInfoOfClient != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("The Client " + clientOrphanCheck + " already has an item of type ClickPriceInfo whose client column cannot be null. Please make another selection for the client field.");
            }
        }
        if (illegalOrphanMessages != null) {
            throw new IllegalOrphanException(illegalOrphanMessages);
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            PharmacyClickPriceInfo pharmacyClickPriceInfo = clickPriceInfo.getPharmacyClickPriceInfo();
            if (pharmacyClickPriceInfo != null) {
                pharmacyClickPriceInfo = em.getReference(pharmacyClickPriceInfo.getClass(), pharmacyClickPriceInfo.getId());
                clickPriceInfo.setPharmacyClickPriceInfo(pharmacyClickPriceInfo);
            }
            SupplierClickPriceInfo supplierClickPriceInfo = clickPriceInfo.getSupplierClickPriceInfo();
            if (supplierClickPriceInfo != null) {
                supplierClickPriceInfo = em.getReference(supplierClickPriceInfo.getClass(), supplierClickPriceInfo.getId());
                clickPriceInfo.setSupplierClickPriceInfo(supplierClickPriceInfo);
            }
            Client client = clickPriceInfo.getClient();
            if (client != null) {
                client = em.getReference(client.getClass(), client.getId());
                clickPriceInfo.setClient(client);
            }
            em.persist(clickPriceInfo);
            if (pharmacyClickPriceInfo != null) {
                ClickPriceInfo oldClickPriceInfoOfPharmacyClickPriceInfo = pharmacyClickPriceInfo.getClickPriceInfo();
                if (oldClickPriceInfoOfPharmacyClickPriceInfo != null) {
                    oldClickPriceInfoOfPharmacyClickPriceInfo.setPharmacyClickPriceInfo(null);
                    oldClickPriceInfoOfPharmacyClickPriceInfo = em.merge(oldClickPriceInfoOfPharmacyClickPriceInfo);
                }
                pharmacyClickPriceInfo.setClickPriceInfo(clickPriceInfo);
                pharmacyClickPriceInfo = em.merge(pharmacyClickPriceInfo);
            }
            if (supplierClickPriceInfo != null) {
                ClickPriceInfo oldClickPriceInfoOfSupplierClickPriceInfo = supplierClickPriceInfo.getClickPriceInfo();
                if (oldClickPriceInfoOfSupplierClickPriceInfo != null) {
                    oldClickPriceInfoOfSupplierClickPriceInfo.setSupplierClickPriceInfo(null);
                    oldClickPriceInfoOfSupplierClickPriceInfo = em.merge(oldClickPriceInfoOfSupplierClickPriceInfo);
                }
                supplierClickPriceInfo.setClickPriceInfo(clickPriceInfo);
                supplierClickPriceInfo = em.merge(supplierClickPriceInfo);
            }
            if (client != null) {
                client.setClickPriceInfo(clickPriceInfo);
                client = em.merge(client);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findClickPriceInfo(clickPriceInfo.getId()) != null) {
                throw new PreexistingEntityException("ClickPriceInfo " + clickPriceInfo + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(ClickPriceInfo clickPriceInfo) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            ClickPriceInfo persistentClickPriceInfo = em.find(ClickPriceInfo.class, clickPriceInfo.getId());
            PharmacyClickPriceInfo pharmacyClickPriceInfoOld = persistentClickPriceInfo.getPharmacyClickPriceInfo();
            PharmacyClickPriceInfo pharmacyClickPriceInfoNew = clickPriceInfo.getPharmacyClickPriceInfo();
            SupplierClickPriceInfo supplierClickPriceInfoOld = persistentClickPriceInfo.getSupplierClickPriceInfo();
            SupplierClickPriceInfo supplierClickPriceInfoNew = clickPriceInfo.getSupplierClickPriceInfo();
            Client clientOld = persistentClickPriceInfo.getClient();
            Client clientNew = clickPriceInfo.getClient();
            List<String> illegalOrphanMessages = null;
            if (pharmacyClickPriceInfoOld != null && !pharmacyClickPriceInfoOld.equals(pharmacyClickPriceInfoNew)) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("You must retain PharmacyClickPriceInfo " + pharmacyClickPriceInfoOld + " since its clickPriceInfo field is not nullable.");
            }
            if (supplierClickPriceInfoOld != null && !supplierClickPriceInfoOld.equals(supplierClickPriceInfoNew)) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("You must retain SupplierClickPriceInfo " + supplierClickPriceInfoOld + " since its clickPriceInfo field is not nullable.");
            }
            if (clientNew != null && !clientNew.equals(clientOld)) {
                ClickPriceInfo oldClickPriceInfoOfClient = clientNew.getClickPriceInfo();
                if (oldClickPriceInfoOfClient != null) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("The Client " + clientNew + " already has an item of type ClickPriceInfo whose client column cannot be null. Please make another selection for the client field.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (pharmacyClickPriceInfoNew != null) {
                pharmacyClickPriceInfoNew = em.getReference(pharmacyClickPriceInfoNew.getClass(), pharmacyClickPriceInfoNew.getId());
                clickPriceInfo.setPharmacyClickPriceInfo(pharmacyClickPriceInfoNew);
            }
            if (supplierClickPriceInfoNew != null) {
                supplierClickPriceInfoNew = em.getReference(supplierClickPriceInfoNew.getClass(), supplierClickPriceInfoNew.getId());
                clickPriceInfo.setSupplierClickPriceInfo(supplierClickPriceInfoNew);
            }
            if (clientNew != null) {
                clientNew = em.getReference(clientNew.getClass(), clientNew.getId());
                clickPriceInfo.setClient(clientNew);
            }
            clickPriceInfo = em.merge(clickPriceInfo);
            if (pharmacyClickPriceInfoNew != null && !pharmacyClickPriceInfoNew.equals(pharmacyClickPriceInfoOld)) {
                ClickPriceInfo oldClickPriceInfoOfPharmacyClickPriceInfo = pharmacyClickPriceInfoNew.getClickPriceInfo();
                if (oldClickPriceInfoOfPharmacyClickPriceInfo != null) {
                    oldClickPriceInfoOfPharmacyClickPriceInfo.setPharmacyClickPriceInfo(null);
                    oldClickPriceInfoOfPharmacyClickPriceInfo = em.merge(oldClickPriceInfoOfPharmacyClickPriceInfo);
                }
                pharmacyClickPriceInfoNew.setClickPriceInfo(clickPriceInfo);
                pharmacyClickPriceInfoNew = em.merge(pharmacyClickPriceInfoNew);
            }
            if (supplierClickPriceInfoNew != null && !supplierClickPriceInfoNew.equals(supplierClickPriceInfoOld)) {
                ClickPriceInfo oldClickPriceInfoOfSupplierClickPriceInfo = supplierClickPriceInfoNew.getClickPriceInfo();
                if (oldClickPriceInfoOfSupplierClickPriceInfo != null) {
                    oldClickPriceInfoOfSupplierClickPriceInfo.setSupplierClickPriceInfo(null);
                    oldClickPriceInfoOfSupplierClickPriceInfo = em.merge(oldClickPriceInfoOfSupplierClickPriceInfo);
                }
                supplierClickPriceInfoNew.setClickPriceInfo(clickPriceInfo);
                supplierClickPriceInfoNew = em.merge(supplierClickPriceInfoNew);
            }
            if (clientOld != null && !clientOld.equals(clientNew)) {
                clientOld.setClickPriceInfo(null);
                clientOld = em.merge(clientOld);
            }
            if (clientNew != null && !clientNew.equals(clientOld)) {
                clientNew.setClickPriceInfo(clickPriceInfo);
                clientNew = em.merge(clientNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = clickPriceInfo.getId();
                if (findClickPriceInfo(id) == null) {
                    throw new NonexistentEntityException("The clickPriceInfo with id " + id + " no longer exists.");
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
            ClickPriceInfo clickPriceInfo;
            try {
                clickPriceInfo = em.getReference(ClickPriceInfo.class, id);
                clickPriceInfo.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The clickPriceInfo with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            PharmacyClickPriceInfo pharmacyClickPriceInfoOrphanCheck = clickPriceInfo.getPharmacyClickPriceInfo();
            if (pharmacyClickPriceInfoOrphanCheck != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This ClickPriceInfo (" + clickPriceInfo + ") cannot be destroyed since the PharmacyClickPriceInfo " + pharmacyClickPriceInfoOrphanCheck + " in its pharmacyClickPriceInfo field has a non-nullable clickPriceInfo field.");
            }
            SupplierClickPriceInfo supplierClickPriceInfoOrphanCheck = clickPriceInfo.getSupplierClickPriceInfo();
            if (supplierClickPriceInfoOrphanCheck != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This ClickPriceInfo (" + clickPriceInfo + ") cannot be destroyed since the SupplierClickPriceInfo " + supplierClickPriceInfoOrphanCheck + " in its supplierClickPriceInfo field has a non-nullable clickPriceInfo field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Client client = clickPriceInfo.getClient();
            if (client != null) {
                client.setClickPriceInfo(null);
                client = em.merge(client);
            }
            em.remove(clickPriceInfo);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<ClickPriceInfo> findClickPriceInfoEntities() {
        return findClickPriceInfoEntities(true, -1, -1);
    }

    public List<ClickPriceInfo> findClickPriceInfoEntities(int maxResults, int firstResult) {
        return findClickPriceInfoEntities(false, maxResults, firstResult);
    }

    private List<ClickPriceInfo> findClickPriceInfoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(ClickPriceInfo.class));
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

    public ClickPriceInfo findClickPriceInfo(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(ClickPriceInfo.class, id);
        } finally {
            em.close();
        }
    }

    public int getClickPriceInfoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<ClickPriceInfo> rt = cq.from(ClickPriceInfo.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
