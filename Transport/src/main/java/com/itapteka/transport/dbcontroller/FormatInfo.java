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
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Ponomarev
 */
@Entity
@Table(name = "format_info")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "FormatInfo.findAll", query = "SELECT f FROM FormatInfo f"),
    @NamedQuery(name = "FormatInfo.findById", query = "SELECT f FROM FormatInfo f WHERE f.id = :id"),
    @NamedQuery(name = "FormatInfo.findByTypeFormat", query = "SELECT f FROM FormatInfo f WHERE f.typeFormat = :typeFormat"),
    @NamedQuery(name = "FormatInfo.findByName", query = "SELECT f FROM FormatInfo f WHERE f.name = :name"),
    @NamedQuery(name = "FormatInfo.findByScript", query = "SELECT f FROM FormatInfo f WHERE f.script = :script"),
    @NamedQuery(name = "FormatInfo.findBySettings", query = "SELECT f FROM FormatInfo f WHERE f.settings = :settings"),
    @NamedQuery(name = "FormatInfo.findByNameTemplate", query = "SELECT f FROM FormatInfo f WHERE f.nameTemplate = :nameTemplate")})
public class FormatInfo implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;
    @Basic(optional = false)
    @Column(name = "type_format")
    private String typeFormat;
    @Basic(optional = false)
    @Column(name = "name")
    private String name;
    @Basic(optional = false)
    @Column(name = "script")
    private String script;
    @Basic(optional = false)
    @Column(name = "settings")
    private String settings;
    @Column(name = "name_template")
    private String nameTemplate;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "formatInfoId")
    private Collection<ClickPriceFormatInfo> clickPriceFormatInfoCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "formatInfoId")
    private Collection<TransportFormatInfo> transportFormatInfoCollection;

    public FormatInfo() {
    }

    public FormatInfo(Long id) {
        this.id = id;
    }

    public FormatInfo(Long id, String typeFormat, String name, String script, String settings) {
        this.id = id;
        this.typeFormat = typeFormat;
        this.name = name;
        this.script = script;
        this.settings = settings;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTypeFormat() {
        return typeFormat;
    }

    public void setTypeFormat(String typeFormat) {
        this.typeFormat = typeFormat;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getScript() {
        return script;
    }

    public void setScript(String script) {
        this.script = script;
    }

    public String getSettings() {
        return settings;
    }

    public void setSettings(String settings) {
        this.settings = settings;
    }

    public String getNameTemplate() {
        return nameTemplate;
    }

    public void setNameTemplate(String nameTemplate) {
        this.nameTemplate = nameTemplate;
    }

    @XmlTransient
    public Collection<ClickPriceFormatInfo> getClickPriceFormatInfoCollection() {
        return clickPriceFormatInfoCollection;
    }

    public void setClickPriceFormatInfoCollection(Collection<ClickPriceFormatInfo> clickPriceFormatInfoCollection) {
        this.clickPriceFormatInfoCollection = clickPriceFormatInfoCollection;
    }

    @XmlTransient
    public Collection<TransportFormatInfo> getTransportFormatInfoCollection() {
        return transportFormatInfoCollection;
    }

    public void setTransportFormatInfoCollection(Collection<TransportFormatInfo> transportFormatInfoCollection) {
        this.transportFormatInfoCollection = transportFormatInfoCollection;
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
        if (!(object instanceof FormatInfo)) {
            return false;
        }
        FormatInfo other = (FormatInfo) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.itapteka.transport.dbcontroller.FormatInfo[ id=" + id + " ]";
    }
    
}
