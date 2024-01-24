package com.project.atmiraFCT.model;


import jakarta.persistence.*;

@Entity
@Table(name="expense")
public class Expense {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer  id;

    @Column(name="day",nullable = false)
    private Integer day;

    @Column(name="month",nullable = false)
    private Integer month;

    @Column(name="year",nullable = false)
    private Integer year;

    @Column(name="hours",nullable = false)
    private Integer hours;

    @Column(name="cost",nullable = false)
    private Integer cost;

    @Column(name="description",length = 256,nullable = false)
    private String description;

    @Column(name="state",nullable = false)
    private Boolean state;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="id_code_project")
    private Project project;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="id_code_task")
    private Task task;

    @ManyToOne(fetch = FetchType.LAZY)
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
        this.task = task;
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

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }

    public Colaborator getColaborator() {
        return colaborator;
    }

    public void setColaborator(Colaborator colaborator) {
        this.colaborator = colaborator;
    }
}
