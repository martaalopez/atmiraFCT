package com.project.atmiraFCT.repository;

import com.project.atmiraFCT.model.domain.Colaborator;
import com.project.atmiraFCT.model.domain.Project;
import com.project.atmiraFCT.model.domain.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TaskRepository extends JpaRepository<Task, String> {
    List<Task> findByColaborator(Colaborator colaborator);

    List<Task> findByProject(Project project);

    List<Task> findByColaboratorAndProject(Colaborator colaborator, Project project);


    Optional<Task> findByIdCode(String idCode);

    @Query("SELECT u FROM Task u WHERE u.tareaPrincipal IS NULL")
    List<Task> findAllTasks(String startNumber);


    @Query("SELECT u FROM Task u WHERE u.tareaPrincipal IS NOT NULL")
    List<Task> findSubtasksByParentTaskId(String parentTaskId);

    List<Task> findByParentTaskId(String parentTaskId);


}
