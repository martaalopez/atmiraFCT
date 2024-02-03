package com.project.atmiraFCT.model.domain;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name="workplace")
public class WorkPlace {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="code",length = 50)
    private String code;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "workPlace", cascade = CascadeType.ALL)
    List<Colaborator> colaborators;

    public WorkPlace(Long id, String code, List<Colaborator> colaborators) {
        this.id = id;
        this.code = code;
        this.colaborators = colaborators;

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

}
