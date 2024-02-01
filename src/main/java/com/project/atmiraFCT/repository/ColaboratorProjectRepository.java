package com.project.atmiraFCT.repository;


import com.project.atmiraFCT.model.domain.Colaborator;
import com.project.atmiraFCT.model.domain.ColaboratorProject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ColaboratorProjectRepository extends JpaRepository<ColaboratorProject, String> {
}