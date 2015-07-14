/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.itapteka.transport.dbcontroller;

import java.io.Serializable;
import java.math.BigInteger;
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
@Table(name = "client_traffic")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ClientTraffic.findAll", query = "SELECT c FROM ClientTraffic c"),
    @NamedQuery(name = "ClientTraffic.findById", query = "SELECT c FROM ClientTraffic c WHERE c.id = :id"),
    @NamedQuery(name = "ClientTraffic.findByPathTo", query = "SELECT c FROM ClientTraffic c WHERE c.pathTo = :pathTo"),
    @NamedQuery(name = "ClientTraffic.findByPathFrom", query = "SELECT c FROM ClientTraffic c WHERE c.pathFrom = :pathFrom"),
    @NamedQuery(name = "ClientTraffic.findByMaxSize", query = "SELECT c FROM ClientTraffic c WHERE c.maxSize = :maxSize"),
    @NamedQuery(name = "ClientTraffic.findByName", query = "SELECT c FROM ClientTraffic c WHERE c.name = :name"),
    @NamedQuery(name = "ClientTraffic.findByPack", query = "SELECT c FROM ClientTraffic c WHERE c.pack = :pack")})
public class ClientTraffic implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;
    @Column(name = "path_to")
    private String pathTo;
    @Column(name = "path_from")
    private String pathFrom;
    @Column(name = "max_size")
    private BigInteger maxSize;
    @Basic(optional = false)
    @Column(name = "name")
    private String name;
    @Basic(optional = false)
    @Column(name = "pack")
    private boolean pack;
    @JoinColumn(name = "http_info_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private HttpInfo httpInfoId;

    public ClientTraffic() {
    }

    public ClientTraffic(Long id) {
        this.id = id;
    }

    public ClientTraffic(Long id, String name, boolean pack) {
        this.id = id;
        this.name = name;
        this.pack = pack;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPathTo() {
        return pathTo;
    }

    public void setPathTo(String pathTo) {
        this.pathTo = pathTo;
    }

    public String getPathFrom() {
        return pathFrom;
    }

    public void setPathFrom(String pathFrom) {
        this.pathFrom = pathFrom;
    }

    public BigInteger getMaxSize() {
        return maxSize;
    }

    public void setMaxSize(BigInteger maxSize) {
        this.maxSize = maxSize;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean getPack() {
        return pack;
    }

    public void setPack(boolean pack) {
        this.pack = pack;
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
        if (!(object instanceof ClientTraffic)) {
            return false;
        }
        ClientTraffic other = (ClientTraffic) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.itapteka.transport.dbcontroller.ClientTraffic[ id=" + id + " ]";
    }
    
}
