package com.project.atmiraFCT.repository;

import com.project.atmiraFCT.model.domain.Colaborator;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ColaboratorRepository extends JpaRepository<Colaborator, String> {
}
