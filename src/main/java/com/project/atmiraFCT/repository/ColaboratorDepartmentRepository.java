package com.project.atmiraFCT.repository;

import com.project.atmiraFCT.model.domain.ColaboratorDepartment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ColaboratorDepartmentRepository  extends JpaRepository<ColaboratorDepartment,Long> {
}
