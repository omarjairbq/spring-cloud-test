package mx.com.omarjbq.item.service;

import java.util.List;

import mx.com.omarjbq.item.model.Item;
import mx.com.omarjbq.item.model.Producto;

/**
 * Interfaz del los items.
 * 
 * @author Omar Balbuena
 *
 */
public interface ItemService {

	/**
	 * Consulta de todos los items.
	 * 
	 * @return List<Item>
	 */
	List<Item> obtenerItems();

	/**
	 * Consulta de un item por el id del producto.
	 * 
	 * @param id       identificador del producto
	 * @param cantidad cantidad de items
	 * @return Item
	 */
	Item obtenerItemPorProductoId(Long id, Long cantidad);

	/**
	 * Crear un producto.
	 * 
	 * @param producto nuevo producto
	 * @return Producto
	 */
	Producto crearProducto(Producto producto);

	/**
	 * Crear un producto.
	 * 
	 * @param id       identificador del producto
	 * @param producto nuevo producto
	 * @return Producto
	 */
	Producto actualizarProducto(Long id, Producto producto);

	/**
	 * Eliminar el producto.
	 * 
	 * @param id identificador del producto
	 */
	void eliminarProducto(Long id);

}
