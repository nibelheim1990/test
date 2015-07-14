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
import com.itapteka.transport.dbcontroller.PharmacyMaInfo;
import com.itapteka.transport.dbcontroller.MzPartnerInfo;
import com.itapteka.transport.dbcontroller.SupplierSettings;
import com.itapteka.transport.dbcontroller.Region;
import com.itapteka.transport.dbcontroller.TransportInfo;
import com.itapteka.transport.dbcontroller.ClickPriceInfo;
import com.itapteka.transport.dbcontroller.Client;
import com.itapteka.transport.dbcontroller.Document;
import java.util.ArrayList;
import java.util.Collection;
import com.itapteka.transport.dbcontroller.ClientNotes;
import com.itapteka.transport.dbcontroller.OfflineHistory;
import com.itapteka.transport.dbcontroller.Route;
import com.itapteka.transport.dbcontroller.RouteCopy;
import com.itapteka.transport.dbcontroller.UserAccount;
import com.itapteka.transport.dbcontroller.FtpInfo;
import com.itapteka.transport.jpacontroller.exceptions.IllegalOrphanException;
import com.itapteka.transport.jpacontroller.exceptions.NonexistentEntityException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Ponomarev
 */
public class ClientJpaController implements Serializable {

    public ClientJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Client client) {
        if (client.getDocumentCollection() == null) {
            client.setDocumentCollection(new ArrayList<Document>());
        }
        if (client.getDocumentCollection1() == null) {
            client.setDocumentCollection1(new ArrayList<Document>());
        }
        if (client.getClientNotesCollection() == null) {
            client.setClientNotesCollection(new ArrayList<ClientNotes>());
        }
        if (client.getOfflineHistoryCollection() == null) {
            client.setOfflineHistoryCollection(new ArrayList<OfflineHistory>());
        }
        if (client.getRouteCollection() == null) {
            client.setRouteCollection(new ArrayList<Route>());
        }
        if (client.getRouteCollection1() == null) {
            client.setRouteCollection1(new ArrayList<Route>());
        }
        if (client.getRouteCopyCollection() == null) {
            client.setRouteCopyCollection(new ArrayList<RouteCopy>());
        }
        if (client.getUserAccountCollection() == null) {
            client.setUserAccountCollection(new ArrayList<UserAccount>());
        }
        if (client.getFtpInfoCollection() == null) {
            client.setFtpInfoCollection(new ArrayList<FtpInfo>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            PharmacyMaInfo pharmacyMaInfo = client.getPharmacyMaInfo();
            if (pharmacyMaInfo != null) {
                pharmacyMaInfo = em.getReference(pharmacyMaInfo.getClass(), pharmacyMaInfo.getId());
                client.setPharmacyMaInfo(pharmacyMaInfo);
            }
            MzPartnerInfo mzPartnerInfo = client.getMzPartnerInfo();
            if (mzPartnerInfo != null) {
                mzPartnerInfo = em.getReference(mzPartnerInfo.getClass(), mzPartnerInfo.getId());
                client.setMzPartnerInfo(mzPartnerInfo);
            }
            SupplierSettings supplierSettings = client.getSupplierSettings();
            if (supplierSettings != null) {
                supplierSettings = em.getReference(supplierSettings.getClass(), supplierSettings.getId());
                client.setSupplierSettings(supplierSettings);
            }
            Region regionId = client.getRegionId();
            if (regionId != null) {
                regionId = em.getReference(regionId.getClass(), regionId.getId());
                client.setRegionId(regionId);
            }
            TransportInfo transportInfo = client.getTransportInfo();
            if (transportInfo != null) {
                transportInfo = em.getReference(transportInfo.getClass(), transportInfo.getId());
                client.setTransportInfo(transportInfo);
            }
            ClickPriceInfo clickPriceInfo = client.getClickPriceInfo();
            if (clickPriceInfo != null) {
                clickPriceInfo = em.getReference(clickPriceInfo.getClass(), clickPriceInfo.getId());
                client.setClickPriceInfo(clickPriceInfo);
            }
            Collection<Document> attachedDocumentCollection = new ArrayList<Document>();
            for (Document documentCollectionDocumentToAttach : client.getDocumentCollection()) {
                documentCollectionDocumentToAttach = em.getReference(documentCollectionDocumentToAttach.getClass(), documentCollectionDocumentToAttach.getId());
                attachedDocumentCollection.add(documentCollectionDocumentToAttach);
            }
            client.setDocumentCollection(attachedDocumentCollection);
            Collection<Document> attachedDocumentCollection1 = new ArrayList<Document>();
            for (Document documentCollection1DocumentToAttach : client.getDocumentCollection1()) {
                documentCollection1DocumentToAttach = em.getReference(documentCollection1DocumentToAttach.getClass(), documentCollection1DocumentToAttach.getId());
                attachedDocumentCollection1.add(documentCollection1DocumentToAttach);
            }
            client.setDocumentCollection1(attachedDocumentCollection1);
            Collection<ClientNotes> attachedClientNotesCollection = new ArrayList<ClientNotes>();
            for (ClientNotes clientNotesCollectionClientNotesToAttach : client.getClientNotesCollection()) {
                clientNotesCollectionClientNotesToAttach = em.getReference(clientNotesCollectionClientNotesToAttach.getClass(), clientNotesCollectionClientNotesToAttach.getId());
                attachedClientNotesCollection.add(clientNotesCollectionClientNotesToAttach);
            }
            client.setClientNotesCollection(attachedClientNotesCollection);
            Collection<OfflineHistory> attachedOfflineHistoryCollection = new ArrayList<OfflineHistory>();
            for (OfflineHistory offlineHistoryCollectionOfflineHistoryToAttach : client.getOfflineHistoryCollection()) {
                offlineHistoryCollectionOfflineHistoryToAttach = em.getReference(offlineHistoryCollectionOfflineHistoryToAttach.getClass(), offlineHistoryCollectionOfflineHistoryToAttach.getId());
                attachedOfflineHistoryCollection.add(offlineHistoryCollectionOfflineHistoryToAttach);
            }
            client.setOfflineHistoryCollection(attachedOfflineHistoryCollection);
            Collection<Route> attachedRouteCollection = new ArrayList<Route>();
            for (Route routeCollectionRouteToAttach : client.getRouteCollection()) {
                routeCollectionRouteToAttach = em.getReference(routeCollectionRouteToAttach.getClass(), routeCollectionRouteToAttach.getId());
                attachedRouteCollection.add(routeCollectionRouteToAttach);
            }
            client.setRouteCollection(attachedRouteCollection);
            Collection<Route> attachedRouteCollection1 = new ArrayList<Route>();
            for (Route routeCollection1RouteToAttach : client.getRouteCollection1()) {
                routeCollection1RouteToAttach = em.getReference(routeCollection1RouteToAttach.getClass(), routeCollection1RouteToAttach.getId());
                attachedRouteCollection1.add(routeCollection1RouteToAttach);
            }
            client.setRouteCollection1(attachedRouteCollection1);
            Collection<RouteCopy> attachedRouteCopyCollection = new ArrayList<RouteCopy>();
            for (RouteCopy routeCopyCollectionRouteCopyToAttach : client.getRouteCopyCollection()) {
                routeCopyCollectionRouteCopyToAttach = em.getReference(routeCopyCollectionRouteCopyToAttach.getClass(), routeCopyCollectionRouteCopyToAttach.getId());
                attachedRouteCopyCollection.add(routeCopyCollectionRouteCopyToAttach);
            }
            client.setRouteCopyCollection(attachedRouteCopyCollection);
            Collection<UserAccount> attachedUserAccountCollection = new ArrayList<UserAccount>();
            for (UserAccount userAccountCollectionUserAccountToAttach : client.getUserAccountCollection()) {
                userAccountCollectionUserAccountToAttach = em.getReference(userAccountCollectionUserAccountToAttach.getClass(), userAccountCollectionUserAccountToAttach.getId());
                attachedUserAccountCollection.add(userAccountCollectionUserAccountToAttach);
            }
            client.setUserAccountCollection(attachedUserAccountCollection);
            Collection<FtpInfo> attachedFtpInfoCollection = new ArrayList<FtpInfo>();
            for (FtpInfo ftpInfoCollectionFtpInfoToAttach : client.getFtpInfoCollection()) {
                ftpInfoCollectionFtpInfoToAttach = em.getReference(ftpInfoCollectionFtpInfoToAttach.getClass(), ftpInfoCollectionFtpInfoToAttach.getId());
                attachedFtpInfoCollection.add(ftpInfoCollectionFtpInfoToAttach);
            }
            client.setFtpInfoCollection(attachedFtpInfoCollection);
            em.persist(client);
            if (pharmacyMaInfo != null) {
                Client oldClientOfPharmacyMaInfo = pharmacyMaInfo.getClient();
                if (oldClientOfPharmacyMaInfo != null) {
                    oldClientOfPharmacyMaInfo.setPharmacyMaInfo(null);
                    oldClientOfPharmacyMaInfo = em.merge(oldClientOfPharmacyMaInfo);
                }
                pharmacyMaInfo.setClient(client);
                pharmacyMaInfo = em.merge(pharmacyMaInfo);
            }
            if (mzPartnerInfo != null) {
                Client oldClientOfMzPartnerInfo = mzPartnerInfo.getClient();
                if (oldClientOfMzPartnerInfo != null) {
                    oldClientOfMzPartnerInfo.setMzPartnerInfo(null);
                    oldClientOfMzPartnerInfo = em.merge(oldClientOfMzPartnerInfo);
                }
                mzPartnerInfo.setClient(client);
                mzPartnerInfo = em.merge(mzPartnerInfo);
            }
            if (supplierSettings != null) {
                Client oldClientOfSupplierSettings = supplierSettings.getClient();
                if (oldClientOfSupplierSettings != null) {
                    oldClientOfSupplierSettings.setSupplierSettings(null);
                    oldClientOfSupplierSettings = em.merge(oldClientOfSupplierSettings);
                }
                supplierSettings.setClient(client);
                supplierSettings = em.merge(supplierSettings);
            }
            if (regionId != null) {
                regionId.getClientCollection().add(client);
                regionId = em.merge(regionId);
            }
            if (transportInfo != null) {
                Client oldClientOfTransportInfo = transportInfo.getClient();
                if (oldClientOfTransportInfo != null) {
                    oldClientOfTransportInfo.setTransportInfo(null);
                    oldClientOfTransportInfo = em.merge(oldClientOfTransportInfo);
                }
                transportInfo.setClient(client);
                transportInfo = em.merge(transportInfo);
            }
            if (clickPriceInfo != null) {
                Client oldClientOfClickPriceInfo = clickPriceInfo.getClient();
                if (oldClientOfClickPriceInfo != null) {
                    oldClientOfClickPriceInfo.setClickPriceInfo(null);
                    oldClientOfClickPriceInfo = em.merge(oldClientOfClickPriceInfo);
                }
                clickPriceInfo.setClient(client);
                clickPriceInfo = em.merge(clickPriceInfo);
            }
            for (Document documentCollectionDocument : client.getDocumentCollection()) {
                Client oldClientDstIdOfDocumentCollectionDocument = documentCollectionDocument.getClientDstId();
                documentCollectionDocument.setClientDstId(client);
                documentCollectionDocument = em.merge(documentCollectionDocument);
                if (oldClientDstIdOfDocumentCollectionDocument != null) {
                    oldClientDstIdOfDocumentCollectionDocument.getDocumentCollection().remove(documentCollectionDocument);
                    oldClientDstIdOfDocumentCollectionDocument = em.merge(oldClientDstIdOfDocumentCollectionDocument);
                }
            }
            for (Document documentCollection1Document : client.getDocumentCollection1()) {
                Client oldClientSrcIdOfDocumentCollection1Document = documentCollection1Document.getClientSrcId();
                documentCollection1Document.setClientSrcId(client);
                documentCollection1Document = em.merge(documentCollection1Document);
                if (oldClientSrcIdOfDocumentCollection1Document != null) {
                    oldClientSrcIdOfDocumentCollection1Document.getDocumentCollection1().remove(documentCollection1Document);
                    oldClientSrcIdOfDocumentCollection1Document = em.merge(oldClientSrcIdOfDocumentCollection1Document);
                }
            }
            for (ClientNotes clientNotesCollectionClientNotes : client.getClientNotesCollection()) {
                Client oldClientIdOfClientNotesCollectionClientNotes = clientNotesCollectionClientNotes.getClientId();
                clientNotesCollectionClientNotes.setClientId(client);
                clientNotesCollectionClientNotes = em.merge(clientNotesCollectionClientNotes);
                if (oldClientIdOfClientNotesCollectionClientNotes != null) {
                    oldClientIdOfClientNotesCollectionClientNotes.getClientNotesCollection().remove(clientNotesCollectionClientNotes);
                    oldClientIdOfClientNotesCollectionClientNotes = em.merge(oldClientIdOfClientNotesCollectionClientNotes);
                }
            }
            for (OfflineHistory offlineHistoryCollectionOfflineHistory : client.getOfflineHistoryCollection()) {
                Client oldClientIdOfOfflineHistoryCollectionOfflineHistory = offlineHistoryCollectionOfflineHistory.getClientId();
                offlineHistoryCollectionOfflineHistory.setClientId(client);
                offlineHistoryCollectionOfflineHistory = em.merge(offlineHistoryCollectionOfflineHistory);
                if (oldClientIdOfOfflineHistoryCollectionOfflineHistory != null) {
                    oldClientIdOfOfflineHistoryCollectionOfflineHistory.getOfflineHistoryCollection().remove(offlineHistoryCollectionOfflineHistory);
                    oldClientIdOfOfflineHistoryCollectionOfflineHistory = em.merge(oldClientIdOfOfflineHistoryCollectionOfflineHistory);
                }
            }
            for (Route routeCollectionRoute : client.getRouteCollection()) {
                Client oldClientDstIdOfRouteCollectionRoute = routeCollectionRoute.getClientDstId();
                routeCollectionRoute.setClientDstId(client);
                routeCollectionRoute = em.merge(routeCollectionRoute);
                if (oldClientDstIdOfRouteCollectionRoute != null) {
                    oldClientDstIdOfRouteCollectionRoute.getRouteCollection().remove(routeCollectionRoute);
                    oldClientDstIdOfRouteCollectionRoute = em.merge(oldClientDstIdOfRouteCollectionRoute);
                }
            }
            for (Route routeCollection1Route : client.getRouteCollection1()) {
                Client oldClientSrcIdOfRouteCollection1Route = routeCollection1Route.getClientSrcId();
                routeCollection1Route.setClientSrcId(client);
                routeCollection1Route = em.merge(routeCollection1Route);
                if (oldClientSrcIdOfRouteCollection1Route != null) {
                    oldClientSrcIdOfRouteCollection1Route.getRouteCollection1().remove(routeCollection1Route);
                    oldClientSrcIdOfRouteCollection1Route = em.merge(oldClientSrcIdOfRouteCollection1Route);
                }
            }
            for (RouteCopy routeCopyCollectionRouteCopy : client.getRouteCopyCollection()) {
                Client oldClientIdOfRouteCopyCollectionRouteCopy = routeCopyCollectionRouteCopy.getClientId();
                routeCopyCollectionRouteCopy.setClientId(client);
                routeCopyCollectionRouteCopy = em.merge(routeCopyCollectionRouteCopy);
                if (oldClientIdOfRouteCopyCollectionRouteCopy != null) {
                    oldClientIdOfRouteCopyCollectionRouteCopy.getRouteCopyCollection().remove(routeCopyCollectionRouteCopy);
                    oldClientIdOfRouteCopyCollectionRouteCopy = em.merge(oldClientIdOfRouteCopyCollectionRouteCopy);
                }
            }
            for (UserAccount userAccountCollectionUserAccount : client.getUserAccountCollection()) {
                Client oldClientIdOfUserAccountCollectionUserAccount = userAccountCollectionUserAccount.getClientId();
                userAccountCollectionUserAccount.setClientId(client);
                userAccountCollectionUserAccount = em.merge(userAccountCollectionUserAccount);
                if (oldClientIdOfUserAccountCollectionUserAccount != null) {
                    oldClientIdOfUserAccountCollectionUserAccount.getUserAccountCollection().remove(userAccountCollectionUserAccount);
                    oldClientIdOfUserAccountCollectionUserAccount = em.merge(oldClientIdOfUserAccountCollectionUserAccount);
                }
            }
            for (FtpInfo ftpInfoCollectionFtpInfo : client.getFtpInfoCollection()) {
                Client oldClientIdOfFtpInfoCollectionFtpInfo = ftpInfoCollectionFtpInfo.getClientId();
                ftpInfoCollectionFtpInfo.setClientId(client);
                ftpInfoCollectionFtpInfo = em.merge(ftpInfoCollectionFtpInfo);
                if (oldClientIdOfFtpInfoCollectionFtpInfo != null) {
                    oldClientIdOfFtpInfoCollectionFtpInfo.getFtpInfoCollection().remove(ftpInfoCollectionFtpInfo);
                    oldClientIdOfFtpInfoCollectionFtpInfo = em.merge(oldClientIdOfFtpInfoCollectionFtpInfo);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Client client) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Client persistentClient = em.find(Client.class, client.getId());
            PharmacyMaInfo pharmacyMaInfoOld = persistentClient.getPharmacyMaInfo();
            PharmacyMaInfo pharmacyMaInfoNew = client.getPharmacyMaInfo();
            MzPartnerInfo mzPartnerInfoOld = persistentClient.getMzPartnerInfo();
            MzPartnerInfo mzPartnerInfoNew = client.getMzPartnerInfo();
            SupplierSettings supplierSettingsOld = persistentClient.getSupplierSettings();
            SupplierSettings supplierSettingsNew = client.getSupplierSettings();
            Region regionIdOld = persistentClient.getRegionId();
            Region regionIdNew = client.getRegionId();
            TransportInfo transportInfoOld = persistentClient.getTransportInfo();
            TransportInfo transportInfoNew = client.getTransportInfo();
            ClickPriceInfo clickPriceInfoOld = persistentClient.getClickPriceInfo();
            ClickPriceInfo clickPriceInfoNew = client.getClickPriceInfo();
            Collection<Document> documentCollectionOld = persistentClient.getDocumentCollection();
            Collection<Document> documentCollectionNew = client.getDocumentCollection();
            Collection<Document> documentCollection1Old = persistentClient.getDocumentCollection1();
            Collection<Document> documentCollection1New = client.getDocumentCollection1();
            Collection<ClientNotes> clientNotesCollectionOld = persistentClient.getClientNotesCollection();
            Collection<ClientNotes> clientNotesCollectionNew = client.getClientNotesCollection();
            Collection<OfflineHistory> offlineHistoryCollectionOld = persistentClient.getOfflineHistoryCollection();
            Collection<OfflineHistory> offlineHistoryCollectionNew = client.getOfflineHistoryCollection();
            Collection<Route> routeCollectionOld = persistentClient.getRouteCollection();
            Collection<Route> routeCollectionNew = client.getRouteCollection();
            Collection<Route> routeCollection1Old = persistentClient.getRouteCollection1();
            Collection<Route> routeCollection1New = client.getRouteCollection1();
            Collection<RouteCopy> routeCopyCollectionOld = persistentClient.getRouteCopyCollection();
            Collection<RouteCopy> routeCopyCollectionNew = client.getRouteCopyCollection();
            Collection<UserAccount> userAccountCollectionOld = persistentClient.getUserAccountCollection();
            Collection<UserAccount> userAccountCollectionNew = client.getUserAccountCollection();
            Collection<FtpInfo> ftpInfoCollectionOld = persistentClient.getFtpInfoCollection();
            Collection<FtpInfo> ftpInfoCollectionNew = client.getFtpInfoCollection();
            List<String> illegalOrphanMessages = null;
            if (pharmacyMaInfoOld != null && !pharmacyMaInfoOld.equals(pharmacyMaInfoNew)) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("You must retain PharmacyMaInfo " + pharmacyMaInfoOld + " since its client field is not nullable.");
            }
            if (mzPartnerInfoOld != null && !mzPartnerInfoOld.equals(mzPartnerInfoNew)) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("You must retain MzPartnerInfo " + mzPartnerInfoOld + " since its client field is not nullable.");
            }
            if (supplierSettingsOld != null && !supplierSettingsOld.equals(supplierSettingsNew)) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("You must retain SupplierSettings " + supplierSettingsOld + " since its client field is not nullable.");
            }
            if (transportInfoOld != null && !transportInfoOld.equals(transportInfoNew)) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("You must retain TransportInfo " + transportInfoOld + " since its client field is not nullable.");
            }
            if (clickPriceInfoOld != null && !clickPriceInfoOld.equals(clickPriceInfoNew)) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("You must retain ClickPriceInfo " + clickPriceInfoOld + " since its client field is not nullable.");
            }
            for (ClientNotes clientNotesCollectionOldClientNotes : clientNotesCollectionOld) {
                if (!clientNotesCollectionNew.contains(clientNotesCollectionOldClientNotes)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain ClientNotes " + clientNotesCollectionOldClientNotes + " since its clientId field is not nullable.");
                }
            }
            for (OfflineHistory offlineHistoryCollectionOldOfflineHistory : offlineHistoryCollectionOld) {
                if (!offlineHistoryCollectionNew.contains(offlineHistoryCollectionOldOfflineHistory)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain OfflineHistory " + offlineHistoryCollectionOldOfflineHistory + " since its clientId field is not nullable.");
                }
            }
            for (Route routeCollectionOldRoute : routeCollectionOld) {
                if (!routeCollectionNew.contains(routeCollectionOldRoute)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Route " + routeCollectionOldRoute + " since its clientDstId field is not nullable.");
                }
            }
            for (Route routeCollection1OldRoute : routeCollection1Old) {
                if (!routeCollection1New.contains(routeCollection1OldRoute)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Route " + routeCollection1OldRoute + " since its clientSrcId field is not nullable.");
                }
            }
            for (RouteCopy routeCopyCollectionOldRouteCopy : routeCopyCollectionOld) {
                if (!routeCopyCollectionNew.contains(routeCopyCollectionOldRouteCopy)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain RouteCopy " + routeCopyCollectionOldRouteCopy + " since its clientId field is not nullable.");
                }
            }
            for (FtpInfo ftpInfoCollectionOldFtpInfo : ftpInfoCollectionOld) {
                if (!ftpInfoCollectionNew.contains(ftpInfoCollectionOldFtpInfo)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain FtpInfo " + ftpInfoCollectionOldFtpInfo + " since its clientId field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (pharmacyMaInfoNew != null) {
                pharmacyMaInfoNew = em.getReference(pharmacyMaInfoNew.getClass(), pharmacyMaInfoNew.getId());
                client.setPharmacyMaInfo(pharmacyMaInfoNew);
            }
            if (mzPartnerInfoNew != null) {
                mzPartnerInfoNew = em.getReference(mzPartnerInfoNew.getClass(), mzPartnerInfoNew.getId());
                client.setMzPartnerInfo(mzPartnerInfoNew);
            }
            if (supplierSettingsNew != null) {
                supplierSettingsNew = em.getReference(supplierSettingsNew.getClass(), supplierSettingsNew.getId());
                client.setSupplierSettings(supplierSettingsNew);
            }
            if (regionIdNew != null) {
                regionIdNew = em.getReference(regionIdNew.getClass(), regionIdNew.getId());
                client.setRegionId(regionIdNew);
            }
            if (transportInfoNew != null) {
                transportInfoNew = em.getReference(transportInfoNew.getClass(), transportInfoNew.getId());
                client.setTransportInfo(transportInfoNew);
            }
            if (clickPriceInfoNew != null) {
                clickPriceInfoNew = em.getReference(clickPriceInfoNew.getClass(), clickPriceInfoNew.getId());
                client.setClickPriceInfo(clickPriceInfoNew);
            }
            Collection<Document> attachedDocumentCollectionNew = new ArrayList<Document>();
            for (Document documentCollectionNewDocumentToAttach : documentCollectionNew) {
                documentCollectionNewDocumentToAttach = em.getReference(documentCollectionNewDocumentToAttach.getClass(), documentCollectionNewDocumentToAttach.getId());
                attachedDocumentCollectionNew.add(documentCollectionNewDocumentToAttach);
            }
            documentCollectionNew = attachedDocumentCollectionNew;
            client.setDocumentCollection(documentCollectionNew);
            Collection<Document> attachedDocumentCollection1New = new ArrayList<Document>();
            for (Document documentCollection1NewDocumentToAttach : documentCollection1New) {
                documentCollection1NewDocumentToAttach = em.getReference(documentCollection1NewDocumentToAttach.getClass(), documentCollection1NewDocumentToAttach.getId());
                attachedDocumentCollection1New.add(documentCollection1NewDocumentToAttach);
            }
            documentCollection1New = attachedDocumentCollection1New;
            client.setDocumentCollection1(documentCollection1New);
            Collection<ClientNotes> attachedClientNotesCollectionNew = new ArrayList<ClientNotes>();
            for (ClientNotes clientNotesCollectionNewClientNotesToAttach : clientNotesCollectionNew) {
                clientNotesCollectionNewClientNotesToAttach = em.getReference(clientNotesCollectionNewClientNotesToAttach.getClass(), clientNotesCollectionNewClientNotesToAttach.getId());
                attachedClientNotesCollectionNew.add(clientNotesCollectionNewClientNotesToAttach);
            }
            clientNotesCollectionNew = attachedClientNotesCollectionNew;
            client.setClientNotesCollection(clientNotesCollectionNew);
            Collection<OfflineHistory> attachedOfflineHistoryCollectionNew = new ArrayList<OfflineHistory>();
            for (OfflineHistory offlineHistoryCollectionNewOfflineHistoryToAttach : offlineHistoryCollectionNew) {
                offlineHistoryCollectionNewOfflineHistoryToAttach = em.getReference(offlineHistoryCollectionNewOfflineHistoryToAttach.getClass(), offlineHistoryCollectionNewOfflineHistoryToAttach.getId());
                attachedOfflineHistoryCollectionNew.add(offlineHistoryCollectionNewOfflineHistoryToAttach);
            }
            offlineHistoryCollectionNew = attachedOfflineHistoryCollectionNew;
            client.setOfflineHistoryCollection(offlineHistoryCollectionNew);
            Collection<Route> attachedRouteCollectionNew = new ArrayList<Route>();
            for (Route routeCollectionNewRouteToAttach : routeCollectionNew) {
                routeCollectionNewRouteToAttach = em.getReference(routeCollectionNewRouteToAttach.getClass(), routeCollectionNewRouteToAttach.getId());
                attachedRouteCollectionNew.add(routeCollectionNewRouteToAttach);
            }
            routeCollectionNew = attachedRouteCollectionNew;
            client.setRouteCollection(routeCollectionNew);
            Collection<Route> attachedRouteCollection1New = new ArrayList<Route>();
            for (Route routeCollection1NewRouteToAttach : routeCollection1New) {
                routeCollection1NewRouteToAttach = em.getReference(routeCollection1NewRouteToAttach.getClass(), routeCollection1NewRouteToAttach.getId());
                attachedRouteCollection1New.add(routeCollection1NewRouteToAttach);
            }
            routeCollection1New = attachedRouteCollection1New;
            client.setRouteCollection1(routeCollection1New);
            Collection<RouteCopy> attachedRouteCopyCollectionNew = new ArrayList<RouteCopy>();
            for (RouteCopy routeCopyCollectionNewRouteCopyToAttach : routeCopyCollectionNew) {
                routeCopyCollectionNewRouteCopyToAttach = em.getReference(routeCopyCollectionNewRouteCopyToAttach.getClass(), routeCopyCollectionNewRouteCopyToAttach.getId());
                attachedRouteCopyCollectionNew.add(routeCopyCollectionNewRouteCopyToAttach);
            }
            routeCopyCollectionNew = attachedRouteCopyCollectionNew;
            client.setRouteCopyCollection(routeCopyCollectionNew);
            Collection<UserAccount> attachedUserAccountCollectionNew = new ArrayList<UserAccount>();
            for (UserAccount userAccountCollectionNewUserAccountToAttach : userAccountCollectionNew) {
                userAccountCollectionNewUserAccountToAttach = em.getReference(userAccountCollectionNewUserAccountToAttach.getClass(), userAccountCollectionNewUserAccountToAttach.getId());
                attachedUserAccountCollectionNew.add(userAccountCollectionNewUserAccountToAttach);
            }
            userAccountCollectionNew = attachedUserAccountCollectionNew;
            client.setUserAccountCollection(userAccountCollectionNew);
            Collection<FtpInfo> attachedFtpInfoCollectionNew = new ArrayList<FtpInfo>();
            for (FtpInfo ftpInfoCollectionNewFtpInfoToAttach : ftpInfoCollectionNew) {
                ftpInfoCollectionNewFtpInfoToAttach = em.getReference(ftpInfoCollectionNewFtpInfoToAttach.getClass(), ftpInfoCollectionNewFtpInfoToAttach.getId());
                attachedFtpInfoCollectionNew.add(ftpInfoCollectionNewFtpInfoToAttach);
            }
            ftpInfoCollectionNew = attachedFtpInfoCollectionNew;
            client.setFtpInfoCollection(ftpInfoCollectionNew);
            client = em.merge(client);
            if (pharmacyMaInfoNew != null && !pharmacyMaInfoNew.equals(pharmacyMaInfoOld)) {
                Client oldClientOfPharmacyMaInfo = pharmacyMaInfoNew.getClient();
                if (oldClientOfPharmacyMaInfo != null) {
                    oldClientOfPharmacyMaInfo.setPharmacyMaInfo(null);
                    oldClientOfPharmacyMaInfo = em.merge(oldClientOfPharmacyMaInfo);
                }
                pharmacyMaInfoNew.setClient(client);
                pharmacyMaInfoNew = em.merge(pharmacyMaInfoNew);
            }
            if (mzPartnerInfoNew != null && !mzPartnerInfoNew.equals(mzPartnerInfoOld)) {
                Client oldClientOfMzPartnerInfo = mzPartnerInfoNew.getClient();
                if (oldClientOfMzPartnerInfo != null) {
                    oldClientOfMzPartnerInfo.setMzPartnerInfo(null);
                    oldClientOfMzPartnerInfo = em.merge(oldClientOfMzPartnerInfo);
                }
                mzPartnerInfoNew.setClient(client);
                mzPartnerInfoNew = em.merge(mzPartnerInfoNew);
            }
            if (supplierSettingsNew != null && !supplierSettingsNew.equals(supplierSettingsOld)) {
                Client oldClientOfSupplierSettings = supplierSettingsNew.getClient();
                if (oldClientOfSupplierSettings != null) {
                    oldClientOfSupplierSettings.setSupplierSettings(null);
                    oldClientOfSupplierSettings = em.merge(oldClientOfSupplierSettings);
                }
                supplierSettingsNew.setClient(client);
                supplierSettingsNew = em.merge(supplierSettingsNew);
            }
            if (regionIdOld != null && !regionIdOld.equals(regionIdNew)) {
                regionIdOld.getClientCollection().remove(client);
                regionIdOld = em.merge(regionIdOld);
            }
            if (regionIdNew != null && !regionIdNew.equals(regionIdOld)) {
                regionIdNew.getClientCollection().add(client);
                regionIdNew = em.merge(regionIdNew);
            }
            if (transportInfoNew != null && !transportInfoNew.equals(transportInfoOld)) {
                Client oldClientOfTransportInfo = transportInfoNew.getClient();
                if (oldClientOfTransportInfo != null) {
                    oldClientOfTransportInfo.setTransportInfo(null);
                    oldClientOfTransportInfo = em.merge(oldClientOfTransportInfo);
                }
                transportInfoNew.setClient(client);
                transportInfoNew = em.merge(transportInfoNew);
            }
            if (clickPriceInfoNew != null && !clickPriceInfoNew.equals(clickPriceInfoOld)) {
                Client oldClientOfClickPriceInfo = clickPriceInfoNew.getClient();
                if (oldClientOfClickPriceInfo != null) {
                    oldClientOfClickPriceInfo.setClickPriceInfo(null);
                    oldClientOfClickPriceInfo = em.merge(oldClientOfClickPriceInfo);
                }
                clickPriceInfoNew.setClient(client);
                clickPriceInfoNew = em.merge(clickPriceInfoNew);
            }
            for (Document documentCollectionOldDocument : documentCollectionOld) {
                if (!documentCollectionNew.contains(documentCollectionOldDocument)) {
                    documentCollectionOldDocument.setClientDstId(null);
                    documentCollectionOldDocument = em.merge(documentCollectionOldDocument);
                }
            }
            for (Document documentCollectionNewDocument : documentCollectionNew) {
                if (!documentCollectionOld.contains(documentCollectionNewDocument)) {
                    Client oldClientDstIdOfDocumentCollectionNewDocument = documentCollectionNewDocument.getClientDstId();
                    documentCollectionNewDocument.setClientDstId(client);
                    documentCollectionNewDocument = em.merge(documentCollectionNewDocument);
                    if (oldClientDstIdOfDocumentCollectionNewDocument != null && !oldClientDstIdOfDocumentCollectionNewDocument.equals(client)) {
                        oldClientDstIdOfDocumentCollectionNewDocument.getDocumentCollection().remove(documentCollectionNewDocument);
                        oldClientDstIdOfDocumentCollectionNewDocument = em.merge(oldClientDstIdOfDocumentCollectionNewDocument);
                    }
                }
            }
            for (Document documentCollection1OldDocument : documentCollection1Old) {
                if (!documentCollection1New.contains(documentCollection1OldDocument)) {
                    documentCollection1OldDocument.setClientSrcId(null);
                    documentCollection1OldDocument = em.merge(documentCollection1OldDocument);
                }
            }
            for (Document documentCollection1NewDocument : documentCollection1New) {
                if (!documentCollection1Old.contains(documentCollection1NewDocument)) {
                    Client oldClientSrcIdOfDocumentCollection1NewDocument = documentCollection1NewDocument.getClientSrcId();
                    documentCollection1NewDocument.setClientSrcId(client);
                    documentCollection1NewDocument = em.merge(documentCollection1NewDocument);
                    if (oldClientSrcIdOfDocumentCollection1NewDocument != null && !oldClientSrcIdOfDocumentCollection1NewDocument.equals(client)) {
                        oldClientSrcIdOfDocumentCollection1NewDocument.getDocumentCollection1().remove(documentCollection1NewDocument);
                        oldClientSrcIdOfDocumentCollection1NewDocument = em.merge(oldClientSrcIdOfDocumentCollection1NewDocument);
                    }
                }
            }
            for (ClientNotes clientNotesCollectionNewClientNotes : clientNotesCollectionNew) {
                if (!clientNotesCollectionOld.contains(clientNotesCollectionNewClientNotes)) {
                    Client oldClientIdOfClientNotesCollectionNewClientNotes = clientNotesCollectionNewClientNotes.getClientId();
                    clientNotesCollectionNewClientNotes.setClientId(client);
                    clientNotesCollectionNewClientNotes = em.merge(clientNotesCollectionNewClientNotes);
                    if (oldClientIdOfClientNotesCollectionNewClientNotes != null && !oldClientIdOfClientNotesCollectionNewClientNotes.equals(client)) {
                        oldClientIdOfClientNotesCollectionNewClientNotes.getClientNotesCollection().remove(clientNotesCollectionNewClientNotes);
                        oldClientIdOfClientNotesCollectionNewClientNotes = em.merge(oldClientIdOfClientNotesCollectionNewClientNotes);
                    }
                }
            }
            for (OfflineHistory offlineHistoryCollectionNewOfflineHistory : offlineHistoryCollectionNew) {
                if (!offlineHistoryCollectionOld.contains(offlineHistoryCollectionNewOfflineHistory)) {
                    Client oldClientIdOfOfflineHistoryCollectionNewOfflineHistory = offlineHistoryCollectionNewOfflineHistory.getClientId();
                    offlineHistoryCollectionNewOfflineHistory.setClientId(client);
                    offlineHistoryCollectionNewOfflineHistory = em.merge(offlineHistoryCollectionNewOfflineHistory);
                    if (oldClientIdOfOfflineHistoryCollectionNewOfflineHistory != null && !oldClientIdOfOfflineHistoryCollectionNewOfflineHistory.equals(client)) {
                        oldClientIdOfOfflineHistoryCollectionNewOfflineHistory.getOfflineHistoryCollection().remove(offlineHistoryCollectionNewOfflineHistory);
                        oldClientIdOfOfflineHistoryCollectionNewOfflineHistory = em.merge(oldClientIdOfOfflineHistoryCollectionNewOfflineHistory);
                    }
                }
            }
            for (Route routeCollectionNewRoute : routeCollectionNew) {
                if (!routeCollectionOld.contains(routeCollectionNewRoute)) {
                    Client oldClientDstIdOfRouteCollectionNewRoute = routeCollectionNewRoute.getClientDstId();
                    routeCollectionNewRoute.setClientDstId(client);
                    routeCollectionNewRoute = em.merge(routeCollectionNewRoute);
                    if (oldClientDstIdOfRouteCollectionNewRoute != null && !oldClientDstIdOfRouteCollectionNewRoute.equals(client)) {
                        oldClientDstIdOfRouteCollectionNewRoute.getRouteCollection().remove(routeCollectionNewRoute);
                        oldClientDstIdOfRouteCollectionNewRoute = em.merge(oldClientDstIdOfRouteCollectionNewRoute);
                    }
                }
            }
            for (Route routeCollection1NewRoute : routeCollection1New) {
                if (!routeCollection1Old.contains(routeCollection1NewRoute)) {
                    Client oldClientSrcIdOfRouteCollection1NewRoute = routeCollection1NewRoute.getClientSrcId();
                    routeCollection1NewRoute.setClientSrcId(client);
                    routeCollection1NewRoute = em.merge(routeCollection1NewRoute);
                    if (oldClientSrcIdOfRouteCollection1NewRoute != null && !oldClientSrcIdOfRouteCollection1NewRoute.equals(client)) {
                        oldClientSrcIdOfRouteCollection1NewRoute.getRouteCollection1().remove(routeCollection1NewRoute);
                        oldClientSrcIdOfRouteCollection1NewRoute = em.merge(oldClientSrcIdOfRouteCollection1NewRoute);
                    }
                }
            }
            for (RouteCopy routeCopyCollectionNewRouteCopy : routeCopyCollectionNew) {
                if (!routeCopyCollectionOld.contains(routeCopyCollectionNewRouteCopy)) {
                    Client oldClientIdOfRouteCopyCollectionNewRouteCopy = routeCopyCollectionNewRouteCopy.getClientId();
                    routeCopyCollectionNewRouteCopy.setClientId(client);
                    routeCopyCollectionNewRouteCopy = em.merge(routeCopyCollectionNewRouteCopy);
                    if (oldClientIdOfRouteCopyCollectionNewRouteCopy != null && !oldClientIdOfRouteCopyCollectionNewRouteCopy.equals(client)) {
                        oldClientIdOfRouteCopyCollectionNewRouteCopy.getRouteCopyCollection().remove(routeCopyCollectionNewRouteCopy);
                        oldClientIdOfRouteCopyCollectionNewRouteCopy = em.merge(oldClientIdOfRouteCopyCollectionNewRouteCopy);
                    }
                }
            }
            for (UserAccount userAccountCollectionOldUserAccount : userAccountCollectionOld) {
                if (!userAccountCollectionNew.contains(userAccountCollectionOldUserAccount)) {
                    userAccountCollectionOldUserAccount.setClientId(null);
                    userAccountCollectionOldUserAccount = em.merge(userAccountCollectionOldUserAccount);
                }
            }
            for (UserAccount userAccountCollectionNewUserAccount : userAccountCollectionNew) {
                if (!userAccountCollectionOld.contains(userAccountCollectionNewUserAccount)) {
                    Client oldClientIdOfUserAccountCollectionNewUserAccount = userAccountCollectionNewUserAccount.getClientId();
                    userAccountCollectionNewUserAccount.setClientId(client);
                    userAccountCollectionNewUserAccount = em.merge(userAccountCollectionNewUserAccount);
                    if (oldClientIdOfUserAccountCollectionNewUserAccount != null && !oldClientIdOfUserAccountCollectionNewUserAccount.equals(client)) {
                        oldClientIdOfUserAccountCollectionNewUserAccount.getUserAccountCollection().remove(userAccountCollectionNewUserAccount);
                        oldClientIdOfUserAccountCollectionNewUserAccount = em.merge(oldClientIdOfUserAccountCollectionNewUserAccount);
                    }
                }
            }
            for (FtpInfo ftpInfoCollectionNewFtpInfo : ftpInfoCollectionNew) {
                if (!ftpInfoCollectionOld.contains(ftpInfoCollectionNewFtpInfo)) {
                    Client oldClientIdOfFtpInfoCollectionNewFtpInfo = ftpInfoCollectionNewFtpInfo.getClientId();
                    ftpInfoCollectionNewFtpInfo.setClientId(client);
                    ftpInfoCollectionNewFtpInfo = em.merge(ftpInfoCollectionNewFtpInfo);
                    if (oldClientIdOfFtpInfoCollectionNewFtpInfo != null && !oldClientIdOfFtpInfoCollectionNewFtpInfo.equals(client)) {
                        oldClientIdOfFtpInfoCollectionNewFtpInfo.getFtpInfoCollection().remove(ftpInfoCollectionNewFtpInfo);
                        oldClientIdOfFtpInfoCollectionNewFtpInfo = em.merge(oldClientIdOfFtpInfoCollectionNewFtpInfo);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = client.getId();
                if (findClient(id) == null) {
                    throw new NonexistentEntityException("The client with id " + id + " no longer exists.");
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
            Client client;
            try {
                client = em.getReference(Client.class, id);
                client.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The client with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            PharmacyMaInfo pharmacyMaInfoOrphanCheck = client.getPharmacyMaInfo();
            if (pharmacyMaInfoOrphanCheck != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Client (" + client + ") cannot be destroyed since the PharmacyMaInfo " + pharmacyMaInfoOrphanCheck + " in its pharmacyMaInfo field has a non-nullable client field.");
            }
            MzPartnerInfo mzPartnerInfoOrphanCheck = client.getMzPartnerInfo();
            if (mzPartnerInfoOrphanCheck != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Client (" + client + ") cannot be destroyed since the MzPartnerInfo " + mzPartnerInfoOrphanCheck + " in its mzPartnerInfo field has a non-nullable client field.");
            }
            SupplierSettings supplierSettingsOrphanCheck = client.getSupplierSettings();
            if (supplierSettingsOrphanCheck != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Client (" + client + ") cannot be destroyed since the SupplierSettings " + supplierSettingsOrphanCheck + " in its supplierSettings field has a non-nullable client field.");
            }
            TransportInfo transportInfoOrphanCheck = client.getTransportInfo();
            if (transportInfoOrphanCheck != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Client (" + client + ") cannot be destroyed since the TransportInfo " + transportInfoOrphanCheck + " in its transportInfo field has a non-nullable client field.");
            }
            ClickPriceInfo clickPriceInfoOrphanCheck = client.getClickPriceInfo();
            if (clickPriceInfoOrphanCheck != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Client (" + client + ") cannot be destroyed since the ClickPriceInfo " + clickPriceInfoOrphanCheck + " in its clickPriceInfo field has a non-nullable client field.");
            }
            Collection<ClientNotes> clientNotesCollectionOrphanCheck = client.getClientNotesCollection();
            for (ClientNotes clientNotesCollectionOrphanCheckClientNotes : clientNotesCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Client (" + client + ") cannot be destroyed since the ClientNotes " + clientNotesCollectionOrphanCheckClientNotes + " in its clientNotesCollection field has a non-nullable clientId field.");
            }
            Collection<OfflineHistory> offlineHistoryCollectionOrphanCheck = client.getOfflineHistoryCollection();
            for (OfflineHistory offlineHistoryCollectionOrphanCheckOfflineHistory : offlineHistoryCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Client (" + client + ") cannot be destroyed since the OfflineHistory " + offlineHistoryCollectionOrphanCheckOfflineHistory + " in its offlineHistoryCollection field has a non-nullable clientId field.");
            }
            Collection<Route> routeCollectionOrphanCheck = client.getRouteCollection();
            for (Route routeCollectionOrphanCheckRoute : routeCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Client (" + client + ") cannot be destroyed since the Route " + routeCollectionOrphanCheckRoute + " in its routeCollection field has a non-nullable clientDstId field.");
            }
            Collection<Route> routeCollection1OrphanCheck = client.getRouteCollection1();
            for (Route routeCollection1OrphanCheckRoute : routeCollection1OrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Client (" + client + ") cannot be destroyed since the Route " + routeCollection1OrphanCheckRoute + " in its routeCollection1 field has a non-nullable clientSrcId field.");
            }
            Collection<RouteCopy> routeCopyCollectionOrphanCheck = client.getRouteCopyCollection();
            for (RouteCopy routeCopyCollectionOrphanCheckRouteCopy : routeCopyCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Client (" + client + ") cannot be destroyed since the RouteCopy " + routeCopyCollectionOrphanCheckRouteCopy + " in its routeCopyCollection field has a non-nullable clientId field.");
            }
            Collection<FtpInfo> ftpInfoCollectionOrphanCheck = client.getFtpInfoCollection();
            for (FtpInfo ftpInfoCollectionOrphanCheckFtpInfo : ftpInfoCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Client (" + client + ") cannot be destroyed since the FtpInfo " + ftpInfoCollectionOrphanCheckFtpInfo + " in its ftpInfoCollection field has a non-nullable clientId field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Region regionId = client.getRegionId();
            if (regionId != null) {
                regionId.getClientCollection().remove(client);
                regionId = em.merge(regionId);
            }
            Collection<Document> documentCollection = client.getDocumentCollection();
            for (Document documentCollectionDocument : documentCollection) {
                documentCollectionDocument.setClientDstId(null);
                documentCollectionDocument = em.merge(documentCollectionDocument);
            }
            Collection<Document> documentCollection1 = client.getDocumentCollection1();
            for (Document documentCollection1Document : documentCollection1) {
                documentCollection1Document.setClientSrcId(null);
                documentCollection1Document = em.merge(documentCollection1Document);
            }
            Collection<UserAccount> userAccountCollection = client.getUserAccountCollection();
            for (UserAccount userAccountCollectionUserAccount : userAccountCollection) {
                userAccountCollectionUserAccount.setClientId(null);
                userAccountCollectionUserAccount = em.merge(userAccountCollectionUserAccount);
            }
            em.remove(client);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Client> findClientEntities() {
        return findClientEntities(true, -1, -1);
    }

    public List<Client> findClientEntities(int maxResults, int firstResult) {
        return findClientEntities(false, maxResults, firstResult);
    }

    private List<Client> findClientEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Client.class));
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

    public Client findClient(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Client.class, id);
        } finally {
            em.close();
        }
    }

    public int getClientCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Client> rt = cq.from(Client.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
