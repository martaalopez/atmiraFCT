package com.project.atmiraFCT.model.domain;

import jakarta.persistence.*;

@Entity
@Table(name = "colaborator_department")
public class ColaboratorDepartment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "colaborator_id_alias")
    private Colaborator colaborator;

    @ManyToOne
    @JoinColumn(name = "department_id")
    private Department department;

    public ColaboratorDepartment(Department savedDepartment, Colaborator colaborator) {
    }

    public ColaboratorDepartment() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Colaborator getColaborator() {
        return colaborator;
    }

    public void setColaborator(Colaborator colaborator) {
        this.colaborator = colaborator;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }
}
