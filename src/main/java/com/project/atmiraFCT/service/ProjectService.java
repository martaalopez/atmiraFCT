package com.project.atmiraFCT.service;


import com.project.atmiraFCT.model.Project;
import com.project.atmiraFCT.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectService {

    @Autowired
    private ProjectRepository projectRepository;


    public void saveProject(Project project){
        projectRepository.save(project);

    }

    public void deleteProject(Long id_code){
        projectRepository.deleteById(id_code);
    }

    public List<Project> getProjects(){
        return projectRepository.findAll();
    }
}
