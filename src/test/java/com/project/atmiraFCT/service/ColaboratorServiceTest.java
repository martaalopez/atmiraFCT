package com.project.atmiraFCT.service;


import com.project.atmiraFCT.exception.RecordNotFoundException;
import com.project.atmiraFCT.model.domain.Colaborator;
import com.project.atmiraFCT.repository.ColaboratorRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ColaboratorServiceTest {

    @Mock
    private ColaboratorRepository colaboratorRepository;

    @InjectMocks
    private ColaboratorService colaboratorService;

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
    void testGetColaboratorByIdFound() {
        // Given
        when(colaboratorRepository.findById("1")).thenReturn(Optional.of(colaborator));

        // When
        Colaborator foundColaborator = colaboratorService.getColaboratorById("1");

        // Then
        assertThat(foundColaborator).isEqualTo(colaborator);
    }

    @DisplayName("Test getColaboratorById when not found")
    @Test
    void testGetColaboratorByIdNotFound() {
        // Given
        when(colaboratorRepository.findById("2")).thenReturn(Optional.empty());

        // Then
        assertThatThrownBy(() -> colaboratorService.getColaboratorById("2"))
                .isInstanceOf(RecordNotFoundException.class);
    }

    @DisplayName("Test getAllColaborators")
    @Test
    void testGetAllColaborators() {
        // Given
        List<Colaborator> colaboratorList = new ArrayList<>();
        colaboratorList.add(colaborator);
        when(colaboratorRepository.findAll()).thenReturn(colaboratorList);

        // When
        List<Colaborator> foundColaborators = colaboratorService.getAllColaborators();

        // Then
        assertThat(foundColaborators).hasSize(1);
        assertThat(foundColaborators.get(0)).isEqualTo(colaborator);
    }

    @DisplayName("Test getUserById when found")
    @Test
    void testGetUserByIdFound() {
        // Given
        when(colaboratorRepository.findById("1")).thenReturn(Optional.of(colaborator));

        // When
        Colaborator foundColaborator = colaboratorService.getUserById("1");

        // Then
        assertThat(foundColaborator).isEqualTo(colaborator);
    }

    @DisplayName("Test getUserById when not found")
    @Test
    void testGetUserByIdNotFound() {
        // Given
        when(colaboratorRepository.findById("2")).thenReturn(Optional.empty());

        // Then
        assertThatThrownBy(() -> colaboratorService.getUserById("2"))
                .isInstanceOf(RecordNotFoundException.class);
    }

    @DisplayName("Test deleteColaborator when found")
    @Test
    void testDeleteColaboratorFound() {
        // Given
        when(colaboratorRepository.findById("1")).thenReturn(Optional.of(colaborator));

        // When
        colaboratorService.deleteColaborator("1");

        // Then
        verify(colaboratorRepository, times(1)).deleteById("1");
    }

    @DisplayName("Test deleteColaborator when not found")
    @Test
    void testDeleteColaboratorNotFound() {
        // Given
        when(colaboratorRepository.findById("2")).thenReturn(Optional.empty());

        // Then
        assertThatThrownBy(() -> colaboratorService.deleteColaborator("2"))
                .isInstanceOf(RecordNotFoundException.class);
    }

    @DisplayName("Test updateColaborator when found")
    @Test
    void testUpdateColaboratorFound() throws Exception {
        // Given
        when(colaboratorRepository.findById("1")).thenReturn(Optional.of(colaborator));
        Colaborator updatedColaborator = new Colaborator();
        updatedColaborator.setId_alias("1");
        updatedColaborator.setName("Jane");
        updatedColaborator.setSurname("Doe");
        updatedColaborator.setEmail("jane.doe@example.com");

        // When
        Colaborator result = colaboratorService.updateColaborator("1", updatedColaborator);

        // Then
        assertThat(result.getId_alias()).isEqualTo("1");
        assertThat(result.getName()).isEqualTo("Jane");
        assertThat(result.getSurname()).isEqualTo("Doe");
        assertThat(result.getEmail()).isEqualTo("jane.doe@example.com");
    }

    @DisplayName("Test updateColaborator when not found")
    @Test
    void testUpdateColaboratorNotFound() {
        // Given
        when(colaboratorRepository.findById("2")).thenReturn(Optional.empty());
        Colaborator updatedColaborator = new Colaborator();
        updatedColaborator.setId_alias("2");
        updatedColaborator.setName("Jane");
        updatedColaborator.setSurname("Doe");
        updatedColaborator.setEmail("jane.doe@example.com");

        // Then
        assertThatThrownBy(() -> colaboratorService.updateColaborator("2", updatedColaborator))
                .isInstanceOf(Exception.class);
    }
}

