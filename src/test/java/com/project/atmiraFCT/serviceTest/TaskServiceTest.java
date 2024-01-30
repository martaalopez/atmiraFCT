package com.project.atmiraFCT.serviceTest;

import com.project.atmiraFCT.exception.RecordNotFoundException;
import com.project.atmiraFCT.model.domain.Project;
import com.project.atmiraFCT.model.domain.Task;
import com.project.atmiraFCT.repository.ColaboratorRepository;
import com.project.atmiraFCT.repository.ProjectRepository;
import com.project.atmiraFCT.repository.TaskRepository;
import com.project.atmiraFCT.service.TaskService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class TaskServiceTest {


    @Mock
    private TaskRepository taskRepository;

    @Mock
    private ProjectRepository projectRepository;

    @Mock
    private ColaboratorRepository colaboratorRepository;

    @InjectMocks
    private TaskService taskService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void saveTask_ShouldSaveTask() {
        Task task = new Task();
        Project project = new Project();
        task.setProject(project);

        when(projectRepository.save(any(Project.class))).thenReturn(project);
        when(taskRepository.save(any(Task.class))).thenReturn(task);

        Task savedTask = taskService.saveTask(task);

        verify(projectRepository, times(1)).save(any(Project.class));
        verify(taskRepository, times(1)).save(any(Task.class));

        assertNotNull(savedTask);
        assertEquals(project, savedTask.getProject());
    }

    @Test
    public void getAllTasks_ShouldReturnAllTasks() {
        List<Task> tasks = Collections.singletonList(new Task());

        when(taskRepository.findAll()).thenReturn(tasks);

        List<Task> result = taskService.getAllTasks();

        verify(taskRepository, times(1)).findAll();

        assertEquals(tasks, result);
    }

    @Test
    public void getTaskById_WhenTaskExists_ShouldReturnTask() {
        Long taskId = 1L;
        Task task = new Task();
        task.setId_code(taskId);

        when(taskRepository.findById(taskId)).thenReturn(Optional.of(task));

        Task retrievedTask = taskService.getTaskById(taskId);

        verify(taskRepository, times(1)).findById(taskId);

        assertEquals(task, retrievedTask);
    }

    @Test
    public void getTaskById_WhenTaskDoesNotExist_ShouldThrowException() {
        Long taskId = 1L;

        when(taskRepository.findById(taskId)).thenReturn(Optional.empty());

        assertThrows(RecordNotFoundException.class, () -> taskService.getTaskById(taskId));

        verify(taskRepository, times(1)).findById(taskId);
    }

    @Test
    public void deleteTask_WhenTaskExists_ShouldDeleteTask() {
        Long taskId = 1L;

        when(taskRepository.findById(taskId)).thenReturn(Optional.of(new Task()));

        taskService.deleteTask(taskId);

        verify(taskRepository, times(1)).findById(taskId);
        verify(taskRepository, times(1)).deleteById(taskId);
    }

    @Test
    public void deleteTask_WhenTaskDoesNotExist_ShouldThrowException() {
        Long taskId = 1L;

        when(taskRepository.findById(taskId)).thenReturn(Optional.empty());

        assertThrows(RecordNotFoundException.class, () -> taskService.deleteTask(taskId));

        verify(taskRepository, times(1)).findById(taskId);
    }

    @Test
    public void createOrUpdateTask_WhenTaskIdNotNull_ShouldUpdateTask() {
        Long taskId = 1L;
        Task existingTask = new Task();
        existingTask.setId_code(taskId);

        when(taskRepository.findById(taskId)).thenReturn(Optional.of(existingTask));
        when(taskRepository.save(any(Task.class))).thenReturn(existingTask);

        Task updatedTask = new Task();
        updatedTask.setId_code(taskId);
        updatedTask.setDescription("Updated Description");

        Task result = taskService.createOrUpdateTask(updatedTask);

        verify(taskRepository, times(1)).findById(taskId);
        verify(taskRepository, times(1)).save(any(Task.class));

        assertEquals(existingTask.getId_code(), result.getId_code());
        assertEquals(updatedTask.getDescription(), result.getDescription());
    }

    @Test
    public void createOrUpdateTask_WhenTaskIdNull_ShouldInsertTask() {
        Task newTask = new Task();
        newTask.setDescription("New Task");

        when(taskRepository.save(any(Task.class))).thenReturn(newTask);

        Task result = taskService.createOrUpdateTask(newTask);

        verify(taskRepository, never()).findById(anyLong());
        verify(taskRepository, times(1)).save(any(Task.class));

        assertNotNull(result.getId_code());
        assertEquals(newTask.getDescription(), result.getDescription());
    }
}
