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


    public List<Expense> getExpenseByFilter(String id_project, String id_alias, String date) {
        String query ="SELECT * FROM Expense u WHERE "+ (id_project != null ? "id_code_project LIKE '"+id_project+"'" : "")+(id_project!=null && (id_alias!=null || date!=null) ? "AND" : "")+(id_alias != null ? "id_colaborator LIKE '"+id_alias+"'": "")+(id_alias!=null && date!=null ? "AND" : "")+(date != null ? "ticket_date= "+date : "");
        System.out.println(query);
        return expenseRepository.findByFilter(query);
    }

}
