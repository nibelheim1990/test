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
@Table(name = "offline_history")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "OfflineHistory.findAll", query = "SELECT o FROM OfflineHistory o"),
    @NamedQuery(name = "OfflineHistory.findById", query = "SELECT o FROM OfflineHistory o WHERE o.id = :id"),
    @NamedQuery(name = "OfflineHistory.findByConnected", query = "SELECT o FROM OfflineHistory o WHERE o.connected = :connected"),
    @NamedQuery(name = "OfflineHistory.findByRestore", query = "SELECT o FROM OfflineHistory o WHERE o.restore = :restore"),
    @NamedQuery(name = "OfflineHistory.findByNotify", query = "SELECT o FROM OfflineHistory o WHERE o.notify = :notify"),
    @NamedQuery(name = "OfflineHistory.findByNotifyRestore", query = "SELECT o FROM OfflineHistory o WHERE o.notifyRestore = :notifyRestore")})
public class OfflineHistory implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;
    @Column(name = "connected")
    @Temporal(TemporalType.TIMESTAMP)
    private Date connected;
    @Column(name = "restore")
    @Temporal(TemporalType.TIMESTAMP)
    private Date restore;
    @Column(name = "notify")
    @Temporal(TemporalType.TIMESTAMP)
    private Date notify;
    @Column(name = "notify_restore")
    @Temporal(TemporalType.TIMESTAMP)
    private Date notifyRestore;
    @JoinColumn(name = "client_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Client clientId;

    public OfflineHistory() {
    }

    public OfflineHistory(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getConnected() {
        return connected;
    }

    public void setConnected(Date connected) {
        this.connected = connected;
    }

    public Date getRestore() {
        return restore;
    }

    public void setRestore(Date restore) {
        this.restore = restore;
    }

    public Date getNotify() {
        return notify;
    }

    public void setNotify(Date notify) {
        this.notify = notify;
    }

    public Date getNotifyRestore() {
        return notifyRestore;
    }

    public void setNotifyRestore(Date notifyRestore) {
        this.notifyRestore = notifyRestore;
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
        if (!(object instanceof OfflineHistory)) {
            return false;
        }
        OfflineHistory other = (OfflineHistory) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.itapteka.transport.dbcontroller.OfflineHistory[ id=" + id + " ]";
    }
    
}
