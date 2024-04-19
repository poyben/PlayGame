package es.rgs.playgame.service;

import java.util.List;
import java.util.Optional;

import es.rgs.playgame.controller.CategoriaController;
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
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class JuegoService {

	private final IJuegoRepository juegoRepository;
	private final CategoriaService categoriaService;

	 CategoriaController categoriaController;
	
	
	
	public JuegoDto createJuego(JuegoRequest request) {

		Categoria categoria = categoriaService.findOrCreateCategoria(categoriaDtoToEntity(request.getCategoriaDto()));
	Juego juego = Juego.builder()
			.name(request.getName())
			.price(request.getPrice())
			.stock(request.getStock())
			.categoria(categoria)
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


	public ResponseEntity<Juego> updateJuego(int id, JuegoDto juego) {
		Optional<Juego> juegoOptional = juegoRepository.findById(id);
		if (juegoOptional.isPresent()) {
			Juego existingJuego = juegoOptional.get();
			existingJuego.setName(juego.getName());
			existingJuego.setPrice(juego.getPrice());
			existingJuego.setStock(juego.getStock());

			// Verificar si la categoría DTO no es nula
			if (juego.getCategoria() != null) {
				CategoriaDto categoriaDto = juego.getCategoria();

				// Crear una instancia de categoría basada en el DTO
				Categoria categoria = new Categoria();
				categoria.setName(categoriaDto.getName());

				// Buscar o crear la categoría en la base de datos
				Categoria existingCategoria = categoriaService.findOrCreateCategoria(categoria);

				// Establecer la nueva categoría en el juego
				existingJuego.setCategoria(existingCategoria);
			}

			// Guardar el juego actualizado en la base de datos
			Juego updatedJuego = juegoRepository.save(existingJuego);
			return ResponseEntity.ok(updatedJuego);
		} else {
			return ResponseEntity.notFound().build();
		}
	}
	
	
	public Categoria categoriaDtoToEntity(CategoriaDto categoriaDto) {
		if (categoriaDto != null) {
			Categoria categoria = new Categoria();
			categoria.setName(categoriaDto.getName());
			return categoria;
		} else {
			// Manejar el caso en que categoriaDto es nulo, por ejemplo, lanzar una excepción o devolver un valor por defecto
			// Aquí estoy lanzando una excepción para señalar el error
			throw new IllegalArgumentException("El objeto CategoriaDto es nulo");
		}
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
	            .categoria(categoriaToDto(juego.getCategoria()))
	            .build();
	}



	
}
