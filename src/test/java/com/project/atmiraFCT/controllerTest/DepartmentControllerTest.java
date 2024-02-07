package com.project.atmiraFCT.controllerTest;
import com.project.atmiraFCT.controller.DepartmentController;
import com.project.atmiraFCT.model.domain.Department;
import com.project.atmiraFCT.service.DepartmentService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DepartmentControllerTest {

    @Mock
    private DepartmentService departmentService;

    @InjectMocks
    private DepartmentController departmentController;

    @Test
    void save_shouldReturnCreatedStatusAndDepartment() {
        // Arrange
        Department mockedDepartment = new Department();
        mockedDepartment.setId(1L);
        mockedDepartment.setCode("TestCode");

        when(departmentService.createDeparmentWithExistingColaborator(any(), any(), any()))
                .thenReturn(mockedDepartment);

        // Act
        ResponseEntity<Department> response = departmentController.save("testColaboratorId", new Department());

        // Assert
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(mockedDepartment, response.getBody());
    }




}
