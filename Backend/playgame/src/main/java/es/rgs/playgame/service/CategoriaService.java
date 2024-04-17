package es.rgs.playgame.service;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import es.rgs.playgame.dto.CategoriaDto;
import es.rgs.playgame.model.Categoria;
import es.rgs.playgame.model.Rol;
import es.rgs.playgame.repository.ICategoriaRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CategoriaService {

	private final ICategoriaRepository categoriaRepository;
	
	public CategoriaDto createCategoria(CategoriaDto categoriaDto) {
		Categoria categoria = new Categoria();
		categoria.setName(categoriaDto.getName());
		Categoria savedCategoria = categoriaRepository.save(categoria);
		return new CategoriaDto(savedCategoria.getId(), savedCategoria.getName());
	}
	
	
	public ResponseEntity<List<Categoria>> findAll() {
        List<Categoria> findAll = categoriaRepository.findAll();
        if (findAll != null) {
            return ResponseEntity.ok(findAll);
        } else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

 public ResponseEntity<Categoria> findById(Integer id) {
        Optional<Categoria> optionalCategoria = categoriaRepository.findById(id);
        if (optionalCategoria.isPresent()) {
            return ResponseEntity.ok(optionalCategoria.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }
 
 
 public ResponseEntity<Void> deleteById(Integer id) {
	    if (categoriaRepository.existsById(id)) {
	        categoriaRepository.deleteById(id);
	        return ResponseEntity.noContent().build();
	    } else {
	        return ResponseEntity.notFound().build();
	    }
	}

 
 public ResponseEntity<Categoria> updateCategoria(int id, Categoria categoria) {
	    Optional<Categoria> categoriaOptional = categoriaRepository.findById(id);
	    if (categoriaOptional.isPresent()) {
	    	Categoria existingCategoria = categoriaOptional.get();
	        existingCategoria.setName(categoria.getName());
	        
	        Categoria updatedCategoria = categoriaRepository.save(existingCategoria);
	        return ResponseEntity.ok(updatedCategoria);
	    } else {
	        return ResponseEntity.notFound().build();
	    }
	}

	
}
