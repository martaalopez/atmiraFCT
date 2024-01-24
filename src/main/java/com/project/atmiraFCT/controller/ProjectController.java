package com.project.atmiraFCT.controller;

import com.project.atmiraFCT.model.domain.Project;
import com.project.atmiraFCT.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
public class ProjectController {

    @Autowired
    private ProjectService service;

    @GetMapping("/projects")
    public List<Project> getProjects() {
        return service.getAllProjects();
    }

    @PostMapping("/projects")
    public Project saveProject(@RequestBody Project project) {
        return service.saveProject(project);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Project> getUserById(@PathVariable("id") Long id){
        Project project = service.getUserById(id);
        return ResponseEntity.ok(project);
    }

    @GetMapping("/projects/byColaborator")
    public List<Project> getProjectsByColaborator(@RequestParam("colaborator") String collaboratorName) {
        return service.getProjectsByColaborator(collaboratorName);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Project> updateProject(@PathVariable("id") Long id, @RequestBody Project updatedProject) {
        Project updated = service.createOrUpdateProject(updatedProject);
        return ResponseEntity.ok(updated);
    }

}
