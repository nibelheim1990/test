/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.itapteka.transport.dbcontroller;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Ponomarev
 */
@Entity
@Table(name = "document")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Document.findAll", query = "SELECT d FROM Document d"),
    @NamedQuery(name = "Document.findById", query = "SELECT d FROM Document d WHERE d.id = :id"),
    @NamedQuery(name = "Document.findByName", query = "SELECT d FROM Document d WHERE d.name = :name"),
    @NamedQuery(name = "Document.findByUploadStart", query = "SELECT d FROM Document d WHERE d.uploadStart = :uploadStart"),
    @NamedQuery(name = "Document.findByDate", query = "SELECT d FROM Document d WHERE d.date = :date"),
    @NamedQuery(name = "Document.findByTrafficType", query = "SELECT d FROM Document d WHERE d.trafficType = :trafficType"),
    @NamedQuery(name = "Document.findByHash", query = "SELECT d FROM Document d WHERE d.hash = :hash"),
    @NamedQuery(name = "Document.findBySize", query = "SELECT d FROM Document d WHERE d.size = :size"),
    @NamedQuery(name = "Document.findByState", query = "SELECT d FROM Document d WHERE d.state = :state"),
    @NamedQuery(name = "Document.findByIsPacked", query = "SELECT d FROM Document d WHERE d.isPacked = :isPacked"),
    @NamedQuery(name = "Document.findByGridFsId", query = "SELECT d FROM Document d WHERE d.gridFsId = :gridFsId")})
public class Document implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;
    @Basic(optional = false)
    @Column(name = "name")
    private String name;
    @Column(name = "upload_start")
    @Temporal(TemporalType.TIMESTAMP)
    private Date uploadStart;
    @Column(name = "date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date date;
    @Column(name = "traffic_type")
    private String trafficType;
    @Column(name = "hash")
    private String hash;
    @Column(name = "size")
    private BigInteger size;
    @Basic(optional = false)
    @Column(name = "state")
    private int state;
    @Basic(optional = false)
    @Column(name = "is_packed")
    private boolean isPacked;
    @Column(name = "grid_fs_id")
    private String gridFsId;
    @Basic(optional = false)
    @Lob
    @Column(name = "unique_field")
    private Object uniqueField;
    @JoinColumn(name = "client_dst_id", referencedColumnName = "id")
    @ManyToOne
    private Client clientDstId;
    @JoinColumn(name = "client_src_id", referencedColumnName = "id")
    @ManyToOne
    private Client clientSrcId;
    @OneToMany(mappedBy = "packId")
    private Collection<Document> documentCollection;
    @JoinColumn(name = "pack_id", referencedColumnName = "id")
    @ManyToOne
    private Document packId;
    @OneToMany(mappedBy = "parentId")
    private Collection<Document> documentCollection1;
    @JoinColumn(name = "parent_id", referencedColumnName = "id")
    @ManyToOne
    private Document parentId;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "documentUniqueField")
    private Collection<ExportSysTrafficMapping> exportSysTrafficMappingCollection;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "document")
    private EnaklMetadata enaklMetadata;

    public Document() {
    }

    public Document(Long id) {
        this.id = id;
    }

    public Document(Long id, String name, int state, boolean isPacked, Object uniqueField) {
        this.id = id;
        this.name = name;
        this.state = state;
        this.isPacked = isPacked;
        this.uniqueField = uniqueField;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getUploadStart() {
        return uploadStart;
    }

    public void setUploadStart(Date uploadStart) {
        this.uploadStart = uploadStart;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getTrafficType() {
        return trafficType;
    }

    public void setTrafficType(String trafficType) {
        this.trafficType = trafficType;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public BigInteger getSize() {
        return size;
    }

    public void setSize(BigInteger size) {
        this.size = size;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public boolean getIsPacked() {
        return isPacked;
    }

    public void setIsPacked(boolean isPacked) {
        this.isPacked = isPacked;
    }

    public String getGridFsId() {
        return gridFsId;
    }

    public void setGridFsId(String gridFsId) {
        this.gridFsId = gridFsId;
    }

    public Object getUniqueField() {
        return uniqueField;
    }

    public void setUniqueField(Object uniqueField) {
        this.uniqueField = uniqueField;
    }

    public Client getClientDstId() {
        return clientDstId;
    }

    public void setClientDstId(Client clientDstId) {
        this.clientDstId = clientDstId;
    }

    public Client getClientSrcId() {
        return clientSrcId;
    }

    public void setClientSrcId(Client clientSrcId) {
        this.clientSrcId = clientSrcId;
    }

    @XmlTransient
    public Collection<Document> getDocumentCollection() {
        return documentCollection;
    }

    public void setDocumentCollection(Collection<Document> documentCollection) {
        this.documentCollection = documentCollection;
    }

    public Document getPackId() {
        return packId;
    }

    public void setPackId(Document packId) {
        this.packId = packId;
    }

    @XmlTransient
    public Collection<Document> getDocumentCollection1() {
        return documentCollection1;
    }

    public void setDocumentCollection1(Collection<Document> documentCollection1) {
        this.documentCollection1 = documentCollection1;
    }

    public Document getParentId() {
        return parentId;
    }

    public void setParentId(Document parentId) {
        this.parentId = parentId;
    }

    @XmlTransient
    public Collection<ExportSysTrafficMapping> getExportSysTrafficMappingCollection() {
        return exportSysTrafficMappingCollection;
    }

    public void setExportSysTrafficMappingCollection(Collection<ExportSysTrafficMapping> exportSysTrafficMappingCollection) {
        this.exportSysTrafficMappingCollection = exportSysTrafficMappingCollection;
    }

    public EnaklMetadata getEnaklMetadata() {
        return enaklMetadata;
    }

    public void setEnaklMetadata(EnaklMetadata enaklMetadata) {
        this.enaklMetadata = enaklMetadata;
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
        if (!(object instanceof Document)) {
            return false;
        }
        Document other = (Document) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.itapteka.transport.dbcontroller.Document[ id=" + id + " ]";
    }
    
}
