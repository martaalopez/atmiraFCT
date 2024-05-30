package com.project.atmiraFCT.service;


import com.project.atmiraFCT.model.domain.Colaborator;
import com.project.atmiraFCT.model.domain.Department;
import com.project.atmiraFCT.repository.ColaboratorRepository;
import com.project.atmiraFCT.repository.DepartmentRepository;
import com.project.atmiraFCT.repository.ColaboratorDepartmentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class DepartmentServiceTest {

    @Mock
    private DepartmentRepository departmentRepository;

    @Mock
    private ColaboratorRepository colaboratorRepository;

    @Mock
    private ColaboratorDepartmentRepository colaboratorDepartmentRepository;

    @InjectMocks
    private DepartmentService departmentService;

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
        given(departmentRepository.save(any()))
                .willAnswer(invocation -> invocation.getArgument(0));
        given(colaboratorRepository.findById(any()))
                .willReturn(Optional.of(colaborator));

        // When
        Department savedDepartment = departmentService.createDeparmentWithExistingColaborator(
                department.getId(),
                department.getCode(),
                colaborator.getId_alias()
        );

        // Then
        assertThat(savedDepartment).isNotNull();
        verify(departmentRepository).save(any());
        verify(colaboratorDepartmentRepository).save(any());
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
        List<Department> result = departmentService.getAll();

        // Then

        assertThat(result).isNotNull();
        assertThat(result.size()).isEqualTo(2);
        assertThat(result.get(0).getId()).isEqualTo(1L);
        assertThat(result.get(0).getCode()).isEqualTo("ABC123");
        assertThat(result.get(1).getId()).isEqualTo(2L);
        assertThat(result.get(1).getCode()).isEqualTo("DEF456");
    }
}
