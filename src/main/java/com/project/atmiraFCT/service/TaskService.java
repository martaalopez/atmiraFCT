package com.project.atmiraFCT.service;

import com.project.atmiraFCT.exception.RecordNotFoundException;
import com.project.atmiraFCT.model.domain.Colaborator;
import com.project.atmiraFCT.model.domain.Project;
import com.project.atmiraFCT.model.domain.Task;
import com.project.atmiraFCT.repository.ColaboratorRepository;
import com.project.atmiraFCT.repository.ProjectRepository;
import com.project.atmiraFCT.repository.TaskRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;
    @Autowired
    private ColaboratorRepository colaboratorRepository;
    @Autowired
    private ProjectRepository projectRepository;


    public Task saveTaskexistingProyectColaborator(String description,String objective,Boolean isClosed,Long task,String colaboratorId,Long projectId) {
    Optional<Colaborator> colaborator=colaboratorRepository.findById(colaboratorId);
    Optional<Project> project=projectRepository.findById(projectId);
    if(colaborator.isPresent() && project.isPresent()){
        Task task1=new Task();
        task1.setDescription(description);
        task1.setObjective(objective);
        task1.setClosed(isClosed);
      task1.setTask(task);
      task1.setColaborator(colaborator.get());
      task1.setProject(project.get());
        Task savedTask = taskRepository.save(task1);
            return savedTask;
    }else{
            throw new RecordNotFoundException("Colaborator or project not found ");
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


}
