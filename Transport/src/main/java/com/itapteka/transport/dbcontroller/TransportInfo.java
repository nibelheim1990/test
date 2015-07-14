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
@Table(name = "transport_info")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TransportInfo.findAll", query = "SELECT t FROM TransportInfo t"),
    @NamedQuery(name = "TransportInfo.findById", query = "SELECT t FROM TransportInfo t WHERE t.id = :id"),
    @NamedQuery(name = "TransportInfo.findByIsOffice", query = "SELECT t FROM TransportInfo t WHERE t.isOffice = :isOffice"),
    @NamedQuery(name = "TransportInfo.findByDoNotify", query = "SELECT t FROM TransportInfo t WHERE t.doNotify = :doNotify"),
    @NamedQuery(name = "TransportInfo.findByPackSys", query = "SELECT t FROM TransportInfo t WHERE t.packSys = :packSys")})
public class TransportInfo implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;
    @Basic(optional = false)
    @Column(name = "is_office")
    private boolean isOffice;
    @Basic(optional = false)
    @Column(name = "do_notify")
    private boolean doNotify;
    @Basic(optional = false)
    @Column(name = "pack_sys")
    private boolean packSys;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "transportInfo")
    private TransportClientInfo transportClientInfo;
    @JoinColumn(name = "id", referencedColumnName = "id", insertable = false, updatable = false)
    @OneToOne(optional = false)
    private Client client;
    @JoinColumn(name = "schedule_id", referencedColumnName = "id")
    @ManyToOne
    private Schedule scheduleId;
    @OneToMany(mappedBy = "officeId")
    private Collection<TransportInfo> transportInfoCollection;
    @JoinColumn(name = "office_id", referencedColumnName = "id")
    @ManyToOne
    private TransportInfo officeId;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "transportInfoId")
    private Collection<HttpInfo> httpInfoCollection;

    public TransportInfo() {
    }

    public TransportInfo(Long id) {
        this.id = id;
    }

    public TransportInfo(Long id, boolean isOffice, boolean doNotify, boolean packSys) {
        this.id = id;
        this.isOffice = isOffice;
        this.doNotify = doNotify;
        this.packSys = packSys;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean getIsOffice() {
        return isOffice;
    }

    public void setIsOffice(boolean isOffice) {
        this.isOffice = isOffice;
    }

    public boolean getDoNotify() {
        return doNotify;
    }

    public void setDoNotify(boolean doNotify) {
        this.doNotify = doNotify;
    }

    public boolean getPackSys() {
        return packSys;
    }

    public void setPackSys(boolean packSys) {
        this.packSys = packSys;
    }

    public TransportClientInfo getTransportClientInfo() {
        return transportClientInfo;
    }

    public void setTransportClientInfo(TransportClientInfo transportClientInfo) {
        this.transportClientInfo = transportClientInfo;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Schedule getScheduleId() {
        return scheduleId;
    }

    public void setScheduleId(Schedule scheduleId) {
        this.scheduleId = scheduleId;
    }

    @XmlTransient
    public Collection<TransportInfo> getTransportInfoCollection() {
        return transportInfoCollection;
    }

    public void setTransportInfoCollection(Collection<TransportInfo> transportInfoCollection) {
        this.transportInfoCollection = transportInfoCollection;
    }

    public TransportInfo getOfficeId() {
        return officeId;
    }

    public void setOfficeId(TransportInfo officeId) {
        this.officeId = officeId;
    }

    @XmlTransient
    public Collection<HttpInfo> getHttpInfoCollection() {
        return httpInfoCollection;
    }

    public void setHttpInfoCollection(Collection<HttpInfo> httpInfoCollection) {
        this.httpInfoCollection = httpInfoCollection;
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
        if (!(object instanceof TransportInfo)) {
            return false;
        }
        TransportInfo other = (TransportInfo) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.itapteka.transport.dbcontroller.TransportInfo[ id=" + id + " ]";
    }
    
}
