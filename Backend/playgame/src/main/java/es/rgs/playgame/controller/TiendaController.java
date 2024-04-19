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

import es.rgs.playgame.dto.TiendaDto;
import es.rgs.playgame.model.Tienda;
import es.rgs.playgame.service.TiendaService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(value="/api/v1/tienda")
@RequiredArgsConstructor
@CrossOrigin(origins= {"http://localhost:4200"})
public class TiendaController {

	private final TiendaService tiendaService;

	@PostMapping
	public TiendaDto createTienda(@RequestBody TiendaDto tiendaDto) {
		return tiendaService.createTienda(tiendaDto);
	}
	
	@GetMapping
	public ResponseEntity<List<Tienda>> findAll() {
		return tiendaService.findAll();
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Tienda> findById(@PathVariable Integer id) {
		return tiendaService.findById(id);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteById(@PathVariable Integer id) {
	    return tiendaService.deleteById(id);
	}

	@PutMapping("/{id}")
	public ResponseEntity<Tienda> updateTienda(@PathVariable int id, @RequestBody Tienda tienda) {
		return tiendaService.updateTienda(id, tienda);
	}
	
	
}
