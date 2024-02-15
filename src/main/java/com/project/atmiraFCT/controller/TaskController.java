package com.project.atmiraFCT.controller;

import com.project.atmiraFCT.exception.RecordNotFoundException;
import com.project.atmiraFCT.model.domain.Task;
import com.project.atmiraFCT.repository.ColaboratorRepository;
import com.project.atmiraFCT.repository.ProjectRepository;
import com.project.atmiraFCT.repository.TaskRepository;
import com.project.atmiraFCT.service.StorageService;
import com.project.atmiraFCT.service.TaskService;
import jakarta.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class TaskController {

    private final StorageService storageService;
    private final HttpServletRequest request;

    @Autowired
    private TaskService taskService;
    @Autowired
    private ColaboratorRepository colaboratorRepository;
    @Autowired
    private ProjectRepository projectRepository;
    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    public TaskController(StorageService storageService, HttpServletRequest request, TaskService taskService,
                          ColaboratorRepository colaboratorRepository, ProjectRepository projectRepository,
                          TaskRepository taskRepository) {
        this.storageService = storageService;
        this.request = request;
        this.taskService = taskService;
        this.colaboratorRepository = colaboratorRepository;
        this.projectRepository = projectRepository;
        this.taskRepository = taskRepository;
    }

    @PostMapping("/media/upload")
    public Map<String, String> uploadFile(@RequestParam("file") MultipartFile multipartFile) {
        String path = storageService.store(multipartFile);
        String host = request.getRequestURL().toString().replace(request.getRequestURI(), "");
        String url = ServletUriComponentsBuilder
                .fromHttpUrl(host)
                .path("/media/")
                .path(path)
                .toUriString();
        return Map.of("url", url);
    }

    @GetMapping("/media/{filename:.+}")
    public ResponseEntity<Resource> getFile(@PathVariable String filename) throws IOException {
        Resource file = (Resource) storageService.loadAsResource(filename);
        String contentType=Files.probeContentType(file.getFile().toPath());

        return ResponseEntity
                .ok()
                .header(HttpHeaders.CONTENT_TYPE, contentType)
                .body(file);
    }


    public TaskController(StorageService storageService, HttpServletRequest request) {
        this.storageService = storageService;
        this.request = request;
    }

    @GetMapping("/task/all")
    public List<Task> getTasks() {
        return taskService.getAllTasks();
    }


    @PostMapping("/task/save/{colaboratorId}/{projectId}")
    public ResponseEntity<Task> createProjectWithExistingProjectColaborator(
            @PathVariable String colaboratorId,
            @PathVariable Long projectId,
            @RequestBody Task task
    ) {
        Task createdTask = taskService.saveTaskexistingProyectColaborator(
                task.getDescription(),
                task.getObjective(),
                task.getClosed(),
                colaboratorId,
                projectId
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(createdTask);
    }

    @PostMapping("/task/save/{colaboratorId}/{projectId}/{taskid}")
    public ResponseEntity<Task> createsubTaskWithExistingProjectColaboratorTask(
            @PathVariable String colaboratorId,
            @PathVariable Long projectId,
            @PathVariable String taskid,
            @RequestBody Task task
    ) {
        Task createdTask = taskService.saveSubTaskexistingProyectColaboratorTask(
                task.getDescription(),
                task.getObjective(),
                task.getClosed(),
                colaboratorId,
                projectId,
                taskid
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(createdTask);
    }


    @GetMapping("task/{id}")
    public ResponseEntity<Task> getTaskById(@PathVariable("id") String id) {
        Task task = taskService.getTaskById(id);
        return ResponseEntity.ok(task);
    }
    @DeleteMapping("taskDelete/{id}")
    public ResponseEntity<Boolean> deleteTask(@PathVariable String id) {
        try {
            taskService.deleteTask(id);
            return ResponseEntity.ok(true);
        } catch (RecordNotFoundException e) {
            throw new RecordNotFoundException("No task found with id: " + id);
        }
    }


   /* @PutMapping("/task/{id}")
    public ResponseEntity<Task> updateTask(@PathVariable("id") Long id, @RequestBody Task updatedTask) {
        Task updated = taskService.createOrUpdateTask(updatedTask);
        return ResponseEntity.ok(updated);
    }*/

    @GetMapping("/task/byColaborator/{colaboratorId}")
    public ResponseEntity<List<Task>> getTasksByColaborator(@PathVariable String colaboratorId) {
        List<Task> tasks = taskService.getTasksByColaborator(colaboratorId);
        return ResponseEntity.ok(tasks);
    }

    @GetMapping("/task/byProject/{projectId}")
    public ResponseEntity<List<Task>> getTasksByProject(@PathVariable Long projectId) {
        List<Task> tasks = taskService.getTasksByProject(projectId);
        return ResponseEntity.ok(tasks);
    }

    @GetMapping("/task/byColaboratorAndProject/{colaboratorId}/{projectId}")
    public ResponseEntity<List<Task>> getTasksByColaboratorAndProject(
            @PathVariable String colaboratorId,
            @PathVariable Long projectId
    ) {
        List<Task> tasks = taskService.getTasksByColaboratorAndProject(colaboratorId, projectId);
        return ResponseEntity.ok(tasks);
    }

}
