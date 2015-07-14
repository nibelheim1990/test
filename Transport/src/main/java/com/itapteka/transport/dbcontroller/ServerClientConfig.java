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
@Table(name = "server_client_config")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ServerClientConfig.findAll", query = "SELECT s FROM ServerClientConfig s"),
    @NamedQuery(name = "ServerClientConfig.findById", query = "SELECT s FROM ServerClientConfig s WHERE s.id = :id"),
    @NamedQuery(name = "ServerClientConfig.findByMaxfiles", query = "SELECT s FROM ServerClientConfig s WHERE s.maxfiles = :maxfiles")})
public class ServerClientConfig implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;
    @Basic(optional = false)
    @Column(name = "maxfiles")
    private int maxfiles;
    @JoinColumn(name = "id", referencedColumnName = "id", insertable = false, updatable = false)
    @OneToOne(optional = false)
    private HttpInfo httpInfo;

    public ServerClientConfig() {
    }

    public ServerClientConfig(Long id) {
        this.id = id;
    }

    public ServerClientConfig(Long id, int maxfiles) {
        this.id = id;
        this.maxfiles = maxfiles;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getMaxfiles() {
        return maxfiles;
    }

    public void setMaxfiles(int maxfiles) {
        this.maxfiles = maxfiles;
    }

    public HttpInfo getHttpInfo() {
        return httpInfo;
    }

    public void setHttpInfo(HttpInfo httpInfo) {
        this.httpInfo = httpInfo;
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
        if (!(object instanceof ServerClientConfig)) {
            return false;
        }
        ServerClientConfig other = (ServerClientConfig) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.itapteka.transport.dbcontroller.ServerClientConfig[ id=" + id + " ]";
    }
    
}
