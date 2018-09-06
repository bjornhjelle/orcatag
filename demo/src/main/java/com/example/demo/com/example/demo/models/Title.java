package com.example.demo.com.example.demo.models;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;


import org.apache.commons.lang3.builder.HashCodeBuilder;

@Entity(name="titles")
public class Title implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = -6244799262456533278L;

    @Id
    @ManyToOne
    @JoinColumn(name="emp_no")
    private Employee employee;

    @Id
    @Column(name="title")
    private String title;

    @Id
    @Column(name="from_date")
    private Date fromDate;

    @Column(name="to_date")
    private Date to_date;

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getFromDate() {
        return fromDate;
    }

    public void setFromDate(Date fromDate) {
        this.fromDate = fromDate;
    }

    public Date getTo_date() {
        return to_date;
    }

    public void setTo_date(Date to_date) {
        this.to_date = to_date;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null)
            return false;

        if (!(o instanceof Title))
            return false;

        Title other = (Title) o;
        if (!(this.equals(other)))
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(this.getEmployee().getEmployee_id()).append(this.getTitle()).toHashCode();
    }


}
