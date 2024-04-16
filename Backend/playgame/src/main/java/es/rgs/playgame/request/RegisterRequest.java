package es.rgs.playgame.request;

import es.rgs.playgame.model.Rol;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class RegisterRequest {
	
	String email;
	String username;
	String password;
	String firstname;
	String lastname;
	Rol rol;
	
}
