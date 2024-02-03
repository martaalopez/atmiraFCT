package com.project.atmiraFCT.repository;


import com.project.atmiraFCT.model.domain.Colaborator;
import com.project.atmiraFCT.model.domain.Expense;
import com.project.atmiraFCT.model.domain.Project;
import com.project.atmiraFCT.model.domain.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExpenseRepository  extends JpaRepository<Expense, Integer> {
    List<Expense> findByColaborator(Colaborator colaborator);

    List<Expense> findByProject(Project project);

    List<Expense> findByColaboratorAndProject(Colaborator colaborator, Project project);
}
