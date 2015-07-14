/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.itapteka.transport.dbcontroller;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Ponomarev
 */
@Entity
@Table(name = "client_settings")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ClientSettings.findAll", query = "SELECT c FROM ClientSettings c"),
    @NamedQuery(name = "ClientSettings.findById", query = "SELECT c FROM ClientSettings c WHERE c.id = :id"),
    @NamedQuery(name = "ClientSettings.findByName", query = "SELECT c FROM ClientSettings c WHERE c.name = :name"),
    @NamedQuery(name = "ClientSettings.findBySettingValue", query = "SELECT c FROM ClientSettings c WHERE c.settingValue = :settingValue")})
public class ClientSettings implements Serializable {
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
    @Column(name = "setting_value")
    private String settingValue;
    @JoinColumn(name = "http_info_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private HttpInfo httpInfoId;

    public ClientSettings() {
    }

    public ClientSettings(Long id) {
        this.id = id;
    }

    public ClientSettings(Long id, String name, String settingValue) {
        this.id = id;
        this.name = name;
        this.settingValue = settingValue;
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

    public String getSettingValue() {
        return settingValue;
    }

    public void setSettingValue(String settingValue) {
        this.settingValue = settingValue;
    }

    public HttpInfo getHttpInfoId() {
        return httpInfoId;
    }

    public void setHttpInfoId(HttpInfo httpInfoId) {
        this.httpInfoId = httpInfoId;
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
        if (!(object instanceof ClientSettings)) {
            return false;
        }
        ClientSettings other = (ClientSettings) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.itapteka.transport.dbcontroller.ClientSettings[ id=" + id + " ]";
    }
    
}
