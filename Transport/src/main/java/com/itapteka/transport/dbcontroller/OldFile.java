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
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Ponomarev
 */
@Entity
@Table(name = "old_file")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "OldFile.findAll", query = "SELECT o FROM OldFile o"),
    @NamedQuery(name = "OldFile.findById", query = "SELECT o FROM OldFile o WHERE o.id = :id"),
    @NamedQuery(name = "OldFile.findByFindDate", query = "SELECT o FROM OldFile o WHERE o.findDate = :findDate"),
    @NamedQuery(name = "OldFile.findByPath", query = "SELECT o FROM OldFile o WHERE o.path = :path")})
public class OldFile implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;
    @Basic(optional = false)
    @Column(name = "find_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date findDate;
    @Basic(optional = false)
    @Column(name = "path")
    private String path;

    public OldFile() {
    }

    public OldFile(Long id) {
        this.id = id;
    }

    public OldFile(Long id, Date findDate, String path) {
        this.id = id;
        this.findDate = findDate;
        this.path = path;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getFindDate() {
        return findDate;
    }

    public void setFindDate(Date findDate) {
        this.findDate = findDate;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
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
        if (!(object instanceof OldFile)) {
            return false;
        }
        OldFile other = (OldFile) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.itapteka.transport.dbcontroller.OldFile[ id=" + id + " ]";
    }
    
}
