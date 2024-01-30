package com.project.atmiraFCT.controllerTest;

import com.project.atmiraFCT.controller.ColaboratorController;
import com.project.atmiraFCT.exception.RecordNotFoundException;
import com.project.atmiraFCT.model.domain.Colaborator;
import com.project.atmiraFCT.repository.ColaboratorRepository;
import com.project.atmiraFCT.service.ColaboratorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class ColaboratorControllerTest {

    @Mock
    private ColaboratorRepository colaboratorRepository;

    @Mock
    private ColaboratorService colaboratorService;

    @InjectMocks
    private ColaboratorController colaboratorController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void saveColaborator_ShouldReturnSavedColaborator() {
        Colaborator colaborator = new Colaborator();

        when(colaboratorRepository.save(any(Colaborator.class))).thenReturn(colaborator);

        Colaborator savedColaborator = colaboratorController.saveColaborator(colaborator);

        verify(colaboratorRepository, times(1)).save(any(Colaborator.class));

        assertNotNull(savedColaborator);
    }

    @Test
    public void getAllColaborators_ShouldReturnAllColaborators() {
        List<Colaborator> colaborators = Collections.singletonList(new Colaborator());

        when(colaboratorRepository.findAll()).thenReturn(colaborators);

        List<Colaborator> result = colaboratorController.getAllColaborators();

        verify(colaboratorRepository, times(1)).findAll();

        assertEquals(colaborators, result);
    }

    @Test
    public void deleteColaborator_WhenColaboratorExists_ShouldDeleteColaborator() {
        String colaboratorId = "1";

        when(colaboratorRepository.findById(colaboratorId)).thenReturn(Optional.of(new Colaborator()));

        ResponseEntity<String> response = colaboratorController.deleteColaborator(colaboratorId);

        verify(colaboratorRepository, times(1)).findById(colaboratorId);
        verify(colaboratorRepository, times(1)).deleteById(colaboratorId);

        assertEquals("Colaborator with ID " + colaboratorId + " has been deleted.", response.getBody());
    }

    @Test
    public void deleteColaborator_WhenColaboratorDoesNotExist_ShouldThrowException() {
        String colaboratorId = "1";

        when(colaboratorRepository.findById(colaboratorId)).thenReturn(Optional.empty());

        assertThrows(RecordNotFoundException.class, () -> colaboratorController.deleteColaborator(colaboratorId));

        verify(colaboratorRepository, times(1)).findById(colaboratorId);
        verify(colaboratorRepository, never()).deleteById(colaboratorId);
    }

    @Test
    public void updateColaborator_ShouldReturnUpdatedColaborator() {
        Long colaboratorId = 1L;
        Colaborator updatedColaborator = new Colaborator();
        updatedColaborator.setId_alias("1");
        updatedColaborator.setName("Updated Name");

        when(colaboratorService.createOrUpdateColaborator(any(Colaborator.class))).thenReturn(updatedColaborator);

        ResponseEntity<Colaborator> response = colaboratorController.updateColaborator(colaboratorId, updatedColaborator);

        verify(colaboratorService, times(1)).createOrUpdateColaborator(any(Colaborator.class));

        assertEquals(updatedColaborator, response.getBody());
    }
}
