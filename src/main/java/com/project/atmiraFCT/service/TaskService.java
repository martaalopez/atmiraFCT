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

    // Método para verificar el formato del parentTaskIdCode
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

    public void deleteTask(String id) {
        Optional<Task> result = taskRepository.findById(id);
        if (result.isPresent()) {
            taskRepository.deleteById(id);
        } else {
            throw new RecordNotFoundException("No task found with id: " + id);
        }
    }


    public List<Task> getTasksByColaborator(String colaboratorId) {
        Optional<Colaborator> colaboratorOptional = colaboratorRepository.findById(colaboratorId);
        if (colaboratorOptional.isPresent()) {
            return taskRepository.findByColaborator(colaboratorOptional.get());
        } else {
            throw new RecordNotFoundException("Colaborator not found with id: " + colaboratorId);
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
    public List<Task> getSubtasksByTaskId(String taskId) {
        return taskRepository.findByParentTaskId(taskId);
    }




    public List<Task> getSubtasksByParentTaskId(String parentTaskId) {
        Optional<Task> parentTaskOptional = taskRepository.findById(parentTaskId);

        if (parentTaskOptional.isPresent()) {
            Task parentTask = parentTaskOptional.get();

            // Obtener el prefijo del idCode de la tarea padre
            String prefix = parentTask.getIdCode();

            // Buscar las subtareas que cumplen con la estructura deseada
            List<Task> subtasks = taskRepository.findSubtasksByParentTaskId(prefix);

            return subtasks;
        } else {
            throw new RecordNotFoundException("Parent task not found with id: " + parentTaskId);
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


