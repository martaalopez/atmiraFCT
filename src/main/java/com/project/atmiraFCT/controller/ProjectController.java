    package com.project.atmiraFCT.controller;

    import com.project.atmiraFCT.exception.RecordNotFoundException;
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

    @CrossOrigin(origins = "http://localhost:4200")
    @RestController
    public class ProjectController {

        @Autowired
        private ProjectService service;
        @Autowired
        private ProjectRepository projectRepository;

        @Autowired
        private ProjectService projectService;



        @GetMapping("/project/list/{id}")
        public ResponseEntity<Project> getProjectById(@PathVariable("id") Long id){
            Project project = service.getProjectById(id);
            return ResponseEntity.ok(project);
        }


        @PutMapping("/projects/{id}")
        public ResponseEntity<Project> updateProject(@PathVariable("id") Long id, @RequestBody Project updatedProject) throws Exception {
            Project updated = service.updateProject(id,updatedProject);
            return ResponseEntity.ok(updated);
        }


        @DeleteMapping("/project/delete/{id}")
        public ResponseEntity<Boolean> deleteProject(@PathVariable Long id) {
            Optional<Project> result = projectRepository.findById(id);
            if (result.isPresent()) {
                projectRepository.deleteById(id);
                return ResponseEntity.ok(true);
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

        @GetMapping("/colaborator/{colaboratorId}/projects")
        public List<Project> getProjectsByColaboratorId(@PathVariable String colaboratorId) {
            return projectService.getProjectsByColaboratorId(colaboratorId);
        }

        @PostMapping("/project/save/colaboratorId/{colaboratorId}")
       public ResponseEntity<Project> save(@PathVariable String colaboratorId, @RequestBody Project project) {
            LocalDate today = LocalDate.now();
            LocalDate initialDate = project.getInitialDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            LocalDate endDate = project.getEndDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

            if (initialDate.isBefore(today)) {
                throw new IllegalArgumentException("La fecha inicial debe ser el día de hoy o posterior.");
            }

            if (endDate.isBefore(initialDate)) {
                throw new IllegalArgumentException("La fecha de finalización no puede ser anterior a la fecha inicial.");
            }

            Project createdProject = projectService.createProjectWithExistingColaborator(project.getName(), project.getInitialDate(),project.getEndDate(),project.getActive(),project.getTypeOfService(),colaboratorId);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdProject);
        }

    }
