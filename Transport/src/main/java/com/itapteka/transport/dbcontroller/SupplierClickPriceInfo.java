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
@Table(name = "supplier_click_price_info")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SupplierClickPriceInfo.findAll", query = "SELECT s FROM SupplierClickPriceInfo s"),
    @NamedQuery(name = "SupplierClickPriceInfo.findById", query = "SELECT s FROM SupplierClickPriceInfo s WHERE s.id = :id"),
    @NamedQuery(name = "SupplierClickPriceInfo.findByStupidOrm", query = "SELECT s FROM SupplierClickPriceInfo s WHERE s.stupidOrm = :stupidOrm")})
public class SupplierClickPriceInfo implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;
    @Column(name = "stupid_orm")
    private String stupidOrm;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "supplierClickPriceInfoId")
    private Collection<ClickPriceFormatInfo> clickPriceFormatInfoCollection;
    @JoinColumn(name = "id", referencedColumnName = "id", insertable = false, updatable = false)
    @OneToOne(optional = false)
    private ClickPriceInfo clickPriceInfo;
    @JoinColumn(name = "send_settings_id", referencedColumnName = "id")
    @ManyToOne
    private ClientSendSettings sendSettingsId;

    public SupplierClickPriceInfo() {
    }

    public SupplierClickPriceInfo(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStupidOrm() {
        return stupidOrm;
    }

    public void setStupidOrm(String stupidOrm) {
        this.stupidOrm = stupidOrm;
    }

    @XmlTransient
    public Collection<ClickPriceFormatInfo> getClickPriceFormatInfoCollection() {
        return clickPriceFormatInfoCollection;
    }

    public void setClickPriceFormatInfoCollection(Collection<ClickPriceFormatInfo> clickPriceFormatInfoCollection) {
        this.clickPriceFormatInfoCollection = clickPriceFormatInfoCollection;
    }

    public ClickPriceInfo getClickPriceInfo() {
        return clickPriceInfo;
    }

    public void setClickPriceInfo(ClickPriceInfo clickPriceInfo) {
        this.clickPriceInfo = clickPriceInfo;
    }

    public ClientSendSettings getSendSettingsId() {
        return sendSettingsId;
    }

    public void setSendSettingsId(ClientSendSettings sendSettingsId) {
        this.sendSettingsId = sendSettingsId;
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
        if (!(object instanceof SupplierClickPriceInfo)) {
            return false;
        }
        SupplierClickPriceInfo other = (SupplierClickPriceInfo) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.itapteka.transport.dbcontroller.SupplierClickPriceInfo[ id=" + id + " ]";
    }
    
}
