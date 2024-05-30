package com.project.atmiraFCT.controller;

import com.project.atmiraFCT.model.domain.Colaborator;
import com.project.atmiraFCT.model.domain.Department;
import com.project.atmiraFCT.repository.ColaboratorDepartmentRepository;
import com.project.atmiraFCT.repository.ColaboratorRepository;
import com.project.atmiraFCT.repository.DepartmentRepository;
import com.project.atmiraFCT.service.DepartmentService;
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
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;



@ExtendWith(MockitoExtension.class)
public class DepartmentControllerTest {

    @Mock
    private DepartmentRepository departmentRepository;

    @Mock
    private DepartmentService departmentService;

    @Mock
    private ColaboratorRepository colaboratorRepository;

    @Mock
    private ColaboratorDepartmentRepository colaboratorDepartmentRepository;

    @InjectMocks
    private DepartmentController departmentController;

    private Department department;
    private Colaborator colaborator;

    @BeforeEach
    void setUp() {
        department = new Department();
        department.setId(1L);
        department.setCode("ABC123");

        colaborator = new Colaborator();
        colaborator.setId_alias("COLAB_ID");
    }

    @DisplayName("Test saveDepartment method")
    @Test
    void testSaveDepartment() {
        // Given
        String colaboratorId = "1"; // ID de colaborador simulado
        Department department = new Department(); // Crear un objeto Department simulado

        given(departmentService.createDeparmentWithExistingColaborator(any(), any(), any()))
                .willReturn(department); // Simular el servicio para devolver el departamento creado

        // When
        ResponseEntity<Department> responseEntity = departmentController.save(colaboratorId, department);

        // Then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(responseEntity.getBody()).isEqualTo(department);
        verify(departmentService).createDeparmentWithExistingColaborator(any(), any(), any());
    }

    @DisplayName("Test getAll method")
    @Test
    void testGetAllDepartments() {
        // Given
        Department department1 = new Department();
        department1.setId(1L);
        department1.setCode("ABC123");

        Department department2 = new Department();
        department2.setId(2L);
        department2.setCode("DEF456");

        List<Department> departments = Arrays.asList(department1, department2);

        when(departmentRepository.findAll()).thenReturn(departments);

        // When
        List<Department> result = departmentController.getAll();

        // Then
        assertThat(result).isNotNull();
        assertThat(result.size()).isEqualTo(2);
        assertThat(result.get(0).getId()).isEqualTo(1L);
        assertThat(result.get(0).getCode()).isEqualTo("ABC123");
        assertThat(result.get(1).getId()).isEqualTo(2L);
        assertThat(result.get(1).getCode()).isEqualTo("DEF456");

        // Verify
        verify(departmentRepository).findAll();
    }

}

