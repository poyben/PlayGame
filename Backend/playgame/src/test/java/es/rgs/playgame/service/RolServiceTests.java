package es.rgs.playgame.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import es.rgs.playgame.dto.RolDto;
import es.rgs.playgame.model.Rol;
import es.rgs.playgame.repository.IRolRepository;

@TestMethodOrder(OrderAnnotation.class)
public class RolServiceTests {

    @Mock
    private IRolRepository rolRepository;

    @InjectMocks
    private RolService rolService;
    

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }
    

    @Test
    @Order(1)
    void testCreateRol() {
        RolDto rolDto = new RolDto("TestRol");
        Rol rol = new Rol(1,"TestRol");
        Rol savedRol = new Rol(1, "TestRol");
        when(rolRepository.save(rol)).thenReturn(savedRol);
        RolDto result = rolService.createRol(rolDto);
        assertEquals(savedRol.getName(), result.getName());
    }

    @Test
    @Order(2)
    void testFindAll() {
        Rol rol1 = new Rol(1, "Rol1");
        Rol rol2 = new Rol(2, "Rol2");
        List<Rol> roles = Arrays.asList(rol1, rol2);
        when(rolRepository.findAll()).thenReturn(roles);
        ResponseEntity<List<Rol>> response = rolService.findAll();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(roles, response.getBody());
    }

    @Test
    @Order(3)
    void testFindByIdExistingRol() {
        int id = 1;
        Rol rol = new Rol(id, "TestRol");
        when(rolRepository.findById(id)).thenReturn(Optional.of(rol));
        ResponseEntity<Rol> response = rolService.findById(id);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        
    }

    @Test
    @Order(4)
    void testFindByIdNonExistingRol() {
        int id = 1;
        when(rolRepository.findById(id)).thenReturn(Optional.empty());

        ResponseEntity<Rol> response = rolService.findById(id);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
       
    }

    @Test
    @Order(5)
    void testDeleteByIdExistingRol() {
        int id = 1;
        when(rolRepository.existsById(id)).thenReturn(true);
        ResponseEntity<Void> response = rolService.deleteById(id);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    @Order(6)
    void testDeleteByIdNonExistingRol() {
        int id = 1;
        when(rolRepository.existsById(id)).thenReturn(false);
        ResponseEntity<Void> response = rolService.deleteById(id);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    @Order(7)
    void testUpdateRolExistingRol() {
        int id = 1;
        Rol existingRol = new Rol(id, "ExistingRol");
        Rol updatedRol = new Rol(id, "UpdatedRol");
        when(rolRepository.findById(id)).thenReturn(Optional.of(existingRol));
        when(rolRepository.save(existingRol)).thenReturn(updatedRol);
        ResponseEntity<Rol> response = rolService.updateRol(id, updatedRol);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    @Order(8)
    void testUpdateRolNonExistingRol() {
        int id = 1;
        Rol nonExistingRol = new Rol(id, "NonExistingRol");
        when(rolRepository.findById(id)).thenReturn(Optional.empty());
        ResponseEntity<Rol> response = rolService.updateRol(id, nonExistingRol);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
}
