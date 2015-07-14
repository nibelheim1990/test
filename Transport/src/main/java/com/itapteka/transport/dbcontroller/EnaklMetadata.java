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
@Table(name = "enakl_metadata")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "EnaklMetadata.findAll", query = "SELECT e FROM EnaklMetadata e"),
    @NamedQuery(name = "EnaklMetadata.findById", query = "SELECT e FROM EnaklMetadata e WHERE e.id = :id"),
    @NamedQuery(name = "EnaklMetadata.findByEnaklMaptekaId", query = "SELECT e FROM EnaklMetadata e WHERE e.enaklMaptekaId = :enaklMaptekaId")})
public class EnaklMetadata implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;
    @Basic(optional = false)
    @Column(name = "enakl_mapteka_id")
    private String enaklMaptekaId;
    @JoinColumn(name = "id", referencedColumnName = "id", insertable = false, updatable = false)
    @OneToOne(optional = false)
    private Document document;

    public EnaklMetadata() {
    }

    public EnaklMetadata(Long id) {
        this.id = id;
    }

    public EnaklMetadata(Long id, String enaklMaptekaId) {
        this.id = id;
        this.enaklMaptekaId = enaklMaptekaId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEnaklMaptekaId() {
        return enaklMaptekaId;
    }

    public void setEnaklMaptekaId(String enaklMaptekaId) {
        this.enaklMaptekaId = enaklMaptekaId;
    }

    public Document getDocument() {
        return document;
    }

    public void setDocument(Document document) {
        this.document = document;
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
        if (!(object instanceof EnaklMetadata)) {
            return false;
        }
        EnaklMetadata other = (EnaklMetadata) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.itapteka.transport.dbcontroller.EnaklMetadata[ id=" + id + " ]";
    }
    
}
