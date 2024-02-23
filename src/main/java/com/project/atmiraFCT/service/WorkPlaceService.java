package com.project.atmiraFCT.service;

import com.project.atmiraFCT.model.domain.WorkPlace;
import com.project.atmiraFCT.repository.WorkPlaceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;


@Service
public class WorkPlaceService {

    @Autowired
    private WorkPlaceRepository workPlaceRepository;

    /**
     * Guarda un lugar de trabajo en la base de datos.
     *
     * @param workPlace El lugar de trabajo a guardar.
     */
    public void save(WorkPlace workPlace){
        workPlaceRepository.save(workPlace);
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
