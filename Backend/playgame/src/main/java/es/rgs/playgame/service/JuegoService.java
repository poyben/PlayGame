package es.rgs.playgame.service;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import es.rgs.playgame.dto.CategoriaDto;
import es.rgs.playgame.dto.JuegoDto;
import es.rgs.playgame.model.Categoria;
import es.rgs.playgame.model.Juego;
import es.rgs.playgame.model.Tienda;
import es.rgs.playgame.model.Usuario;
import es.rgs.playgame.repository.IJuegoRepository;
import es.rgs.playgame.request.JuegoRequest;
import es.rgs.playgame.request.RegisterRequest;
import es.rgs.playgame.response.AuthResponse;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class JuegoService {

	private final IJuegoRepository juegoRepository;
	
	/*
	public JuegoDto createJuego(JuegoDto juegoDto) {
	    Juego juego = new Juego();
	    juego.setName(juegoDto.getName());  
	    juego.setPrice(juegoDto.getPrice());
	    juego.setStock(juegoDto.getStock());
	    juego.setCategoria(categoriaDtoToEntity(juegoDto.getCategoriaDto()));  
	    Juego savedJuego = juegoRepository.save(juego);
	    return new JuegoDto(savedJuego.getId(), savedJuego.getName(), savedJuego.getPrice(), savedJuego.getStock(), categoriaToDto(savedJuego.getCategoria()));
	}
	*/
	/*
	public JuegoDto createJuego(JuegoDto juegoDto) {
	    Juego juego = new Juego();
	    juego.setName(juegoDto.getName());  
	    juego.setPrice(juegoDto.getPrice());
	    juego.setStock(juegoDto.getStock());
	    
	    // Verificar si la categoría DTO es nula
	    if (juegoDto.getCategoriaDto() != null) {
	        // Convertir la categoría DTO a entidad de categoría
	        Categoria categoria = categoriaDtoToEntity(juegoDto.getCategoriaDto());
	        // Establecer la categoría en el juego
	        juego.setCategoria(categoria);
	    }
	    
	    // Guardar el juego en la base de datos
	    Juego savedJuego = juegoRepository.save(juego);
	    
	    // Convertir la categoría de la entidad de juego a DTO de categoría
	    CategoriaDto categoriaDto = categoriaToDto(savedJuego.getCategoria());
	    
	    // Crear y retornar un nuevo DTO de juego con la categoría DTO establecida
	    return new JuegoDto(savedJuego.getId(), savedJuego.getName(), savedJuego.getPrice(), savedJuego.getStock(), categoriaDto);
	}
	*/
	
	public JuegoDto createJuego(JuegoRequest request) {
	Juego juego = Juego.builder()
			.name(request.getName())
			.price(request.getPrice())
			.stock(request.getStock())
			.categoria(request.getCategoria())
			.build();
	
	Juego savedJuego = juegoRepository.save(juego);
    return juegoToDto(savedJuego);
	}
	public ResponseEntity<List<Juego>> findAll() {
        List<Juego> findAll = juegoRepository.findAll();
        if (findAll != null) {
            return ResponseEntity.ok(findAll);
        } else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }
	
	public ResponseEntity<Juego> findById(Integer id) {
        Optional<Juego> optionalJuego = juegoRepository.findById(id);
        if (optionalJuego.isPresent()) {
            return ResponseEntity.ok(optionalJuego.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }
	
	
	public ResponseEntity<Void> deleteById(Integer id) {
	    if (juegoRepository.existsById(id)) {
	    	juegoRepository.deleteById(id);
	        return ResponseEntity.noContent().build();
	    } else {
	        return ResponseEntity.notFound().build();
	    }
	}
	
	
	public ResponseEntity<Juego> updateJuego(int id, Juego juego) {
	    Optional<Juego> juegoOptional = juegoRepository.findById(id);
	    if (juegoOptional.isPresent()) {
	    	Juego existingJuego = juegoOptional.get();
	        existingJuego.setName(juego.getName());
	        existingJuego.setPrice(juego.getPrice());
	        existingJuego.setStock(juego.getStock());
	        existingJuego.setCategoria(juego.getCategoria());
	        Juego updatedJuego = juegoRepository.save(existingJuego);
	        return ResponseEntity.ok(updatedJuego);
	    } else {
	        return ResponseEntity.notFound().build();
	    }
	}
	
	
	public Categoria categoriaDtoToEntity(CategoriaDto categoriaDto) {
        Categoria categoria = new Categoria();
        categoria.setName(categoriaDto.getName());
        return categoria;
    }
	
	public CategoriaDto categoriaToDto(Categoria categoria) {
        return CategoriaDto.builder()
            .name(categoria.getName())
            .build();
    }
	
	public JuegoDto juegoToDto(Juego juego) {
	    return JuegoDto.builder()
	            .id(juego.getId())
	            .name(juego.getName())
	            .price(juego.getPrice())
	            .stock(juego.getStock())
	            .categoria(juego.getCategoria())
	            .build();
	}
	
}
