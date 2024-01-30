package com.project.atmiraFCT.service;

import com.project.atmiraFCT.exception.RecordNotFoundException;
import com.project.atmiraFCT.model.domain.Colaborator;
import com.project.atmiraFCT.model.domain.ColaboratorProject;
import com.project.atmiraFCT.model.domain.Project;
import com.project.atmiraFCT.repository.ColaboratorRepository;
import com.project.atmiraFCT.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class ProjectService {

    private final ColaboratorRepository colaboratorRepository;

    @Autowired
    private ProjectRepository projectRepository;

    public ProjectService(ColaboratorRepository colaboratorRepository) {
        this.colaboratorRepository = colaboratorRepository;
    }

    public Project saveProject(Project project) {
        return projectRepository.save(project);
    }

    public List<Project> getAllProjects() {
        return projectRepository.findAll();
    }

    public Project getUserById(Long id) {
        Optional<Project> project = projectRepository.findById(id);
        if(project.isPresent()){
            return project.get();
        }else{
            throw new RecordNotFoundException("No user found with id: " + id);
        }
    }

    public void deleteProject(Long id) {
        Optional<Project> result = projectRepository.findById(id);
        if(result.isPresent()){
            projectRepository.deleteById(id);
        }else{
            throw new RecordNotFoundException("No user found with id: " + id);
        }
    }


    public Project createOrUpdateProject(Project project) {
        Project end;

        if (project.getId_code() != null) { // Update
            Optional<Project> result = projectRepository.findById(project.getId_code());
            if (result.isPresent()) {
                Project fromDB = result.get();
                fromDB.setTypeOfService(project.getTypeOfService());
                fromDB.setName(project.getName());
                fromDB.setInitialDate(project.getInitialDate());
                fromDB.setEndDate(project.getEndDate());
                fromDB.setActive(project.getActive());
                // Update other fields as needed

                end = projectRepository.save(fromDB);
            } else {
                throw new RecordNotFoundException("No project found with id: " + project.getId_code());
            }
        } else { // Insert
            end = projectRepository.save(project);
        }

        return end;
    }

    public List<Project> getProjectsByColaboratorId(String colaboratorId) {
        Optional<Colaborator> colaborator = colaboratorRepository.findById(colaboratorId);

        if (colaborator.isPresent()) {
            List<ColaboratorProject> colaboratorProjects = colaborator.get().getColaboratorProjects();
            List<Project> projects = colaboratorProjects.stream()
                    .map(ColaboratorProject::getProject)
                    .collect(Collectors.toList());
            return projects;
        } else {
            throw new RecordNotFoundException("No collaborator found with id: " + colaboratorId);
        }
    }


}
