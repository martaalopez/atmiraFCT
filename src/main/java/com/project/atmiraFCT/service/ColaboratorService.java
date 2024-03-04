package com.project.atmiraFCT.service;

import com.project.atmiraFCT.exception.RecordNotFoundException;
import com.project.atmiraFCT.model.domain.Colaborator;
import com.project.atmiraFCT.model.domain.WorkPlace;
import com.project.atmiraFCT.repository.ColaboratorRepository;
import com.project.atmiraFCT.repository.WorkPlaceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;
import jakarta.persistence.EntityNotFoundException;
import java.util.Optional;

@Service
public class ColaboratorService {

    @Autowired
    private ColaboratorRepository colaboratorRepository;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    private WorkPlaceRepository workplaceRepository;

    private final AuthenticationManager authenticationManager;


    @Autowired
    public ColaboratorService(ColaboratorRepository colaboratorRepository, AuthenticationManager authenticationManager) {
        this.colaboratorRepository = colaboratorRepository;
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    /**
     * Obtiene un colaborador por su ID.
     *
     * @param colaboratorId El ID del colaborador a buscar.
     * @return El colaborador encontrado.
     * @throws EntityNotFoundException Si no se encuentra el colaborador.
     */
    public Colaborator getColaboratorById(String colaboratorId) {
        return colaboratorRepository.findById(colaboratorId)
                .orElseThrow(() -> new EntityNotFoundException("Colaborator not found with id: " + colaboratorId));
    }

    /**
     * Guarda un nuevo colaborador.
     *
     * @param colaborator El colaborador a guardar.
     * @param workplaceId El ID del lugar de trabajo asociado.
     * @return El colaborador guardado.
     * @throws RecordNotFoundException Si no se encuentra el lugar de trabajo asociado.
     */
    public Colaborator saveColaborator(Colaborator colaborator, Long workplaceId) {
        WorkPlace workplace = workplaceRepository.findById(workplaceId)
                .orElseThrow(() -> new RecordNotFoundException("Workplace not found with id: " + workplaceId));
        colaborator.setWorkPlace(workplace);
        colaborator.setPassword(passwordEncoder.encode(colaborator.getPassword())); // Codificar la contraseña con BCrypt
        return colaboratorRepository.save(colaborator);
    }

    /**
     * Actualiza la contraseña de un colaborador.
     *
     * @param id       El ID del colaborador.
     * @param password La nueva contraseña.
     */
    public void updatePassword(String id, String password) {
        Optional<Colaborator> result = colaboratorRepository.findById(id);
        if (result.isPresent()) {
            Colaborator fromDB = result.get();
            fromDB.setPassword(password);
            colaboratorRepository.save(fromDB);
        }
    }

    /**
     * Obtiene todos los colaboradores.
     *
     * @return Lista de todos los colaboradores.
     */
    public List<Colaborator> getAllColaborators() {
        return colaboratorRepository.findAll();
    }

    /**
     * Obtiene un colaborador por su ID.
     *
     * @param id El ID del colaborador.
     * @return El colaborador encontrado.
     * @throws RecordNotFoundException Si no se encuentra el colaborador.
     */
    public Colaborator getUserById(String id) {
        Optional<Colaborator> colaborator = colaboratorRepository.findById(id);
        if (colaborator.isPresent()) {
            return colaborator.get();
        } else {
            throw new RecordNotFoundException("No user found with id: " + id);
        }
    }

    /**
     * Elimina un colaborador por su ID.
     *
     * @param id El ID del colaborador a eliminar.
     * @throws RecordNotFoundException Si no se encuentra el colaborador.
     */
    public void deleteColaborator(String id) {
        Optional<Colaborator> result = colaboratorRepository.findById(id);
        if (result.isPresent()) {
            colaboratorRepository.deleteById(id);
        } else {
            throw new RecordNotFoundException("No user found with id: " + id);
        }
    }

    /**
     * Actualiza un colaborador.
     *
     * @param id          El ID del colaborador a actualizar.
     * @param colaborator El colaborador con los nuevos datos.
     * @return El colaborador actualizado.
     * @throws Exception Si no se encuentra el colaborador.
     */
    public Colaborator updateColaborator(String id, Colaborator colaborator) throws Exception {
        Optional<Colaborator> result = colaboratorRepository.findById(id);
        if (result.isPresent()) {
            Colaborator fromDB = result.get();
            fromDB.setEmail(colaborator.getEmail());
            fromDB.setRelaseDate(colaborator.getRelaseDate());
            fromDB.setHours(colaborator.getHours());
            fromDB.setName(colaborator.getName());
            fromDB.setSurname(colaborator.getSurname());
            fromDB.setExpense(colaborator.getExpense());
            fromDB.setActive(colaborator.getActive());
            fromDB.setGuards(colaborator.getGuards());
            return colaboratorRepository.save(fromDB);
        } else {
            throw new Exception("No project found with id" + id);
        }
    }


}
