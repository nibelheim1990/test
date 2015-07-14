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
@Table(name = "mz_partner_info")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "MzPartnerInfo.findAll", query = "SELECT m FROM MzPartnerInfo m"),
    @NamedQuery(name = "MzPartnerInfo.findById", query = "SELECT m FROM MzPartnerInfo m WHERE m.id = :id"),
    @NamedQuery(name = "MzPartnerInfo.findByCode", query = "SELECT m FROM MzPartnerInfo m WHERE m.code = :code")})
public class MzPartnerInfo implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;
    @Column(name = "code")
    private Integer code;
    @JoinColumn(name = "id", referencedColumnName = "id", insertable = false, updatable = false)
    @OneToOne(optional = false)
    private Client client;

    public MzPartnerInfo() {
    }

    public MzPartnerInfo(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
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
        if (!(object instanceof MzPartnerInfo)) {
            return false;
        }
        MzPartnerInfo other = (MzPartnerInfo) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.itapteka.transport.dbcontroller.MzPartnerInfo[ id=" + id + " ]";
    }
    
}
