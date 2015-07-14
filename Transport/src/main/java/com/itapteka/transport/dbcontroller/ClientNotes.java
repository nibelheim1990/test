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
@Table(name = "client_notes")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ClientNotes.findAll", query = "SELECT c FROM ClientNotes c"),
    @NamedQuery(name = "ClientNotes.findById", query = "SELECT c FROM ClientNotes c WHERE c.id = :id"),
    @NamedQuery(name = "ClientNotes.findByNoteDate", query = "SELECT c FROM ClientNotes c WHERE c.noteDate = :noteDate"),
    @NamedQuery(name = "ClientNotes.findByNoteText", query = "SELECT c FROM ClientNotes c WHERE c.noteText = :noteText")})
public class ClientNotes implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;
    @Basic(optional = false)
    @Column(name = "note_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date noteDate;
    @Basic(optional = false)
    @Column(name = "note_text")
    private String noteText;
    @JoinColumn(name = "client_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Client clientId;
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private UserAccount userId;

    public ClientNotes() {
    }

    public ClientNotes(Long id) {
        this.id = id;
    }

    public ClientNotes(Long id, Date noteDate, String noteText) {
        this.id = id;
        this.noteDate = noteDate;
        this.noteText = noteText;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getNoteDate() {
        return noteDate;
    }

    public void setNoteDate(Date noteDate) {
        this.noteDate = noteDate;
    }

    public String getNoteText() {
        return noteText;
    }

    public void setNoteText(String noteText) {
        this.noteText = noteText;
    }

    public Client getClientId() {
        return clientId;
    }

    public void setClientId(Client clientId) {
        this.clientId = clientId;
    }

    public UserAccount getUserId() {
        return userId;
    }

    public void setUserId(UserAccount userId) {
        this.userId = userId;
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
        if (!(object instanceof ClientNotes)) {
            return false;
        }
        ClientNotes other = (ClientNotes) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.itapteka.transport.dbcontroller.ClientNotes[ id=" + id + " ]";
    }
    
}
