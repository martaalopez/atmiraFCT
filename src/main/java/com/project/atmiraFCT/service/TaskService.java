package com.project.atmiraFCT.service;


import com.project.atmiraFCT.model.Project;
import com.project.atmiraFCT.model.Task;
import com.project.atmiraFCT.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    public void saveTask(Task task){
        taskRepository.save(task);
    }
    public void deleteTask(Long id_code){
        taskRepository.deleteById(id_code);
    }

    public List<Task> getTask(){
        return taskRepository.findAll();
    }


}
