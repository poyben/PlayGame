package es.rgs.playgame.service;

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

@Service
@RequiredArgsConstructor
public class AuthService {

	private final IUsuarioRepository userRepository;
	private final JwtService jwtService;
	private final PasswordEncoder passwordEncoder;
	private final AuthenticationManager authnticationManager;
	
	public AuthResponse login(LoginRequest request) {
		authnticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
		UserDetails user = userRepository.findByUsername(request.getUsername()).orElseThrow();
		String token= jwtService.getToken(user);
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
				.rol(request.getRol())
				.build();
		
		userRepository.save(user);
		return AuthResponse.builder()
				.token(jwtService.getToken(user))
				.build();
	}

}
