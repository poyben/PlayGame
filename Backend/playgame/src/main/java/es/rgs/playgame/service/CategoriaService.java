package es.rgs.playgame.service;

import org.springframework.stereotype.Service;

import es.rgs.playgame.dto.CategoriaDto;
import es.rgs.playgame.model.Categoria;
import es.rgs.playgame.repository.ICategoriaRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CategoriaService {

	private final ICategoriaRepository categoriaRepository;
	
	public CategoriaDto createCategoria(CategoriaDto categoriaDto) {
		Categoria categoria = new Categoria();
		categoria.setName(categoriaDto.getName());
		Categoria savedCategoria = categoriaRepository.save(categoria);
		return new CategoriaDto(savedCategoria.getId(), savedCategoria.getName());
	}
	
}
