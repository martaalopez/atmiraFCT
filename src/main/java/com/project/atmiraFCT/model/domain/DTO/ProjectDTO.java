package com.project.atmiraFCT.model.domain.DTO;

import com.project.atmiraFCT.model.Enum.TypeOfService;
import com.project.atmiraFCT.model.domain.ColaboratorProject;
import com.project.atmiraFCT.model.domain.Expense;
import com.project.atmiraFCT.model.domain.Project;
import com.project.atmiraFCT.model.domain.Task;

import java.util.Date;
import java.util.List;

public class ProjectDTO {
    private String id_code;
    private TypeOfService typeOfService;
    private String name;
    private Date initialDate;
    private Date endDate;
    private Boolean active;
    private Integer tasks_count;
    private List<Expense> expenses;
    private List<ColaboratorProject> colaboratorProjects;

    public ProjectDTO convertToDto(Project project) {
        ProjectDTO projectDTO = new ProjectDTO();
        projectDTO.setId_code(project.getId_code());
        projectDTO.setTypeOfService(project.getTypeOfService());
        projectDTO.setName(project.getName());
        projectDTO.setInitialDate(project.getInitialDate());
        projectDTO.setEndDate(project.getEndDate());
        projectDTO.setActive(project.getActive());
        projectDTO.setTasks_count(project.getTasks_count());
        projectDTO.setExpenses(project.getExpenses());
        projectDTO.setColaboratorProjects(project.getColaboratorProjects());
        return projectDTO;
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

    public Integer getTasks_count() {
        return tasks_count;
    }

    public void setTasks_count(Integer tasks_count) {
        this.tasks_count = tasks_count;
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

    // getters y setters
}

