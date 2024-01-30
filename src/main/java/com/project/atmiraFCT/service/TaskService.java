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


    public Task saveTask(Task task) {
        return taskRepository.save(task);
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

    public Task createOrUpdateTask(Task task) {
        Task resultado;

        if (task.getId_code() != null) { // Actualización
            Optional<Task> resultadoConsulta = taskRepository.findById(task.getId_code());
            if (resultadoConsulta.isPresent()) {
                Task desdeBD = resultadoConsulta.get();
                desdeBD.setDescription(task.getDescription());
                desdeBD.setObjective(task.getObjective());
                desdeBD.setClosed(task.getClosed());
                desdeBD.setTask(task.getTask());
                // Actualiza otros campos según sea necesario

                resultado = taskRepository.save(desdeBD);
            } else {
                throw new RecordNotFoundException("No se encontró ninguna tarea con id: " + task.getId_code());
            }
        } else { // Inserción
            resultado = taskRepository.save(task);
        }

        return resultado;
    }



}
