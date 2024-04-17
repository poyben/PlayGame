package es.rgs.playgame.service;

import org.springframework.stereotype.Service;

import es.rgs.playgame.dto.RolDto;
import es.rgs.playgame.model.Categoria;
import es.rgs.playgame.model.Rol;
import es.rgs.playgame.repository.IRolRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RolService {

	private final IRolRepository rolRepository;
	
	public RolDto createRol(RolDto rolDto) {
		Rol rol = new Rol();
		rol.setName(rolDto.getName());
		Rol savedRol = rolRepository.save(rol);
		return new RolDto(savedRol.getId(), savedRol.getName());
	}
	
}

