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
@Table(name = "client_updates")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ClientUpdates.findAll", query = "SELECT c FROM ClientUpdates c"),
    @NamedQuery(name = "ClientUpdates.findById", query = "SELECT c FROM ClientUpdates c WHERE c.id = :id"),
    @NamedQuery(name = "ClientUpdates.findByGridFsId", query = "SELECT c FROM ClientUpdates c WHERE c.gridFsId = :gridFsId"),
    @NamedQuery(name = "ClientUpdates.findByMajor", query = "SELECT c FROM ClientUpdates c WHERE c.major = :major"),
    @NamedQuery(name = "ClientUpdates.findByMinor", query = "SELECT c FROM ClientUpdates c WHERE c.minor = :minor"),
    @NamedQuery(name = "ClientUpdates.findByBuild", query = "SELECT c FROM ClientUpdates c WHERE c.build = :build"),
    @NamedQuery(name = "ClientUpdates.findByRevision", query = "SELECT c FROM ClientUpdates c WHERE c.revision = :revision"),
    @NamedQuery(name = "ClientUpdates.findByBranch", query = "SELECT c FROM ClientUpdates c WHERE c.branch = :branch")})
public class ClientUpdates implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;
    @Basic(optional = false)
    @Column(name = "grid_fs_id")
    private String gridFsId;
    @Basic(optional = false)
    @Column(name = "major")
    private int major;
    @Basic(optional = false)
    @Column(name = "minor")
    private int minor;
    @Basic(optional = false)
    @Column(name = "build")
    private int build;
    @Basic(optional = false)
    @Column(name = "revision")
    private int revision;
    @Basic(optional = false)
    @Column(name = "branch")
    private String branch;

    public ClientUpdates() {
    }

    public ClientUpdates(Long id) {
        this.id = id;
    }

    public ClientUpdates(Long id, String gridFsId, int major, int minor, int build, int revision, String branch) {
        this.id = id;
        this.gridFsId = gridFsId;
        this.major = major;
        this.minor = minor;
        this.build = build;
        this.revision = revision;
        this.branch = branch;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getGridFsId() {
        return gridFsId;
    }

    public void setGridFsId(String gridFsId) {
        this.gridFsId = gridFsId;
    }

    public int getMajor() {
        return major;
    }

    public void setMajor(int major) {
        this.major = major;
    }

    public int getMinor() {
        return minor;
    }

    public void setMinor(int minor) {
        this.minor = minor;
    }

    public int getBuild() {
        return build;
    }

    public void setBuild(int build) {
        this.build = build;
    }

    public int getRevision() {
        return revision;
    }

    public void setRevision(int revision) {
        this.revision = revision;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
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
        if (!(object instanceof ClientUpdates)) {
            return false;
        }
        ClientUpdates other = (ClientUpdates) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.itapteka.transport.dbcontroller.ClientUpdates[ id=" + id + " ]";
    }
    
}
