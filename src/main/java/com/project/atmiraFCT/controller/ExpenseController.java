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

@CrossOrigin(origins = "${Front_URL}")
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
    @CrossOrigin(origins = "${Front_URL}")
    @PostMapping("/expense/save/{colaboratorId}/{projectId}")
    public ResponseEntity<Expense> createExpenseWithExistingProjectColaborator(
            @PathVariable String colaboratorId,
            @PathVariable String projectId,
            @RequestBody Expense expense
    ) {
        Expense createdExpense = expenseService.saveExpenseExistingProyectColaborator(
                expense.getTicketId(),
                expense.getTicketDate(),
                expense.getCost(),
                expense.getDescription(),
                expense.getState(),
                expense.getTypeExpensive(),
                colaboratorId,
                projectId
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(createdExpense);
    }

    /**
     * Metodo que filtrara los gastos en base a los parametros que recive de "proyecto, colaborador y fecha"
     * @param id_code id del proyecto del cual queremos extraer sus dietas
     * @param id_alias id del colaborador de quien deseamos extraer sus gastos
     * @param date en formato YYYY-MM-DD (puedes omitir el dia y se buscara todos los del mes, y si omites dia y mes se busca todos los del año)
     * @return
     */
    @CrossOrigin(origins = "${Front_URL}")
    @GetMapping("/expense")
    public ResponseEntity<List<Expense>> getExpenseByFilter(@RequestParam(name = "id_code_project",required = false) String id_code,@RequestParam(name="id_alias",required = false) String id_alias, @RequestParam(name="date",required = false) String date) {
        return ResponseEntity.ok(expenseService.getExpenseByFilter(id_code, id_alias, date));
    }

    /**
     * Elimina un gasto por su ID.
     *
     * @param id    El ID del gasto a eliminar.
     * @return      ResponseEntity indicando el éxito de la operación.
     *              Devuelve true si el gasto se eliminó correctamente, false en caso contrario.
     * @throws RecordNotFoundException   Si no se encuentra ningún gasto con el ID proporcionado.
     */
    @CrossOrigin(origins = "${Front_URL}")
    @DeleteMapping("/expense/delete/{id}")
    public ResponseEntity<Boolean> deleteExpense(@PathVariable String id) {
        Optional<Expense> result =expenseRepository.findByTicketId(id);
        if (result.isPresent()) {
            expenseService.deleteExpense(id);
            return ResponseEntity.ok(true);
        } else {
            throw new RecordNotFoundException("No project found with id: " + id);
        }
    }

    @CrossOrigin(origins = "${Front_URL}")
    @PutMapping("expensive/state")
    public ResponseEntity<Expense> updateExpenseState(@RequestBody Expense expense) {
        try {
            return ResponseEntity.ok(expenseService.updateExpenseState(expense));
        } catch (RecordNotFoundException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }
}