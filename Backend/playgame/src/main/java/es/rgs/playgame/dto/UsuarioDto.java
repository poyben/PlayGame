package es.rgs.playgame.dto;

import es.rgs.playgame.model.Rol;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioDto {

	int id;
	String email;
	String username;
	String firstname;
	String lastname;
	Rol rol;
	
}
