package es.rgs.playgame.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import es.rgs.playgame.dto.RolDto;
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
	
	
}
