package es.rgs.playgame.service;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import es.rgs.playgame.dto.RolDto;
import es.rgs.playgame.model.Rol;
import es.rgs.playgame.repository.IRolRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RolService{

	private final IRolRepository rolRepository;
	
	public RolDto createRol(RolDto rolDto) {
		Rol rol = new Rol();
		rol.setName(rolDto.getName());
		Rol savedRol = rolRepository.save(rol);
		return new RolDto(savedRol.getId(), savedRol.getName());
	}
	
	
	 public ResponseEntity<List<Rol>> findAll() {
	        List<Rol> findAll = rolRepository.findAll();
	        if (findAll != null) {
	            return ResponseEntity.ok(findAll);
	        } else {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
	        }
	    }
	
	 public ResponseEntity<Rol> findById(Integer id) {
	        Optional<Rol> optionalRol = rolRepository.findById(id);
	        if (optionalRol.isPresent()) {
	            return ResponseEntity.ok(optionalRol.get());
	        } else {
	            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
	        }
	    }
	 
	 
	 public ResponseEntity<Void> deleteById(Integer id) {
		    if (rolRepository.existsById(id)) {
		        rolRepository.deleteById(id);
		        return ResponseEntity.noContent().build();
		    } else {
		        return ResponseEntity.notFound().build();
		    }
		}

	 
	 public ResponseEntity<Rol> updateRol(int id, Rol rol) {
		    Optional<Rol> rolOptional = rolRepository.findById(id);
		    if (rolOptional.isPresent()) {
		        Rol existingRol = rolOptional.get();
		        existingRol.setName(rol.getName());
		        
		        Rol updatedRol = rolRepository.save(existingRol);
		        return ResponseEntity.ok(updatedRol);
		    } else {
		        return ResponseEntity.notFound().build();
		    }
		}

	
}

