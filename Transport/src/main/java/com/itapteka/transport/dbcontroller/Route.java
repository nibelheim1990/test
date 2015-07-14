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
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Ponomarev
 */
@Entity
@Table(name = "route")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Route.findAll", query = "SELECT r FROM Route r"),
    @NamedQuery(name = "Route.findById", query = "SELECT r FROM Route r WHERE r.id = :id"),
    @NamedQuery(name = "Route.findByObjectId", query = "SELECT r FROM Route r WHERE r.objectId = :objectId"),
    @NamedQuery(name = "Route.findByCode", query = "SELECT r FROM Route r WHERE r.code = :code"),
    @NamedQuery(name = "Route.findByComment", query = "SELECT r FROM Route r WHERE r.comment = :comment"),
    @NamedQuery(name = "Route.findByType", query = "SELECT r FROM Route r WHERE r.type = :type"),
    @NamedQuery(name = "Route.findByIsOffice", query = "SELECT r FROM Route r WHERE r.isOffice = :isOffice")})
public class Route implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;
    @Basic(optional = false)
    @Column(name = "object_id")
    private String objectId;
    @Basic(optional = false)
    @Column(name = "code")
    private String code;
    @Column(name = "comment")
    private String comment;
    @Basic(optional = false)
    @Column(name = "type")
    private String type;
    @Basic(optional = false)
    @Column(name = "is_office")
    private boolean isOffice;
    @JoinColumn(name = "client_dst_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Client clientDstId;
    @JoinColumn(name = "client_src_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Client clientSrcId;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "routeId")
    private Collection<RouteCopy> routeCopyCollection;

    public Route() {
    }

    public Route(Long id) {
        this.id = id;
    }

    public Route(Long id, String objectId, String code, String type, boolean isOffice) {
        this.id = id;
        this.objectId = objectId;
        this.code = code;
        this.type = type;
        this.isOffice = isOffice;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean getIsOffice() {
        return isOffice;
    }

    public void setIsOffice(boolean isOffice) {
        this.isOffice = isOffice;
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
    public Collection<RouteCopy> getRouteCopyCollection() {
        return routeCopyCollection;
    }

    public void setRouteCopyCollection(Collection<RouteCopy> routeCopyCollection) {
        this.routeCopyCollection = routeCopyCollection;
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
        if (!(object instanceof Route)) {
            return false;
        }
        Route other = (Route) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.itapteka.transport.dbcontroller.Route[ id=" + id + " ]";
    }
    
}
