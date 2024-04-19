package es.rgs.playgame.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import es.rgs.playgame.model.Juego;

public interface IJuegoRepository extends JpaRepository<Juego,Integer>{
	Optional<Juego> findByName(String name);
}

