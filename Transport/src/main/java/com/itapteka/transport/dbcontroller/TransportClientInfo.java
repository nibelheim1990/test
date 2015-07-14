/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.itapteka.transport.dbcontroller;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Ponomarev
 */
@Entity
@Table(name = "transport_client_info")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TransportClientInfo.findAll", query = "SELECT t FROM TransportClientInfo t"),
    @NamedQuery(name = "TransportClientInfo.findById", query = "SELECT t FROM TransportClientInfo t WHERE t.id = :id"),
    @NamedQuery(name = "TransportClientInfo.findByVersion", query = "SELECT t FROM TransportClientInfo t WHERE t.version = :version"),
    @NamedQuery(name = "TransportClientInfo.findByInDate", query = "SELECT t FROM TransportClientInfo t WHERE t.inDate = :inDate"),
    @NamedQuery(name = "TransportClientInfo.findByOutDate", query = "SELECT t FROM TransportClientInfo t WHERE t.outDate = :outDate"),
    @NamedQuery(name = "TransportClientInfo.findByInInfo", query = "SELECT t FROM TransportClientInfo t WHERE t.inInfo = :inInfo"),
    @NamedQuery(name = "TransportClientInfo.findByOutInfo", query = "SELECT t FROM TransportClientInfo t WHERE t.outInfo = :outInfo"),
    @NamedQuery(name = "TransportClientInfo.findByDownloadDate", query = "SELECT t FROM TransportClientInfo t WHERE t.downloadDate = :downloadDate"),
    @NamedQuery(name = "TransportClientInfo.findByUploadDate", query = "SELECT t FROM TransportClientInfo t WHERE t.uploadDate = :uploadDate"),
    @NamedQuery(name = "TransportClientInfo.findByMachineKey", query = "SELECT t FROM TransportClientInfo t WHERE t.machineKey = :machineKey")})
public class TransportClientInfo implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;
    @Column(name = "version")
    private String version;
    @Column(name = "in_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date inDate;
    @Column(name = "out_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date outDate;
    @Column(name = "in_info")
    private String inInfo;
    @Column(name = "out_info")
    private String outInfo;
    @Column(name = "download_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date downloadDate;
    @Column(name = "upload_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date uploadDate;
    @Column(name = "machine_key")
    private String machineKey;
    @JoinColumn(name = "id", referencedColumnName = "id", insertable = false, updatable = false)
    @OneToOne(optional = false)
    private TransportInfo transportInfo;

    public TransportClientInfo() {
    }

    public TransportClientInfo(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public Date getInDate() {
        return inDate;
    }

    public void setInDate(Date inDate) {
        this.inDate = inDate;
    }

    public Date getOutDate() {
        return outDate;
    }

    public void setOutDate(Date outDate) {
        this.outDate = outDate;
    }

    public String getInInfo() {
        return inInfo;
    }

    public void setInInfo(String inInfo) {
        this.inInfo = inInfo;
    }

    public String getOutInfo() {
        return outInfo;
    }

    public void setOutInfo(String outInfo) {
        this.outInfo = outInfo;
    }

    public Date getDownloadDate() {
        return downloadDate;
    }

    public void setDownloadDate(Date downloadDate) {
        this.downloadDate = downloadDate;
    }

    public Date getUploadDate() {
        return uploadDate;
    }

    public void setUploadDate(Date uploadDate) {
        this.uploadDate = uploadDate;
    }

    public String getMachineKey() {
        return machineKey;
    }

    public void setMachineKey(String machineKey) {
        this.machineKey = machineKey;
    }

    public TransportInfo getTransportInfo() {
        return transportInfo;
    }

    public void setTransportInfo(TransportInfo transportInfo) {
        this.transportInfo = transportInfo;
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
        if (!(object instanceof TransportClientInfo)) {
            return false;
        }
        TransportClientInfo other = (TransportClientInfo) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.itapteka.transport.dbcontroller.TransportClientInfo[ id=" + id + " ]";
    }
    
}
