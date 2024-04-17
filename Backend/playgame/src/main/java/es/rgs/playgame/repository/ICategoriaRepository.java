package es.rgs.playgame.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import es.rgs.playgame.model.Categoria;

public interface ICategoriaRepository  extends JpaRepository<Categoria,Integer>{
	Optional<Categoria> findByName(String name);
}
