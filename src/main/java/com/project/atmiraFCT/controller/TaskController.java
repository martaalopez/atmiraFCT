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

    @PostMapping("/task/save/{colaboratorId}/{identifier}")
    public ResponseEntity<Task> saveTask(
            @RequestBody Task task,
            @PathVariable String colaboratorId,
            @PathVariable String identifier
    ) {
        Task savedTask;
        if (identifier.contains("_")) {
            String[] parts = identifier.split("_");
            if (parts.length != 2) {
                throw new IllegalArgumentException("Invalid identifier format: " + identifier);
            }
            String projectId = parts[0];
            String parentTaskIdCode = parts[0]+"_"+parts[1];


            savedTask = taskService.saveSubTask(
                    task.getDescription(),
                    task.getObjective(),
                    task.getClosed(),
                    colaboratorId,
                    projectId,
                    parentTaskIdCode
            );
        }  else {
            // Si no contiene una barra baja, se trata de una tarea principal
            savedTask = taskService.saveTask(
                    task.getDescription(),
                    task.getObjective(),
                    task.getClosed(),
                    colaboratorId,
                    identifier
            );
        }

        return new ResponseEntity<>(savedTask, HttpStatus.CREATED);
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

    @GetMapping("/task/byColaborator/{colaboratorId}")
    public ResponseEntity<List<Task>> getTasksByColaborator(@PathVariable String colaboratorId) {
        List<Task> tasks = taskService.getTasksByColaborator(colaboratorId);
        return ResponseEntity.ok(tasks);
    }

    @GetMapping("/task/byProject/{projectId}")
    public ResponseEntity<List<Task>> getTasksByProject(@PathVariable String projectId) {
        List<Task> tasks = taskService.getTasksByProject(projectId);
        return ResponseEntity.ok(tasks);
    }

    @GetMapping("/task/bySubTask/{taskId}")
    public ResponseEntity<List<Task>> getSubTasksByParentTask(@PathVariable String taskId) {
        List<Task> tasks = taskService.getSubTasksByPrefix(taskId);
        return ResponseEntity.ok(tasks);
    }

    @GetMapping("/task/byColaboratorAndProject/{colaboratorId}/{projectId}")
    public ResponseEntity<List<Task>> getTasksByColaboratorAndProject(
            @PathVariable String colaboratorId,
            @PathVariable String projectId
    ) {
        List<Task> tasks = taskService.getTasksByColaboratorAndProject(colaboratorId, projectId);
        return ResponseEntity.ok(tasks);
    }

}
