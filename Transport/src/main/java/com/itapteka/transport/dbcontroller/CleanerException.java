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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Ponomarev
 */
@Entity
@Table(name = "cleaner_exception")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CleanerException.findAll", query = "SELECT c FROM CleanerException c"),
    @NamedQuery(name = "CleanerException.findById", query = "SELECT c FROM CleanerException c WHERE c.id = :id"),
    @NamedQuery(name = "CleanerException.findByStorageId", query = "SELECT c FROM CleanerException c WHERE c.storageId = :storageId")})
public class CleanerException implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;
    @Basic(optional = false)
    @Column(name = "storage_id")
    private String storageId;

    public CleanerException() {
    }

    public CleanerException(Long id) {
        this.id = id;
    }

    public CleanerException(Long id, String storageId) {
        this.id = id;
        this.storageId = storageId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStorageId() {
        return storageId;
    }

    public void setStorageId(String storageId) {
        this.storageId = storageId;
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
        if (!(object instanceof CleanerException)) {
            return false;
        }
        CleanerException other = (CleanerException) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.itapteka.transport.dbcontroller.CleanerException[ id=" + id + " ]";
    }
    
}
