package es.rgs.playgame.model;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
public class Juego {
	@Id
	@GeneratedValue
	private int id;
    private String nombre;
    private float precio;
    private int stock;
    private Categoria categoria;
    
    @ManyToMany(mappedBy = "juegos")
    private List<Tienda> tiendas;
    
    
}
