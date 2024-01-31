package com.project.atmiraFCT.controller;

import com.project.atmiraFCT.exception.RecordNotFoundException;
import com.project.atmiraFCT.model.domain.Colaborator;
import com.project.atmiraFCT.model.domain.Project;
import com.project.atmiraFCT.repository.ProjectRepository;
import com.project.atmiraFCT.service.ProjectService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;
@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class ProjectController {

    @Autowired
    private ProjectService service;
    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private ProjectService projectService;

 /*listar proyecto por personas*/



    @PostMapping("/project/save")
    public ResponseEntity<Project> saveProject(@RequestBody Project project) {
        LocalDate today = LocalDate.now();
        LocalDate initialDate = project.getInitialDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate endDate = project.getEndDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

        if (initialDate.isBefore(today)) {
            throw new IllegalArgumentException("La fecha inicial debe ser el día de hoy o posterior.");
        }

        if (endDate.isBefore(initialDate)) {
            throw new IllegalArgumentException("La fecha de finalización no puede ser anterior a la fecha inicial.");
        }

        Project savedProject = service.saveProject(project);
        return ResponseEntity.ok(savedProject);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Project> getUserById(@PathVariable("id") Long id){
        Project project = service.getUserById(id);
        return ResponseEntity.ok(project);
    }



  /************/
  /*  @GetMapping("/projects/byColaborator")
    public List<Project> getProjectsByColaborator(@RequestParam("colaborator") String collaboratorName) {
        return service.getProjectsByColaborator(collaboratorName);
    }*/

    @PutMapping("/projects/{id}")
    public ResponseEntity<Project> updateProject(@PathVariable("id") Long id, @RequestBody Project updatedProject) {
        Project updated = service.createOrUpdateProject(updatedProject);
        return ResponseEntity.ok(updated);
    }


    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteProject(@PathVariable Long id) {
        Optional<Project> result = projectRepository.findById(id);
        if (result.isPresent()) {
            projectRepository.deleteById(id);
            return ResponseEntity.ok("Project with ID " + id + " has been deleted.");
        } else {
            throw new RecordNotFoundException("No project found with id: " + id);
        }
    }


 /*   @GetMapping("/user/{id_alias}/{id_code}")
    public ResponseEntity<Page<Project>> getProjectsByUserId(
            @PathVariable("id_alias") String id_alias,
            @PathVariable("id_code") Long id_code,
            Pageable pageable,
            @RequestParam(required = false) String name
    ) {
        if (name != null) {
            Page<Project> projects = service.getProjectsByName(name, pageable);
            return new ResponseEntity<>(projects, HttpStatus.OK);
        }

        Page<Project> projects = service.getProjectsByUserId(id_alias, id_code, pageable);
        return new ResponseEntity<>(projects, HttpStatus.OK);
    }*/

    @GetMapping("/collaborator/{colaboratorId}/projects")
    public List<Project> getProjectsByColaboratorId(@PathVariable String colaboratorId) {
        return projectService.getProjectsByColaboratorId(colaboratorId);
    }


}
