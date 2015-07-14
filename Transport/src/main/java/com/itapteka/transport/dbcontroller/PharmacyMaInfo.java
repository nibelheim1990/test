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
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Ponomarev
 */
@Entity
@Table(name = "pharmacy_ma_info")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PharmacyMaInfo.findAll", query = "SELECT p FROM PharmacyMaInfo p"),
    @NamedQuery(name = "PharmacyMaInfo.findById", query = "SELECT p FROM PharmacyMaInfo p WHERE p.id = :id"),
    @NamedQuery(name = "PharmacyMaInfo.findByMaptekaUsername", query = "SELECT p FROM PharmacyMaInfo p WHERE p.maptekaUsername = :maptekaUsername"),
    @NamedQuery(name = "PharmacyMaInfo.findByMaptekaCode", query = "SELECT p FROM PharmacyMaInfo p WHERE p.maptekaCode = :maptekaCode"),
    @NamedQuery(name = "PharmacyMaInfo.findByMaptekaBase", query = "SELECT p FROM PharmacyMaInfo p WHERE p.maptekaBase = :maptekaBase")})
public class PharmacyMaInfo implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;
    @Basic(optional = false)
    @Column(name = "mapteka_username")
    private String maptekaUsername;
    @Basic(optional = false)
    @Column(name = "mapteka_code")
    private int maptekaCode;
    @Basic(optional = false)
    @Column(name = "mapteka_base")
    private int maptekaBase;
    @JoinColumn(name = "id", referencedColumnName = "id", insertable = false, updatable = false)
    @OneToOne(optional = false)
    private Client client;

    public PharmacyMaInfo() {
    }

    public PharmacyMaInfo(Long id) {
        this.id = id;
    }

    public PharmacyMaInfo(Long id, String maptekaUsername, int maptekaCode, int maptekaBase) {
        this.id = id;
        this.maptekaUsername = maptekaUsername;
        this.maptekaCode = maptekaCode;
        this.maptekaBase = maptekaBase;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMaptekaUsername() {
        return maptekaUsername;
    }

    public void setMaptekaUsername(String maptekaUsername) {
        this.maptekaUsername = maptekaUsername;
    }

    public int getMaptekaCode() {
        return maptekaCode;
    }

    public void setMaptekaCode(int maptekaCode) {
        this.maptekaCode = maptekaCode;
    }

    public int getMaptekaBase() {
        return maptekaBase;
    }

    public void setMaptekaBase(int maptekaBase) {
        this.maptekaBase = maptekaBase;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
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
        if (!(object instanceof PharmacyMaInfo)) {
            return false;
        }
        PharmacyMaInfo other = (PharmacyMaInfo) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.itapteka.transport.dbcontroller.PharmacyMaInfo[ id=" + id + " ]";
    }
    
}
