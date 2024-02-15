package com.project.atmiraFCT.controller;

import com.project.atmiraFCT.model.domain.WorkPlace;
import com.project.atmiraFCT.repository.WorkPlaceRepository;
import com.project.atmiraFCT.service.WorkPlaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class WorkPlaceController {

    @Autowired
    private WorkPlaceRepository workPlaceRepository;

    @Autowired
    private WorkPlaceService workPlaceService;

    @PostMapping("/workplace/save")
    public ResponseEntity<WorkPlace> saveWorkPlace(@RequestBody WorkPlace workPlace) {
        workPlaceService.save(workPlace);
        return ResponseEntity.status(HttpStatus.CREATED).body(workPlace);
    }

    @GetMapping("/workplace/all")
    public List<WorkPlace> getAll() {
        return  workPlaceRepository.findAll();
    }

}
