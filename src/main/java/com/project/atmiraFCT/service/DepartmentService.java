package com.project.atmiraFCT.service;

import com.project.atmiraFCT.model.domain.*;
import com.project.atmiraFCT.repository.ColaboratorDepartmentRepository;
import com.project.atmiraFCT.repository.ColaboratorRepository;
import com.project.atmiraFCT.repository.DepartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepartmentService {

    @Autowired
    private DepartmentRepository departmentRepository;

    private final ColaboratorRepository colaboratorRepository;

    private final ColaboratorDepartmentRepository colaboratorDepartmentRepository;

    public DepartmentService(ColaboratorRepository colaboratorRepository, ColaboratorDepartmentRepository colaboratorDepartmentRepository) {
        this.colaboratorRepository = colaboratorRepository;
        this.colaboratorDepartmentRepository = colaboratorDepartmentRepository;
    }

    /**
     * Crea un departamento con un colaborador existente.
     *
     * @param id             El ID del departamento.
     * @param code           El código del departamento.
     * @param colaboratorId  El ID del colaborador para asociar con el departamento.
     * @return               El departamento creado.
     * @throws RuntimeException Si el colaborador no se encuentra.
     */
    public Department createDeparmentWithExistingColaborator(Long id, String code, String colaboratorId) {
        Colaborator colaborator = colaboratorRepository.findById(colaboratorId)
                .orElseThrow(() -> new RuntimeException("Colaborator not found"));

        Department department = new Department();
        department.setId(id);
        department.setCode(code);

        Department savedDepartment = departmentRepository.save(department);

        ColaboratorDepartment colaboratorDepartment = new ColaboratorDepartment();
        colaboratorDepartment.setDepartment(savedDepartment);
        colaboratorDepartment.setColaborator(colaborator);

        colaboratorDepartmentRepository.save(colaboratorDepartment);

        return savedDepartment;
    }

    /**
     * Obtiene todos los departamentos.
     *
     * @return La lista de todos los departamentos.
     */
    public List<Department> getAll(){
        return departmentRepository.findAll();
    }
}
