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
@Table(name = "schedule_day")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ScheduleDay.findAll", query = "SELECT s FROM ScheduleDay s"),
    @NamedQuery(name = "ScheduleDay.findById", query = "SELECT s FROM ScheduleDay s WHERE s.id = :id"),
    @NamedQuery(name = "ScheduleDay.findByDayOfWeek", query = "SELECT s FROM ScheduleDay s WHERE s.dayOfWeek = :dayOfWeek"),
    @NamedQuery(name = "ScheduleDay.findByOpenAt", query = "SELECT s FROM ScheduleDay s WHERE s.openAt = :openAt"),
    @NamedQuery(name = "ScheduleDay.findByOperationTime", query = "SELECT s FROM ScheduleDay s WHERE s.operationTime = :operationTime")})
public class ScheduleDay implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;
    @Basic(optional = false)
    @Column(name = "day_of_week")
    private int dayOfWeek;
    @Basic(optional = false)
    @Column(name = "open_at")
    private int openAt;
    @Basic(optional = false)
    @Column(name = "operation_time")
    private int operationTime;
    @JoinColumn(name = "schedule_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Schedule scheduleId;

    public ScheduleDay() {
    }

    public ScheduleDay(Long id) {
        this.id = id;
    }

    public ScheduleDay(Long id, int dayOfWeek, int openAt, int operationTime) {
        this.id = id;
        this.dayOfWeek = dayOfWeek;
        this.openAt = openAt;
        this.operationTime = operationTime;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(int dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public int getOpenAt() {
        return openAt;
    }

    public void setOpenAt(int openAt) {
        this.openAt = openAt;
    }

    public int getOperationTime() {
        return operationTime;
    }

    public void setOperationTime(int operationTime) {
        this.operationTime = operationTime;
    }

    public Schedule getScheduleId() {
        return scheduleId;
    }

    public void setScheduleId(Schedule scheduleId) {
        this.scheduleId = scheduleId;
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
        if (!(object instanceof ScheduleDay)) {
            return false;
        }
        ScheduleDay other = (ScheduleDay) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.itapteka.transport.dbcontroller.ScheduleDay[ id=" + id + " ]";
    }
    
}
