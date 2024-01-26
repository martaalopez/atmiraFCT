package com.project.atmiraFCT.service;

import com.project.atmiraFCT.exception.RecordNotFoundException;
import com.project.atmiraFCT.model.domain.Colaborator;
import com.project.atmiraFCT.repository.ColaboratorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class ColaboratorService  {
    @Autowired
    private ColaboratorRepository colaboratorRepository;

    public Colaborator saveColaborator(Colaborator colaborator) {
        return colaboratorRepository.save(colaborator);
    }

    public List<Colaborator> getAllColaborators() {
        return colaboratorRepository.findAll();
    }

    public Colaborator getUserById(String id) {
        Optional<Colaborator> colaborator = colaboratorRepository.findById(id);
        if(colaborator.isPresent()){
            return colaborator.get();
        }else{
            throw new RecordNotFoundException("No user found with id: " + id);
        }
    }

    public void deleteColaborator(String id) {
        Optional<Colaborator> result = colaboratorRepository.findById(id);
        if(result.isPresent()){
            colaboratorRepository.deleteById(id);
        }else{
            throw new RecordNotFoundException("No user found with id: " + id);
        }
    }
    public Colaborator createOrUpdateColaborator(Colaborator colaborator) {
        Colaborator end;

        if (colaborator.getId_alias() != null) { // Update
            Optional<Colaborator> result = colaboratorRepository.findById(colaborator.getId_alias());
            if (result.isPresent()) {
                Colaborator fromDB = result.get();
                fromDB.setEmail(colaborator.getEmail());
                fromDB.setActive(colaborator.getActive());
                fromDB.setRelaseDate(colaborator.getRelaseDate());
                fromDB.setHours(colaborator.getHours());
                fromDB.setGuards(colaborator.getGuards());
                fromDB.setExpense(colaborator.getExpense());
                fromDB.setName(colaborator.getName());
                fromDB.setSurname(colaborator.getSurname());
                fromDB.setPassword(colaborator.getPassword());


                end = colaboratorRepository.save(fromDB);
            } else {
                throw new RecordNotFoundException("No colaborator found with id: " + colaborator.getId_alias());
            }
        } else { // Insert
            end = colaboratorRepository.save(colaborator);
        }

        return end;
    }


}
