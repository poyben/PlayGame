package es.rgs.playgame.service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import es.rgs.playgame.dto.UsuarioDto;
import es.rgs.playgame.model.Tienda;
import es.rgs.playgame.model.Usuario;
import es.rgs.playgame.repository.ITiendaRepository;
import es.rgs.playgame.repository.IUsuarioRepository;
import es.rgs.playgame.request.UsuarioRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UsuarioService {

	private final IUsuarioRepository userRepository;
	private final ITiendaRepository tiendaRepository;
	
	@Transactional
	public ResponseEntity<Usuario> updateUser(int id, UsuarioRequest userRequest) {
	    Usuario existingUser = userRepository.findById(id).orElse(null);
	    
	    if (existingUser != null) {
	        existingUser.setFirstname(userRequest.getFirstname());
	        existingUser.setLastname(userRequest.getLastname());
	        existingUser.setUsername(userRequest.getUsername());

	        userRepository.save(existingUser);

	        return ResponseEntity.ok(existingUser);
	    } else {
	        return ResponseEntity.notFound().build();
	    }
	}

	
	public UsuarioDto getUsuario(Integer id) {
		Usuario user = userRepository.findById(id).orElse(null);
		
		if(user!=null) {
			UsuarioDto userDto = UsuarioDto.builder()
					.id(user.getId())
					.username(user.getUsername())
					.firstname(user.getFirstname())
					.lastname(user.getLastname())
					.rol(user.getRol())
					.build();
			return userDto;
		}
		return null;
		
	}
	
	
	 public UsuarioDto findById(Integer id) {
	        Optional<Usuario> optionalUser = userRepository.findById(id);
	        if (optionalUser.isPresent()) {
	            Usuario user = optionalUser.get();
	            UsuarioDto userDto = UsuarioDto.builder()
	                    .id(user.getId())
	                    .username(user.getUsername())
	                    .firstname(user.getFirstname())
	                    .lastname(user.getLastname())
	                    .rol(user.getRol())
	                    .build();
	            return userDto;
	        } else {
	            return null; 
	        }
	    }
	
	 public ResponseEntity<List<Usuario>> findAll() {
	        List<Usuario> findAll = userRepository.findAll();
	        if (findAll != null) {
	            return ResponseEntity.ok(findAll);
	        } else {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
	        }
	    }
	 
	 
	 public ResponseEntity<Void> deleteById(Integer id) {
		    if (userRepository.existsById(id)) {
		    	userRepository.deleteById(id);
		        return ResponseEntity.noContent().build();
		    } else {
		        return ResponseEntity.notFound().build();
		    }
		}
	
	 
	 public ResponseEntity<Void> assignUserToStore(int userId, int storeId) {
		 Usuario usuario = userRepository.findById(userId)
		            .orElseThrow(NoSuchElementException::new);
		    Tienda tienda = tiendaRepository.findById(storeId)
		            .orElseThrow(NoSuchElementException::new);
	        usuario.setTienda(tienda);
	        userRepository.save(usuario);

	        return ResponseEntity.ok().build();
	    }
	 
}
