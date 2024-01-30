package com.project.atmiraFCT.controllerTest;

import com.project.atmiraFCT.controller.ProjectController;
import com.project.atmiraFCT.exception.RecordNotFoundException;
import com.project.atmiraFCT.model.domain.Project;
import com.project.atmiraFCT.repository.ProjectRepository;
import com.project.atmiraFCT.service.ProjectService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class ProjectControllerTest {

    @Mock
    private ProjectService projectService;

    @Mock
    private ProjectRepository projectRepository;

    @InjectMocks
    private ProjectController projectController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void getProjects_ShouldReturnAllProjects() {
        List<Project> projects = Collections.singletonList(new Project());

        when(projectService.getAllProjects()).thenReturn(projects);

        List<Project> result = projectController.getProjects();

        verify(projectService, times(1)).getAllProjects();

        assertEquals(projects, result);
    }

    @Test
    public void saveProject_WhenDatesAreValid_ShouldReturnSavedProject() {
        Project project = new Project();
        project.setInitialDate(new java.util.Date());
        project.setEndDate(java.util.Date.from(LocalDate.now().plusDays(1).atStartOfDay(ZoneId.systemDefault()).toInstant()));

        when(projectService.saveProject(any(Project.class))).thenReturn(project);

        ResponseEntity<Project> response = projectController.saveProject(project);

        verify(projectService, times(1)).saveProject(any(Project.class));

        assertEquals(project, response.getBody());
    }

    @Test
    public void saveProject_WhenInitialDateIsBeforeToday_ShouldThrowException() {
        Project project = new Project();
        project.setInitialDate(java.util.Date.from(LocalDate.now().minusDays(1).atStartOfDay(ZoneId.systemDefault()).toInstant()));
        project.setEndDate(new java.util.Date());

        assertThrows(IllegalArgumentException.class, () -> projectController.saveProject(project));

        verify(projectService, never()).saveProject(any(Project.class));
    }

    @Test
    public void saveProject_WhenEndDateIsBeforeInitialDate_ShouldThrowException() {
        Project project = new Project();
        project.setInitialDate(new java.util.Date());
        project.setEndDate(java.util.Date.from(LocalDate.now().minusDays(1).atStartOfDay(ZoneId.systemDefault()).toInstant()));

        assertThrows(IllegalArgumentException.class, () -> projectController.saveProject(project));

        verify(projectService, never()).saveProject(any(Project.class));
    }

    @Test
    public void getUserById_ShouldReturnProject() {
        Long projectId = 1L;
        Project project = new Project();

        when(projectService.getUserById(projectId)).thenReturn(project);

        ResponseEntity<Project> response = projectController.getUserById(projectId);

        verify(projectService, times(1)).getUserById(projectId);

        assertEquals(project, response.getBody());
    }

    @Test
    public void getProjectsByColaborator_ShouldReturnProjects() {
        String collaboratorName = "John Doe";
        List<Project> projects = Collections.singletonList(new Project());

        when(projectService.getProjectsByColaborator(collaboratorName)).thenReturn(projects);

        List<Project> result = projectController.getProjectsByColaborator(collaboratorName);

        verify(projectService, times(1)).getProjectsByColaborator(collaboratorName);

        assertEquals(projects, result);
    }

    @Test
    public void updateProject_ShouldReturnUpdatedProject() {
        Long projectId = 1L;
        Project updatedProject = new Project();

        when(projectService.createOrUpdateProject(any(Project.class))).thenReturn(updatedProject);

        ResponseEntity<Project> response = projectController.updateProject(projectId, updatedProject);

        verify(projectService, times(1)).createOrUpdateProject(any(Project.class));

        assertEquals(updatedProject, response.getBody());
    }

    @Test
    public void deleteProject_WhenProjectExists_ShouldDeleteProject() {
        Long projectId = 1L;

        when(projectRepository.findById(projectId)).thenReturn(Optional.of(new Project()));

        ResponseEntity<String> response = projectController.deleteProject(projectId);

        verify(projectRepository, times(1)).findById(projectId);
        verify(projectRepository, times(1)).deleteById(projectId);

        assertEquals("Project with ID " + projectId + " has been deleted.", response.getBody());
    }

    @Test
    public void deleteProject_WhenProjectDoesNotExist_ShouldThrowException() {
        Long projectId = 1L;

        when(projectRepository.findById(projectId)).thenReturn(Optional.empty());

        assertThrows(RecordNotFoundException.class, () -> projectController.deleteProject(projectId));

        verify(projectRepository, times(1)).findById(projectId);
        verify(projectRepository, never()).deleteById(projectId);
    }
}

