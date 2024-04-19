package es.rgs.playgame.service;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import es.rgs.playgame.dto.RolDto;
import es.rgs.playgame.dto.TiendaDto;
import es.rgs.playgame.model.Rol;
import es.rgs.playgame.model.Tienda;
import es.rgs.playgame.repository.IRolRepository;
import es.rgs.playgame.repository.ITiendaRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TiendaService {

	private final ITiendaRepository tiendaRepository;
	
	public TiendaDto createTienda(TiendaDto tiendaDto) {
		Tienda tienda = new Tienda();
		tienda.setName(tiendaDto.getName());
		tienda.setAddress(tiendaDto.getAddress());
		Tienda savedTienda =tiendaRepository.save(tienda);
		return new TiendaDto(savedTienda.getId(), savedTienda.getName(), savedTienda.getAddress());
	}
	
	public ResponseEntity<List<Tienda>> findAll() {
        List<Tienda> findAll = tiendaRepository.findAll();
        if (findAll != null) {
            return ResponseEntity.ok(findAll);
        } else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }
	
	public ResponseEntity<Tienda> findById(Integer id) {
        Optional<Tienda> optionalTienda = tiendaRepository.findById(id);
        if (optionalTienda.isPresent()) {
            return ResponseEntity.ok(optionalTienda.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }
	
	
	public ResponseEntity<Void> deleteById(Integer id) {
	    if (tiendaRepository.existsById(id)) {
	    	tiendaRepository.deleteById(id);
	        return ResponseEntity.noContent().build();
	    } else {
	        return ResponseEntity.notFound().build();
	    }
	}
	
	
	public ResponseEntity<Tienda> updateTienda(int id, Tienda tienda) {
	    Optional<Tienda> tiendaOptional = tiendaRepository.findById(id);
	    if (tiendaOptional.isPresent()) {
	    	Tienda existingTienda = tiendaOptional.get();
	        existingTienda.setName(tienda.getName());
	        existingTienda.setAddress(tienda.getAddress());
	        Tienda updatedTienda = tiendaRepository.save(existingTienda);
	        return ResponseEntity.ok(updatedTienda);
	    } else {
	        return ResponseEntity.notFound().build();
	    }
	}
	
}
