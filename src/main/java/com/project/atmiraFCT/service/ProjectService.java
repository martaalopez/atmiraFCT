package com.project.atmiraFCT.service;

import com.project.atmiraFCT.exception.RecordNotFoundException;
import com.project.atmiraFCT.model.Enum.TypeOfService;
import com.project.atmiraFCT.model.domain.Colaborator;
import com.project.atmiraFCT.model.domain.ColaboratorProject;
import com.project.atmiraFCT.model.domain.Project;
import com.project.atmiraFCT.repository.ColaboratorProjectRepository;
import com.project.atmiraFCT.repository.ColaboratorRepository;
import com.project.atmiraFCT.repository.ProjectRepository;
import org.antlr.v4.runtime.misc.LogManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class ProjectService {


    private final ColaboratorRepository colaboratorRepository;

    @Autowired
    private ProjectRepository projectRepository;
    private final ColaboratorProjectRepository colaboratorProjectRepository;

    public ProjectService(ColaboratorRepository colaboratorRepository,
                          ColaboratorProjectRepository colaboratorProjectRepository) {
        this.colaboratorRepository = colaboratorRepository;

        this.colaboratorProjectRepository = colaboratorProjectRepository;
    }

    public Project saveProject(Project project) {
        return projectRepository.save(project);
    }

    public List<Project> getAllProjects() {
        return projectRepository.findAll();
    }

    public Project getUserById(Long id) {
        Optional<Project> project = projectRepository.findById(id);
        if (project.isPresent()) {
            return project.get();
        } else {
            throw new RecordNotFoundException("No user found with id: " + id);
        }
    }

    public void deleteProject(Long id) {
        Optional<Project> result = projectRepository.findById(id);
        if (result.isPresent()) {
            projectRepository.deleteById(id);
        } else {
            throw new RecordNotFoundException("No user found with id: " + id);
        }
    }


    public Project createOrUpdateProject(Project project) {
        Project end;

        if (project.getId_code() != null) { // Update
            Optional<Project> result = projectRepository.findById(project.getId_code());
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

    public Project createProjectWithExistingColaborator(String projectName, Date initialDate, Date endDate, Boolean active, TypeOfService typeOfService, String colaboratorId) {

        Colaborator colaborator = colaboratorRepository.findById(colaboratorId)
                .orElseThrow(() -> new RuntimeException("Colaborator not found"));

        Project project = new Project();
        project.setName(projectName);
        project.setInitialDate(initialDate);
        project.setEndDate(endDate);
        project .setActive(active);
        project.setTypeOfService(typeOfService);

        // Guardar el proyecto en la base de datos
        Project savedProject = projectRepository.save(project);

        // Crear la relación ColaboratorProject y asignar al proyecto y al colaborador
        ColaboratorProject colaboratorProject = new ColaboratorProject(savedProject, colaborator);

     colaboratorProjectRepository.save(colaboratorProject);
        // Asignar la relación al proyecto
        savedProject.getColaboratorProjects().add(colaboratorProject);

        return savedProject;
    }



}



