package com.project.atmiraFCT.controller;

import com.project.atmiraFCT.model.Project;
import com.project.atmiraFCT.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
/*@RequestMapping("/project")*/
public class ProjectController {

    @Autowired
    private ProjectService service;

    @GetMapping("/projectlist")
    public List<Project> projectList(){
        return service.getProjects();
    }

    @PostMapping("/project")
    public void saveProject( @RequestBody Project project){
        service.saveProject(project);
    }


}
