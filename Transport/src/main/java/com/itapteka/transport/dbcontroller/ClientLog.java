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
@Table(name = "client_log")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ClientLog.findAll", query = "SELECT c FROM ClientLog c"),
    @NamedQuery(name = "ClientLog.findById", query = "SELECT c FROM ClientLog c WHERE c.id = :id"),
    @NamedQuery(name = "ClientLog.findByLogLevel", query = "SELECT c FROM ClientLog c WHERE c.logLevel = :logLevel"),
    @NamedQuery(name = "ClientLog.findBySender", query = "SELECT c FROM ClientLog c WHERE c.sender = :sender"),
    @NamedQuery(name = "ClientLog.findByMessage", query = "SELECT c FROM ClientLog c WHERE c.message = :message"),
    @NamedQuery(name = "ClientLog.findByLogDate", query = "SELECT c FROM ClientLog c WHERE c.logDate = :logDate")})
public class ClientLog implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;
    @Basic(optional = false)
    @Column(name = "log_level")
    private int logLevel;
    @Basic(optional = false)
    @Column(name = "sender")
    private String sender;
    @Basic(optional = false)
    @Column(name = "message")
    private String message;
    @Basic(optional = false)
    @Column(name = "log_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date logDate;
    @JoinColumn(name = "http_info_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private HttpInfo httpInfoId;

    public ClientLog() {
    }

    public ClientLog(Long id) {
        this.id = id;
    }

    public ClientLog(Long id, int logLevel, String sender, String message, Date logDate) {
        this.id = id;
        this.logLevel = logLevel;
        this.sender = sender;
        this.message = message;
        this.logDate = logDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getLogLevel() {
        return logLevel;
    }

    public void setLogLevel(int logLevel) {
        this.logLevel = logLevel;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getLogDate() {
        return logDate;
    }

    public void setLogDate(Date logDate) {
        this.logDate = logDate;
    }

    public HttpInfo getHttpInfoId() {
        return httpInfoId;
    }

    public void setHttpInfoId(HttpInfo httpInfoId) {
        this.httpInfoId = httpInfoId;
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
        if (!(object instanceof ClientLog)) {
            return false;
        }
        ClientLog other = (ClientLog) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.itapteka.transport.dbcontroller.ClientLog[ id=" + id + " ]";
    }
    
}
