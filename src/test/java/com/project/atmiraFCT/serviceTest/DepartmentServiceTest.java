package com.project.atmiraFCT.serviceTest;

import com.project.atmiraFCT.model.domain.Colaborator;
import com.project.atmiraFCT.model.domain.ColaboratorDepartment;
import com.project.atmiraFCT.model.domain.Department;
import com.project.atmiraFCT.repository.ColaboratorDepartmentRepository;
import com.project.atmiraFCT.repository.ColaboratorRepository;
import com.project.atmiraFCT.repository.DepartmentRepository;
import com.project.atmiraFCT.service.DepartmentService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
class DepartmentServiceTest {

    @Mock
    private DepartmentRepository departmentRepository;

    @Mock
    private ColaboratorRepository colaboratorRepository;

    @Mock
    private ColaboratorDepartmentRepository colaboratorDepartmentRepository;

    @InjectMocks
    private DepartmentService departmentService;

    @Test
    void testCreateDepartmentWithExistingColaborator() {
        // Arrange
        Long departmentId = 1L;
        String departmentCode = "ABC";
        String colaboratorId = "123";

        Colaborator colaborator = new Colaborator();
        colaborator.setId(colaboratorId);

        Department expectedDepartment = new Department();
        expectedDepartment.setId(departmentId);
        expectedDepartment.setCode(departmentCode);

        when(colaboratorRepository.findById(colaboratorId)).thenReturn(Optional.of(colaborator));
        when(departmentRepository.save(any(Department.class))).thenReturn(expectedDepartment);

        // Act
        Department result = departmentService.createDeparmentWithExistingColaborator(departmentId, departmentCode, colaboratorId);

        // Assert
        assertEquals(expectedDepartment, result);
        verify(colaboratorDepartmentRepository, times(1)).save(any(ColaboratorDepartment.class));
    }

    @Test
    void testGetAllDepartments() {
        // Arrange
        when(departmentRepository.findAll()).thenReturn(List.of(new Department(), new Department()));

        // Act
        List<Department> result = departmentService.getAll();

        // Assert
        assertEquals(2, result.size());
    }
}
