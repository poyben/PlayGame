package es.rgs.playgame.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import es.rgs.playgame.dto.CategoriaDto;
import es.rgs.playgame.model.Categoria;
import es.rgs.playgame.service.CategoriaService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(value="/api/v1/categoria")
@RequiredArgsConstructor
@CrossOrigin(origins= {"http://localhost:4200"})
public class CategoriaController {

	private final CategoriaService categoriaService;
	
	@PostMapping
	public CategoriaDto createCategoria(@RequestBody CategoriaDto categoriaDto) {
		return categoriaService.createCategoria(categoriaDto);
	}
	
	@GetMapping
	public ResponseEntity<List<Categoria>> findAll() {
		return categoriaService.findAll();
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Categoria> findById(@PathVariable Integer id) {
		return categoriaService.findById(id);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteById(@PathVariable Integer id) {
	    return categoriaService.deleteById(id);
	}

	@PutMapping("/{id}")
	public ResponseEntity<Categoria> updateCategoria(@PathVariable int id, @RequestBody Categoria categoria) {
		return categoriaService.updateCategoria(id, categoria);
	}
	
	
}
