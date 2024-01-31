package com.project.atmiraFCT.service;

import com.project.atmiraFCT.controller.ColaboratorController;
import com.project.atmiraFCT.exception.RecordNotFoundException;
import com.project.atmiraFCT.model.domain.Colaborator;
import com.project.atmiraFCT.model.domain.ColaboratorProject;
import com.project.atmiraFCT.model.domain.Project;
import com.project.atmiraFCT.repository.ColaboratorRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
/*import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;*/
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ColaboratorService  {
    @Autowired
    private ColaboratorRepository colaboratorRepository;


    public Colaborator getColaboratorById(String colaboratorId) {
        return colaboratorRepository.findById(colaboratorId)
                .orElseThrow(() -> new EntityNotFoundException("Colaborator not found with id: " + colaboratorId));
    }

  /*  private final PasswordEncoder passwordEncoder;

    public ColaboratorService(ColaboratorRepository colaboratorRepository) {
        this.colaboratorRepository=colaboratorRepository;
        this.passwordEncoder =new BCryptPasswordEncoder();

    }*/

    public Colaborator saveColaborator(Colaborator colaborator) {
       /* String encoderPassword=this.passwordEncoder.encode(colaborator.getPassword());
        colaborator.setPassword(encoderPassword);*/
        return colaboratorRepository.save(colaborator);
    }

 /*   public void updatePassword(String id ,String password){
        Optional<Colaborator > result=colaboratorRepository.findById(id);
        if(result.isPresent()){
            Colaborator fromDB=result.get();
            fromDB.setPassword(password);
            colaboratorRepository.save(fromDB);
        }
    }
   /* public void login(String email,String password){
        Optional<Colaborator> result=colaboratorRepository.findByEmailAndPassword(email,password);
        if(result.isPresent()) {
            Colaborator fromDB = result.get();
            fromDB.getActive(true);
            colaboratorRepository.save(fromDB);
        }else{
            throw new RecordNotFoundException("No user found with email:"+email+" and password"+password);
        }
    }*/

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

   /* public Colaborator getUserByGmail(String gmail) {
        Optional<Colaborator> colaborator = colaboratorRepository.findByGmail(gmail);
        if(colaborator.isPresent()){
            return colaborator.get();
        }else{
            throw new RecordNotFoundException("No user found with gmail" +": " + gmail);
        }
    }*/

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
