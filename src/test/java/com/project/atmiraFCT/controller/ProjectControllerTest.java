package com.project.atmiraFCT.controller;

import com.project.atmiraFCT.model.Enum.TypeOfService;
import com.project.atmiraFCT.model.domain.DTO.ProjectDTO;
import com.project.atmiraFCT.model.domain.Project;
import com.project.atmiraFCT.service.ProjectService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProjectControllerTest {

    @Mock
    private ProjectService projectService;

    @InjectMocks
    private ProjectController projectController;

    private Project project;

    @BeforeEach
    void setUp() {
        project = new Project();
        project.setId_code("1");
        project.setName("Test Project");
        project.setTypeOfService(TypeOfService.DESARROLLO);
        project.setInitialDate(new Date());
        project.setEndDate(new Date());
        project.setActive(true);
    }


    @DisplayName("Test get project by ID")
    @Test
    void testGetProjectById() {

        // Given
        List<Project> projects = Arrays.asList(new Project(), new Project());
        when(projectService.getProjectById("1")).thenReturn(project);

        // When
        ResponseEntity<ProjectDTO> response = projectController.getProjectById("1");

        // Then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(project);
    }

    @DisplayName("Test update project")
    @Test
    void testUpdateProject() throws Exception {
        // Given
        Project updatedProject = new Project();
        updatedProject.setId_code("1");
        updatedProject.setName("Updated Project");

        when(projectService.updateProject("1", updatedProject)).thenReturn(updatedProject);

        // When
        ResponseEntity<Project> response = projectController.updateProject("1", updatedProject);

        // Then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(updatedProject);
    }



    @DisplayName("Test get projects by colaborator ID")
    @Test
    void testGetProjectsByColaboratorId() {
        // Given
            List<ProjectDTO> projects = Arrays.asList(new ProjectDTO(), new ProjectDTO());
        when(projectService.getProjectsByColaboratorId("123")).thenReturn(projects);

        // When
        List<ProjectDTO> result = projectController.getProjectsByColaboratorId("123");

        // Then
        assertThat(result).hasSize(2);
    }

    // Add more tests for other methods as needed
}
