package es.rgs.playgame.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import es.rgs.playgame.dto.UsuarioDto;
import es.rgs.playgame.model.Usuario;
import es.rgs.playgame.repository.IUsuarioRepository;
import es.rgs.playgame.request.UsuarioRequest;
import es.rgs.playgame.response.UsuarioResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UsuarioService {

	private final IUsuarioRepository userRepository;
	
	@Transactional
	public UsuarioResponse updateUser(UsuarioRequest userRequest) {
		
		Usuario existingUser = userRepository.findById(userRequest.getId()).orElse(null);
	    
	    if (existingUser != null) {
	        Usuario updatedUser = Usuario.builder()
	            .id(existingUser.getId())
	            .firstname(userRequest.getFirstname())
	            .lastname(userRequest.getLastname())
	            .username(userRequest.getUsername())
	            .build();
	        
	        userRepository.updateUser(updatedUser.getId(), updatedUser.getFirstname(), 
	                updatedUser.getLastname(), updatedUser.getUsername());
	        
	        return new UsuarioResponse("El usuario se ha modificado");
	    } else {
	        return new UsuarioResponse("El usuario no existe");
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
	
	
	
}
