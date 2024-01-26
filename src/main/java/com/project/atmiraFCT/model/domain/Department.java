package com.project.atmiraFCT.model.domain;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name="department")
public class Department {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="code",length = 50,nullable = false)
    private String code;

    @ManyToMany(mappedBy = "departments")
    private List<Colaborator> colaborators;


    public Department(Long id, String code) {
        this.id = id;
        this.code = code;

    }

    public Department() {

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

}
