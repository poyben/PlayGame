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

import es.rgs.playgame.dto.JuegoDto;
import es.rgs.playgame.model.Juego;
import es.rgs.playgame.request.JuegoRequest;
import es.rgs.playgame.service.JuegoService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(value="/api/v1/juego")
@RequiredArgsConstructor
@CrossOrigin(origins= {"http://localhost:4200"})
public class JuegoController {

	private final JuegoService juegoService;
	
	@PostMapping
	public JuegoDto createJuego(@RequestBody JuegoRequest juegoRequest) {
		return juegoService.createJuego(juegoRequest);
	}
	
	@GetMapping
	public ResponseEntity<List<Juego>> findAll() {
		return juegoService.findAll();
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Juego> findById(@PathVariable Integer id) {
		return juegoService.findById(id);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteById(@PathVariable Integer id) {
	    return juegoService.deleteById(id);
	}

	@PutMapping("/{id}")
	public ResponseEntity<Juego> updateJuego(@PathVariable int id, @RequestBody Juego juego) {
		return juegoService.updateJuego(id, juego);
	}
	
}
