package es.rgs.playgame.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import es.rgs.playgame.model.Usuario;

@Repository
public interface IUsuarioRepository extends JpaRepository<Usuario,Integer>{
	Optional<Usuario> findByUsername(String username);
	
	@Modifying()
	@Query("update Usuario u set u.firstname=:firstname, u.lastname=:lastname,u.username=:username where u.id=:id")

	void updateUser(@Param(value="id") Integer id, @Param(value="firstname") String firstname, @Param(value="lastname") String lastname, @Param(value="username") String username);
}