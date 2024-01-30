package com.project.atmiraFCT.controllerTest;
import com.project.atmiraFCT.exception.RecordNotFoundException;
import com.project.atmiraFCT.model.domain.Task;
import com.project.atmiraFCT.service.TaskService;
import com.project.atmiraFCT.controller.TaskController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class TaskControllerTest {

    @Mock
    private TaskService taskService;

    @InjectMocks
    private TaskController taskController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void getTasks_ShouldReturnAllTasks() {
        List<Task> tasks = Collections.singletonList(new Task());

        when(taskService.getAllTasks()).thenReturn(tasks);

        List<Task> result = taskController.getTasks();

        verify(taskService, times(1)).getAllTasks();

        assertEquals(tasks, result);
    }

    @Test
    public void saveTask_ShouldReturnSavedTask() {
        Task task = new Task();

        when(taskService.saveTask(any(Task.class))).thenReturn(task);

        Task result = taskController.saveTask(task);

        verify(taskService, times(1)).saveTask(any(Task.class));

        assertEquals(task, result);
    }

    @Test
    public void getTaskById_ShouldReturnTask() {
        Long taskId = 1L;
        Task task = new Task();

        when(taskService.getTaskById(taskId)).thenReturn(task);

        ResponseEntity<Task> response = taskController.getTaskById(taskId);

        verify(taskService, times(1)).getTaskById(taskId);

        assertEquals(task, response.getBody());
    }

    @Test
    public void deleteTask_WhenTaskExists_ShouldDeleteTask() {
        Long taskId = 1L;

        ResponseEntity<String> response = taskController.deleteTask(taskId);

        verify(taskService, times(1)).deleteTask(taskId);

        assertEquals("Task with ID " + taskId + " has been deleted.", response.getBody());
    }

    @Test
    public void deleteTask_WhenTaskDoesNotExist_ShouldThrowException() {
        Long taskId = 1L;

        doThrow(new RecordNotFoundException("No task found with id: " + taskId)).when(taskService).deleteTask(taskId);

        assertThrows(RecordNotFoundException.class, () -> taskController.deleteTask(taskId));

        verify(taskService, times(1)).deleteTask(taskId);
    }

    @Test
    public void updateTask_ShouldReturnUpdatedTask() {
        Long taskId = 1L;
        Task updatedTask = new Task();

        when(taskService.createOrUpdateTask(any(Task.class))).thenReturn(updatedTask);

        ResponseEntity<Task> response = taskController.updateTask(taskId, updatedTask);

        verify(taskService, times(1)).createOrUpdateTask(any(Task.class));

        assertEquals(updatedTask, response.getBody());
    }
}
