package com.project.atmiraFCT.model.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name="task")
public class Task {

    @Id
    @Column(name = "id_code")
    private String idCode;

    @Column(name = "title",length = 60)
    private String title;

    @Column(name="description", length = 256)
    private String description;

    @Column(name="objective", length = 256)
    private String objective;


    @Column(name="isClosed")
    private Boolean isClosed;


    @Column(name="tasks_count")
    private Integer tasks_count;

    // Relación OneToMany para las subtareas de una tarea
    @OneToMany(mappedBy = "task", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Task> subtareas;

    @ManyToOne
    @JoinColumn(name = "task")
   /* @JsonBackReference*/
    private Task task;


    @ManyToOne
    @JoinColumn(name="id_code_project")
    private Project project;


    @ManyToOne
    @JoinColumn(name="asigned")
    private Colaborator colaborator;

    public Task() {


    }
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIdCode() {
        return idCode;
    }

    public void setIdCode(String idCode) {
        this.idCode = idCode;
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

    public List<Task> getSubtareas() {
        return subtareas;
    }

    public void setSubtareas(List<Task> subtareas) {
        this.subtareas = subtareas;
    }

    public Task getTask() {
        return task;
    }

    public void setTask(Task tareaPrincipal) {
        this.task = tareaPrincipal;
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

    public Integer getTasks_count() {
        return tasks_count;
    }

    public void setTasks_count(Integer tasks_count) {
        this.tasks_count = tasks_count;
    }

    public void updateTasksCount() {
        this.tasks_count = (subtareas != null) ? subtareas.size() : 0;
    }

    @Override
    public String toString() {
        return "Task{" +
                "idCode='" + idCode + '\'' +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", objective='" + objective + '\'' +
                ", isClosed=" + isClosed +
                ", tasks_count=" + tasks_count +
                ", subtareas=" + subtareas +
                ", task=" + task +
                ", project=" + project +
                ", colaborator=" + colaborator +
                '}';
    }
}