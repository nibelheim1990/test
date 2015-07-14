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
@Table(name = "supplier_settings")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SupplierSettings.findAll", query = "SELECT s FROM SupplierSettings s"),
    @NamedQuery(name = "SupplierSettings.findById", query = "SELECT s FROM SupplierSettings s WHERE s.id = :id"),
    @NamedQuery(name = "SupplierSettings.findByFileMask", query = "SELECT s FROM SupplierSettings s WHERE s.fileMask = :fileMask"),
    @NamedQuery(name = "SupplierSettings.findByIsActive", query = "SELECT s FROM SupplierSettings s WHERE s.isActive = :isActive")})
public class SupplierSettings implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;
    @Basic(optional = false)
    @Column(name = "file_mask")
    private String fileMask;
    @Basic(optional = false)
    @Column(name = "is_active")
    private boolean isActive;
    @JoinColumn(name = "id", referencedColumnName = "id", insertable = false, updatable = false)
    @OneToOne(optional = false)
    private Client client;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "supplierSettingsId")
    private Collection<TransportFormatInfo> transportFormatInfoCollection;

    public SupplierSettings() {
    }

    public SupplierSettings(Long id) {
        this.id = id;
    }

    public SupplierSettings(Long id, String fileMask, boolean isActive) {
        this.id = id;
        this.fileMask = fileMask;
        this.isActive = isActive;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFileMask() {
        return fileMask;
    }

    public void setFileMask(String fileMask) {
        this.fileMask = fileMask;
    }

    public boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    @XmlTransient
    public Collection<TransportFormatInfo> getTransportFormatInfoCollection() {
        return transportFormatInfoCollection;
    }

    public void setTransportFormatInfoCollection(Collection<TransportFormatInfo> transportFormatInfoCollection) {
        this.transportFormatInfoCollection = transportFormatInfoCollection;
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
        if (!(object instanceof SupplierSettings)) {
            return false;
        }
        SupplierSettings other = (SupplierSettings) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.itapteka.transport.dbcontroller.SupplierSettings[ id=" + id + " ]";
    }
    
}
