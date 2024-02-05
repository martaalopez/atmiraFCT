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
}
