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
import com.itapteka.transport.dbcontroller.Region;
import java.util.ArrayList;
import java.util.Collection;
import com.itapteka.transport.dbcontroller.UserAccount;
import com.itapteka.transport.jpacontroller.exceptions.NonexistentEntityException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Ponomarev
 */
public class RegionJpaController implements Serializable {

    public RegionJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Region region) {
        if (region.getClientCollection() == null) {
            region.setClientCollection(new ArrayList<Client>());
        }
        if (region.getUserAccountCollection() == null) {
            region.setUserAccountCollection(new ArrayList<UserAccount>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<Client> attachedClientCollection = new ArrayList<Client>();
            for (Client clientCollectionClientToAttach : region.getClientCollection()) {
                clientCollectionClientToAttach = em.getReference(clientCollectionClientToAttach.getClass(), clientCollectionClientToAttach.getId());
                attachedClientCollection.add(clientCollectionClientToAttach);
            }
            region.setClientCollection(attachedClientCollection);
            Collection<UserAccount> attachedUserAccountCollection = new ArrayList<UserAccount>();
            for (UserAccount userAccountCollectionUserAccountToAttach : region.getUserAccountCollection()) {
                userAccountCollectionUserAccountToAttach = em.getReference(userAccountCollectionUserAccountToAttach.getClass(), userAccountCollectionUserAccountToAttach.getId());
                attachedUserAccountCollection.add(userAccountCollectionUserAccountToAttach);
            }
            region.setUserAccountCollection(attachedUserAccountCollection);
            em.persist(region);
            for (Client clientCollectionClient : region.getClientCollection()) {
                Region oldRegionIdOfClientCollectionClient = clientCollectionClient.getRegionId();
                clientCollectionClient.setRegionId(region);
                clientCollectionClient = em.merge(clientCollectionClient);
                if (oldRegionIdOfClientCollectionClient != null) {
                    oldRegionIdOfClientCollectionClient.getClientCollection().remove(clientCollectionClient);
                    oldRegionIdOfClientCollectionClient = em.merge(oldRegionIdOfClientCollectionClient);
                }
            }
            for (UserAccount userAccountCollectionUserAccount : region.getUserAccountCollection()) {
                Region oldRegionIdOfUserAccountCollectionUserAccount = userAccountCollectionUserAccount.getRegionId();
                userAccountCollectionUserAccount.setRegionId(region);
                userAccountCollectionUserAccount = em.merge(userAccountCollectionUserAccount);
                if (oldRegionIdOfUserAccountCollectionUserAccount != null) {
                    oldRegionIdOfUserAccountCollectionUserAccount.getUserAccountCollection().remove(userAccountCollectionUserAccount);
                    oldRegionIdOfUserAccountCollectionUserAccount = em.merge(oldRegionIdOfUserAccountCollectionUserAccount);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Region region) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Region persistentRegion = em.find(Region.class, region.getId());
            Collection<Client> clientCollectionOld = persistentRegion.getClientCollection();
            Collection<Client> clientCollectionNew = region.getClientCollection();
            Collection<UserAccount> userAccountCollectionOld = persistentRegion.getUserAccountCollection();
            Collection<UserAccount> userAccountCollectionNew = region.getUserAccountCollection();
            Collection<Client> attachedClientCollectionNew = new ArrayList<Client>();
            for (Client clientCollectionNewClientToAttach : clientCollectionNew) {
                clientCollectionNewClientToAttach = em.getReference(clientCollectionNewClientToAttach.getClass(), clientCollectionNewClientToAttach.getId());
                attachedClientCollectionNew.add(clientCollectionNewClientToAttach);
            }
            clientCollectionNew = attachedClientCollectionNew;
            region.setClientCollection(clientCollectionNew);
            Collection<UserAccount> attachedUserAccountCollectionNew = new ArrayList<UserAccount>();
            for (UserAccount userAccountCollectionNewUserAccountToAttach : userAccountCollectionNew) {
                userAccountCollectionNewUserAccountToAttach = em.getReference(userAccountCollectionNewUserAccountToAttach.getClass(), userAccountCollectionNewUserAccountToAttach.getId());
                attachedUserAccountCollectionNew.add(userAccountCollectionNewUserAccountToAttach);
            }
            userAccountCollectionNew = attachedUserAccountCollectionNew;
            region.setUserAccountCollection(userAccountCollectionNew);
            region = em.merge(region);
            for (Client clientCollectionOldClient : clientCollectionOld) {
                if (!clientCollectionNew.contains(clientCollectionOldClient)) {
                    clientCollectionOldClient.setRegionId(null);
                    clientCollectionOldClient = em.merge(clientCollectionOldClient);
                }
            }
            for (Client clientCollectionNewClient : clientCollectionNew) {
                if (!clientCollectionOld.contains(clientCollectionNewClient)) {
                    Region oldRegionIdOfClientCollectionNewClient = clientCollectionNewClient.getRegionId();
                    clientCollectionNewClient.setRegionId(region);
                    clientCollectionNewClient = em.merge(clientCollectionNewClient);
                    if (oldRegionIdOfClientCollectionNewClient != null && !oldRegionIdOfClientCollectionNewClient.equals(region)) {
                        oldRegionIdOfClientCollectionNewClient.getClientCollection().remove(clientCollectionNewClient);
                        oldRegionIdOfClientCollectionNewClient = em.merge(oldRegionIdOfClientCollectionNewClient);
                    }
                }
            }
            for (UserAccount userAccountCollectionOldUserAccount : userAccountCollectionOld) {
                if (!userAccountCollectionNew.contains(userAccountCollectionOldUserAccount)) {
                    userAccountCollectionOldUserAccount.setRegionId(null);
                    userAccountCollectionOldUserAccount = em.merge(userAccountCollectionOldUserAccount);
                }
            }
            for (UserAccount userAccountCollectionNewUserAccount : userAccountCollectionNew) {
                if (!userAccountCollectionOld.contains(userAccountCollectionNewUserAccount)) {
                    Region oldRegionIdOfUserAccountCollectionNewUserAccount = userAccountCollectionNewUserAccount.getRegionId();
                    userAccountCollectionNewUserAccount.setRegionId(region);
                    userAccountCollectionNewUserAccount = em.merge(userAccountCollectionNewUserAccount);
                    if (oldRegionIdOfUserAccountCollectionNewUserAccount != null && !oldRegionIdOfUserAccountCollectionNewUserAccount.equals(region)) {
                        oldRegionIdOfUserAccountCollectionNewUserAccount.getUserAccountCollection().remove(userAccountCollectionNewUserAccount);
                        oldRegionIdOfUserAccountCollectionNewUserAccount = em.merge(oldRegionIdOfUserAccountCollectionNewUserAccount);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = region.getId();
                if (findRegion(id) == null) {
                    throw new NonexistentEntityException("The region with id " + id + " no longer exists.");
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
            Region region;
            try {
                region = em.getReference(Region.class, id);
                region.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The region with id " + id + " no longer exists.", enfe);
            }
            Collection<Client> clientCollection = region.getClientCollection();
            for (Client clientCollectionClient : clientCollection) {
                clientCollectionClient.setRegionId(null);
                clientCollectionClient = em.merge(clientCollectionClient);
            }
            Collection<UserAccount> userAccountCollection = region.getUserAccountCollection();
            for (UserAccount userAccountCollectionUserAccount : userAccountCollection) {
                userAccountCollectionUserAccount.setRegionId(null);
                userAccountCollectionUserAccount = em.merge(userAccountCollectionUserAccount);
            }
            em.remove(region);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Region> findRegionEntities() {
        return findRegionEntities(true, -1, -1);
    }

    public List<Region> findRegionEntities(int maxResults, int firstResult) {
        return findRegionEntities(false, maxResults, firstResult);
    }

    private List<Region> findRegionEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Region.class));
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

    public Region findRegion(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Region.class, id);
        } finally {
            em.close();
        }
    }

    public int getRegionCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Region> rt = cq.from(Region.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
