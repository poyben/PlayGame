package es.rgs.playgame.service;

import static org.hamcrest.CoreMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import es.rgs.playgame.dto.CategoriaDto;
import es.rgs.playgame.model.Categoria;
import es.rgs.playgame.repository.ICategoriaRepository;

@TestMethodOrder(OrderAnnotation.class)
class CategoriaServiceTests {

    @Mock
    private ICategoriaRepository categoriaRepository;

    @InjectMocks
    private CategoriaService categoriaService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    @Order(1)
    void testCreateCategoria() {
        CategoriaDto categoriaDto = new CategoriaDto();
        categoriaDto.setName("TestCategory");

        Categoria categoria = new Categoria();
        categoria.setId(1);
        categoria.setName("TestCategory");

        //when(categoriaRepository.save(any(Categoria.class))).thenReturn(categoria);

        CategoriaDto result = categoriaService.createCategoria(categoriaDto);

        assertEquals(categoria.getId(), result.getId());
        assertEquals(categoria.getName(), result.getName());
    }

    @Test
    @Order(2)
    void testFindAll() {
        List<Categoria> categorias = new ArrayList<>();
        categorias.add(new Categoria(1, "Categoria1"));
        categorias.add(new Categoria(2, "Categoria2"));

        when(categoriaRepository.findAll()).thenReturn(categorias);

        ResponseEntity<List<Categoria>> responseEntity = categoriaService.findAll();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(categorias, responseEntity.getBody());
    }

    @Test
    @Order(3)
    void testFindById() {
        int id = 1;
        Categoria categoria = new Categoria(id, "TestCategory");

        when(categoriaRepository.findById(id)).thenReturn(Optional.of(categoria));

        ResponseEntity<Categoria> responseEntity = categoriaService.findById(id);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(categoria, responseEntity.getBody());
    }

    @Test
    @Order(4)
    void testFindByIdNotFound() {
        int id = 1;

        when(categoriaRepository.findById(id)).thenReturn(Optional.empty());

        ResponseEntity<Categoria> responseEntity = categoriaService.findById(id);

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }

    @Test
    @Order(5)
    void testDeleteById() {
        int id = 1;

        when(categoriaRepository.existsById(id)).thenReturn(true);

        ResponseEntity<Void> responseEntity = categoriaService.deleteById(id);

        assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
        verify(categoriaRepository, times(1)).deleteById(id);
    }

    @Test
    @Order(6)
    void testDeleteByIdNotFound() {
        int id = 1;

        when(categoriaRepository.existsById(id)).thenReturn(false);

        ResponseEntity<Void> responseEntity = categoriaService.deleteById(id);

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        verify(categoriaRepository, never()).deleteById(id);
    }

    @Test
    @Order(7)
    void testUpdateCategoria() {
        int id = 1;
        Categoria categoria = new Categoria(id, "TestCategory");

        when(categoriaRepository.findById(id)).thenReturn(Optional.of(categoria));
        //when(categoriaRepository.save(any(Categoria.class))).thenReturn(categoria);

        Categoria updatedCategoria = new Categoria(id, "UpdatedCategory");

        ResponseEntity<Categoria> responseEntity = categoriaService.updateCategoria(id, updatedCategoria);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(updatedCategoria, responseEntity.getBody());
    }

    @Test
    @Order(8)
    void testUpdateCategoriaNotFound() {
        int id = 1;

        when(categoriaRepository.findById(id)).thenReturn(Optional.empty());

        ResponseEntity<Categoria> responseEntity = categoriaService.updateCategoria(id, new Categoria());

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        //verify(categoriaRepository, never()).save(any(Categoria.class));
    }
}