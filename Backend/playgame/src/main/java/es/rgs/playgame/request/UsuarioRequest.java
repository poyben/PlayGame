package es.rgs.playgame.request;

import es.rgs.playgame.model.Rol;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioRequest {

	//int id;
	String username;
	String firstname;
	String lastname;
	Rol rol;
	
}
