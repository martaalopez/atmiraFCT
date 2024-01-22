package com.project.atmiraFCT.repository;

import com.project.atmiraFCT.model.Project;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectRepository  extends JpaRepository<Project, Long> {
}
