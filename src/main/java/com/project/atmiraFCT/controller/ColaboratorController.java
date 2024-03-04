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

@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class ColaboratorController {

    @Autowired
    private ColaboratorRepository colaboratorRepository;

    @Autowired
    private ColaboratorService colaboratorService;

    /**
     * Guarda un nuevo Colaborador.
     *
     * @param colaborator   El objeto Colaborador a guardar.
     * @param workplaceId   El ID del lugar de trabajo para asociar con el Colaborador.
     * @return              El objeto Colaborador guardado.
     */
    @PostMapping("/colaborator/save/{workplaceId}")
    public Colaborator saveColaborator(@RequestBody Colaborator colaborator, @PathVariable Long workplaceId) {
        return colaboratorService.saveColaborator(colaborator, workplaceId);
    }

    /**
     * Obtiene todos los Colaboradores.
     *
     * @return  Una lista de todos los Colaboradores.
     */
    @GetMapping("/colaborator/all")
    public List<Colaborator> getAllColaborators() {
        return colaboratorRepository.findAll();
    }

    /**
     * Elimina un Colaborador por su ID.
     *
     * @param id    El ID del Colaborador a eliminar.
     * @return      ResponseEntity indicando el éxito de la operación.
     *              Devuelve true si el Colaborador se eliminó correctamente, false en caso contrario.
     * @throws RecordNotFoundException   Si no se encuentra ningún Colaborador con el ID proporcionado.
     */
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
    @PutMapping("/colaborator/update/{id}")
    public ResponseEntity<Colaborator> updateColaborator(@PathVariable("id") String id, @RequestBody Colaborator updatedColaborator) throws Exception {
        Colaborator updated = colaboratorService.updateColaborator(id, updatedColaborator);
        return ResponseEntity.ok(updated);
    }

    @GetMapping("/auth/user/{id_alias}")
    public ResponseEntity<Colaborator> getColaboratorById(@PathVariable("id_alias") String id){
        Colaborator colaborator = colaboratorService.getColaboratorById(id);
        return ResponseEntity.ok(colaborator);
    }
}
