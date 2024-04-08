package com.project.atmiraFCT.controller;

import com.project.atmiraFCT.model.domain.Department;
import com.project.atmiraFCT.repository.DepartmentRepository;
import com.project.atmiraFCT.service.DepartmentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(DepartmentController.class)
class DepartmentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DepartmentService departmentService;

    @MockBean
    private DepartmentRepository departmentRepository;

    @InjectMocks
    private DepartmentController departmentController;

    private Department department;

    @BeforeEach
    void setUp() {
        department = new Department();
        department.setId(1L);
        department.setCode("ABC123");
    }

    @Test
    @Order(1)
    void saveDepartment() throws Exception {
        String colaboratorId = "1";
        String requestBody = "{\"id\":1,\"code\":\"ABC123\"}";

        when(departmentService.createDeparmentWithExistingColaborator(any(), any(), any())).thenReturn(department);

        ResultActions result = mockMvc.perform(post("/deparment/save/colaboratorId={colaboratorId}", colaboratorId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody));

        result.andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.code").value("ABC123"));
    }

    @Test
    @Order(2)
    void getAllDepartments() throws Exception {
        List<Department> departmentList = new ArrayList<>();
        departmentList.add(department);

        when(departmentRepository.findAll()).thenReturn(departmentList);

        ResultActions result = mockMvc.perform(get("/department/all"));

        result.andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].code").value("ABC123"));
    }
}
