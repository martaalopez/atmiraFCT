package com.project.atmiraFCT.serviceTest;

import com.project.atmiraFCT.exception.RecordNotFoundException;
import com.project.atmiraFCT.model.domain.Colaborator;
import com.project.atmiraFCT.repository.ColaboratorRepository;
import com.project.atmiraFCT.service.ColaboratorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class ColaboratorServiceTest {

    @Mock
    private ColaboratorRepository colaboratorRepository;

    @InjectMocks
    private ColaboratorService colaboratorService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void saveColaborator_ShouldSaveColaborator() {
        Colaborator colaborator = new Colaborator();

        when(colaboratorRepository.save(any(Colaborator.class))).thenReturn(colaborator);

        Colaborator savedColaborator = colaboratorService.saveColaborator(colaborator);

        verify(colaboratorRepository, times(1)).save(any(Colaborator.class));

        assertNotNull(savedColaborator);
    }

    @Test
    public void getAllColaborators_ShouldReturnAllColaborators() {
        List<Colaborator> colaborators = Collections.singletonList(new Colaborator());

        when(colaboratorRepository.findAll()).thenReturn(colaborators);

        List<Colaborator> result = colaboratorService.getAllColaborators();

        verify(colaboratorRepository, times(1)).findAll();

        assertEquals(colaborators, result);
    }

    @Test
    public void getUserById_WhenColaboratorExists_ShouldReturnColaborator() {
        String colaboratorId = "1";
        Colaborator colaborator = new Colaborator();
        colaborator.setId_alias(colaboratorId);

        when(colaboratorRepository.findById(colaboratorId)).thenReturn(Optional.of(colaborator));

        Colaborator retrievedColaborator = colaboratorService.getUserById(colaboratorId);

        verify(colaboratorRepository, times(1)).findById(colaboratorId);

        assertEquals(colaborator, retrievedColaborator);
    }

    @Test
    public void getUserById_WhenColaboratorDoesNotExist_ShouldThrowException() {
        String colaboratorId = "1";

        when(colaboratorRepository.findById(colaboratorId)).thenReturn(Optional.empty());

        assertThrows(RecordNotFoundException.class, () -> colaboratorService.getUserById(colaboratorId));

        verify(colaboratorRepository, times(1)).findById(colaboratorId);
    }

    @Test
    public void deleteColaborator_WhenColaboratorExists_ShouldDeleteColaborator() {
        String colaboratorId = "1";

        when(colaboratorRepository.findById(colaboratorId)).thenReturn(Optional.of(new Colaborator()));

        colaboratorService.deleteColaborator(colaboratorId);

        verify(colaboratorRepository, times(1)).findById(colaboratorId);
        verify(colaboratorRepository, times(1)).deleteById(colaboratorId);
    }

    @Test
    public void deleteColaborator_WhenColaboratorDoesNotExist_ShouldThrowException() {
        String colaboratorId = "1";

        when(colaboratorRepository.findById(colaboratorId)).thenReturn(Optional.empty());

        assertThrows(RecordNotFoundException.class, () -> colaboratorService.deleteColaborator(colaboratorId));

        verify(colaboratorRepository, times(1)).findById(colaboratorId);
    }

    @Test
    public void createOrUpdateColaborator_WhenColaboratorIdNotNull_ShouldUpdateColaborator() {
        String colaboratorId = "1";
        Colaborator existingColaborator = new Colaborator();
        existingColaborator.setId_alias(colaboratorId);

        when(colaboratorRepository.findById(colaboratorId)).thenReturn(Optional.of(existingColaborator));
        when(colaboratorRepository.save(any(Colaborator.class))).thenReturn(existingColaborator);

        Colaborator updatedColaborator = new Colaborator();
        updatedColaborator.setId_alias(colaboratorId);
        updatedColaborator.setName("Updated Name");

        Colaborator result = colaboratorService.createOrUpdateColaborator(updatedColaborator);

        verify(colaboratorRepository, times(1)).findById(colaboratorId);
        verify(colaboratorRepository, times(1)).save(any(Colaborator.class));

        assertEquals(existingColaborator.getId_alias(), result.getId_alias());
        assertEquals(updatedColaborator.getName(), result.getName());
    }

    @Test
    public void createOrUpdateColaborator_WhenColaboratorIdNull_ShouldInsertColaborator() {
        Colaborator newColaborator = new Colaborator();
        newColaborator.setName("New Colaborator");

        when(colaboratorRepository.save(any(Colaborator.class))).thenReturn(newColaborator);

        Colaborator result = colaboratorService.createOrUpdateColaborator(newColaborator);

        verify(colaboratorRepository, never()).findById(anyString());
        verify(colaboratorRepository, times(1)).save(any(Colaborator.class));

        assertNotNull(result.getId_alias());
        assertEquals(newColaborator.getName(), result.getName());
    }
}
