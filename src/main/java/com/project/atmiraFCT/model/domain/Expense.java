package com.project.atmiraFCT.model.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

@Entity
@JsonIgnoreProperties({"project", "colaborator"})
@Table(name="expense")
public class Expense {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer  id;

    @Column(name="day")
    private Integer day;

    @Column(name="month")
    private Integer month;

    @Column(name="year")
    private Integer year;

    @Column(name="hours")
    private Integer hours;

    @Column(name="cost",nullable = false)
    private Integer cost;

    @Column(name="description",length = 256)
    private String description;

    @Column(name="state")
    private Boolean state;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonManagedReference
    @JoinColumn(name="id_code_project")
    private Project project;


    @ManyToOne(fetch = FetchType.LAZY)
    @JsonManagedReference
    @JoinColumn(name="id_colaborator")
    private Colaborator colaborator;

    public Expense(Integer id, Integer day, Integer month, Integer year, Integer hours, Integer cost, String description, Boolean state, Project project, Task task, Colaborator colaborator) {
        this.id = id;
        this.day = day;
        this.month = month;
        this.year = year;
        this.hours = hours;
        this.cost = cost;
        this.description = description;
        this.state = state;
        this.project = project;
        this.colaborator = colaborator;
    }

    public Expense() {

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getDay() {
        return day;
    }

    public void setDay(Integer day) {
        this.day = day;
    }

    public Integer getMonth() {
        return month;
    }

    public void setMonth(Integer month) {
        this.month = month;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Integer getHours() {
        return hours;
    }

    public void setHours(Integer hours) {
        this.hours = hours;
    }

    public Integer getCost() {
        return cost;
    }

    public void setCost(Integer cost) {
        this.cost = cost;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getState() {
        return state;
    }

    public void setState(Boolean state) {
        this.state = state;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }


    public Colaborator getColaborator() {
        return colaborator;
    }

    public void setColaborator(Colaborator colaborator) {
        this.colaborator = colaborator;
    }
}
