package com.project.atmiraFCT.model.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.lang.reflect.Field;

@Entity
@JsonIgnoreProperties({"colaborator", "project"})
@Table(name="task")
public class Task {
    @Id
    private String id_code;

    @Column(name="description",length = 256)
    private String description;

    @Column(name="objective",length = 256)
    private String objective;

    @Column(name="isClosed")
    private Boolean isClosed;

    @Column(name="task")
    private String task;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name="id_code_project")
    @JsonManagedReference
    private Project project;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name="id_colaborator")
    @JsonManagedReference
    private Colaborator colaborator;

    public Task(String id_code, String description, String objective, Boolean isClosed, String task, Boolean active) {
        this.id_code = id_code;
        this.description = description;
        this.objective = objective;
        this.isClosed = isClosed;
        this.task = task;

    }

    public Task(String id_code, String description, String objective, Boolean isClosed, String task, Boolean active, Project project, Colaborator colaborator) {
        this.id_code = id_code;
        this.description = description;
        this.objective = objective;
        this.isClosed = isClosed;
        this.task = task;
        this.project = project;
        this.colaborator = colaborator;
    }

    public Task() {

    }

    public String getId_code() {
        return id_code;
    }

    public void setId_code(String id_code) {
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

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
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

    @PrePersist
    public void generarId() {
        if (this.id_code == null && this.project != null) {
            this.id_code = this.project.getId_code() + "_" + this.project.getContadorTareas();
            this.project.incrementarContadorTareas();
        }
    }




}