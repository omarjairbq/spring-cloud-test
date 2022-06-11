package mx.com.omarjbq.item.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * Modelo de un item.
 * 
 * @author Omar Balbuena
 *
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Item {

	private Producto producto;

	private Long cantidad;

	/**
	 * Constructor.
	 * 
	 * @param producto objeto producto
	 */
	public Item(Producto producto) {
		this.producto = producto;
		this.cantidad = 1L;
	}

	/**
	 * Cálculo del total.
	 * 
	 * @return Double
	 */
	public Double getTotal() {
		return this.producto.getPrecio() * this.cantidad;
	}

}
