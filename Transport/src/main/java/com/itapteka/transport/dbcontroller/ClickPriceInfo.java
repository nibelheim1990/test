/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.itapteka.transport.dbcontroller;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
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
@Table(name = "click_price_info")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ClickPriceInfo.findAll", query = "SELECT c FROM ClickPriceInfo c"),
    @NamedQuery(name = "ClickPriceInfo.findById", query = "SELECT c FROM ClickPriceInfo c WHERE c.id = :id"),
    @NamedQuery(name = "ClickPriceInfo.findByOldClientId", query = "SELECT c FROM ClickPriceInfo c WHERE c.oldClientId = :oldClientId")})
public class ClickPriceInfo implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;
    @Column(name = "old_client_id")
    private Integer oldClientId;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "clickPriceInfo")
    private PharmacyClickPriceInfo pharmacyClickPriceInfo;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "clickPriceInfo")
    private SupplierClickPriceInfo supplierClickPriceInfo;
    @JoinColumn(name = "id", referencedColumnName = "id", insertable = false, updatable = false)
    @OneToOne(optional = false)
    private Client client;

    public ClickPriceInfo() {
    }

    public ClickPriceInfo(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getOldClientId() {
        return oldClientId;
    }

    public void setOldClientId(Integer oldClientId) {
        this.oldClientId = oldClientId;
    }

    public PharmacyClickPriceInfo getPharmacyClickPriceInfo() {
        return pharmacyClickPriceInfo;
    }

    public void setPharmacyClickPriceInfo(PharmacyClickPriceInfo pharmacyClickPriceInfo) {
        this.pharmacyClickPriceInfo = pharmacyClickPriceInfo;
    }

    public SupplierClickPriceInfo getSupplierClickPriceInfo() {
        return supplierClickPriceInfo;
    }

    public void setSupplierClickPriceInfo(SupplierClickPriceInfo supplierClickPriceInfo) {
        this.supplierClickPriceInfo = supplierClickPriceInfo;
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
        if (!(object instanceof ClickPriceInfo)) {
            return false;
        }
        ClickPriceInfo other = (ClickPriceInfo) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.itapteka.transport.dbcontroller.ClickPriceInfo[ id=" + id + " ]";
    }
    
}
