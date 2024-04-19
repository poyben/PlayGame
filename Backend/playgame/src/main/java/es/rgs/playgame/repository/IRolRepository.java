package es.rgs.playgame.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import es.rgs.playgame.model.Rol;

public interface IRolRepository  extends JpaRepository<Rol,Integer>{
	Optional<Rol> findByName(String name);
}
