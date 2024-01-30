package com.project.atmiraFCT.repository;

import com.project.atmiraFCT.model.domain.Colaborator;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ColaboratorRepository extends JpaRepository<Colaborator, String> {
  /*  Optional<Colaborator> findByGmail(String gmail);

    Optional<Colaborator> findByEmailAndPassword(String email, String password);*/
}
