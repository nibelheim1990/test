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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Ponomarev
 */
@Entity
@Table(name = "ftp_info")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "FtpInfo.findAll", query = "SELECT f FROM FtpInfo f"),
    @NamedQuery(name = "FtpInfo.findById", query = "SELECT f FROM FtpInfo f WHERE f.id = :id"),
    @NamedQuery(name = "FtpInfo.findByLogin", query = "SELECT f FROM FtpInfo f WHERE f.login = :login"),
    @NamedQuery(name = "FtpInfo.findByPassword", query = "SELECT f FROM FtpInfo f WHERE f.password = :password"),
    @NamedQuery(name = "FtpInfo.findByHomeDir", query = "SELECT f FROM FtpInfo f WHERE f.homeDir = :homeDir"),
    @NamedQuery(name = "FtpInfo.findByIsDefault", query = "SELECT f FROM FtpInfo f WHERE f.isDefault = :isDefault")})
public class FtpInfo implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;
    @Basic(optional = false)
    @Column(name = "login")
    private String login;
    @Column(name = "password")
    private String password;
    @Basic(optional = false)
    @Column(name = "home_dir")
    private String homeDir;
    @Column(name = "is_default")
    private Boolean isDefault;
    @JoinColumn(name = "client_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Client clientId;

    public FtpInfo() {
    }

    public FtpInfo(Long id) {
        this.id = id;
    }

    public FtpInfo(Long id, String login, String homeDir) {
        this.id = id;
        this.login = login;
        this.homeDir = homeDir;
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

    public String getHomeDir() {
        return homeDir;
    }

    public void setHomeDir(String homeDir) {
        this.homeDir = homeDir;
    }

    public Boolean getIsDefault() {
        return isDefault;
    }

    public void setIsDefault(Boolean isDefault) {
        this.isDefault = isDefault;
    }

    public Client getClientId() {
        return clientId;
    }

    public void setClientId(Client clientId) {
        this.clientId = clientId;
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
        if (!(object instanceof FtpInfo)) {
            return false;
        }
        FtpInfo other = (FtpInfo) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.itapteka.transport.dbcontroller.FtpInfo[ id=" + id + " ]";
    }
    
}
