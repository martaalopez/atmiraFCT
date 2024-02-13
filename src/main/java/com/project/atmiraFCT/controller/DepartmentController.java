package com.project.atmiraFCT.controller;


import com.project.atmiraFCT.model.domain.Department;
import com.project.atmiraFCT.repository.DepartmentRepository;
import com.project.atmiraFCT.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class DepartmentController {

    @Autowired
    private DepartmentService departmentService;

    @Autowired
    private DepartmentRepository departmentRepository;


    @PostMapping("/deparment/save/colaboratorId={colaboratorId}")
    public ResponseEntity<Department> save(@PathVariable String colaboratorId, @RequestBody  Department deparment) {

        Department createdDeparment = departmentService.createDeparmentWithExistingColaborator(deparment.getId(),deparment.getCode(),colaboratorId);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdDeparment);
    }

    @GetMapping("/department/all")
    public List<Department> getAll() {
        return  departmentRepository.findAll();
    }

}
