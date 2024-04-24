package com.project.atmiraFCT.service;

import com.project.atmiraFCT.model.domain.*;
import com.project.atmiraFCT.repository.ColaboratorRepository;
import com.project.atmiraFCT.repository.WorkPlaceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;


@Service
public class WorkPlaceService {

    @Autowired
    private WorkPlaceRepository workPlaceRepository;

    private  final ColaboratorRepository colaboratorRepository;

    public WorkPlaceService(ColaboratorRepository colaboratorRepository) {
        this.colaboratorRepository = colaboratorRepository;
    }





    public WorkPlace createWorkPlacWithExistingColaborator(Long id, String code, String colaboratorId) {
        Colaborator colaborator = colaboratorRepository.findById(colaboratorId)
                .orElseThrow(() -> new RuntimeException("Colaborator not found"));

        WorkPlace workPlace = new WorkPlace();
        workPlace.setId(id);
        workPlace.setCode(code);
        workPlace.getColaborators().add(colaborator);

        return workPlaceRepository.save(workPlace);
    }


    /**
     * Obtiene todos los lugares de trabajo almacenados en la base de datos.
     *
     * @return La lista de todos los lugares de trabajo.
     */
    public List<WorkPlace> getAll(){
        return workPlaceRepository.findAll();
    }
}
