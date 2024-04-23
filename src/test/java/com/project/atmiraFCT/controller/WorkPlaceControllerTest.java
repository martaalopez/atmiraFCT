package com.project.atmiraFCT.controller;

import com.project.atmiraFCT.controller.WorkPlaceController;
import com.project.atmiraFCT.model.domain.WorkPlace;
import com.project.atmiraFCT.repository.WorkPlaceRepository;
import com.project.atmiraFCT.service.WorkPlaceService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class WorkPlaceControllerTest {

    @Mock
    private WorkPlaceRepository workPlaceRepository;

    @Mock
    private WorkPlaceService workPlaceService;

    @InjectMocks
    private WorkPlaceController workPlaceController;

    private WorkPlace workPlace;

    @BeforeEach
    void setUp() {
        workPlace = new WorkPlace();
        workPlace.setId(1L);
        workPlace.setCode("WP001");
    }

    @DisplayName("Test save work place")
    @Test
    void testSaveWorkPlace() {
        // Given
        WorkPlace newWorkPlace = new WorkPlace();
        newWorkPlace.setId(2L);
        newWorkPlace.setCode("WP002");

        // When
        ResponseEntity<WorkPlace> response = workPlaceController.saveWorkPlace(newWorkPlace);

        // Then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody()).isEqualTo(newWorkPlace);
        verify(workPlaceService, times(1)).save(newWorkPlace);
    }

    @DisplayName("Test get all work places")
    @Test
    void testGetAllWorkPlaces() {
        // Given
        List<WorkPlace> workPlaces = Arrays.asList(new WorkPlace(), new WorkPlace());

        when(workPlaceRepository.findAll()).thenReturn(workPlaces);

        // When
        List<WorkPlace> result = workPlaceController.getAll();

        // Then
        assertThat(result).hasSize(2);
    }
}
