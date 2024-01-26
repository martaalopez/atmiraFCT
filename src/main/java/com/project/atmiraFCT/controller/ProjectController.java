package com.project.atmiraFCT.controller;

import com.project.atmiraFCT.exception.RecordNotFoundException;
import com.project.atmiraFCT.model.domain.Project;
import com.project.atmiraFCT.repository.ProjectRepository;
import com.project.atmiraFCT.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;

@RestController
public class ProjectController {

    @Autowired
    private ProjectService service;
    @Autowired
    private ProjectRepository projectRepository;

    @GetMapping("/projectlist")
    public List<Project> getProjects() {
        return service.getAllProjects();
    }

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


    /*EN PROCESO*/
    @GetMapping("/projects/byColaborator")
    public List<Project> getProjectsByColaborator(@RequestParam("colaborator") String collaboratorName) {
        return service.getProjectsByColaborator(collaboratorName);
    }

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


}
