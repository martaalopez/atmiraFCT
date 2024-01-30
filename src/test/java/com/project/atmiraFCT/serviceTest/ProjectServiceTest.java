package com.project.atmiraFCT.serviceTest;
import com.project.atmiraFCT.exception.RecordNotFoundException;
import com.project.atmiraFCT.model.domain.Project;
import com.project.atmiraFCT.repository.ProjectRepository;
import com.project.atmiraFCT.service.ProjectService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ProjectServiceTest {
    @Mock
    private ProjectRepository projectRepository;

    @InjectMocks
    private ProjectService projectService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSaveProject() {
        Project projectToSave = new Project();
        when(projectRepository.save(any())).thenReturn(projectToSave);

        Project savedProject = projectService.saveProject(projectToSave);

        assertNotNull(savedProject);
        verify(projectRepository, times(1)).save(any());
    }

    @Test
    void testGetAllProjects() {
        List<Project> projects = new ArrayList<>();
        when(projectRepository.findAll()).thenReturn(projects);

        List<Project> result = projectService.getAllProjects();

        assertEquals(projects, result);
        verify(projectRepository, times(1)).findAll();
    }

    @Test
    void testGetUserById() {
        Long projectId = 1L;
        Project project = new Project();
        when(projectRepository.findById(projectId)).thenReturn(Optional.of(project));

        Project result = projectService.getUserById(projectId);

        assertEquals(project, result);
        verify(projectRepository, times(1)).findById(projectId);
    }

    @Test
    void testGetUserById_RecordNotFoundException() {
        Long projectId = 1L;
        when(projectRepository.findById(projectId)).thenReturn(Optional.empty());

        assertThrows(RecordNotFoundException.class, () -> projectService.getUserById(projectId));

        verify(projectRepository, times(1)).findById(projectId);
    }

    @Test
    void testDeleteProject() {
        Long projectId = 1L;
        when(projectRepository.findById(projectId)).thenReturn(Optional.of(new Project()));

        projectService.deleteProject(projectId);

        verify(projectRepository, times(1)).deleteById(projectId);
    }

    @Test
    void testDeleteProject_RecordNotFoundException() {
        Long projectId = 1L;
        when(projectRepository.findById(projectId)).thenReturn(Optional.empty());

        assertThrows(RecordNotFoundException.class, () -> projectService.deleteProject(projectId));

        verify(projectRepository, times(0)).deleteById(projectId);
    }
}
