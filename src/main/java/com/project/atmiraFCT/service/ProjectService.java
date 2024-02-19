package com.project.atmiraFCT.service;

import com.project.atmiraFCT.exception.RecordNotFoundException;
import com.project.atmiraFCT.model.Enum.TypeOfService;
import com.project.atmiraFCT.model.domain.Colaborator;
import com.project.atmiraFCT.model.domain.ColaboratorProject;
import com.project.atmiraFCT.model.domain.Project;
import com.project.atmiraFCT.repository.ColaboratorProjectRepository;
import com.project.atmiraFCT.repository.ColaboratorRepository;
import com.project.atmiraFCT.repository.ProjectRepository;
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


    public List<Project> getAllProjects() {
        return projectRepository.findAll();
    }

    public Project getProjectById(String id) {
        Optional<Project> project = projectRepository.findById(id);
        if (project.isPresent()) {
            return project.get();
        } else {
            throw new RecordNotFoundException("No user found with id: " + id);
        }
    }

    public void deleteProject(String id) {
        Optional<Project> result = projectRepository.findById(id);
        if (result.isPresent()) {
            projectRepository.deleteById(id);
        } else {
            throw new RecordNotFoundException("No user found with id: " + id);
        }
    }
public Project updateProject(String id,Project updateProject) throws Exception {
        Optional<Project> result=projectRepository.findById(id);
        if(result.isPresent()){
            Project project=result.get();
            project.setName(updateProject.getName());
            project.setTypeOfService(updateProject.getTypeOfService());
            project.setInitialDate(updateProject.getInitialDate());
            project.setEndDate(updateProject.getEndDate());
            project.setActive(updateProject.getActive());
            return projectRepository.save(project);
        }
        else{
            throw new Exception("No project found with id"+id);
        }
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

    public Project createProjectWithExistingColaborator(String id_code,String projectName, Date initialDate, Date endDate, Boolean active, TypeOfService typeOfService, String colaboratorId) {

        Colaborator colaborator = colaboratorRepository.findById(colaboratorId)
                .orElseThrow(() -> new RuntimeException("Colaborator not found"));

        Project project = new Project();
        String idCode = generateProjectIdCode();
        project.setId_code(idCode);
        project.setName(projectName);
        project.setInitialDate(initialDate);
        project.setEndDate(endDate);
        project .setActive(active);
        project.setTypeOfService(typeOfService);

        Project savedProject = projectRepository.save(project);

        ColaboratorProject colaboratorProject = new ColaboratorProject(savedProject, colaborator);

     colaboratorProjectRepository.save(colaboratorProject);
        savedProject.getColaboratorProjects().add(colaboratorProject);

        return savedProject;
    }

    private String generateProjectIdCode() {
        for(int i=0;i<100;i++){
            String idCode = String.valueOf(i);
            if(projectRepository.findById(idCode).isEmpty()){
                return idCode;
            }
        }
        return null;

    }



}



