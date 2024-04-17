package es.rgs.playgame.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import es.rgs.playgame.dto.UsuarioDto;
import es.rgs.playgame.model.Rol;
import es.rgs.playgame.model.Usuario;
import es.rgs.playgame.request.UsuarioRequest;
import es.rgs.playgame.service.UsuarioService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(value="/api/v1/user")
@RequiredArgsConstructor
@CrossOrigin(origins= {"http://localhost:4200"})
public class UsuarioController {
	
	private final UsuarioService userService;
	/*
	@GetMapping(value="{id}")
	public ResponseEntity<UsuarioDto>getUser(@PathVariable Integer id){
		return userService.getUsuario(id);
		
	}
	*/
	@PutMapping("/{id}")
	public ResponseEntity<Usuario> updateUser(@PathVariable int id,@RequestBody UsuarioRequest userRequest){
		return userService.updateUser(id,userRequest);
	}
	
	@GetMapping("/{id}")
	public UsuarioDto findById(@PathVariable int id) {
	
		return userService.findById(id);	
			
	}
	
	@GetMapping
	public ResponseEntity<List<Usuario>> findAll() {
		return userService.findAll();
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteById(@PathVariable Integer id) {
	    return userService.deleteById(id);
	}
	
	
}
