package com.project.atmiraFCT.controller;

import com.project.atmiraFCT.model.domain.WorkPlace;
import com.project.atmiraFCT.repository.ExpenseRepository;
import com.project.atmiraFCT.repository.WorkPlaceRepository;
import com.project.atmiraFCT.service.ExpenseService;
import com.project.atmiraFCT.service.WorkPlaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class WorkPlaceController {

    @Autowired
    private WorkPlaceRepository workPlaceRepository;

    @Autowired
    private WorkPlaceService workPlaceService;



}
