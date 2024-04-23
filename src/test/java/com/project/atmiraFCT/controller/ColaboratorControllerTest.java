package com.project.atmiraFCT.controller;

import com.project.atmiraFCT.controller.ColaboratorController;
import com.project.atmiraFCT.exception.RecordNotFoundException;
import com.project.atmiraFCT.model.domain.Colaborator;
import com.project.atmiraFCT.repository.ColaboratorRepository;
import com.project.atmiraFCT.service.ColaboratorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ColaboratorControllerTest {

    @Mock
    private ColaboratorRepository colaboratorRepository;

    @Mock
    private ColaboratorService colaboratorService;

    @InjectMocks
    private ColaboratorController colaboratorController;

    private Colaborator colaborator;

    @BeforeEach
    void setUp() {
        colaborator = new Colaborator();
        colaborator.setId_alias("1");
        colaborator.setName("John");
        colaborator.setSurname("Doe");
        colaborator.setEmail("john.doe@example.com");
    }

    @DisplayName("Test getColaboratorById when found")
    @Test
    void shouldReturnColaboratorWhenFound() {
        // Given
        when(colaboratorRepository.findById("1")).thenReturn(Optional.of(colaborator));

        // When
        ResponseEntity<Colaborator> responseEntity = colaboratorController.getColaboratorById("1");

        // Then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isEqualTo(colaborator);
    }

    @Test
    void shouldThrowRecordNotFoundExceptionWhenColaboratorNotFound() {
        // Given
        when(colaboratorRepository.findById("2")).thenReturn(Optional.empty());

        // When and Then
        assertThatThrownBy(() -> colaboratorController.getColaboratorById("2"))
                .isInstanceOf(RecordNotFoundException.class)
                .hasMessageContaining("No colaborator found with id: 2");
    }

    @DisplayName("Test deleteColaborator when found")
    @Test
    void shouldDeleteColaboratorWhenFound() {
        // Given
        when(colaboratorRepository.findById("1")).thenReturn(Optional.of(colaborator));

        // When
        ResponseEntity<Boolean> responseEntity = colaboratorController.deleteColaborator("1");

        // Then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isEqualTo(true);
        verify(colaboratorRepository, times(1)).deleteById("1");
    }

    @DisplayName("Test deleteColaborator when not found")
    @Test
    void shouldThrowRecordNotFoundExceptionWhenDeletingNonExistentColaborator() {
        // Given
        when(colaboratorRepository.findById("2")).thenReturn(Optional.empty());

        // When and Then
        assertThatThrownBy(() -> colaboratorController.deleteColaborator("2"))
                .isInstanceOf(RecordNotFoundException.class)
                .hasMessageContaining("No colaborator found with id: 2");
        verify(colaboratorRepository, never()).deleteById(any());
    }
}
