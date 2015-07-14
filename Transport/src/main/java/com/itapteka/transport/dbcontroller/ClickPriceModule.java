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
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Ponomarev
 */
@Entity
@Table(name = "click_price_module")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ClickPriceModule.findAll", query = "SELECT c FROM ClickPriceModule c"),
    @NamedQuery(name = "ClickPriceModule.findById", query = "SELECT c FROM ClickPriceModule c WHERE c.id = :id"),
    @NamedQuery(name = "ClickPriceModule.findByModuleId", query = "SELECT c FROM ClickPriceModule c WHERE c.moduleId = :moduleId"),
    @NamedQuery(name = "ClickPriceModule.findByFileName", query = "SELECT c FROM ClickPriceModule c WHERE c.fileName = :fileName"),
    @NamedQuery(name = "ClickPriceModule.findByNameSpace", query = "SELECT c FROM ClickPriceModule c WHERE c.nameSpace = :nameSpace"),
    @NamedQuery(name = "ClickPriceModule.findByDescription", query = "SELECT c FROM ClickPriceModule c WHERE c.description = :description"),
    @NamedQuery(name = "ClickPriceModule.findByFileVersionInfo", query = "SELECT c FROM ClickPriceModule c WHERE c.fileVersionInfo = :fileVersionInfo"),
    @NamedQuery(name = "ClickPriceModule.findBySettings", query = "SELECT c FROM ClickPriceModule c WHERE c.settings = :settings"),
    @NamedQuery(name = "ClickPriceModule.findByTarget", query = "SELECT c FROM ClickPriceModule c WHERE c.target = :target")})
public class ClickPriceModule implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;
    @Basic(optional = false)
    @Column(name = "module_id")
    private int moduleId;
    @Column(name = "file_name")
    private String fileName;
    @Lob
    @Column(name = "value")
    private byte[] value;
    @Column(name = "name_space")
    private String nameSpace;
    @Column(name = "description")
    private String description;
    @Column(name = "file_version_info")
    private String fileVersionInfo;
    @Column(name = "settings")
    private String settings;
    @Basic(optional = false)
    @Column(name = "target")
    private String target;

    public ClickPriceModule() {
    }

    public ClickPriceModule(Long id) {
        this.id = id;
    }

    public ClickPriceModule(Long id, int moduleId, String target) {
        this.id = id;
        this.moduleId = moduleId;
        this.target = target;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getModuleId() {
        return moduleId;
    }

    public void setModuleId(int moduleId) {
        this.moduleId = moduleId;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public byte[] getValue() {
        return value;
    }

    public void setValue(byte[] value) {
        this.value = value;
    }

    public String getNameSpace() {
        return nameSpace;
    }

    public void setNameSpace(String nameSpace) {
        this.nameSpace = nameSpace;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFileVersionInfo() {
        return fileVersionInfo;
    }

    public void setFileVersionInfo(String fileVersionInfo) {
        this.fileVersionInfo = fileVersionInfo;
    }

    public String getSettings() {
        return settings;
    }

    public void setSettings(String settings) {
        this.settings = settings;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
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
        if (!(object instanceof ClickPriceModule)) {
            return false;
        }
        ClickPriceModule other = (ClickPriceModule) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.itapteka.transport.dbcontroller.ClickPriceModule[ id=" + id + " ]";
    }
    
}
