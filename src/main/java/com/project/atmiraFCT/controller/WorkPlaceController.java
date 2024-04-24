package com.project.atmiraFCT.controller;

import com.project.atmiraFCT.model.domain.WorkPlace;
import com.project.atmiraFCT.repository.WorkPlaceRepository;
import com.project.atmiraFCT.service.WorkPlaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "${Front_URL}")
@RestController
public class WorkPlaceController {

    @Autowired
    private WorkPlaceRepository workPlaceRepository;

    @Autowired
    private WorkPlaceService workPlaceService;

    /**
     * Guarda un nuevo lugar de trabajo.
     *
     * @param workPlace El objeto WorkPlace a guardar.
     * @return          El lugar de trabajo creado.
     */
    @CrossOrigin(origins = "${Front_URL}")
    @PostMapping("/workplace/save/colaboratorId={id}")
    public ResponseEntity<WorkPlace> saveWorkPlace(@PathVariable String id,@RequestBody WorkPlace workPlace) {
        WorkPlace savedWorkPlace = workPlaceService.createWorkPlacWithExistingColaborator(workPlace.getId(), workPlace.getCode(), id);
        return ResponseEntity.status(HttpStatus.CREATED).body(workPlace);
    }

    /**
     * Obtiene todos los lugares de trabajo.
     *
     * @return Lista de todos los lugares de trabajo.
     */
    @CrossOrigin(origins = "${Front_URL}")
    @GetMapping("/workplace/all")
    public List<WorkPlace> getAll() {
        return workPlaceRepository.findAll();
    }

}