package es.rgs.playgame.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import es.rgs.playgame.dto.TiendaDto;
import es.rgs.playgame.model.Tienda;
import es.rgs.playgame.repository.ITiendaRepository;

@TestMethodOrder(OrderAnnotation.class)
public class TiendaServiceTests {

    @Mock
    private ITiendaRepository tiendaRepository;

    @InjectMocks
    private TiendaService tiendaService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }
    
    @Test
    @Order(1)
    void testCreateTienda() {
        TiendaDto tiendaDto = new TiendaDto();
        tiendaDto.setName("TestTienda");
        tiendaDto.setAddress("TestAddress");

        Tienda tienda = new Tienda();
        tienda.setId(1);
        tienda.setName("TestTienda");
        tienda.setAddress("TestAddress");

        when(tiendaRepository.save(tienda)).thenReturn(tienda);

        TiendaDto result = tiendaService.createTienda(tiendaDto);

        assertEquals(tienda.getId(), result.getId());
        assertEquals(tienda.getName(), result.getName());
        assertEquals(tienda.getAddress(), result.getAddress());
    }

    @Test
    @Order(2)
    void testFindAllTiendas() {
        List<Tienda> tiendas = List.of(
            new Tienda(1, "Tienda1", "Dirección1"),
            new Tienda(2, "Tienda2", "Dirección2")
        );
        when(tiendaRepository.findAll()).thenReturn(tiendas);

        ResponseEntity<List<Tienda>> response = tiendaService.findAll();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(tiendas.size(), response.getBody().size());
    }

    @Test
    @Order(3)
    void testFindTiendaById() {
        int id = 1;
        Tienda tienda = new Tienda();
        tienda.setId(id);
        tienda.setName("TestTienda");
        tienda.setAddress("TestAddress");
        when(tiendaRepository.findById(id)).thenReturn(Optional.of(tienda));

        ResponseEntity<Tienda> response = tiendaService.findById(id);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(id, response.getBody().getId());
        assertEquals("TestTienda", response.getBody().getName());
        assertEquals("TestAddress", response.getBody().getAddress());
    }

    @Test
    @Order(4)
    void testFindTiendaByIdNotFound() {
        int id = 999;
        when(tiendaRepository.findById(id)).thenReturn(Optional.empty());

        ResponseEntity<Tienda> response = tiendaService.findById(id);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    @Order(5)
    void testDeleteTiendaById() {
        int id = 1;
        when(tiendaRepository.existsById(id)).thenReturn(true);

        ResponseEntity<Void> response = tiendaService.deleteById(id);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(tiendaRepository).deleteById(id);
    }

    @Test
    @Order(6)
    void testDeleteTiendaByIdNotFound() {
        int id = 999;
        when(tiendaRepository.existsById(id)).thenReturn(false);

        ResponseEntity<Void> response = tiendaService.deleteById(id);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(tiendaRepository).existsById(id);
    }

   
}