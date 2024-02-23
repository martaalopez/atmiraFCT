package com.project.atmiraFCT.repository;

import com.project.atmiraFCT.model.domain.Colaborator;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ColaboratorRepository extends JpaRepository<Colaborator, String> {

    /**
     * Encuentra un colaborador por su dirección de correo electrónico.
     *
     * @param email La dirección de correo electrónico del colaborador a buscar.
     * @return Un Optional que contiene el colaborador si se encuentra, de lo contrario, un Optional vacío.
     */
    Optional<Colaborator> findByEmail(String email);

    /**
     * Encuentra un colaborador por su identificador.
     *
     * @param idAlias El identificador del colaborador a buscar.
     * @return Un Optional que contiene el colaborador si se encuentra, de lo contrario, un Optional vacío.
     */
    Optional<Colaborator> findById(String idAlias);
}
