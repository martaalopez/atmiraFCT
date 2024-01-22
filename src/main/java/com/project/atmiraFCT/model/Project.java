package com.project.atmiraFCT.model;

import jakarta.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_code;

    private String typeOfService;

    private String name;

    private Date initialDate;

    private Date endDate;

    private Boolean active;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "project", cascade = CascadeType.ALL)
    List<Task> task;


    public Project(Long id_code, String typeOfService, String name, Date initialDate, Date endDate, Boolean active, List<Task> task) {
        this.id_code = id_code;
        this.typeOfService = typeOfService;
        this.name = name;
        this.initialDate = initialDate;
        this.endDate = endDate;
        this.active = active;
        this.task = task;
    }

    public Project() {

    }

    public Long getId_code() {
        return id_code;
    }

    public void setId_code(Long id_code) {
        this.id_code = id_code;
    }

    public String getTypeOfService() {
        return typeOfService;
    }

    public void setTypeOfService(String typeOfService) {
        this.typeOfService = typeOfService;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getInitialDate() {
        return initialDate;
    }

    public void setInitialDate(Date initialDate) {
        this.initialDate = initialDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public List<Task> getTask() {
        return task;
    }

    public void setTask(List<Task> task) {
        this.task = task;
    }
}
