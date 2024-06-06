package com.project.atmiraFCT.model.domain.DTO;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.project.atmiraFCT.model.domain.ColaboratorProject;
import com.project.atmiraFCT.model.domain.Expense;
import com.project.atmiraFCT.model.domain.Task;
import com.project.atmiraFCT.model.domain.WorkPlace;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ColaboratorProjectDTO {
    public String id_alias;
    public String email;
    public Boolean isActive;
    public Date relaseDate;
    public Integer hours;
    public Boolean guards;
    public Boolean expense;
    public String name;
    public String surname;
    public String responsible ;
    public List<Expense> expenses;
    public WorkPlace workPlace;

    public ColaboratorProjectDTO() {
    }

    public ColaboratorProjectDTO convertToDto(ColaboratorProject colaboratorProject) {

        this.id_alias = colaboratorProject.getColaborator().getId_alias();
        this.email = colaboratorProject.getColaborator().getEmail();
        this.isActive = colaboratorProject.getColaborator().getActive();
        this.relaseDate = colaboratorProject.getColaborator().getRelaseDate();
        this.hours = colaboratorProject.getColaborator().getHours();
        this.guards = colaboratorProject.getColaborator().getGuards();
        this.expense = colaboratorProject.getColaborator().getExpense();
        this.name = colaboratorProject.getColaborator().getName();
        this.surname = colaboratorProject.getColaborator().getSurname();
        this.responsible = colaboratorProject.getColaborator().getResponsible();
        this.expenses = colaboratorProject.getColaborator().getExpenses();
        this.workPlace = colaboratorProject.getColaborator().getWorkPlace();

        return this;
    }

    public List<ColaboratorProjectDTO> convertToDtoList(List<ColaboratorProject> colaboratorProjectList) {

        List<ColaboratorProjectDTO> aux = new ArrayList<>();

        for (ColaboratorProject colaboratorProject : colaboratorProjectList) {

            ColaboratorProjectDTO colaboratorProjectDTO = new ColaboratorProjectDTO();
            colaboratorProjectDTO.convertToDto(colaboratorProject);
            aux.add(colaboratorProjectDTO);
        }
        return aux;
    }

    public String getId_alias() {
        return id_alias;
    }

    public void setId_alias(String id_alias) {
        this.id_alias = id_alias;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    public Date getRelaseDate() {
        return relaseDate;
    }

    public void setRelaseDate(Date relaseDate) {
        this.relaseDate = relaseDate;
    }

    public Integer getHours() {
        return hours;
    }

    public void setHours(Integer hours) {
        this.hours = hours;
    }

    public Boolean getGuards() {
        return guards;
    }

    public void setGuards(Boolean guards) {
        this.guards = guards;
    }

    public Boolean getExpense() {
        return expense;
    }

    public void setExpense(Boolean expense) {
        this.expense = expense;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getResponsible() {
        return responsible;
    }

    public void setResponsible(String responsible) {
        this.responsible = responsible;
    }

    public List<Expense> getExpenses() {
        return expenses;
    }

    public void setExpenses(List<Expense> expenses) {
        this.expenses = expenses;
    }

    public WorkPlace getWorkPlace() {
        return workPlace;
    }

    public void setWorkPlace(WorkPlace workPlace) {
        this.workPlace = workPlace;
    }

    @Override
    public String toString() {
        return "ColaboratorProjectDTO{" +
                "id_alias='" + id_alias + '\'' +
                '}';
    }
}
