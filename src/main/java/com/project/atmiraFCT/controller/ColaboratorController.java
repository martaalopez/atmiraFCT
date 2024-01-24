package com.project.atmiraFCT.controller;

import com.project.atmiraFCT.exception.RecordNotFoundException;
import com.project.atmiraFCT.model.Colaborator;
import com.project.atmiraFCT.repository.ColaboratorRepository;
import com.project.atmiraFCT.service.ColaboratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/colaborators")
public class ColaboratorController {

    @Autowired
    private ColaboratorRepository colaboratorRepository;

    @Autowired
    private ColaboratorService colaboratorService;

    @PostMapping("/save")
    public Colaborator saveColaborator(@RequestBody Colaborator colaborator) {
        return colaboratorRepository.save(colaborator);
    }

    @GetMapping("/all")
    public List<Colaborator> getAllColaborators() {
        return colaboratorRepository.findAll();
    }

    @GetMapping("/{id}")
    public Colaborator getColaboratorById(@PathVariable String id) {
        Optional<Colaborator> colaborator = colaboratorRepository.findById(id);
        if (colaborator.isPresent()) {
            return colaborator.get();
        } else {
            throw new RecordNotFoundException("No colaborator found with id: " + id);
        }
    }

    @DeleteMapping("/{id}")
    public void deleteColaborator(@PathVariable String id) {
        Optional<Colaborator> result = colaboratorRepository.findById(id);
        if (result.isPresent()) {
            colaboratorRepository.deleteById(id);
        } else {
            throw new RecordNotFoundException("No colaborator found with id: " + id);
        }
    }

    @PostMapping("/createOrUpdate")
    public Colaborator createOrUpdateColaborator(@RequestBody Colaborator colaborator) {
        return colaboratorService.createOrUpdateColaborator(colaborator);
    }
}
