package com.project.atmiraFCT.service;

import com.project.atmiraFCT.exception.RecordNotFoundException;
import com.project.atmiraFCT.model.Enum.TypeExpensive;
import com.project.atmiraFCT.model.domain.Colaborator;
import com.project.atmiraFCT.model.domain.Expense;
import com.project.atmiraFCT.model.domain.Project;
import com.project.atmiraFCT.model.domain.Task;
import com.project.atmiraFCT.repository.ColaboratorRepository;
import com.project.atmiraFCT.repository.ExpenseRepository;
import com.project.atmiraFCT.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ExpenseService {

    @Autowired
    private ExpenseRepository expenseRepository;

    @Autowired
    private ColaboratorRepository colaboratorRepository;

    @Autowired
    private ProjectRepository projectRepository;

    /**
     * Guarda un gasto asociado a un colaborador y proyecto existente.
     * @param cost            Costo del gasto.
     * @param description     Descripción del gasto.
     * @param state           Estado del gasto.
     * @param colaboratorId   ID del colaborador asociado al gasto.
     * @param projectId       ID del proyecto asociado al gasto.
     * @return                El gasto guardado.
     * @throws RecordNotFoundException Si el colaborador o el proyecto no se encuentran.
     */
    public Expense saveExpenseExistingProyectColaborator(String ticketId, Date ticketDate, Integer cost,
                                                         String description, Boolean state, TypeExpensive typeExpensive, String colaboratorId, String projectId) {
        Optional<Colaborator> colaboratorOptional = colaboratorRepository.findById(colaboratorId);
        Optional<Project> projectOptional = projectRepository.findById(projectId);

        if (colaboratorOptional.isPresent() && projectOptional.isPresent()) {
            Expense expense = new Expense(ticketId, ticketDate, cost, description, state, typeExpensive,projectOptional.get() ,colaboratorOptional.get());
            return expenseRepository.save(expense);
        } else {
            throw new RecordNotFoundException("Colaborator or project not found");
        }
    }

    /**
     * Elimina un gasto por su ID.
     *
     * @param id El ID del gasto a eliminar.
     * @throws RecordNotFoundException Si no se encuentra el gasto.
     */
    public void deleteExpense(Integer id) {
        Optional<Expense> result = expenseRepository.findById(id);
        if (result.isPresent()) {
            expenseRepository.deleteById(id);
        } else {
            throw new RecordNotFoundException("No task found with id: " + id);
        }
    }

    /**
     * Obtiene todos los gastos asociados a un colaborador.
     *
     * @param colaboratorId ID del colaborador.
     * @return Lista de gastos asociados al colaborador.
     * @throws RecordNotFoundException Si no se encuentra el colaborador.
     */
    public List<Expense> getExpenseByColaborator(String colaboratorId) {
        Optional<Colaborator> colaboratorOptional = colaboratorRepository.findById(colaboratorId);
        if (colaboratorOptional.isPresent()) {
            return expenseRepository.findByColaborator(colaboratorOptional.get());
        } else {
            throw new RecordNotFoundException("Colaborator not found with id: " + colaboratorId);
        }
    }

    /**
     * Obtiene todos los gastos asociados a un proyecto.
     *
     * @param projectId ID del proyecto.
     * @return Lista de gastos asociados al proyecto.
     * @throws RecordNotFoundException Si no se encuentra el proyecto.
     */
    public List<Expense> getExpenseByProject(String projectId) {
        Optional<Project> projectOptional = projectRepository.findById(projectId);
        if (projectOptional.isPresent()) {
            return expenseRepository.findByProject(projectOptional.get());
        } else {
            throw new RecordNotFoundException("Project not found with id: " + projectId);
        }
    }

    /**
     * Obtiene todos los gastos asociados a un colaborador y proyecto.
     *
     * @param colaboratorId ID del colaborador.
     * @param projectId     ID del proyecto.
     * @return Lista de gastos asociados al colaborador y proyecto.
     * @throws RecordNotFoundException Si no se encuentra el colaborador o el proyecto.
     */
    public List<Expense> getExpenseByColaboratorAndProject(String colaboratorId, String projectId) {
        Optional<Colaborator> colaboratorOptional = colaboratorRepository.findById(colaboratorId);
        Optional<Project> projectOptional = projectRepository.findById(projectId);

        if (colaboratorOptional.isPresent() && projectOptional.isPresent()) {
            return expenseRepository.findByColaboratorAndProject(colaboratorOptional.get(), projectOptional.get());
        } else {
            throw new RecordNotFoundException("Colaborator or project not found");
        }
    }

}
