package com.project.atmiraFCT.model.domain;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name="workplace")
public class WorkPlace {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="code",length = 50,nullable = false)
    private String code;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "workPlace", cascade = CascadeType.ALL)
    List<Colaborator> colaborators;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "workPlace", cascade = CascadeType.ALL)
    List<Department> departments;

    public WorkPlace(Long id, String code, List<Colaborator> colaborators, List<Department> departments) {
        this.id = id;
        this.code = code;
        this.colaborators = colaborators;
        this.departments = departments;
    }

    public WorkPlace() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public List<Colaborator> getColaborators() {
        return colaborators;
    }

    public void setColaborators(List<Colaborator> colaborators) {
        this.colaborators = colaborators;
    }

    public List<Department> getDepartments() {
        return departments;
    }

    public void setDepartments(List<Department> departments) {
        this.departments = departments;
    }
}
