package mx.com.omarjbq.item.model;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * Modelo de un producto.
 * 
 * @author Omar Balbuena
 *
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Producto {

	private Long id;

	private String nombre;

	private Double precio;

	private Date createdAt;
}
