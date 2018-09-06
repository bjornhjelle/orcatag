package com.example.demo.com.example.demo.models;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity(name="employees")
public class Employee {
    @Id
    @Column(name="emp_no")
    private int employee_id;

    @Column(name="birth_date")
    private Date birthdate;

    @Column(name="first_name")
    private String firstname;

    @Column(name="last_name")
    private String lastname;

    @Column(name="gender",columnDefinition = "ENUM('M', 'F', 'UNKNOWN') DEFAULT 'UNKNOWN'")
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Column(name="hire_date")
    private Date hireDate;

    @OneToMany(mappedBy="employee")
    List<Title> titles;

    @OneToMany(mappedBy="employee")
    List<Department_Employee> employee_department;

    // Setters and getters


}