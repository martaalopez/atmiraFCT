package com.project.atmiraFCT.service;


import com.project.atmiraFCT.model.domain.WorkPlace;
import com.project.atmiraFCT.repository.ExpenseRepository;
import com.project.atmiraFCT.repository.WorkPlaceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WorkPlaceService {

    @Autowired
    private WorkPlaceRepository workPlaceRepository;


    public void save(WorkPlace workPlace){
        workPlaceRepository.save(workPlace);
    }
    public List<WorkPlace> getAll(){
        return workPlaceRepository.findAll();
    }

}
