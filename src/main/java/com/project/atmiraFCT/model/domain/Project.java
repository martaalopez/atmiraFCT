package com.project.atmiraFCT.model.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.project.atmiraFCT.model.Enum.TypeOfService;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name="project")
@JsonIgnoreProperties({"colaboratorProjects","tasks","expenses"})
public class Project {

    @Id
    @Column(name = "id_code",length = 256)
    private String id_code;

    @Enumerated(EnumType.STRING)
    @Column(name="typeOfService", length = 20)
    private TypeOfService typeOfService;

    @Column(name="name",length = 20)
    private String name;

    @Column(name="initialDate")
    private Date initialDate;

    @Column(name="endDate")
    private Date endDate;

    @Column(name="active")
    private Boolean active;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "project",cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Task> tasks;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "project")
    @JsonManagedReference
    private List<Expense> expenses;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "project")
    @JsonManagedReference
    private List<ColaboratorProject> colaboratorProjects;

    public Project(String id_code, TypeOfService typeOfService, String name, Date initialDate, Date endDate, Boolean active, List<Task> tasks, List<Expense> expenses, List<ColaboratorProject> colaboratorProjects) {
        this.id_code =  id_code;
        this.typeOfService = typeOfService;
        this.name = name;
        this.initialDate = initialDate;
        this.endDate = endDate;
        this.active = active;
        this.tasks = tasks; // No inicializar aquí
        this.expenses = expenses;
        this.colaboratorProjects = colaboratorProjects;
    }



    public Project() {
        this.colaboratorProjects = new ArrayList<>();
  // Llama al método getTasks() en lugar de acceder directamente al atributo tasks

    }


    public String getId_code() {
        return id_code;
    }

    public void setId_code(String id_code) {
        this.id_code = id_code;
    }

    public TypeOfService getTypeOfService() {
        return typeOfService;
    }

    public void setTypeOfService(TypeOfService typeOfService) {
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

    public List<Task> getTasks() {
        if(tasks == null) {
            tasks = new ArrayList<>();
        }
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }

    public List<Expense> getExpenses() {
        return expenses;
    }

    public void setExpenses(List<Expense> expenses) {
        this.expenses = expenses;
    }

    public List<ColaboratorProject> getColaboratorProjects() {
        return colaboratorProjects;
    }

    public void setColaboratorProjects(List<ColaboratorProject> colaboratorProjects) {
        this.colaboratorProjects = colaboratorProjects;
    }





}
