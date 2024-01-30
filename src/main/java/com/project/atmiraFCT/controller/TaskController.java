package com.project.atmiraFCT.controller;

import com.project.atmiraFCT.exception.RecordNotFoundException;
import com.project.atmiraFCT.model.domain.Task;
import com.project.atmiraFCT.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
@RestController
public class TaskController {
    @Autowired
    private TaskService taskService;

    @GetMapping("/task/list")
    public List<Task> getTasks() {
        return taskService.getAllTasks();
    }

    @PostMapping("/save")
    public ResponseEntity<Task> saveTaskWithProjectAndColaborator(@RequestBody Task task,
                                                                  @RequestParam Long projectId,
                                                                  @RequestParam String colaboratorId) {
        try {
            Task savedTask = taskService.saveTaskWithProjectAndColaborator(task, projectId, colaboratorId);
            return new ResponseEntity<>(savedTask, HttpStatus.CREATED);
        } catch (RecordNotFoundException ex) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("task/{id}")
    public ResponseEntity<Task> getTaskById(@PathVariable("id") Long id) {
        Task task = taskService.getTaskById(id);
        return ResponseEntity.ok(task);
    }
    @DeleteMapping("taskDelete/{id}")
    public ResponseEntity<String> deleteTask(@PathVariable Long id) {
        try {
            taskService.deleteTask(id);
            return ResponseEntity.ok("Task with ID " + id + " has been deleted.");
        } catch (RecordNotFoundException e) {
            throw new RecordNotFoundException("No task found with id: " + id);
        }
    }
    @PutMapping("/task/{id}")
    public ResponseEntity<Task> updateTask(@PathVariable("id") Long id, @RequestBody Task updatedTask) {
        Task updated = taskService.createOrUpdateTask(updatedTask);
        return ResponseEntity.ok(updated);
    }

}
