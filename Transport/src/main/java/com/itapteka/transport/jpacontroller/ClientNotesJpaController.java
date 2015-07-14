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
import com.itapteka.transport.dbcontroller.ClientNotes;
import com.itapteka.transport.dbcontroller.UserAccount;
import com.itapteka.transport.jpacontroller.exceptions.NonexistentEntityException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Ponomarev
 */
public class ClientNotesJpaController implements Serializable {

    public ClientNotesJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(ClientNotes clientNotes) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Client clientId = clientNotes.getClientId();
            if (clientId != null) {
                clientId = em.getReference(clientId.getClass(), clientId.getId());
                clientNotes.setClientId(clientId);
            }
            UserAccount userId = clientNotes.getUserId();
            if (userId != null) {
                userId = em.getReference(userId.getClass(), userId.getId());
                clientNotes.setUserId(userId);
            }
            em.persist(clientNotes);
            if (clientId != null) {
                clientId.getClientNotesCollection().add(clientNotes);
                clientId = em.merge(clientId);
            }
            if (userId != null) {
                userId.getClientNotesCollection().add(clientNotes);
                userId = em.merge(userId);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(ClientNotes clientNotes) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            ClientNotes persistentClientNotes = em.find(ClientNotes.class, clientNotes.getId());
            Client clientIdOld = persistentClientNotes.getClientId();
            Client clientIdNew = clientNotes.getClientId();
            UserAccount userIdOld = persistentClientNotes.getUserId();
            UserAccount userIdNew = clientNotes.getUserId();
            if (clientIdNew != null) {
                clientIdNew = em.getReference(clientIdNew.getClass(), clientIdNew.getId());
                clientNotes.setClientId(clientIdNew);
            }
            if (userIdNew != null) {
                userIdNew = em.getReference(userIdNew.getClass(), userIdNew.getId());
                clientNotes.setUserId(userIdNew);
            }
            clientNotes = em.merge(clientNotes);
            if (clientIdOld != null && !clientIdOld.equals(clientIdNew)) {
                clientIdOld.getClientNotesCollection().remove(clientNotes);
                clientIdOld = em.merge(clientIdOld);
            }
            if (clientIdNew != null && !clientIdNew.equals(clientIdOld)) {
                clientIdNew.getClientNotesCollection().add(clientNotes);
                clientIdNew = em.merge(clientIdNew);
            }
            if (userIdOld != null && !userIdOld.equals(userIdNew)) {
                userIdOld.getClientNotesCollection().remove(clientNotes);
                userIdOld = em.merge(userIdOld);
            }
            if (userIdNew != null && !userIdNew.equals(userIdOld)) {
                userIdNew.getClientNotesCollection().add(clientNotes);
                userIdNew = em.merge(userIdNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = clientNotes.getId();
                if (findClientNotes(id) == null) {
                    throw new NonexistentEntityException("The clientNotes with id " + id + " no longer exists.");
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
            ClientNotes clientNotes;
            try {
                clientNotes = em.getReference(ClientNotes.class, id);
                clientNotes.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The clientNotes with id " + id + " no longer exists.", enfe);
            }
            Client clientId = clientNotes.getClientId();
            if (clientId != null) {
                clientId.getClientNotesCollection().remove(clientNotes);
                clientId = em.merge(clientId);
            }
            UserAccount userId = clientNotes.getUserId();
            if (userId != null) {
                userId.getClientNotesCollection().remove(clientNotes);
                userId = em.merge(userId);
            }
            em.remove(clientNotes);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<ClientNotes> findClientNotesEntities() {
        return findClientNotesEntities(true, -1, -1);
    }

    public List<ClientNotes> findClientNotesEntities(int maxResults, int firstResult) {
        return findClientNotesEntities(false, maxResults, firstResult);
    }

    private List<ClientNotes> findClientNotesEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(ClientNotes.class));
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

    public ClientNotes findClientNotes(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(ClientNotes.class, id);
        } finally {
            em.close();
        }
    }

    public int getClientNotesCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<ClientNotes> rt = cq.from(ClientNotes.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
