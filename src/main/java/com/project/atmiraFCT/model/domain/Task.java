package com.project.atmiraFCT.model.domain;
import jakarta.persistence.*;

@Entity
@Table(name="task")
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id_code;

    @Column(name="description",length = 256)
    private String description;

    @Column(name="objective ",length = 256)
    private String objective;

    @Column(name="isClosed")
    private Boolean isClosed;

    @Column(name="task")
    private Long task;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name="id_code_project")
    private Project project;


    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name="id_colaborator")
    private Colaborator colaborator;

    public Task(Long id_code, String description, String objective, Boolean isClosed, Long task, Boolean active) {
        this.id_code = id_code;
        this.description = description;
        this.objective = objective;
        this.isClosed = isClosed;
        this.task = task;

    }

    public Task(Long id_code, String description, String objective, Boolean isClosed, Long task, Boolean active, Project project, Colaborator colaborator) {
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

    public Colaborator getColaborator() {
        return colaborator;
    }

    public void setColaborator(Colaborator colaborator) {
        this.colaborator = colaborator;
    }


}
