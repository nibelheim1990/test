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
import javax.persistence.Lob;
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
@Table(name = "pharmacy_click_price_info")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PharmacyClickPriceInfo.findAll", query = "SELECT p FROM PharmacyClickPriceInfo p"),
    @NamedQuery(name = "PharmacyClickPriceInfo.findById", query = "SELECT p FROM PharmacyClickPriceInfo p WHERE p.id = :id"),
    @NamedQuery(name = "PharmacyClickPriceInfo.findByActivationDate", query = "SELECT p FROM PharmacyClickPriceInfo p WHERE p.activationDate = :activationDate"),
    @NamedQuery(name = "PharmacyClickPriceInfo.findByActivationKey", query = "SELECT p FROM PharmacyClickPriceInfo p WHERE p.activationKey = :activationKey")})
public class PharmacyClickPriceInfo implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;
    @Column(name = "activation_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date activationDate;
    @Column(name = "activation_key")
    private String activationKey;
    @Lob
    @Column(name = "hardware_hash")
    private byte[] hardwareHash;
    @JoinColumn(name = "id", referencedColumnName = "id", insertable = false, updatable = false)
    @OneToOne(optional = false)
    private ClickPriceInfo clickPriceInfo;

    public PharmacyClickPriceInfo() {
    }

    public PharmacyClickPriceInfo(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getActivationDate() {
        return activationDate;
    }

    public void setActivationDate(Date activationDate) {
        this.activationDate = activationDate;
    }

    public String getActivationKey() {
        return activationKey;
    }

    public void setActivationKey(String activationKey) {
        this.activationKey = activationKey;
    }

    public byte[] getHardwareHash() {
        return hardwareHash;
    }

    public void setHardwareHash(byte[] hardwareHash) {
        this.hardwareHash = hardwareHash;
    }

    public ClickPriceInfo getClickPriceInfo() {
        return clickPriceInfo;
    }

    public void setClickPriceInfo(ClickPriceInfo clickPriceInfo) {
        this.clickPriceInfo = clickPriceInfo;
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
        if (!(object instanceof PharmacyClickPriceInfo)) {
            return false;
        }
        PharmacyClickPriceInfo other = (PharmacyClickPriceInfo) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.itapteka.transport.dbcontroller.PharmacyClickPriceInfo[ id=" + id + " ]";
    }
    
}
