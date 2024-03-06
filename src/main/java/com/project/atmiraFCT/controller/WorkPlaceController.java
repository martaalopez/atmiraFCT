package com.project.atmiraFCT.controller;

import com.project.atmiraFCT.model.domain.WorkPlace;
import com.project.atmiraFCT.repository.WorkPlaceRepository;
import com.project.atmiraFCT.service.WorkPlaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "https://fct-atmira-front.vercel.app:443")
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

    @PostMapping("/workplace/save")
    public ResponseEntity<WorkPlace> saveWorkPlace(@RequestBody WorkPlace workPlace) {
        workPlaceService.save(workPlace);
        return ResponseEntity.status(HttpStatus.CREATED).body(workPlace);
    }

    /**
     * Obtiene todos los lugares de trabajo.
     *
     * @return Lista de todos los lugares de trabajo.
     */

    @GetMapping("/workplace/all")
    public List<WorkPlace> getAll() {
        return workPlaceRepository.findAll();
    }

}
