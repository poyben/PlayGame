package es.rgs.playgame.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JuegoDto {
     int id;
     String name;
     float price;
     int stock;
     CategoriaDto categoria;
}
