package es.rgs.playgame.request;

import es.rgs.playgame.dto.CategoriaDto;
import es.rgs.playgame.model.Categoria;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JuegoRequest {
     String name;
     float price;
     int stock;
     CategoriaDto categoriaDto;
}