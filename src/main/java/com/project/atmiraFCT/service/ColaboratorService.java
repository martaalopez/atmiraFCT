package com.project.atmiraFCT.service;

import com.project.atmiraFCT.exception.RecordNotFoundException;
import com.project.atmiraFCT.model.domain.Colaborator;
import com.project.atmiraFCT.model.domain.WorkPlace;
import com.project.atmiraFCT.repository.ColaboratorRepository;
import com.project.atmiraFCT.repository.WorkPlaceRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class ColaboratorService  {
    @Autowired
    private ColaboratorRepository colaboratorRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    private WorkPlaceRepository workplaceRepository;

    private final AuthenticationManager authenticationManager;


    public Colaborator getColaboratorById(String colaboratorId) {
        return colaboratorRepository.findById(colaboratorId)
                .orElseThrow(() -> new EntityNotFoundException("Colaborator not found with id: " + colaboratorId));
    }

    public ColaboratorService(ColaboratorRepository colaboratorRepository,AuthenticationManager authenticationManager) {
        this.colaboratorRepository=colaboratorRepository;
        this.authenticationManager = authenticationManager;
        this.passwordEncoder =new BCryptPasswordEncoder();

    }

   public Colaborator saveColaborator(Colaborator colaborator, Long workplaceId) {
        WorkPlace workplace = workplaceRepository.findById(workplaceId)
                .orElseThrow(() -> new RecordNotFoundException("Workplace not found with id: " + workplaceId));
        colaborator.setWorkPlace(workplace);
        colaborator.setPassword(passwordEncoder.encode(colaborator.getPassword())); // Codificar la contraseña con BCrypt
        return colaboratorRepository.save(colaborator);
    }


    public void updatePassword(String id ,String password){
        Optional<Colaborator > result=colaboratorRepository.findById(id);
        if(result.isPresent()){
            Colaborator fromDB=result.get();
            fromDB.setPassword(password);
            colaboratorRepository.save(fromDB);
        }
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
    public Colaborator updateColaborator(String id,Colaborator colaborator) throws Exception {
      Optional<Colaborator> result=colaboratorRepository.findById(id);
      if(result.isPresent()){
         Colaborator fromDB=result.get();
         fromDB.setEmail(colaborator.getEmail());
         fromDB.setRelaseDate(colaborator.getRelaseDate());
          fromDB.setHours(colaborator.getHours());
          fromDB.setName(colaborator.getName());
          fromDB.setSurname(colaborator.getSurname());
          fromDB.setExpense(colaborator.getExpense());
          fromDB.setActive(colaborator.getActive());
          fromDB.setGuards(colaborator.getGuards());

          return colaboratorRepository.save(fromDB);
    }
        else{
        throw new Exception("No project found with id"+id);
    }
}



}



