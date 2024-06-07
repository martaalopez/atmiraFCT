package com.project.atmiraFCT.service;

import com.project.atmiraFCT.exception.RecordNotFoundException;
import com.project.atmiraFCT.model.Enum.TypeExpensive;
import com.project.atmiraFCT.model.domain.Colaborator;
import com.project.atmiraFCT.model.domain.Expense;
import com.project.atmiraFCT.model.domain.Project;
import com.project.atmiraFCT.repository.ColaboratorRepository;
import com.project.atmiraFCT.repository.ExpenseRepository;
import com.project.atmiraFCT.repository.ProjectRepository;
import com.project.atmiraFCT.service.Specifications.ExpenseSpecifications;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneOffset;
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
    public Expense saveExpenseExistingProyectColaborator(Integer ticketId, LocalDate ticketDate, Double cost,
                                                         String description, Boolean state, TypeExpensive typeExpensive, String colaboratorId, String projectId) {
        Optional<Colaborator> colaboratorOptional = colaboratorRepository.findById(colaboratorId);
        Optional<Project> projectOptional = projectRepository.findById(projectId);
        if(this.expenseRepository.findByTicketId(ticketId).isPresent())throw new RecordNotFoundException("Expense already exists");

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
     * Busca los gastos filtrados por proyecto, colaborador y fecha.
     * @param expense el objeto Expense con los filtros
     * @return la lista de gastos en base a los filtros
     */
    public List<Expense> getExpenseByFilter(Expense expense) {
        return expenseRepository.findAll(ExpenseSpecifications.withFilter(expense));
    }

    /**
     * Actualiza el estado de un gasto.
     *
     * @param expense El gasto a actualizar.
     * @throws RecordNotFoundException Si no se encuentra el gasto.
     */
    public Expense updateExpenseState(Expense expense) {
        if(expense.getTicketId() !=null){
            Optional<Expense> result = expenseRepository.findById(expense.getTicketId());
            if (result.isPresent()) {
                Expense savedExpense = result.get();
                savedExpense.setState(expense.getState());
                expenseRepository.save(savedExpense);
                return savedExpense;
            } else {
                throw new RecordNotFoundException("No expense found with id: " + expense.getTicketId());
            }
        }else{
            expense.setCreatedDate(Date.from(LocalDate.now().atStartOfDay().toInstant(ZoneOffset.UTC)));
            return expenseRepository.save(expense);
        }
    }
}
