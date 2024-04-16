package es.rgs.playgame.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import es.rgs.playgame.model.Usuario;

public interface IUsuarioRepository extends JpaRepository<Usuario,Integer>{
	Optional<Usuario> findByUsername(String username);
	
	@Modifying()
	@Query("update User u set u.firstname=:firstname, u.lastname=:lastname,u.username=:username where u.id=:id")

	void updateUser(@Param(value="id") Integer id, @Param(value="firstname") String firstname, @Param(value="lastname") String lastname, @Param(value="username") String username);
}