package mx.com.omarjbq.productos.model;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * Producto.
 * 
 * @author Omar Balbuena
 *
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ProductoDTO {

	private Long id;

	private String nombre;

	private Double precio;

	private Date createdAt;
}
