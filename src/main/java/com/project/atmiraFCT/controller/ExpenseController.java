package com.project.atmiraFCT.controller;

import com.project.atmiraFCT.exception.RecordNotFoundException;
import com.project.atmiraFCT.model.domain.Expense;
import com.project.atmiraFCT.repository.ExpenseRepository;
import com.project.atmiraFCT.service.ExpenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "https://fct-atmira-front.vercel.app:443")
@RestController
public class ExpenseController {

    @Autowired
    private ExpenseRepository expenseRepository;

    @Autowired
    private ExpenseService expenseService;

    /**
     * Crea un nuevo gasto asociado a un colaborador y proyecto existentes.
     *
     * @param colaboratorId     El ID del colaborador existente asociado al gasto.
     * @param projectId         El ID del proyecto existente asociado al gasto.
     * @param expense           El objeto Expense a ser guardado.
     * @return                  ResponseEntity con el gasto creado.
     */
    @PostMapping("/expense/save/{colaboratorId}/{projectId}")
    public ResponseEntity<Expense> createExpenseWithExistingProjectColaborator(
            @PathVariable String colaboratorId,
            @PathVariable String projectId,
            @RequestBody Expense expense
    ) {
        Expense createdExpense = expenseService.saveExpenseExistingProyectColaborator(
                expense.getDay(),
                expense.getMonth(),
                expense.getYear(),
                expense.getHours(),
                expense.getCost(),
                expense.getDescription(),
                expense.getState(),
                colaboratorId,
                projectId
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(createdExpense);
    }

    /**
     * Obtiene todos los gastos asociados a un colaborador.
     *
     * @param colaboratorId     El ID del colaborador.
     * @return                  ResponseEntity con una lista de gastos.
     */
    @GetMapping("/expense/byColaborator/{colaboratorId}")
    public ResponseEntity<List<Expense>> getExpensesByColaborator(@PathVariable String colaboratorId) {
        List<Expense> expenses = expenseService.getExpenseByColaborator(colaboratorId);
        return ResponseEntity.ok(expenses);
    }

    /**
     * Obtiene todos los gastos asociados a un proyecto.
     *
     * @param projectId     El ID del proyecto.
     * @return              ResponseEntity con una lista de gastos.
     */
    @CrossOrigin
    @GetMapping("/expense/byProject/{projectId}")
    public ResponseEntity<List<Expense>> getExpensesByProject(@PathVariable String projectId) {
        List<Expense> expenses = expenseService.getExpenseByProject(projectId);
        return ResponseEntity.ok(expenses);
    }

    /**
     * Obtiene todos los gastos asociados a un colaborador y proyecto.
     *
     * @param colaboratorId     El ID del colaborador.
     * @param projectId         El ID del proyecto.
     * @return                  ResponseEntity con una lista de gastos.
     */

    @GetMapping("/expense/byColaboratorAndProject/{colaboratorId}/{projectId}")
    public ResponseEntity<List<Expense>> getExpensesByColaboratorAndProject(
            @PathVariable String colaboratorId,
            @PathVariable String projectId
    ) {
        List<Expense> expenses= expenseService.getExpenseByColaboratorAndProject(colaboratorId, projectId);
        return ResponseEntity.ok(expenses);
    }

    /**
     * Elimina un gasto por su ID.
     *
     * @param id    El ID del gasto a eliminar.
     * @return      ResponseEntity indicando el éxito de la operación.
     *              Devuelve true si el gasto se eliminó correctamente, false en caso contrario.
     * @throws RecordNotFoundException   Si no se encuentra ningún gasto con el ID proporcionado.
     */

    @DeleteMapping("/expense/delete/{id}")
    public ResponseEntity<Boolean> deleteExpense(@PathVariable Integer id) {
        Optional<Expense> result =expenseRepository.findById(id);
        if (result.isPresent()) {
            expenseRepository.deleteById(id);
            return ResponseEntity.ok(true);
        } else {
            throw new RecordNotFoundException("No project found with id: " + id);
        }
    }
}