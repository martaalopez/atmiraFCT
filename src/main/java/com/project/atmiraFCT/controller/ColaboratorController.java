package com.project.atmiraFCT.controller;

import com.project.atmiraFCT.exception.RecordNotFoundException;
import com.project.atmiraFCT.model.domain.Colaborator;
import com.project.atmiraFCT.repository.ColaboratorRepository;
import com.project.atmiraFCT.service.ColaboratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "${Front_URL}")
@RestController
public class ColaboratorController {

    @Autowired
    private ColaboratorRepository colaboratorRepository;

    @Autowired
    private ColaboratorService colaboratorService;


    /**
     * Elimina un Colaborador por su ID.
     *
     * @param id    El ID del Colaborador a eliminar.
     * @return      ResponseEntity indicando el éxito de la operación.
     *              Devuelve true si el Colaborador se eliminó correctamente, false en caso contrario.
     * @throws RecordNotFoundException   Si no se encuentra ningún Colaborador con el ID proporcionado.
     */
    @CrossOrigin(origins = "${Front_URL}")
    @DeleteMapping("colaborator/delete/{id}")
    public ResponseEntity<Boolean> deleteColaborator(@PathVariable String id) {
        Optional<Colaborator> result = colaboratorRepository.findById(id);
        if (result.isPresent()) {
            colaboratorRepository.deleteById(id);
            return ResponseEntity.ok(true);
        } else {
            throw new RecordNotFoundException("No colaborator found with id: " + id);
        }
    }

    /**
     * Actualiza un Colaborador existente.
     *
     * @param id                    El ID del Colaborador a actualizar.
     * @param updatedColaborator    El objeto Colaborador actualizado.
     * @return                      ResponseEntity con el Colaborador actualizado.
     * @throws Exception            Si ocurre algún error durante el proceso de actualización.
     */
    @CrossOrigin(origins = "${Front_URL}")
    @PutMapping("/colaborator/update/{id}")
    public ResponseEntity<Colaborator> updateColaborator(@PathVariable("id") String id, @RequestBody Colaborator updatedColaborator) throws Exception {
        Colaborator updated = colaboratorService.updateColaborator(id, updatedColaborator);
        return ResponseEntity.ok(updated);
    }
    @CrossOrigin(origins = "${Front_URL}")
    @GetMapping("/auth/user/{id_alias}")
    public ResponseEntity<Colaborator> getColaboratorById(@PathVariable("id_alias") String id){
        Colaborator colaborator = colaboratorService.getColaboratorById(id);
        return ResponseEntity.ok(colaborator);
    }
}