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
import com.itapteka.transport.dbcontroller.PharmacyClickPriceInfo;
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
public class PharmacyClickPriceInfoJpaController implements Serializable {

    public PharmacyClickPriceInfoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(PharmacyClickPriceInfo pharmacyClickPriceInfo) throws IllegalOrphanException, PreexistingEntityException, Exception {
        List<String> illegalOrphanMessages = null;
        ClickPriceInfo clickPriceInfoOrphanCheck = pharmacyClickPriceInfo.getClickPriceInfo();
        if (clickPriceInfoOrphanCheck != null) {
            PharmacyClickPriceInfo oldPharmacyClickPriceInfoOfClickPriceInfo = clickPriceInfoOrphanCheck.getPharmacyClickPriceInfo();
            if (oldPharmacyClickPriceInfoOfClickPriceInfo != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("The ClickPriceInfo " + clickPriceInfoOrphanCheck + " already has an item of type PharmacyClickPriceInfo whose clickPriceInfo column cannot be null. Please make another selection for the clickPriceInfo field.");
            }
        }
        if (illegalOrphanMessages != null) {
            throw new IllegalOrphanException(illegalOrphanMessages);
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            ClickPriceInfo clickPriceInfo = pharmacyClickPriceInfo.getClickPriceInfo();
            if (clickPriceInfo != null) {
                clickPriceInfo = em.getReference(clickPriceInfo.getClass(), clickPriceInfo.getId());
                pharmacyClickPriceInfo.setClickPriceInfo(clickPriceInfo);
            }
            em.persist(pharmacyClickPriceInfo);
            if (clickPriceInfo != null) {
                clickPriceInfo.setPharmacyClickPriceInfo(pharmacyClickPriceInfo);
                clickPriceInfo = em.merge(clickPriceInfo);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findPharmacyClickPriceInfo(pharmacyClickPriceInfo.getId()) != null) {
                throw new PreexistingEntityException("PharmacyClickPriceInfo " + pharmacyClickPriceInfo + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(PharmacyClickPriceInfo pharmacyClickPriceInfo) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            PharmacyClickPriceInfo persistentPharmacyClickPriceInfo = em.find(PharmacyClickPriceInfo.class, pharmacyClickPriceInfo.getId());
            ClickPriceInfo clickPriceInfoOld = persistentPharmacyClickPriceInfo.getClickPriceInfo();
            ClickPriceInfo clickPriceInfoNew = pharmacyClickPriceInfo.getClickPriceInfo();
            List<String> illegalOrphanMessages = null;
            if (clickPriceInfoNew != null && !clickPriceInfoNew.equals(clickPriceInfoOld)) {
                PharmacyClickPriceInfo oldPharmacyClickPriceInfoOfClickPriceInfo = clickPriceInfoNew.getPharmacyClickPriceInfo();
                if (oldPharmacyClickPriceInfoOfClickPriceInfo != null) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("The ClickPriceInfo " + clickPriceInfoNew + " already has an item of type PharmacyClickPriceInfo whose clickPriceInfo column cannot be null. Please make another selection for the clickPriceInfo field.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (clickPriceInfoNew != null) {
                clickPriceInfoNew = em.getReference(clickPriceInfoNew.getClass(), clickPriceInfoNew.getId());
                pharmacyClickPriceInfo.setClickPriceInfo(clickPriceInfoNew);
            }
            pharmacyClickPriceInfo = em.merge(pharmacyClickPriceInfo);
            if (clickPriceInfoOld != null && !clickPriceInfoOld.equals(clickPriceInfoNew)) {
                clickPriceInfoOld.setPharmacyClickPriceInfo(null);
                clickPriceInfoOld = em.merge(clickPriceInfoOld);
            }
            if (clickPriceInfoNew != null && !clickPriceInfoNew.equals(clickPriceInfoOld)) {
                clickPriceInfoNew.setPharmacyClickPriceInfo(pharmacyClickPriceInfo);
                clickPriceInfoNew = em.merge(clickPriceInfoNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = pharmacyClickPriceInfo.getId();
                if (findPharmacyClickPriceInfo(id) == null) {
                    throw new NonexistentEntityException("The pharmacyClickPriceInfo with id " + id + " no longer exists.");
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
            PharmacyClickPriceInfo pharmacyClickPriceInfo;
            try {
                pharmacyClickPriceInfo = em.getReference(PharmacyClickPriceInfo.class, id);
                pharmacyClickPriceInfo.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The pharmacyClickPriceInfo with id " + id + " no longer exists.", enfe);
            }
            ClickPriceInfo clickPriceInfo = pharmacyClickPriceInfo.getClickPriceInfo();
            if (clickPriceInfo != null) {
                clickPriceInfo.setPharmacyClickPriceInfo(null);
                clickPriceInfo = em.merge(clickPriceInfo);
            }
            em.remove(pharmacyClickPriceInfo);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<PharmacyClickPriceInfo> findPharmacyClickPriceInfoEntities() {
        return findPharmacyClickPriceInfoEntities(true, -1, -1);
    }

    public List<PharmacyClickPriceInfo> findPharmacyClickPriceInfoEntities(int maxResults, int firstResult) {
        return findPharmacyClickPriceInfoEntities(false, maxResults, firstResult);
    }

    private List<PharmacyClickPriceInfo> findPharmacyClickPriceInfoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(PharmacyClickPriceInfo.class));
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

    public PharmacyClickPriceInfo findPharmacyClickPriceInfo(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(PharmacyClickPriceInfo.class, id);
        } finally {
            em.close();
        }
    }

    public int getPharmacyClickPriceInfoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<PharmacyClickPriceInfo> rt = cq.from(PharmacyClickPriceInfo.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
