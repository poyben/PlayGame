package es.rgs.playgame.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import es.rgs.playgame.dto.UsuarioDto;
import es.rgs.playgame.request.UsuarioRequest;
import es.rgs.playgame.response.UsuarioResponse;
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
	@PutMapping()
	public UsuarioResponse updateUser(@RequestBody UsuarioRequest userRequest){
		return userService.updateUser(userRequest);
	}
	
	@GetMapping("/{id}")
	public UsuarioDto findById(@PathVariable int id) {
	
		return userService.findById(id);	
			
	}
	
	

	
}
