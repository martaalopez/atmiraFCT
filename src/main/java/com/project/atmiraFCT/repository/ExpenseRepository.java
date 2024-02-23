package com.project.atmiraFCT.repository;

import com.project.atmiraFCT.model.domain.Colaborator;
import com.project.atmiraFCT.model.domain.Expense;
import com.project.atmiraFCT.model.domain.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense, Integer> {

    /**
     * Busca todos los gastos asociados a un colaborador.
     *
     * @param colaborator El colaborador para el que se buscan los gastos.
     * @return Una lista de gastos asociados al colaborador.
     */
    List<Expense> findByColaborator(Colaborator colaborator);

    /**
     * Busca todos los gastos asociados a un proyecto.
     *
     * @param project El proyecto para el que se buscan los gastos.
     * @return Una lista de gastos asociados al proyecto.
     */
    List<Expense> findByProject(Project project);

    /**
     * Busca todos los gastos asociados a un colaborador y un proyecto específicos.
     *
     * @param colaborator El colaborador para el que se buscan los gastos.
     * @param project     El proyecto para el que se buscan los gastos.
     * @return Una lista de gastos asociados al colaborador y proyecto especificados.
     */
    List<Expense> findByColaboratorAndProject(Colaborator colaborator, Project project);
}
