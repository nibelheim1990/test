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
@Table(name = "transport_format_info")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TransportFormatInfo.findAll", query = "SELECT t FROM TransportFormatInfo t"),
    @NamedQuery(name = "TransportFormatInfo.findById", query = "SELECT t FROM TransportFormatInfo t WHERE t.id = :id")})
public class TransportFormatInfo implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;
    @JoinColumn(name = "format_info_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private FormatInfo formatInfoId;
    @JoinColumn(name = "supplier_settings_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private SupplierSettings supplierSettingsId;

    public TransportFormatInfo() {
    }

    public TransportFormatInfo(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public FormatInfo getFormatInfoId() {
        return formatInfoId;
    }

    public void setFormatInfoId(FormatInfo formatInfoId) {
        this.formatInfoId = formatInfoId;
    }

    public SupplierSettings getSupplierSettingsId() {
        return supplierSettingsId;
    }

    public void setSupplierSettingsId(SupplierSettings supplierSettingsId) {
        this.supplierSettingsId = supplierSettingsId;
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
        if (!(object instanceof TransportFormatInfo)) {
            return false;
        }
        TransportFormatInfo other = (TransportFormatInfo) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.itapteka.transport.dbcontroller.TransportFormatInfo[ id=" + id + " ]";
    }
    
}
