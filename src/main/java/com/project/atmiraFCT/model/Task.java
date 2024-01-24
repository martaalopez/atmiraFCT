package com.project.atmiraFCT.model;


import jakarta.persistence.*;

import java.util.List;
import java.util.Objects;

@Entity
@Table(name="task")
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_code;

    @Column(name="description",length = 256,nullable = false)
    private String description;

    @Column(name="objective ",length = 256,nullable = false)
    private String objective;

    @Column(name="isClosed ",length = 256,nullable = false)
    private Boolean isClosed;

    @Column(name="task",nullable = true)
    private Long task;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="id_code_project")
    private Project project;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "task", cascade = CascadeType.ALL)
    List<Expense> expenses;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="id_colaborator")
    private Colaborator colaborator;


    public Task(Long id_code, String description, String objective, Boolean isClosed, Long task, Boolean active, Project project, List<Expense> expenses, Colaborator colaborator) {
        this.id_code = id_code;
        this.description = description;
        this.objective = objective;
        this.isClosed = isClosed;
        this.task = task;
        this.project = project;
        this.expenses = expenses;
        this.colaborator = colaborator;
    }

    public Task() {

    }

    public Long getId_code() {
        return id_code;
    }

    public void setId_code(Long id_code) {
        this.id_code = id_code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getObjective() {
        return objective;
    }

    public void setObjective(String objective) {
        this.objective = objective;
    }

    public Boolean getClosed() {
        return isClosed;
    }

    public void setClosed(Boolean closed) {
        isClosed = closed;
    }

    public Long getTask() {
        return task;
    }

    public void setTask(Long task) {
        this.task = task;
    }


    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public List<Expense> getExpenses() {
        return expenses;
    }

    public void setExpenses(List<Expense> expenses) {
        this.expenses = expenses;
    }

    public Colaborator getColaborator() {
        return colaborator;
    }

    public void setColaborator(Colaborator colaborator) {
        this.colaborator = colaborator;
    }
}
