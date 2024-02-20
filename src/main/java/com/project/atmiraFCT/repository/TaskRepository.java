package com.project.atmiraFCT.repository;

import com.project.atmiraFCT.model.domain.Colaborator;
import com.project.atmiraFCT.model.domain.Project;
import com.project.atmiraFCT.model.domain.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

@Repository
public interface TaskRepository extends JpaRepository<Task, String> {
    List<Task> findByColaborator(Colaborator colaborator);

    List<Task> findByProject(Project project);

    List<Task> findByColaboratorAndProject(Colaborator colaborator, Project project);


    Optional<Task> findByIdCode(String idCode);

    @Query("SELECT u FROM Task u WHERE u.task IS NULL")
    List<Task> findAllTasks(String startNumber);

    @Query("SELECT t FROM Task t WHERE t.idCode LIKE CONCAT(:prefix, '_%')")
    List<Task> findSubtasksByParentTaskId(@Param("prefix") String prefix);

    /*List<Task> findByParentTaskId(String parentTaskId);*/


   /* @Query("SELECT MAX(CAST(SUBSTRING(t.id_code, LENGTH(t.id_code) - LOCATE('_', REVERSE(t.id_code)) + 2, LENGTH(t.id_code)) AS integer)) FROM Task t WHERE t.task = :parentTaskIdCode")
    Integer findMaxSubTaskNumber(@Param("parentTaskIdCode") String parentTaskIdCode);*/

}
