package com.project.atmiraFCT.service;

import com.project.atmiraFCT.exception.RecordNotFoundException;
import com.project.atmiraFCT.model.Enum.TypeOfService;
import com.project.atmiraFCT.model.domain.Colaborator;
import com.project.atmiraFCT.model.domain.ColaboratorProject;
import com.project.atmiraFCT.model.domain.DTO.ProjectDTO;
import com.project.atmiraFCT.model.domain.Project;
import com.project.atmiraFCT.repository.ColaboratorProjectRepository;
import com.project.atmiraFCT.repository.ColaboratorRepository;
import com.project.atmiraFCT.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class ProjectService {


    private final ProjectRepository projectRepository;
    private final ColaboratorRepository colaboratorRepository;
    private final ColaboratorProjectRepository colaboratorProjectRepository;

    @Autowired
    public ProjectService(ProjectRepository projectRepository,
                          ColaboratorRepository colaboratorRepository,
                          ColaboratorProjectRepository colaboratorProjectRepository) {
        this.projectRepository = projectRepository;
        this.colaboratorRepository = colaboratorRepository;
        this.colaboratorProjectRepository = colaboratorProjectRepository;
    }
    /**
     * Obtiene todos los proyectos.
     *
     * @return La lista de todos los proyectos.
     */
    public List<Project> getAllProjects() {
        return projectRepository.findAll();
    }

    /**
     * Obtiene un proyecto por su ID.
     *
     * @param id El ID del proyecto.
     * @return El proyecto encontrado.
     * @throws RecordNotFoundException Si no se encuentra el proyecto.
     */
    public ProjectDTO getProjectById(String id) {
        Optional<Project> project = projectRepository.findById(id);
        if (project.isPresent()) {
            return new ProjectDTO().convertToDto(project.get());
        } else {
            throw new RecordNotFoundException("No user found with id: " + id);
        }
    }

    /**
     * Elimina un proyecto por su ID.
     *
     * @param id El ID del proyecto a eliminar.
     * @throws RecordNotFoundException Si no se encuentra el proyecto.
     */
    public void deleteProject(String id) {
        Optional<Project> result = projectRepository.findById(id);
        if (result.isPresent()) {
            projectRepository.deleteById(id);
        } else {
            throw new RecordNotFoundException("No user found with id: " + id);
        }
    }

    /**
     * Actualiza un proyecto existente.
     *
     * @param id           El ID del proyecto a actualizar.
     * @param updateProject El proyecto actualizado.
     * @return El proyecto actualizado.
     * @throws Exception Si no se encuentra el proyecto.
     */
    public Project updateProject(String id, Project updateProject) throws Exception {
        Optional<Project> result = projectRepository.findById(id);
        if (result.isPresent()) {
            Project project = result.get();
            project.setName(updateProject.getName());
            project.setTypeOfService(updateProject.getTypeOfService());
            project.setInitialDate(updateProject.getInitialDate());
            project.setEndDate(updateProject.getEndDate());
            project.setActive(updateProject.getActive());
            return projectRepository.save(project);
        } else {
            throw new Exception("No project found with id" + id);
        }
    }

    /**
     * Obtiene los proyectos asociados a un colaborador por su ID.
     *
     * @param colaboratorId El ID del colaborador.
     * @return La lista de proyectos asociados al colaborador.
     * @throws RecordNotFoundException Si no se encuentra el colaborador.
     */
    public List<ProjectDTO> getProjectsByColaboratorId(String colaboratorId) {
        Optional<Colaborator> colaborator = colaboratorRepository.findById(colaboratorId);

        if (colaborator.isPresent()) {
            List<ColaboratorProject> colaboratorProjects = colaborator.get().getColaboratorProjects();
            List<ProjectDTO> projects = colaboratorProjects.stream()
                    .map(ColaboratorProject::getProject)
                    .map(project -> new ProjectDTO().convertToDto(project)) // Convierte Project a ProjectDTO
                    .collect(Collectors.toList());
            return projects;
        } else {
            throw new RecordNotFoundException("No collaborator found with id: " + colaboratorId);
        }
    }


    /**
     * Crea un proyecto con un colaborador existente.
     *
     * @param id_code     El código del proyecto.
     * @param projectName El nombre del proyecto.
     * @param initialDate La fecha de inicio del proyecto.
     * @param endDate     La fecha de fin del proyecto.
     * @param active      El estado del proyecto.
     * @param typeOfService El tipo de servicio del proyecto.
     * @param colaboratorId El ID del colaborador asociado al proyecto.
     * @return El proyecto creado.
     */
    public Project createProjectWithExistingColaborator(String id_code, String projectName, Date initialDate, Date endDate, Boolean active, TypeOfService typeOfService, String colaboratorId) {

        Colaborator colaborator = colaboratorRepository.findById(colaboratorId)
                .orElseThrow(() -> new RuntimeException("Colaborator not found"));

        Project project = new Project();
        String idCode = generateProjectIdCode();
        project.setId_code(idCode);
        project.setName(projectName);
        project.setInitialDate(initialDate);
        project.setEndDate(endDate);
        project.setActive(active);
        project.setTypeOfService(typeOfService);

        Project savedProject = projectRepository.save(project);

        ColaboratorProject colaboratorProject = new ColaboratorProject(savedProject, colaborator);

        colaboratorProjectRepository.save(colaboratorProject);
        if(savedProject.getColaboratorProjects() == null){
            savedProject.setColaboratorProjects(new ArrayList<>());
            savedProject.getColaboratorProjects().add(colaboratorProject);
        }else{
            savedProject.getColaboratorProjects().add(colaboratorProject);
        }

        return savedProject;
    }

    /**
     * Genera un código de proyecto único.
     *
     * @return El código generado.
     */
    private String generateProjectIdCode() {
        for (int i = 0; i < 100; i++) {
            String idCode = String.valueOf(i);
            if (projectRepository.findById(idCode).isEmpty()) {
                return idCode;
            }
        }
        return null;

    }

    /**
     * @deprecated  YA  NO ES NECESARIO
     * @param id
     * @return
     */
    public List<Colaborator> getColaboratorsByProject(String id) {
        Optional<Project> project = projectRepository.findById(id);
        if (project.isPresent()) {
            List<ColaboratorProject> colaboratorProjects = project.get().getColaboratorProjects();
            List<Colaborator> colaborators = colaboratorProjects.stream()
                    .map(ColaboratorProject::getColaborator)
                    .collect(Collectors.toList());
            return colaborators;
        } else {
            throw new RecordNotFoundException("No project found with id: " + id);
        }
    }
}
