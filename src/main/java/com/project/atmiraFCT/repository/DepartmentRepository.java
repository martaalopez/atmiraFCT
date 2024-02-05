package com.project.atmiraFCT.repository;

import com.project.atmiraFCT.model.domain.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DepartmentRepository  extends JpaRepository<Department,Long> {

}
