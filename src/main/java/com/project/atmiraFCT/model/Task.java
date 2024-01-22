package com.project.atmiraFCT.model;


import jakarta.persistence.*;

import java.util.Objects;

@Entity
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_code;

    private String description;

    private String objective;

    private Boolean isClosed;

    private Long task;

    private Boolean active;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="id_code_project")
    private Project project;

    public Task(Long id_code, String description, String objective, Boolean isClosed, Long task, Boolean active, Project project) {
        this.id_code = id_code;
        this.description = description;
        this.objective = objective;
        this.isClosed = isClosed;
        this.task = task;
        this.active = active;
        this.project = project;
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

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task1 = (Task) o;
        return Objects.equals(id_code, task1.id_code) && Objects.equals(description, task1.description) && Objects.equals(objective, task1.objective) && Objects.equals(isClosed, task1.isClosed) && Objects.equals(task, task1.task) && Objects.equals(active, task1.active) && Objects.equals(project, task1.project);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id_code, description, objective, isClosed, task, active, project);
    }
}
