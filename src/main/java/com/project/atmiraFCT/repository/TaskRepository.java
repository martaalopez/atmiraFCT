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

/**
 * Repositorio para la entidad Task que gestiona las operaciones de base de datos.
 */
@Repository
public interface TaskRepository extends JpaRepository<Task, String> {


    @Query("SELECT t FROM Task t WHERE t.project.id_code like :projectId and t.task IS NULL")
    List<Task> findByProjectId(@Param("projectId") String projectId);


    List<Task> findByColaborator(Colaborator colaborator);


    List<Task> findByColaboratorAndProject(Colaborator colaborator, Project project);


    Optional<Task> findByIdCode(String idCode);


    @Query("SELECT u FROM Task u WHERE u.task IS NULL")
    List<Task> findAllTasks(String startNumber);


    @Query(value = "SELECT * FROM Task t WHERE  t.task LIKE :prefix",nativeQuery = true)
    List<Task> findSubtasksByParentTaskId(@Param("prefix") String prefix);


    @Query("SELECT u FROM Task u WHERE u.colaborator = :colaborator AND  u.task IS NULL")
    List<Task> findAllTasksByColaborator(Colaborator colaborator);


    @Query("SELECT t FROM Task t WHERE t.project.id_code = :projectId AND t.idCode = :taskIdCode")
    Optional<Task> findByProjectIdAndTaskIdCode(@Param("projectId") String projectId, @Param("taskIdCode") String taskIdCode);

}
