package com.project.atmiraFCT.model.domain;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.util.ArrayList;
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
    @JsonBackReference
    List<Colaborator> colaborators=new ArrayList<>();

    public WorkPlace(Long id, String code) {
        this.id = id;
        this.code = code;
        this.colaborators = new ArrayList<>();

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
