package es.rgs.playgame.service;

import es.rgs.playgame.dto.RolDto;
import es.rgs.playgame.model.Rol;
import es.rgs.playgame.repository.IRolRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import es.rgs.playgame.jwt.JwtService;
import es.rgs.playgame.model.Usuario;
import es.rgs.playgame.repository.IUsuarioRepository;
import es.rgs.playgame.request.LoginRequest;
import es.rgs.playgame.request.RegisterRequest;
import es.rgs.playgame.response.AuthResponse;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {

	private final IUsuarioRepository userRepository;
	private final JwtService jwtService;
	private final PasswordEncoder passwordEncoder;
	private final AuthenticationManager authenticationManager;
	private final IRolRepository rolRepository;

	/*
	public AuthResponse login(LoginRequest request) {
		authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
		UserDetails user = userRepository.findByUsername(request.getUsername()).orElseThrow();
		String token= jwtService.getToken(user);
		return AuthResponse.builder()
				.token(token)
				.build();
	}
	*/
	
	public AuthResponse login(LoginRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        UserDetails user = userRepository.findByUsername(request.getUsername()).orElseThrow();
        String token = jwtService.getToken(user);
		return AuthResponse.builder()
				.token(token)
				.build();
    }

	public AuthResponse register(RegisterRequest request) {

		Usuario user = Usuario.builder()
				.username(request.getUsername())
				.password(passwordEncoder.encode(request.getPassword()))
				.firstname(request.getFirstname())
				.lastname(request.getLastname())
				.email(request.getEmail())
				.rol(rolDtoToEntity(request.getRol()))
				.build();
		
		userRepository.save(user);
		return AuthResponse.builder()
				.token(jwtService.getToken(user))
				.build();
	}

	public Rol rolDtoToEntity(RolDto rolDto) {
		Optional<Rol> optionalRol = rolRepository.findByName(rolDto.getName());
		return optionalRol.orElseThrow(() -> new RuntimeException("Rol no encontrado: " + rolDto.getName()));
	}

}
