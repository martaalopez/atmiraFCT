package com.project.atmiraFCT.service;


import com.project.atmiraFCT.model.Enum.TypeOfService;
import com.project.atmiraFCT.model.domain.Colaborator;
import com.project.atmiraFCT.model.domain.Project;
import com.project.atmiraFCT.model.domain.Task;
import com.project.atmiraFCT.repository.ColaboratorRepository;
import com.project.atmiraFCT.repository.ProjectRepository;
import com.project.atmiraFCT.repository.TaskRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TaskServiceTest {

    @Mock
    private TaskRepository taskRepository;

    @InjectMocks
    private TaskService taskService;

    @Mock
    private ColaboratorRepository colaboratorRepository;

    @Mock
    private ProjectRepository projectRepository;

    private Task task;

    private Colaborator colaborator;

    private Project project;



    @BeforeEach
    public void setUp() {
        task = new Task();
        task.setIdCode("1");
        task.setTitle("Test1");
        task.setDescription("test");
        task.setObjective("objetivos");
        task.setClosed(false);

        colaborator = new Colaborator();
        colaborator.setId_alias("1");
        colaborator.setName("John");
        colaborator.setSurname("Doe");
        colaborator.setEmail("john.doe@example.com");

        project = new Project();
        project.setId_code("1");
        project.setName("Test Project");
        project.setInitialDate(new Date());
        project.setEndDate(new Date());
        project.setActive(true);
        project.setTypeOfService(TypeOfService.DESARROLLO);


    }



    @DisplayName("Get task by project id")
    @Test
    public void testGetTasksByProjectId() {
        // Given
        when(projectRepository.findById("1")).thenReturn(Optional.of(project));
        when(taskRepository.findByProjectId("1")).thenReturn(List.of(task));

        // When
        List<Task> tasks = taskService.getTasksByProject("1");

        // Then
        assertNotNull(tasks);
        assertEquals(1, tasks.size());
        verify(projectRepository, times(1)).findById("1");
        verify(taskRepository, times(1)).findByProjectId("1");
    }


    @DisplayName("Test create task")
    @Test
    public void testCreateTask() {
        // Given
        when(colaboratorRepository.findById("1")).thenReturn(Optional.of(colaborator));
        when(projectRepository.findById("1")).thenReturn(Optional.of(project));
        when(taskRepository.save(any(Task.class))).thenAnswer(invocation -> {
            Task taskArgument = invocation.getArgument(0);
            taskArgument.setIdCode("1_1");
            return taskArgument;
        });

        // When
        Task savedTask = taskService.saveTask("Test1", "test", "objectivos", false, "1", "1");

        // Then
        assertNotNull(savedTask);
        assertEquals("1_1", savedTask.getIdCode());
        verify(taskRepository, times(1)).save(any(Task.class));
        verify(colaboratorRepository, times(1)).findById("1");
        verify(projectRepository, times(1)).findById("1");
    }




    @DisplayName("List tasks")
    @Test
    public void testListTasks() {
        // Given
        when(taskRepository.findAll()).thenReturn(List.of(task));
        // When
        List<Task> tasks = taskService.getAllTasks();
        // Then
        assertNotNull(tasks);
        assertEquals(1, tasks.size());
        verify(taskRepository, times(1)).findAll();
    }


    @DisplayName("Delete task")
    @Test
    public void testDeleteTask() {
        // Given
        when(taskRepository.findById("1")).thenReturn(Optional.of(task));

        // When
        taskService.deleteTask("1");

        // Then
        verify(taskRepository, times(1)).findById("1");
        verify(taskRepository, times(1)).deleteById("1");

    }

    @DisplayName("Get tasks by colaborator and project")
    @Test
    public void testGetTasksByColaboratorAndProject() {
        // Given
        when(colaboratorRepository.findById("1")).thenReturn(Optional.of(colaborator));
        when(projectRepository.findById("1")).thenReturn(Optional.of(project));
        when(taskRepository.findByColaboratorAndProject(colaborator, project)).thenReturn(List.of(task));

        // When
        List<Task> tasks = taskService.getTasksByColaboratorAndProject("1", "1");

        // Then
        assertNotNull(tasks);
        assertEquals(1, tasks.size());
        verify(colaboratorRepository, times(1)).findById("1");
        verify(projectRepository, times(1)).findById("1");
        verify(taskRepository, times(1)).findByColaboratorAndProject(colaborator, project);
    }

    @DisplayName("Update task")
    @Test
    public void testUpdateTask() throws Exception {
        // Given
        when(taskRepository.findById("1")).thenReturn(Optional.of(task));

        // When
        taskService.updateTask("1", task);

        // Then
        verify(taskRepository, times(1)).findById("1");
        verify(taskRepository, times(1)).save(any(Task.class));
    }

}
