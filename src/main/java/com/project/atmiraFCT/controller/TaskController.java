package com.project.atmiraFCT.controller;

import com.project.atmiraFCT.exception.RecordNotFoundException;
import com.project.atmiraFCT.model.domain.Colaborator;
import com.project.atmiraFCT.model.domain.Project;
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

@CrossOrigin(origins = "${Front_URL}")
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



    // Constructor con inyección de dependencias
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

    /**
     * Obtiene todas las tareas asociadas a un proyecto específico.
     *
     * @param projectId El ID del proyecto del que se desean obtener las tareas.
     * @return Lista de tareas asociadas al proyecto especificado.
     */
    @CrossOrigin(origins = "${Front_URL}")
    @GetMapping("/tasks/{projectId}")
    public ResponseEntity<List<Task>> getTasksByProjectId(@PathVariable String projectId) {
        List<Task> tasks = taskService.getTasksByProjectId(projectId);
        return ResponseEntity.ok(tasks);
    }

    /**
     * Endpoint para subir un archivo.
     *
     * @param multipartFile Archivo a subir.
     * @return Mapa que contiene la URL del archivo subido.
     */

    @CrossOrigin(origins = "${Front_URL}")
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

    /**
     * Obtiene un archivo por su nombre de archivo.
     *
     * @param filename Nombre del archivo.
     * @return El archivo como un recurso.
     * @throws IOException Si ocurre un error al cargar el archivo.
     */
    @CrossOrigin(origins = "${Front_URL}")
    @GetMapping("/media/{filename:.+}")
    public ResponseEntity<Resource> getFile(@PathVariable String filename) throws IOException {
        Resource file = (Resource) storageService.loadAsResource(filename);
        String contentType = Files.probeContentType(file.getFile().toPath());

        return ResponseEntity
                .ok()
                .header(HttpHeaders.CONTENT_TYPE, contentType)
                .body(file);
    }

    /**
     * Obtiene todas las tareas existentes.
     *
     * @return Lista de todas las tareas.
     */

    @CrossOrigin(origins = "${Front_URL}")
    @GetMapping("/task/all")
    public List<Task> getTasks() {
        return taskService.getAllTasks();
    }

    /**
     * Guarda una nueva tarea o sub-tarea.
     *
     * @param task          La tarea o sub-tarea a guardar.
     * @param colaboratorId El ID del colaborador asociado a la tarea.
     * @param identifier    El identificador de la tarea (puede ser el ID del proyecto o el ID de la tarea padre).
     * @return La tarea guardada.
     */

    @CrossOrigin(origins = "${Front_URL}")
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
            String parentTaskIdCode = parts[0] + "_" + parts[1];

            savedTask = taskService.saveSubTask(
                    task.getDescription(),
                    task.getObjective(),
                    task.getClosed(),
                    colaboratorId,
                    projectId,
                    parentTaskIdCode
            );
        } else {
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

    /**
     * Actualiza el estado de una tarea.
     *
     * @param taskId   ID de la tarea a actualizar.
     * @param isClosed Nuevo estado de la tarea (cerrada o abierta).
     * @return La tarea actualizada.
     */

    @CrossOrigin(origins = "${Front_URL}")
    @PutMapping("task/{taskId}/{isClosed}")
    public ResponseEntity<Task> updateTaskStatus(
            @PathVariable String taskId,
            @PathVariable boolean isClosed) {
        Task updatedTask = taskService.updateTask(taskId, isClosed);
        return ResponseEntity.ok(updatedTask);
    }

    /**
     * Obtiene una tarea por su ID.
     *
     * @param id ID de la tarea.
     * @return La tarea correspondiente al ID especificado.
     */

    @CrossOrigin(origins = "${Front_URL}")
    @GetMapping("task/{id}")
    public ResponseEntity<Task> getTaskById(@PathVariable("id") String id) {
        Task task = taskService.getTaskById(id);
        return ResponseEntity.ok(task);
    }

    /**
     * Elimina una tarea por su ID.
     *
     * @param id ID de la tarea a eliminar.
     * @return True si la tarea fue eliminada con éxito, False en caso contrario.
     */

    @CrossOrigin(origins = "${Front_URL}")
    @DeleteMapping("taskDelete/{id}")
    public ResponseEntity<Boolean> deleteTask(@PathVariable String id) {
        try {
            taskService.deleteTask(id);
            return ResponseEntity.ok(true);
        } catch (RecordNotFoundException e) {
            throw new RecordNotFoundException("No task found with id: " + id);
        }
    }

    /**
     * Obtiene todas las tareas asociadas a un colaborador.
     *
     * @param colaboratorId ID del colaborador.
     * @return Lista de tareas asociadas al colaborador.
     */
    @CrossOrigin(origins = "${Front_URL}")
    @GetMapping("/task/byColaborator/{colaboratorId}")
    public ResponseEntity<List<Task>> getTasksByColaborator(@PathVariable Colaborator colaboratorId) {
        List<Task> tasks = taskService.getTasksByColaborator(colaboratorId);
        return ResponseEntity.ok(tasks);
    }

    /**
     * Obtiene todas las sub-tareas asociadas a un colaborador.
     *
     * @param colaboratorId ID del colaborador.
     * @return Lista de sub-tareas asociadas al colaborador.
     */

    @CrossOrigin(origins = "${Front_URL}")
    @GetMapping("/taskAndSubtask/byColaborator/{colaboratorId}")
    public ResponseEntity<List<Task>> getSubTasksByColaborator(@PathVariable String colaboratorId) {
        List<Task> tasks = taskService.getTasksSubtaskByColaborator(colaboratorId);
        return ResponseEntity.ok(tasks);
    }

    /**
     * Obtiene todas las tareas asociadas a un proyecto.
     *
     * @param projectId ID del proyecto.
     * @return Lista de tareas asociadas al proyecto.
     */
    @CrossOrigin(origins = "${Front_URL}")
    @GetMapping("/task/byProject/{projectId}")
    public ResponseEntity<List<Task>> getTasksByProject(@PathVariable String projectId) {
        List<Task> tasks = taskService.getTasksByProject(projectId);
        return ResponseEntity.ok(tasks);
    }

    /**
     * Obtiene todas las sub-tareas asociadas a una tarea principal.
     *
     * @param taskId ID de la tarea principal.
     * @return Lista de sub-tareas asociadas a la tarea principal.
     */

    @CrossOrigin(origins = "${Front_URL}")
    @GetMapping("/task/bySubTask/{taskId}")
    public ResponseEntity<List<Task>> getSubTasksByParentTask(@PathVariable String taskId) {
        List<Task> tasks = taskService.getSubTasksByPrefix(taskId);
        return ResponseEntity.ok(tasks);
    }

    /**
     * Obtiene todas las tareas asociadas a un colaborador y un proyecto específicos.
     *
     * @param colaboratorId ID del colaborador.
     * @param projectId     ID del proyecto.
     * @return Lista de tareas asociadas al colaborador y proyecto especificados.
     */

    @CrossOrigin(origins = "${Front_URL}")
    @GetMapping("/task/byColaboratorAndProject/{colaboratorId}/{projectId}")
    public ResponseEntity<List<Task>> getTasksByColaboratorAndProject(
            @PathVariable String colaboratorId,
            @PathVariable String projectId
    ) {
        List<Task> tasks = taskService.getTasksByColaboratorAndProject(colaboratorId, projectId);
        return ResponseEntity.ok(tasks);
    }
    @CrossOrigin(origins = "${Front_URL}")
    @PutMapping("/task/{taskId}/colaborator/{colaboratorId}")
    public ResponseEntity<Task> assignTaskToColaborator(
            @PathVariable String taskId, @PathVariable String colaboratorId) {
        Task task = taskService.assignTaskToColaborator(taskId, colaboratorId);
        return ResponseEntity.ok(task);
    }


    /**
     * Actualiza una tarea  existente.
     *
     * @param id                El ID del task a actualizar.
     * @param updatedTask     La tarea actualizada.
     * @return                  ResponseEntity con el task actualizado.
     * @throws Exception        Si ocurre algún error durante el proceso de actualización.
     */
    @CrossOrigin(origins = "${Front_URL}")
    @PutMapping("/task/update/{id}")
    public ResponseEntity<Task> updatetask(@PathVariable String id, @RequestBody Task updatedTask) throws Exception {
        Task updated = taskService.updateTask(id, updatedTask);
        return ResponseEntity.ok(updated);
    }

}
