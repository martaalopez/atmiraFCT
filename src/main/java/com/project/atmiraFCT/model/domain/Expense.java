package com.project.atmiraFCT.model.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.project.atmiraFCT.model.Enum.TypeExpensive;
import jakarta.persistence.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Entity
@JsonIgnoreProperties({"project", "colaborator"})
@Table(name="expense")
public class Expense {

    @Id
    @Column
    private String ticketId;

    @Column(name="ticketDate",nullable = false)
    private Date ticketDate;
    @Column(name="createdDate",nullable = false)
    private Date createdDate;
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
    @Column
    private TypeExpensive typeExpensive;

    public Expense(String ticketId, Date ticketDate, Integer cost, String description, Boolean state,TypeExpensive typeExpensive, Project project, Colaborator colaborator) {
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

    public String getTicketId() {
        return ticketId;
    }

    public void setTicketId(String ticketId) {
        this.ticketId = ticketId;
    }

    public Date getTicketDate() {
        return ticketDate;
    }

    public void setTicketDate(Date ticketDate) {
        this.ticketDate = ticketDate;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
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

    public TypeExpensive getTypeExpensive() {
        return typeExpensive;
    }

    public void setTypeExpensive(TypeExpensive typeExpensive) {
        this.typeExpensive = typeExpensive;
    }
}
