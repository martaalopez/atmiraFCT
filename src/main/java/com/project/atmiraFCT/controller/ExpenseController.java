package com.project.atmiraFCT.controller;

import com.project.atmiraFCT.model.domain.Expense;
import com.project.atmiraFCT.model.domain.Task;
import com.project.atmiraFCT.repository.ExpenseRepository;
import com.project.atmiraFCT.service.ExpenseService;
import com.project.atmiraFCT.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class ExpenseController {

    @Autowired
    private ExpenseRepository expenseRepository;

    @Autowired
    private ExpenseService expenseService;

    @PostMapping("/expense/save/{colaboratorId}/{projectId}")
    public ResponseEntity<Expense> createExpenseWithExistingProjectColaborator(
            @PathVariable String colaboratorId,
            @PathVariable Long projectId,
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
    @GetMapping("/expense/byColaborator/{colaboratorId}")
    public ResponseEntity<List<Expense>> getExpensesByColaborator(@PathVariable String colaboratorId) {
        List<Expense> expenses = expenseService.getExpenseByColaborator(colaboratorId);
        return ResponseEntity.ok(expenses);
    }

    @GetMapping("/expense/byProject/{projectId}")
    public ResponseEntity<List<Expense>> getExpensesByProject(@PathVariable Long projectId) {
        List<Expense> expenses = expenseService.getExpenseByProject(projectId);
        return ResponseEntity.ok(expenses);
    }

    @GetMapping("/expense/byColaboratorAndProject/{colaboratorId}/{projectId}")
    public ResponseEntity<List<Expense>> getExpensesByColaboratorAndProject(
            @PathVariable String colaboratorId,
            @PathVariable Long projectId
    ) {
        List<Expense> expenses= expenseService.getExpenseByColaboratorAndProject(colaboratorId, projectId);
        return ResponseEntity.ok(expenses);
    }

}
