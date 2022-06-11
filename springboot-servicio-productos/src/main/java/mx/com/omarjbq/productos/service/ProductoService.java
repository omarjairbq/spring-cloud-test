package mx.com.omarjbq.productos.service;

import java.util.List;

import mx.com.omarjbq.productos.model.ProductoDTO;

/**
 * Servicio de productos.
 * 
 * @author Omar Balbuena
 *
 */
public interface ProductoService {

	/**
	 * Consulta de productos.
	 * 
	 * @return List<ProductoDTO>
	 */
	List<ProductoDTO> obtenerProductos();

	/**
	 * Consulta de un producto por id.
	 * 
	 * @param id identificador
	 * @return ProductoDTO
	 */
	ProductoDTO obtenerProductoPorID(Long id);

	/**
	 * Creación de un producto.
	 * 
	 * @param producto producto a crear
	 * @return ProductoDTO
	 */
	ProductoDTO guardar(ProductoDTO producto);

	/**
	 * Eliminar producto por id.
	 * 
	 * @param id identificador del producto
	 */
	void eliminarPorId(Long id);

	/**
	 * Actualizar producto.
	 * 
	 * @param id       identificador del producto
	 * @param producto producto a crear
	 * @return ProductoDTO
	 */
	ProductoDTO actualizarProducto(Long id, ProductoDTO producto);

}
