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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Ponomarev
 */
@Entity
@Table(name = "http_info")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "HttpInfo.findAll", query = "SELECT h FROM HttpInfo h"),
    @NamedQuery(name = "HttpInfo.findById", query = "SELECT h FROM HttpInfo h WHERE h.id = :id"),
    @NamedQuery(name = "HttpInfo.findByLogin", query = "SELECT h FROM HttpInfo h WHERE h.login = :login"),
    @NamedQuery(name = "HttpInfo.findByPassword", query = "SELECT h FROM HttpInfo h WHERE h.password = :password")})
public class HttpInfo implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;
    @Basic(optional = false)
    @Column(name = "login")
    private String login;
    @Basic(optional = false)
    @Column(name = "password")
    private String password;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "httpInfoId")
    private Collection<ClientSettings> clientSettingsCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "httpInfoId")
    private Collection<ClientLog> clientLogCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "httpInfoId")
    private Collection<ClientTraffic> clientTrafficCollection;
    @JoinColumn(name = "transport_info_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private TransportInfo transportInfoId;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "httpInfo")
    private ServerClientConfig serverClientConfig;

    public HttpInfo() {
    }

    public HttpInfo(Long id) {
        this.id = id;
    }

    public HttpInfo(Long id, String login, String password) {
        this.id = id;
        this.login = login;
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @XmlTransient
    public Collection<ClientSettings> getClientSettingsCollection() {
        return clientSettingsCollection;
    }

    public void setClientSettingsCollection(Collection<ClientSettings> clientSettingsCollection) {
        this.clientSettingsCollection = clientSettingsCollection;
    }

    @XmlTransient
    public Collection<ClientLog> getClientLogCollection() {
        return clientLogCollection;
    }

    public void setClientLogCollection(Collection<ClientLog> clientLogCollection) {
        this.clientLogCollection = clientLogCollection;
    }

    @XmlTransient
    public Collection<ClientTraffic> getClientTrafficCollection() {
        return clientTrafficCollection;
    }

    public void setClientTrafficCollection(Collection<ClientTraffic> clientTrafficCollection) {
        this.clientTrafficCollection = clientTrafficCollection;
    }

    public TransportInfo getTransportInfoId() {
        return transportInfoId;
    }

    public void setTransportInfoId(TransportInfo transportInfoId) {
        this.transportInfoId = transportInfoId;
    }

    public ServerClientConfig getServerClientConfig() {
        return serverClientConfig;
    }

    public void setServerClientConfig(ServerClientConfig serverClientConfig) {
        this.serverClientConfig = serverClientConfig;
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
        if (!(object instanceof HttpInfo)) {
            return false;
        }
        HttpInfo other = (HttpInfo) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.itapteka.transport.dbcontroller.HttpInfo[ id=" + id + " ]";
    }
    
}
