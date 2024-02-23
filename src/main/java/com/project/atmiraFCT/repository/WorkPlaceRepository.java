package com.project.atmiraFCT.repository;

import com.project.atmiraFCT.model.domain.WorkPlace;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WorkPlaceRepository extends JpaRepository<WorkPlace, Long> {
}
