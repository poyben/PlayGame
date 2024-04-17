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

import es.rgs.playgame.dto.RolDto;
import es.rgs.playgame.model.Rol;
import es.rgs.playgame.service.RolService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(value="/api/v1/rol")
@RequiredArgsConstructor
@CrossOrigin(origins= {"http://localhost:4200"})
public class RolController {

	private final RolService rolService;
	
 
	@PostMapping
	public RolDto createRol(@RequestBody RolDto rolDto) {
		return rolService.createRol(rolDto);
	}
	
	@GetMapping
	public ResponseEntity<List<Rol>> findAll() {
		return rolService.findAll();
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Rol> findById(@PathVariable Integer id) {
		return rolService.findById(id);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteById(@PathVariable Integer id) {
	    return rolService.deleteById(id);
	}

	@PutMapping("/{id}")
	public ResponseEntity<Rol> updateRol(@PathVariable int id, @RequestBody Rol rol) {
		return rolService.updateRol(id, rol);
	}
	
	
}
