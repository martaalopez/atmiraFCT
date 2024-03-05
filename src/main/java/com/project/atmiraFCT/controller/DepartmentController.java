package com.project.atmiraFCT.controller;

import com.project.atmiraFCT.model.domain.Department;
import com.project.atmiraFCT.repository.DepartmentRepository;
import com.project.atmiraFCT.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "https://fct-atmira-front.vercel.app:443")
@RestController
public class DepartmentController {

    @Autowired
    private DepartmentService departmentService;

    @Autowired
    private DepartmentRepository departmentRepository;

    /**
     * Guarda un nuevo departamento asociado a un colaborador existente.
     *
     * @param colaboratorId El ID del colaborador existente al que se asociará el nuevo departamento.
     * @param department    El objeto Department a guardar.
     * @return              El departamento creado.
     */
    @CrossOrigin
    @PostMapping("/deparment/save/colaboratorId={colaboratorId}")
    public ResponseEntity<Department> save(@PathVariable String colaboratorId, @RequestBody Department department) {
        Department createdDepartment = departmentService.createDeparmentWithExistingColaborator(department.getId(), department.getCode(), colaboratorId);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdDepartment);
    }

    /**
     * Obtiene todos los departamentos.
     *
     * @return Lista de todos los departamentos.
     */
    @CrossOrigin
    @GetMapping("/department/all")
    public List<Department> getAll() {
        return departmentRepository.findAll();
    }

}
