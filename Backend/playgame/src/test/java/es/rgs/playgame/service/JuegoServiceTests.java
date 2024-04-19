package es.rgs.playgame.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import es.rgs.playgame.dto.CategoriaDto;
import es.rgs.playgame.dto.JuegoDto;
import es.rgs.playgame.model.Categoria;
import es.rgs.playgame.model.Juego;
import es.rgs.playgame.repository.IJuegoRepository;
import es.rgs.playgame.request.JuegoRequest;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class JuegoServiceTests {

    @Mock
    private IJuegoRepository juegoRepository;

    @Mock
    private CategoriaService categoriaService;

    @InjectMocks
    private JuegoService juegoService;
   
    

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }
    

    @Test
    @Order(1)
    void testCreateJuego() {
        // Arrange
        JuegoRequest request = new JuegoRequest();
        request.setName("Test Juego");
        request.setPrice(50.0f);
        request.setStock(100);
        CategoriaDto categoriaDto = new CategoriaDto(1,"Test Categoria");
        request.setCategoriaDto(categoriaDto);

        Categoria categoria = new Categoria();
        categoria.setName("Test Categoria");
        when(categoriaService.findOrCreateCategoria(categoria)).thenReturn(categoria);

        Juego juego = new Juego();
        juego.setName("Test Juego");
        juego.setPrice(50.0f);
        juego.setStock(100);
        juego.setCategoria(categoria);
        when(juegoRepository.save(any(Juego.class))).thenReturn(juego);

        // Act
        JuegoDto result = juegoService.createJuego(request);

        // Assert
        assertNotNull(result);
        assertEquals(juego.getName(), result.getName());
        assertEquals(juego.getPrice(), result.getPrice());
        assertEquals(juego.getStock(), result.getStock());
        assertEquals(categoria.getName(), result.getCategoria().getName());
    }

    @Test
    @Order(2)
    void testFindAll() {
        // Arrange
        List<Juego> juegos = new ArrayList<>();
        juegos.add(new Juego());
        juegos.add(new Juego());
        when(juegoRepository.findAll()).thenReturn(juegos);

        // Act
        ResponseEntity<List<Juego>> response = juegoService.findAll();

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(juegos.size(), response.getBody().size());
    }

    @Test
    @Order(3)
    void testFindByIdExisting() {
        // Arrange
        int id = 1;
        Juego juego = new Juego();
        juego.setId(id);
        when(juegoRepository.findById(id)).thenReturn(Optional.of(juego));

        // Act
        ResponseEntity<Juego> response = juegoService.findById(id);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(id, response.getBody().getId());
    }

    @Test
    @Order(4)
    void testFindByIdNonExisting() {
        // Arrange
        int id = 1;
        when(juegoRepository.findById(id)).thenReturn(Optional.empty());

        // Act
        ResponseEntity<Juego> response = juegoService.findById(id);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals(null, response.getBody());
    }

    @Test
    @Order(5)
    void testDeleteByIdExisting() {
        // Arrange
        int id = 1;
        when(juegoRepository.existsById(id)).thenReturn(true);

        // Act
        ResponseEntity<Void> response = juegoService.deleteById(id);

        // Assert
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(juegoRepository, times(1)).deleteById(id);
    }

    @Test
    @Order(6)
    void testDeleteByIdNonExisting() {
        // Arrange
        int id = 1;
        when(juegoRepository.existsById(id)).thenReturn(false);

        // Act
        ResponseEntity<Void> response = juegoService.deleteById(id);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(juegoRepository, never()).deleteById(id);
    }
/*
    @Test
    @Order(7)
    void testUpdateJuegoExisting() {
        // Arrange
        int id = 1;
        JuegoDto juegoDto = new JuegoDto();
        juegoDto.setName("Updated Test Juego");
        juegoDto.setPrice(60.0);
        juegoDto.setStock(200);
        CategoriaDto categoriaDto = new CategoriaDto("Updated Test Categoria");
        juegoDto.setCategoria(categoriaDto);

        Juego juego = new Juego();
        juego.setId(id);
        juego.setName("Test Juego");
        juego.setPrice(50.0);
        juego.setStock(100);
        when(juegoRepository.findById(id)).thenReturn(Optional.of(juego));

        Categoria categoria = new Categoria();
        categoria.setName("Updated Test Categoria");
        when(categoriaService.findOrCreateCategoria(categoriaDto)).thenReturn(categoria);

        // Act
        ResponseEntity<Juego> response = juegoService.updateJuego(id, juegoDto);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(id, response.getBody().getId());
        assertEquals(juegoDto.getName(), response.getBody().getName());
        assertEquals(juegoDto.getPrice(), response.getBody().getPrice());
        assertEquals(juegoDto.getStock(), response.getBody().getStock());
        assertEquals(categoria.getName(), response.getBody().getCategoria().getName());
    }
*/
    @Test
    @Order(8)
    void testUpdateJuegoNonExisting() {
        // Arrange
        int id = 1;
        JuegoDto juegoDto = new JuegoDto();
        when(juegoRepository.findById(id)).thenReturn(Optional.empty());

        // Act
        ResponseEntity<Juego> response = juegoService.updateJuego(id, juegoDto);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals(null, response.getBody());
    }
}