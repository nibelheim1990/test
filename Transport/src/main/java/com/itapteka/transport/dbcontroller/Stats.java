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
@Table(name = "stats")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Stats.findAll", query = "SELECT s FROM Stats s"),
    @NamedQuery(name = "Stats.findById", query = "SELECT s FROM Stats s WHERE s.id = :id"),
    @NamedQuery(name = "Stats.findByDate", query = "SELECT s FROM Stats s WHERE s.date = :date"),
    @NamedQuery(name = "Stats.findByWorker", query = "SELECT s FROM Stats s WHERE s.worker = :worker"),
    @NamedQuery(name = "Stats.findBySize", query = "SELECT s FROM Stats s WHERE s.size = :size"),
    @NamedQuery(name = "Stats.findByCount", query = "SELECT s FROM Stats s WHERE s.count = :count"),
    @NamedQuery(name = "Stats.findByErrors", query = "SELECT s FROM Stats s WHERE s.errors = :errors"),
    @NamedQuery(name = "Stats.findByElapsed", query = "SELECT s FROM Stats s WHERE s.elapsed = :elapsed")})
public class Stats implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;
    @Basic(optional = false)
    @Column(name = "date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date date;
    @Basic(optional = false)
    @Column(name = "worker")
    private String worker;
    @Basic(optional = false)
    @Column(name = "size")
    private long size;
    @Basic(optional = false)
    @Column(name = "count")
    private int count;
    @Basic(optional = false)
    @Column(name = "errors")
    private int errors;
    @Basic(optional = false)
    @Column(name = "elapsed")
    private long elapsed;

    public Stats() {
    }

    public Stats(Long id) {
        this.id = id;
    }

    public Stats(Long id, Date date, String worker, long size, int count, int errors, long elapsed) {
        this.id = id;
        this.date = date;
        this.worker = worker;
        this.size = size;
        this.count = count;
        this.errors = errors;
        this.elapsed = elapsed;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getWorker() {
        return worker;
    }

    public void setWorker(String worker) {
        this.worker = worker;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getErrors() {
        return errors;
    }

    public void setErrors(int errors) {
        this.errors = errors;
    }

    public long getElapsed() {
        return elapsed;
    }

    public void setElapsed(long elapsed) {
        this.elapsed = elapsed;
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
        if (!(object instanceof Stats)) {
            return false;
        }
        Stats other = (Stats) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.itapteka.transport.dbcontroller.Stats[ id=" + id + " ]";
    }
    
}
