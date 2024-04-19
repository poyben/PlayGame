package es.rgs.playgame.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import es.rgs.playgame.dto.UsuarioDto;
import es.rgs.playgame.model.Usuario;
import es.rgs.playgame.repository.ITiendaRepository;
import es.rgs.playgame.repository.IUsuarioRepository;
import es.rgs.playgame.request.UsuarioRequest;

@TestMethodOrder(OrderAnnotation.class)
public class UsuarioServiceTests {

    @Mock
    IUsuarioRepository userRepository;

    @Autowired
    ITiendaRepository tiendaRepository;

    @InjectMocks
    private UsuarioService usuarioService;
    
    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }
    
    
    @Test
    @Order(1)
    void testUpdateUserExistingUser() {
        int id = 1;
        UsuarioRequest userRequest = new UsuarioRequest();
        userRequest.setFirstname("TestFirstName");
        userRequest.setLastname("TestLastName");
        userRequest.setUsername("TestUsername");
        Usuario existingUser = new Usuario();
        existingUser.setId(id);
        existingUser.setFirstname("ExistingFirstName");
        existingUser.setLastname("ExistingLastName");
        existingUser.setUsername("ExistingUsername");
        when(userRepository.findById(id)).thenReturn(Optional.of(existingUser));

        ResponseEntity<Usuario> response = usuarioService.updateUser(id, userRequest);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody().getFirstname().equals("TestFirstName"));
        assertTrue(response.getBody().getLastname().equals("TestLastName"));
        assertTrue(response.getBody().getUsername().equals("TestUsername"));
        verify(userRepository).save(existingUser);
    }

    @Test
    @Order(2)
    void testUpdateUserNonExistingUser() {
        int id = 1;
        UsuarioRequest userRequest = new UsuarioRequest();
        userRequest.setFirstname("TestFirstName");
        userRequest.setLastname("TestLastName");
        userRequest.setUsername("TestUsername");
        when(userRepository.findById(id)).thenReturn(Optional.empty());

        ResponseEntity<Usuario> response = usuarioService.updateUser(id, userRequest);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    @Order(3)
    void testGetUsuarioExistingUser() {
        int id = 1;
        Usuario user = new Usuario();
        user.setId(id);
        user.setUsername("TestUsername");
        user.setFirstname("TestFirstName");
        user.setLastname("TestLastName");
        when(userRepository.findById(id)).thenReturn(Optional.of(user));

        UsuarioDto result = usuarioService.getUsuario(id);

        assertNotNull(result);
        assertEquals(id, result.getId());
        assertEquals("TestUsername", result.getUsername());
        assertEquals("TestFirstName", result.getFirstname());
        assertEquals("TestLastName", result.getLastname());
    }

    @Test
    @Order(4)
    void testGetUsuarioNonExistingUser() {
        int id = 41;
        when(userRepository.findById(id)).thenReturn(Optional.empty());

        UsuarioDto result = usuarioService.getUsuario(id);

        assertEquals(null, result);
    }
    
    
    @Test
    @Order(5)
    void testFindByIdExistingUser() {
        int id = 1;
        Usuario user = new Usuario();
        user.setId(id);
        user.setUsername("TestUsername");
        user.setFirstname("TestFirstName");
        user.setLastname("TestLastName");
        when(userRepository.findById(id)).thenReturn(Optional.of(user));

        UsuarioDto response = usuarioService.findById(id);

        assertNotNull(response);
        assertEquals(id, response.getId());
        assertEquals("TestUsername", response.getUsername());
        assertEquals("TestFirstName", response.getFirstname());
        assertEquals("TestLastName", response.getLastname());
    }

    @Test
    @Order(6)
    void testFindByIdNonExistingUser() {
        int id = 1;
        when(userRepository.findById(id)).thenReturn(Optional.empty());

        UsuarioDto response = usuarioService.findById(id);


        assertNull(response);
    }

    @Test
    @Order(7)
    void testFindAll() {
        List<Usuario> userList = List.of(
            new Usuario(1, "TestUser1", "TestFirstName1", "TestLastName1"),
            new Usuario(2, "TestUser2", "TestFirstName2", "TestLastName2")
        );
        when(userRepository.findAll()).thenReturn(userList);

        ResponseEntity<List<Usuario>> response = usuarioService.findAll();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, response.getBody().size());
    }

    @Test
    @Order(8)
    void testDeleteByIdExistingUser() {
        int id = 1;
        when(userRepository.existsById(id)).thenReturn(true);

        ResponseEntity<Void> response = usuarioService.deleteById(id);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(userRepository).deleteById(id);
    }

    
    
}
