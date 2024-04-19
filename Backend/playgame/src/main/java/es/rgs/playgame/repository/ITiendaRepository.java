package es.rgs.playgame.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import es.rgs.playgame.model.Tienda;

public interface ITiendaRepository  extends JpaRepository<Tienda,Integer>{
	Optional<Tienda> findByName(String name);

}
