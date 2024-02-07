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
import java.util.List;
import java.util.Optional;

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



   public Task saveTaskexistingProyectColaborator(String description, String objective, Boolean isClosed,
                                                   Long taskValue, String colaboratorId, Long projectId) {
        Optional<Colaborator> colaboratorOptional = colaboratorRepository.findById(colaboratorId);
        Optional<Project> projectOptional = projectRepository.findById(projectId);

        if (colaboratorOptional.isPresent() && projectOptional.isPresent()) {
            Task task = new Task();
            task.setDescription(description);
            task.setObjective(objective);
            task.setClosed(isClosed);
            task.setTask(taskValue);
            task.setColaborator(colaboratorOptional.get());
            task.setProject(projectOptional.get());

            Task savedTask = taskRepository.save(task);
            return savedTask;
        } else {
            throw new RecordNotFoundException("Colaborator or project not found");
        }
    }


    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    public Task getTaskById(Long id) {
        Optional<Task> task = taskRepository.findById(id);
        if (task.isPresent()) {
            return task.get();
        } else {
            throw new RecordNotFoundException("No task found with id: " + id);
        }
    }

    public void deleteTask(Long id) {
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

    public List<Task> getTasksByProject(Long projectId) {
        Optional<Project> projectOptional = projectRepository.findById(projectId);
        if (projectOptional.isPresent()) {
            return taskRepository.findByProject(projectOptional.get());
        } else {
            throw new RecordNotFoundException("Project not found with id: " + projectId);
        }
    }

    public List<Task> getTasksByColaboratorAndProject(String colaboratorId, Long projectId) {
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
    public jakarta.annotation.Resource loadAsResource(String filename) {
        try {
            Path file = rootLocation.resolve(filename);
            Resource resource = new UrlResource(file.toUri());

            if (resource.exists() && resource.isReadable()) { // Verifica existencia y legibilidad
                return (jakarta.annotation.Resource) resource;
            } else {
                throw new RuntimeException("Could not read file: " + filename);
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException("Could not read file: " + filename, e);
        }
    }

}
