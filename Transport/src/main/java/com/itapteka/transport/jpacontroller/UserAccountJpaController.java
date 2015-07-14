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
import com.itapteka.transport.dbcontroller.ClientNotes;
import com.itapteka.transport.dbcontroller.UserAccount;
import com.itapteka.transport.jpacontroller.exceptions.IllegalOrphanException;
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
public class UserAccountJpaController implements Serializable {

    public UserAccountJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(UserAccount userAccount) {
        if (userAccount.getClientNotesCollection() == null) {
            userAccount.setClientNotesCollection(new ArrayList<ClientNotes>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Client clientId = userAccount.getClientId();
            if (clientId != null) {
                clientId = em.getReference(clientId.getClass(), clientId.getId());
                userAccount.setClientId(clientId);
            }
            Region regionId = userAccount.getRegionId();
            if (regionId != null) {
                regionId = em.getReference(regionId.getClass(), regionId.getId());
                userAccount.setRegionId(regionId);
            }
            Collection<ClientNotes> attachedClientNotesCollection = new ArrayList<ClientNotes>();
            for (ClientNotes clientNotesCollectionClientNotesToAttach : userAccount.getClientNotesCollection()) {
                clientNotesCollectionClientNotesToAttach = em.getReference(clientNotesCollectionClientNotesToAttach.getClass(), clientNotesCollectionClientNotesToAttach.getId());
                attachedClientNotesCollection.add(clientNotesCollectionClientNotesToAttach);
            }
            userAccount.setClientNotesCollection(attachedClientNotesCollection);
            em.persist(userAccount);
            if (clientId != null) {
                clientId.getUserAccountCollection().add(userAccount);
                clientId = em.merge(clientId);
            }
            if (regionId != null) {
                regionId.getUserAccountCollection().add(userAccount);
                regionId = em.merge(regionId);
            }
            for (ClientNotes clientNotesCollectionClientNotes : userAccount.getClientNotesCollection()) {
                UserAccount oldUserIdOfClientNotesCollectionClientNotes = clientNotesCollectionClientNotes.getUserId();
                clientNotesCollectionClientNotes.setUserId(userAccount);
                clientNotesCollectionClientNotes = em.merge(clientNotesCollectionClientNotes);
                if (oldUserIdOfClientNotesCollectionClientNotes != null) {
                    oldUserIdOfClientNotesCollectionClientNotes.getClientNotesCollection().remove(clientNotesCollectionClientNotes);
                    oldUserIdOfClientNotesCollectionClientNotes = em.merge(oldUserIdOfClientNotesCollectionClientNotes);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(UserAccount userAccount) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            UserAccount persistentUserAccount = em.find(UserAccount.class, userAccount.getId());
            Client clientIdOld = persistentUserAccount.getClientId();
            Client clientIdNew = userAccount.getClientId();
            Region regionIdOld = persistentUserAccount.getRegionId();
            Region regionIdNew = userAccount.getRegionId();
            Collection<ClientNotes> clientNotesCollectionOld = persistentUserAccount.getClientNotesCollection();
            Collection<ClientNotes> clientNotesCollectionNew = userAccount.getClientNotesCollection();
            List<String> illegalOrphanMessages = null;
            for (ClientNotes clientNotesCollectionOldClientNotes : clientNotesCollectionOld) {
                if (!clientNotesCollectionNew.contains(clientNotesCollectionOldClientNotes)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain ClientNotes " + clientNotesCollectionOldClientNotes + " since its userId field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (clientIdNew != null) {
                clientIdNew = em.getReference(clientIdNew.getClass(), clientIdNew.getId());
                userAccount.setClientId(clientIdNew);
            }
            if (regionIdNew != null) {
                regionIdNew = em.getReference(regionIdNew.getClass(), regionIdNew.getId());
                userAccount.setRegionId(regionIdNew);
            }
            Collection<ClientNotes> attachedClientNotesCollectionNew = new ArrayList<ClientNotes>();
            for (ClientNotes clientNotesCollectionNewClientNotesToAttach : clientNotesCollectionNew) {
                clientNotesCollectionNewClientNotesToAttach = em.getReference(clientNotesCollectionNewClientNotesToAttach.getClass(), clientNotesCollectionNewClientNotesToAttach.getId());
                attachedClientNotesCollectionNew.add(clientNotesCollectionNewClientNotesToAttach);
            }
            clientNotesCollectionNew = attachedClientNotesCollectionNew;
            userAccount.setClientNotesCollection(clientNotesCollectionNew);
            userAccount = em.merge(userAccount);
            if (clientIdOld != null && !clientIdOld.equals(clientIdNew)) {
                clientIdOld.getUserAccountCollection().remove(userAccount);
                clientIdOld = em.merge(clientIdOld);
            }
            if (clientIdNew != null && !clientIdNew.equals(clientIdOld)) {
                clientIdNew.getUserAccountCollection().add(userAccount);
                clientIdNew = em.merge(clientIdNew);
            }
            if (regionIdOld != null && !regionIdOld.equals(regionIdNew)) {
                regionIdOld.getUserAccountCollection().remove(userAccount);
                regionIdOld = em.merge(regionIdOld);
            }
            if (regionIdNew != null && !regionIdNew.equals(regionIdOld)) {
                regionIdNew.getUserAccountCollection().add(userAccount);
                regionIdNew = em.merge(regionIdNew);
            }
            for (ClientNotes clientNotesCollectionNewClientNotes : clientNotesCollectionNew) {
                if (!clientNotesCollectionOld.contains(clientNotesCollectionNewClientNotes)) {
                    UserAccount oldUserIdOfClientNotesCollectionNewClientNotes = clientNotesCollectionNewClientNotes.getUserId();
                    clientNotesCollectionNewClientNotes.setUserId(userAccount);
                    clientNotesCollectionNewClientNotes = em.merge(clientNotesCollectionNewClientNotes);
                    if (oldUserIdOfClientNotesCollectionNewClientNotes != null && !oldUserIdOfClientNotesCollectionNewClientNotes.equals(userAccount)) {
                        oldUserIdOfClientNotesCollectionNewClientNotes.getClientNotesCollection().remove(clientNotesCollectionNewClientNotes);
                        oldUserIdOfClientNotesCollectionNewClientNotes = em.merge(oldUserIdOfClientNotesCollectionNewClientNotes);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = userAccount.getId();
                if (findUserAccount(id) == null) {
                    throw new NonexistentEntityException("The userAccount with id " + id + " no longer exists.");
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
            UserAccount userAccount;
            try {
                userAccount = em.getReference(UserAccount.class, id);
                userAccount.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The userAccount with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<ClientNotes> clientNotesCollectionOrphanCheck = userAccount.getClientNotesCollection();
            for (ClientNotes clientNotesCollectionOrphanCheckClientNotes : clientNotesCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This UserAccount (" + userAccount + ") cannot be destroyed since the ClientNotes " + clientNotesCollectionOrphanCheckClientNotes + " in its clientNotesCollection field has a non-nullable userId field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Client clientId = userAccount.getClientId();
            if (clientId != null) {
                clientId.getUserAccountCollection().remove(userAccount);
                clientId = em.merge(clientId);
            }
            Region regionId = userAccount.getRegionId();
            if (regionId != null) {
                regionId.getUserAccountCollection().remove(userAccount);
                regionId = em.merge(regionId);
            }
            em.remove(userAccount);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<UserAccount> findUserAccountEntities() {
        return findUserAccountEntities(true, -1, -1);
    }

    public List<UserAccount> findUserAccountEntities(int maxResults, int firstResult) {
        return findUserAccountEntities(false, maxResults, firstResult);
    }

    private List<UserAccount> findUserAccountEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(UserAccount.class));
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

    public UserAccount findUserAccount(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(UserAccount.class, id);
        } finally {
            em.close();
        }
    }

    public int getUserAccountCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<UserAccount> rt = cq.from(UserAccount.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
