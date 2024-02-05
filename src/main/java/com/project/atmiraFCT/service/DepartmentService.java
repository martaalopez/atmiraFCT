package com.project.atmiraFCT.service;

import com.project.atmiraFCT.model.domain.*;
import com.project.atmiraFCT.repository.ColaboratorDepartmentRepository;
import com.project.atmiraFCT.repository.ColaboratorRepository;
import com.project.atmiraFCT.repository.DepartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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


    public Department createDeparmentWithExistingColaborator(Long id,String code, String colaboratorId) {

        Colaborator colaborator = colaboratorRepository.findById(colaboratorId)
                .orElseThrow(() -> new RuntimeException("Colaborator not found"));

        Department department = new Department();
        department.setId(id);
        department.setCode(code);

        Department savedDepartment= departmentRepository.save(department);

        ColaboratorDepartment colaboratorDepartment = new ColaboratorDepartment(savedDepartment, colaborator);

        colaboratorDepartmentRepository.save(colaboratorDepartment);
        savedDepartment.getColaborators().add(colaborator);

        return savedDepartment;
    }

}
