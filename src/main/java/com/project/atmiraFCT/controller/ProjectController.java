package com.project.atmiraFCT.controller;

import com.project.atmiraFCT.exception.RecordNotFoundException;
import com.project.atmiraFCT.exception.Validator;
import com.project.atmiraFCT.model.domain.Colaborator;
import com.project.atmiraFCT.model.domain.DTO.ProjectDTO;
import com.project.atmiraFCT.model.domain.Project;
import com.project.atmiraFCT.repository.ProjectRepository;
import com.project.atmiraFCT.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "https://fct-atmira-front.vercel.app:443")
@RestController
public class ProjectController {

    @Autowired
    private ProjectService service;
    @Autowired
    private ProjectService projectService;

    @Autowired
    private ProjectRepository projectRepository;

    /**
     * Obtiene un proyecto por su ID.
     *
     * @param id    El ID del proyecto a obtener.
     * @return      ResponseEntity con el proyecto encontrado.
     */
    @CrossOrigin(origins = "${Front_URL}")
    @GetMapping("/project/list/{id}")
    public ResponseEntity<ProjectDTO> getProjectById(@PathVariable String id) {
        ProjectDTO project = projectService.getProjectById(id);
        return ResponseEntity.ok(project);
    }
    private ProjectDTO convertToDto(Project project) {
        return new ProjectDTO().convertToDto(project);
    }

    /**
     * Actualiza un proyecto existente.
     *
     * @param id                El ID del proyecto a actualizar.
     * @param updatedProject    El proyecto actualizado.
     * @return                  ResponseEntity con el proyecto actualizado.
     * @throws Exception        Si ocurre algún error durante el proceso de actualización.
     */
    @CrossOrigin(origins = "${Front_URL}")
    @PutMapping("/projects/{id}")
    public ResponseEntity<Project> updateProject(@PathVariable("id") String id, @RequestBody Project updatedProject) throws Exception {
        Project updated = service.updateProject(id,updatedProject);
        return ResponseEntity.ok(updated);
    }

    /**
     * Elimina un proyecto por su ID.
     *
     * @param id    El ID del proyecto a eliminar.
     * @return      ResponseEntity indicando el éxito de la operación.
     *              Devuelve true si el proyecto se eliminó correctamente, false en caso contrario.
     * @throws RecordNotFoundException   Si no se encuentra ningún proyecto con el ID proporcionado.
     */
    @CrossOrigin(origins = "${Front_URL}")
    @DeleteMapping("/project/delete/{id}")
    public ResponseEntity<Boolean> deleteProject(@PathVariable String id) {
        Optional<Project> result = projectRepository.findById(id);
        if (result.isPresent()) {
            projectService.deleteProject(id);
            return ResponseEntity.ok(true);
        } else {
            throw new RecordNotFoundException("No project found with id: " + id);
        }
    }
    /**
     * Obtiene todos los proyectos asociados a un colaborador.
     *
     * @param colaboratorId     El ID del colaborador.
     * @return                  Lista de proyectos asociados al colaborador.
     */
    @CrossOrigin(origins = "${Front_URL}")
    @GetMapping("/colaborator/{colaboratorId}/projects")
    public List<ProjectDTO> getProjectsByColaboratorId(@PathVariable String colaboratorId) {
        return projectService.getProjectsByColaboratorId(colaboratorId);
    }

    /**
     * Guarda un nuevo proyecto asociado a un colaborador existente.
     *
     * @param colaboratorId     El ID del colaborador existente asociado al proyecto.
     * @param project           El objeto Project a ser guardado.
     * @return                  ResponseEntity con el proyecto creado.
     */
    @CrossOrigin(origins = "${Front_URL}")
    @PostMapping("/project/save/colaboratorId/{colaboratorId}")
    public ResponseEntity<Project> save(@PathVariable String colaboratorId, @RequestBody Project project) {
        LocalDate initialDate = project.getInitialDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate endDate = project.getEndDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();


        if (!Validator.isInitialDateValid(initialDate)) {
            throw new IllegalArgumentException("La fecha inicial debe ser el día de hoy o posterior.");
        }


        if (!Validator.isEndDateValid(initialDate, endDate)) {
            throw new IllegalArgumentException("La fecha de finalización no puede ser anterior a la fecha inicial.");
        }

        Project createdProject = projectService.createProjectWithExistingColaborator(project.getId_code(),project.getName(), project.getInitialDate(),project.getEndDate(),project.getActive(),project.getTypeOfService(),colaboratorId);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdProject);
    }
    @CrossOrigin(origins = "${Front_URL}")
    @GetMapping("/project/{id}/colaborators")
    public List<Colaborator> getColaboratorsByProject(@PathVariable String id) {
        return projectService.getColaboratorsByProject(id);
    }
}