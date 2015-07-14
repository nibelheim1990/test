/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.itapteka.transport.dbcontroller;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Ponomarev
 */
@Entity
@Table(name = "client")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Client.findAll", query = "SELECT c FROM Client c"),
    @NamedQuery(name = "Client.findById", query = "SELECT c FROM Client c WHERE c.id = :id"),
    @NamedQuery(name = "Client.findByName", query = "SELECT c FROM Client c WHERE c.name = :name"),
    @NamedQuery(name = "Client.findByEmail", query = "SELECT c FROM Client c WHERE c.email = :email"),
    @NamedQuery(name = "Client.findByAddress", query = "SELECT c FROM Client c WHERE c.address = :address"),
    @NamedQuery(name = "Client.findByRole", query = "SELECT c FROM Client c WHERE c.role = :role")})
public class Client implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;
    @Basic(optional = false)
    @Column(name = "name")
    private String name;
    @Column(name = "email")
    private String email;
    @Column(name = "address")
    private String address;
    @Basic(optional = false)
    @Column(name = "role")
    private int role;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "client")
    private PharmacyMaInfo pharmacyMaInfo;
    @OneToMany(mappedBy = "clientDstId")
    private Collection<Document> documentCollection;
    @OneToMany(mappedBy = "clientSrcId")
    private Collection<Document> documentCollection1;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "client")
    private MzPartnerInfo mzPartnerInfo;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "client")
    private SupplierSettings supplierSettings;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "clientId")
    private Collection<ClientNotes> clientNotesCollection;
    @JoinColumn(name = "region_id", referencedColumnName = "id")
    @ManyToOne
    private Region regionId;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "client")
    private TransportInfo transportInfo;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "clientId")
    private Collection<OfflineHistory> offlineHistoryCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "clientDstId")
    private Collection<Route> routeCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "clientSrcId")
    private Collection<Route> routeCollection1;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "clientId")
    private Collection<RouteCopy> routeCopyCollection;
    @OneToMany(mappedBy = "clientId")
    private Collection<UserAccount> userAccountCollection;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "client")
    private ClickPriceInfo clickPriceInfo;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "clientId")
    private Collection<FtpInfo> ftpInfoCollection;

    public Client() {
    }

    public Client(Long id) {
        this.id = id;
    }

    public Client(Long id, String name, int role) {
        this.id = id;
        this.name = name;
        this.role = role;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }

    public PharmacyMaInfo getPharmacyMaInfo() {
        return pharmacyMaInfo;
    }

    public void setPharmacyMaInfo(PharmacyMaInfo pharmacyMaInfo) {
        this.pharmacyMaInfo = pharmacyMaInfo;
    }

    @XmlTransient
    public Collection<Document> getDocumentCollection() {
        return documentCollection;
    }

    public void setDocumentCollection(Collection<Document> documentCollection) {
        this.documentCollection = documentCollection;
    }

    @XmlTransient
    public Collection<Document> getDocumentCollection1() {
        return documentCollection1;
    }

    public void setDocumentCollection1(Collection<Document> documentCollection1) {
        this.documentCollection1 = documentCollection1;
    }

    public MzPartnerInfo getMzPartnerInfo() {
        return mzPartnerInfo;
    }

    public void setMzPartnerInfo(MzPartnerInfo mzPartnerInfo) {
        this.mzPartnerInfo = mzPartnerInfo;
    }

    public SupplierSettings getSupplierSettings() {
        return supplierSettings;
    }

    public void setSupplierSettings(SupplierSettings supplierSettings) {
        this.supplierSettings = supplierSettings;
    }

    @XmlTransient
    public Collection<ClientNotes> getClientNotesCollection() {
        return clientNotesCollection;
    }

    public void setClientNotesCollection(Collection<ClientNotes> clientNotesCollection) {
        this.clientNotesCollection = clientNotesCollection;
    }

    public Region getRegionId() {
        return regionId;
    }

    public void setRegionId(Region regionId) {
        this.regionId = regionId;
    }

    public TransportInfo getTransportInfo() {
        return transportInfo;
    }

    public void setTransportInfo(TransportInfo transportInfo) {
        this.transportInfo = transportInfo;
    }

    @XmlTransient
    public Collection<OfflineHistory> getOfflineHistoryCollection() {
        return offlineHistoryCollection;
    }

    public void setOfflineHistoryCollection(Collection<OfflineHistory> offlineHistoryCollection) {
        this.offlineHistoryCollection = offlineHistoryCollection;
    }

    @XmlTransient
    public Collection<Route> getRouteCollection() {
        return routeCollection;
    }

    public void setRouteCollection(Collection<Route> routeCollection) {
        this.routeCollection = routeCollection;
    }

    @XmlTransient
    public Collection<Route> getRouteCollection1() {
        return routeCollection1;
    }

    public void setRouteCollection1(Collection<Route> routeCollection1) {
        this.routeCollection1 = routeCollection1;
    }

    @XmlTransient
    public Collection<RouteCopy> getRouteCopyCollection() {
        return routeCopyCollection;
    }

    public void setRouteCopyCollection(Collection<RouteCopy> routeCopyCollection) {
        this.routeCopyCollection = routeCopyCollection;
    }

    @XmlTransient
    public Collection<UserAccount> getUserAccountCollection() {
        return userAccountCollection;
    }

    public void setUserAccountCollection(Collection<UserAccount> userAccountCollection) {
        this.userAccountCollection = userAccountCollection;
    }

    public ClickPriceInfo getClickPriceInfo() {
        return clickPriceInfo;
    }

    public void setClickPriceInfo(ClickPriceInfo clickPriceInfo) {
        this.clickPriceInfo = clickPriceInfo;
    }

    @XmlTransient
    public Collection<FtpInfo> getFtpInfoCollection() {
        return ftpInfoCollection;
    }

    public void setFtpInfoCollection(Collection<FtpInfo> ftpInfoCollection) {
        this.ftpInfoCollection = ftpInfoCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Client)) {
            return false;
        }
        Client other = (Client) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.itapteka.transport.dbcontroller.Client[ id=" + id + " ]";
    }
    
}
