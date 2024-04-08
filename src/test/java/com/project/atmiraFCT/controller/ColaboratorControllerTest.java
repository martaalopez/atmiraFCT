package com.project.atmiraFCT.controller;

import com.project.atmiraFCT.model.domain.Colaborator;
import com.project.atmiraFCT.repository.ColaboratorRepository;
import com.project.atmiraFCT.service.ColaboratorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.ResponseEntity;
import java.util.Date;
import java.util.Optional;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class ColaboratorControllerTest {

   @Mock
    private ColaboratorRepository colaboratorRepository;

   @Mock
    private ColaboratorService colaboratorService;

   @InjectMocks
    private ColaboratorController colaboratorController;

   @BeforeEach
           void setUp() {
       MockitoAnnotations.initMocks(this);

       //creo un colaborador
       Colaborator colaborator=new Colaborator();
       colaborator.setId_alias("example");
       colaborator.setEmail("example@gmail.com");
       colaborator.setActive(false);
       colaborator.setRelaseDate(new Date());
       colaborator.setHours(40);
       colaborator.setGuards(true);
       colaborator.setExpense(true);
       colaborator.setName("Marta");
       colaborator.setSurname("Lopez");
       colaborator.setPassword("password123");

       // Configurar el comportamiento del repositorio para devolver los colaboradores ficticios
       when(colaboratorRepository.findById("marta")).thenReturn(Optional.of(colaborator));
   }

   @Test
    @Order(1)
    void deleteColaborator(){
       //le pasamos el id
       //se prepara el entorno de prueba
       String id="example";
      when(colaboratorRepository.findById(id)).thenReturn(Optional.of(new Colaborator()));
      doNothing().when(colaboratorRepository).deleteById(id);

      //accion
       ResponseEntity<Boolean> response=colaboratorController.deleteColaborator(id);

       //verifica el resultado de la accion es el esperado
       assertTrue(response.getBody());
       assertEquals(200,response.getStatusCodeValue());

   }
    @Test
    @Order(2)
    void updateColaborator() throws Exception {
        // Arrange
        String id = "marta";
        Colaborator updatedColaborator = new Colaborator();
        updatedColaborator.setId_alias("marta1");
        updatedColaborator.setEmail("colaborator1@example.com");
        updatedColaborator.setActive(true);
        updatedColaborator.setRelaseDate(new Date());
        updatedColaborator.setHours(42);
        updatedColaborator.setGuards(true);
        updatedColaborator.setExpense(true);
        updatedColaborator.setName("Marta1");
        updatedColaborator.setSurname("Lopez1");
        updatedColaborator.setPassword("password123");

        when(colaboratorService.updateColaborator(id, updatedColaborator)).thenReturn(updatedColaborator);

        // Act
        ResponseEntity<Colaborator> response = colaboratorController.updateColaborator(id, updatedColaborator);

        // Assert
        assertNotNull(response.getBody());
        assertEquals(updatedColaborator, response.getBody());
    }


}
