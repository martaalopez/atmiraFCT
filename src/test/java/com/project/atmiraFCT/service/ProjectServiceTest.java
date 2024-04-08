package com.project.atmiraFCT.service;

import com.project.atmiraFCT.exception.RecordNotFoundException;
import com.project.atmiraFCT.model.Enum.TypeOfService;
import com.project.atmiraFCT.model.domain.Colaborator;
import com.project.atmiraFCT.model.domain.ColaboratorProject;
import com.project.atmiraFCT.model.domain.Project;
import com.project.atmiraFCT.repository.ColaboratorProjectRepository;
import com.project.atmiraFCT.repository.ColaboratorRepository;
import com.project.atmiraFCT.repository.ProjectRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProjectServiceTest {

    @Mock
    private ProjectRepository projectRepository;

    @Mock
    private ColaboratorRepository colaboratorRepository;

    @Mock
    private ColaboratorProjectRepository colaboratorProjectRepository;

    @InjectMocks
    private ProjectService projectService;

    private Project project;
    private Colaborator colaborator;

    @BeforeEach
    public void setUp() {
        project = new Project();
        project.setId_code("1");
        project.setName("Test Project");
        project.setInitialDate(new Date());
        project.setEndDate(new Date());
        project.setActive(true);
        project.setTypeOfService(TypeOfService.DESARROLLO);

        colaborator = new Colaborator();
        colaborator.setId_alias("1");
        colaborator.setName("John");
        colaborator.setSurname("Doe");
        colaborator.setEmail("john.doe@example.com");
    }

    @DisplayName("Test getProjectById method with existing project")
    @Test
    void testGetProjectByIdExisting() {
        // Given
        when(projectRepository.findById("1")).thenReturn(Optional.of(project));

        // When
        Project foundProject = projectService.getProjectById("1");

        // Then
        assertThat(foundProject).isEqualTo(project);
    }

    @DisplayName("Test getProjectById method with non-existing project")
    @Test
    void testGetProjectByIdNonExisting() {
        // Given
        when(projectRepository.findById("2")).thenReturn(Optional.empty());

        // Then
        assertThatThrownBy(() -> projectService.getProjectById("2"))
                .isInstanceOf(RecordNotFoundException.class);
    }

    @DisplayName("Test getAllProjects")
    @Test
    void testGetAllProjects() {
        // Given
        List<Project> projectList = new ArrayList<>();
        projectList.add(project);
        when(projectRepository.findAll()).thenReturn(projectList);

        // When
        List<Project> foundProjects = projectService.getAllProjects();

        // Then
        assertThat(foundProjects).hasSize(1);
        assertThat(foundProjects.get(0)).isEqualTo(project);
    }

    @DisplayName("Test createProjectWithExistingColaborator method")
    @Test
    void testCreateProjectWithExistingColaborator() {
        // Given
        when(colaboratorRepository.findById("1")).thenReturn(Optional.of(colaborator));
        when(projectRepository.save(any(Project.class))).thenReturn(project);
        when(colaboratorProjectRepository.save(any(ColaboratorProject.class))).thenReturn(new ColaboratorProject(project, colaborator));

        // When
        Project savedProject = projectService.createProjectWithExistingColaborator("1", "Test Project", new Date(), new Date(), true, TypeOfService.DESARROLLO, "1");

        // Then
        assertThat(savedProject).isEqualTo(project);
        verify(projectRepository, times(1)).save(any(Project.class));
        verify(colaboratorProjectRepository, times(1)).save(any(ColaboratorProject.class));
    }

    @DisplayName("Test deleteProject method with existing project")
    @Test
    void testDeleteProjectExisting() {
        // Given
        when(projectRepository.findById("1")).thenReturn(Optional.of(project));

        // When
        projectService.deleteProject("1");

        // Then
        verify(projectRepository, times(1)).deleteById("1");
    }

}
