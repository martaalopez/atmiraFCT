package com.project.atmiraFCT.model.domain;

import jakarta.persistence.*;

@Entity
@Table(name="department")
public class Department {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="code",length = 50,nullable = false)
    private String code;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="id_workplace")
    private WorkPlace workPlace;

    public Department(Long id, String code, WorkPlace workPlace) {
        this.id = id;
        this.code = code;
        this.workPlace = workPlace;
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

    public WorkPlace getWorkPlace() {
        return workPlace;
    }

    public void setWorkPlace(WorkPlace workPlace) {
        this.workPlace = workPlace;
    }
}
