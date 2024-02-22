package com.project.atmiraFCT.service;

import com.project.atmiraFCT.exception.RecordNotFoundException;
import com.project.atmiraFCT.model.domain.Colaborator;
import com.project.atmiraFCT.model.domain.Project;
import com.project.atmiraFCT.model.domain.Task;
import com.project.atmiraFCT.repository.ColaboratorRepository;
import com.project.atmiraFCT.repository.ProjectRepository;
import com.project.atmiraFCT.repository.TaskRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.core.io.Resource;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.*;

@Service
public class TaskService  implements StorageService{

    public List<Task> getTasksByProjectId(String projectId) {
        return taskRepository.findByProjectId(projectId);
    }

    @Value("${media.location}")
    private String mediaLocation;

    private Path rootLocation;

    @Autowired
    private TaskRepository taskRepository;
    @Autowired
    private ColaboratorRepository colaboratorRepository;
    @Autowired
    private ProjectRepository projectRepository;

    public Task saveTask( String description, String objective, Boolean isClosed,String colaboratorId, String projectId) {
        Optional<Colaborator> colaboratorOptional = colaboratorRepository.findById(colaboratorId);
        Optional<Project> projectOptional = projectRepository.findById(projectId);

        if (colaboratorOptional.isPresent() && projectOptional.isPresent()) {
            Task task = new Task();
            task.setDescription(description);
            task.setObjective(objective);
            task.setClosed(isClosed);
            task.setColaborator(colaboratorOptional.get());
            task.setProject(projectOptional.get());

            String idCode = generateTaskIdCode(projectOptional.get());
            task.setIdCode(idCode);

            Task savedTask = taskRepository.save(task);
            return savedTask;
        } else {
            throw new RecordNotFoundException("Colaborator or project not found");
        }
    }

    public Task saveSubTask(String description, String objective, Boolean isClosed, String colaboratorId, String projectId, String parentTaskIdCode) {
        Optional<Colaborator> colaboratorOptional = colaboratorRepository.findById(colaboratorId);
        Optional<Project> projectOptional = projectRepository.findById(projectId);

        if (colaboratorOptional.isPresent() && projectOptional.isPresent()) {

            if (!isValidParentTaskIdCodeFormat(parentTaskIdCode)) {
                throw new IllegalArgumentException("Invalid parentTaskIdCode format: " + parentTaskIdCode);
            }

            String[] ids = parentTaskIdCode.split("_");
            Long parentProjectId = Long.parseLong(ids[0]);
            Long parentTaskId = Long.parseLong(ids[1]);

            int nextSubTaskNumber = getNextSubTaskNumber(parentTaskIdCode);

            Task subTask = new Task();
            subTask.setDescription(description);
            subTask.setObjective(objective);
            subTask.setClosed(isClosed);
            subTask.setColaborator(colaboratorOptional.get());
            subTask.setProject(projectOptional.get());


            String subTaskIdCode = parentProjectId + "_" + parentTaskId + "_" + nextSubTaskNumber;
            subTask.setIdCode(subTaskIdCode);

            Task parentTask = taskRepository.findByIdCode(parentTaskIdCode).orElseThrow(() -> new RecordNotFoundException("Parent task not found"));


            subTask.setTask(parentTask);


            Task savedSubTask = taskRepository.save(subTask);

            List<Task> subTasks = parentTask.getSubtareas();

            subTasks.add(savedSubTask);

            parentTask.setSubtareas(subTasks);

            taskRepository.save(parentTask);

            return savedSubTask;
        } else {
            throw new RecordNotFoundException("Colaborator or project not found");
        }
    }

    private String generateTaskIdCode(Project project) {

        int numberOfTasks = project.getTasks().size() + 1;

        return project.getId_code() + "_" + numberOfTasks;
    }

    private boolean isValidParentTaskIdCodeFormat(String parentTaskIdCode) {
        String[] parts = parentTaskIdCode.split("_");
        return parts.length == 2;
    }

    public int getNextSubTaskNumber(String parentTaskIdCode) {

        Task parentTask = taskRepository.findByIdCode(parentTaskIdCode)
                .orElseThrow(() -> new RecordNotFoundException("Parent task not found"));

        List<Task> subTasks = parentTask.getSubtareas();

        int nextSubTaskNumber = subTasks.size() + 1;

        return nextSubTaskNumber;
    }

    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    public Task getTaskById(String id) {
        Optional<Task> task = taskRepository.findById(id);
        if (task.isPresent()) {
            return task.get();
        } else {
            throw new RecordNotFoundException("No task found with id: " + id);
        }
    }

    /*mostrar tareas por proyecto*/
    public Task getTaskByProjectIdAndTaskIdCode(String projectId, String taskIdCode) {
        Optional<Task> taskOptional = taskRepository.findByProjectIdAndTaskIdCode(projectId, taskIdCode);
        return taskOptional.orElseThrow(() -> new RecordNotFoundException("Task not found"));
    }

    public void deleteTask(String id) {
        Optional<Task> result = taskRepository.findById(id);
        if (result.isPresent()) {
            taskRepository.deleteById(id);
        } else {
            throw new RecordNotFoundException("No task found with id: " + id);
        }
    }

    public List<Task> getTasksSubtaskByColaborator(String colaboratorId) {
        Optional<Colaborator> colaboratorOptional = colaboratorRepository.findById(colaboratorId);
        if (colaboratorOptional.isPresent()) {
            return taskRepository.findByColaborator(colaboratorOptional.get());
        } else {
            throw new RecordNotFoundException("Colaborator not found with id: " + colaboratorId);
        }
    }

    /*obtiene las subtareas de una tarea*/

    public List<Task> getSubTasksByPrefix(String prefix) {
        return taskRepository.findSubtasksByParentTaskId(prefix);
    }

    /*actualizar el atributo isClosed de una tarea y una subtarea*/

    public Task updateTask(String taskId, boolean isClosed) {
        Optional<Task> taskOptional = taskRepository.findById(taskId);
        if (taskOptional.isPresent()) {
            Task task = taskOptional.get();
            if (task.getClosed() == isClosed) {
                throw new IllegalArgumentException("You cannot set the task status to the same value");
            }
            task.setClosed(isClosed);
            return taskRepository.save(task); // Guardar los cambios y devolver la tarea actualizada
        } else {
            throw new RecordNotFoundException("Task not found with id: " + taskId);
        }
    }

    public List<Task> getTasksByProject(String projectId) {
        Optional<Project> projectOptional = projectRepository.findById(projectId);

        if (projectOptional.isPresent()) {
            // Obtener el proyecto
            Project project = projectOptional.get();

            // Buscar las tareas que cumplen con la estructura deseada
            List<Task> taskParent = taskRepository.findAllTasks(project.getId_code());

            // Devolver las tareas relacionadas con el proyecto
            return taskParent;
        } else {
            throw new RecordNotFoundException("Project not found with id: " + projectId);
        }
    }

    public List<Task> getTasksByColaboratorAndProject(String colaboratorId, String projectId) {
        Optional<Colaborator> colaboratorOptional = colaboratorRepository.findById(colaboratorId);
        Optional<Project> projectOptional = projectRepository.findById(projectId);

        if (colaboratorOptional.isPresent() && projectOptional.isPresent()) {
            return taskRepository.findByColaboratorAndProject(colaboratorOptional.get(), projectOptional.get());
        } else {
            throw new RecordNotFoundException("Colaborator or project not found");
        }
    }


    public List<Task> getTasksByColaborator(Colaborator colaboratorId) {
            return  taskRepository.findAllTasksByColaborator(colaboratorId);
    }

    @Override
    @PostConstruct
    public void init() throws IOException {
        rootLocation = Paths.get(mediaLocation);
        Files.createDirectories(rootLocation);
    }

    @Override
    public String store(MultipartFile file) {
        try{
            if (file.isEmpty()) {
                throw new RuntimeException("Faited to store entity file.");
            }
            String filename =file.getOriginalFilename();
            Path destinationFile =rootLocation.resolve(Paths.get(filename))
                    .normalize().toAbsolutePath();
            try (InputStream inputStream =file.getInputStream()) {
                Files.copy(inputStream, destinationFile, StandardCopyOption.REPLACE_EXISTING);
            }
            return filename;
        } catch (IOException e) {
            throw new RuntimeException("Failed to store file", e);
        }
    }


    /*recuperar un archivo a partir de su nombre*/
    @Override
    public Resource loadAsResource(String filename) {
        try {
            Path file = rootLocation.resolve(filename);
            Resource resource = new UrlResource(file.toUri());

            if (resource.exists() && resource.isReadable()) {
                return resource;

            } else {
                throw new RuntimeException("Could not read file: " + filename);
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException("Could not read file: " + filename, e);
        }
    }






}


