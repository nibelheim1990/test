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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "export_sys_traffic_mapping")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ExportSysTrafficMapping.findAll", query = "SELECT e FROM ExportSysTrafficMapping e"),
    @NamedQuery(name = "ExportSysTrafficMapping.findById", query = "SELECT e FROM ExportSysTrafficMapping e WHERE e.id = :id"),
    @NamedQuery(name = "ExportSysTrafficMapping.findByExportDate", query = "SELECT e FROM ExportSysTrafficMapping e WHERE e.exportDate = :exportDate"),
    @NamedQuery(name = "ExportSysTrafficMapping.findByEscName", query = "SELECT e FROM ExportSysTrafficMapping e WHERE e.escName = :escName")})
public class ExportSysTrafficMapping implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;
    @Basic(optional = false)
    @Column(name = "export_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date exportDate;
    @Basic(optional = false)
    @Column(name = "esc_name")
    private String escName;
    @JoinColumn(name = "document_unique_field", referencedColumnName = "unique_field")
    @ManyToOne(optional = false)
    private Document documentUniqueField;

    public ExportSysTrafficMapping() {
    }

    public ExportSysTrafficMapping(Long id) {
        this.id = id;
    }

    public ExportSysTrafficMapping(Long id, Date exportDate, String escName) {
        this.id = id;
        this.exportDate = exportDate;
        this.escName = escName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getExportDate() {
        return exportDate;
    }

    public void setExportDate(Date exportDate) {
        this.exportDate = exportDate;
    }

    public String getEscName() {
        return escName;
    }

    public void setEscName(String escName) {
        this.escName = escName;
    }

    public Document getDocumentUniqueField() {
        return documentUniqueField;
    }

    public void setDocumentUniqueField(Document documentUniqueField) {
        this.documentUniqueField = documentUniqueField;
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
        if (!(object instanceof ExportSysTrafficMapping)) {
            return false;
        }
        ExportSysTrafficMapping other = (ExportSysTrafficMapping) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.itapteka.transport.dbcontroller.ExportSysTrafficMapping[ id=" + id + " ]";
    }
    
}
