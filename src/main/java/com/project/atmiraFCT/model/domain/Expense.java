package com.project.atmiraFCT.model.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.project.atmiraFCT.model.Enum.TypeExpensive;
import jakarta.persistence.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;

@Entity
@JsonIgnoreProperties({"project"})
@Table(name="expense")
public class Expense {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column()
    private Integer ticketId;

    @Column(name="ticketDate",nullable = false)
    private LocalDate ticketDate;
    @Column(name="createdDate",nullable = false)
    private Date createdDate;
    @Column(name="cost",nullable = false)
    private Double cost;

    @Column(name="description",length = 256)
    private String description;

    @Column(name="state")
    private Boolean state;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonManagedReference
    @JoinColumn(name="id_code_project")
    private Project project;


    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="id_colaborator")
    private Colaborator colaborator;
    @Column
    private TypeExpensive typeExpensive;

    public Expense(Integer ticketId, LocalDate ticketDate, Double cost, String description, Boolean state,TypeExpensive typeExpensive, Project project, Colaborator colaborator) {
        this.ticketId = ticketId;
        this.ticketDate = ticketDate;
        try {
            String date =new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new Date());
            this.createdDate = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").parse(date);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        this.cost = cost;
        this.description = description;
        this.state = state;
        this.project = project;
        this.typeExpensive = typeExpensive;
        this.colaborator = colaborator;
    }

    public Expense() {

    }

    public Integer getTicketId() {
        return ticketId;
    }

    public void setTicketId(Integer ticketId) {
        this.ticketId = ticketId;
    }

    public LocalDate getTicketDate() {
        return ticketDate;
    }

    public void setTicketDate(LocalDate ticketDate) {
        this.ticketDate = ticketDate;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Double getCost() {
        return cost;
    }

    public void setCost(Double cost) {
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

    public TypeExpensive getTypeExpensive() {
        return typeExpensive;
    }

    public void setTypeExpensive(TypeExpensive typeExpensive) {
        this.typeExpensive = typeExpensive;
    }

    @Override
    public String toString() {
        return "Expense{" +
                "ticketId=" + ticketId +
                ", ticketDate=" + ticketDate +
                ", createdDate=" + createdDate +
                ", cost=" + cost +
                ", description='" + description + '\'' +
                ", state=" + state +
                ", project=" + project.getId_code() +
                ", colaboratorID=" + colaborator.getId_alias() +
                ", typeExpensive=" + typeExpensive +
                '}';
    }
}
