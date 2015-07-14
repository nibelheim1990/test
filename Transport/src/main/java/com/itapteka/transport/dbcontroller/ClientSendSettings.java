/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.itapteka.transport.dbcontroller;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Ponomarev
 */
@Entity
@Table(name = "client_send_settings")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ClientSendSettings.findAll", query = "SELECT c FROM ClientSendSettings c"),
    @NamedQuery(name = "ClientSendSettings.findById", query = "SELECT c FROM ClientSendSettings c WHERE c.id = :id"),
    @NamedQuery(name = "ClientSendSettings.findByName", query = "SELECT c FROM ClientSendSettings c WHERE c.name = :name"),
    @NamedQuery(name = "ClientSendSettings.findBySettings", query = "SELECT c FROM ClientSendSettings c WHERE c.settings = :settings")})
public class ClientSendSettings implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;
    @Basic(optional = false)
    @Column(name = "name")
    private String name;
    @Basic(optional = false)
    @Column(name = "settings")
    private String settings;
    @OneToMany(mappedBy = "sendSettingsId")
    private Collection<SupplierClickPriceInfo> supplierClickPriceInfoCollection;

    public ClientSendSettings() {
    }

    public ClientSendSettings(Long id) {
        this.id = id;
    }

    public ClientSendSettings(Long id, String name, String settings) {
        this.id = id;
        this.name = name;
        this.settings = settings;
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

    public String getSettings() {
        return settings;
    }

    public void setSettings(String settings) {
        this.settings = settings;
    }

    @XmlTransient
    public Collection<SupplierClickPriceInfo> getSupplierClickPriceInfoCollection() {
        return supplierClickPriceInfoCollection;
    }

    public void setSupplierClickPriceInfoCollection(Collection<SupplierClickPriceInfo> supplierClickPriceInfoCollection) {
        this.supplierClickPriceInfoCollection = supplierClickPriceInfoCollection;
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
        if (!(object instanceof ClientSendSettings)) {
            return false;
        }
        ClientSendSettings other = (ClientSendSettings) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.itapteka.transport.dbcontroller.ClientSendSettings[ id=" + id + " ]";
    }
    
}
