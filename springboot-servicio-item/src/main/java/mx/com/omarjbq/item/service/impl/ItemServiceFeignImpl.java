package mx.com.omarjbq.item.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import mx.com.omarjbq.item.clientes.ProductoClienteRest;
import mx.com.omarjbq.item.model.Item;
import mx.com.omarjbq.item.model.Producto;
import mx.com.omarjbq.item.service.ItemService;

/**
 * Implementación feign servicio de items.
 * 
 * @author Omar Balbuena
 *
 */
@Service("itemServiceFeign")
public class ItemServiceFeignImpl implements ItemService {

	/**
	 * Client REST.
	 */
	private ProductoClienteRest productoClientREST;

	/**
	 * Constructor.
	 * 
	 * @param productoClientREST cliente REST
	 */
	public ItemServiceFeignImpl(ProductoClienteRest productoClientREST) {
		this.productoClientREST = productoClientREST;
	}

	@Override
	public List<Item> obtenerItems() {
		return productoClientREST.obtenerProductos().stream().map(Item::new).collect(Collectors.toList());
	}

	@Override
	public Item obtenerItemPorProductoId(Long id, Long cantidad) {
		Producto producto = this.productoClientREST.obtenerProducto(id);
		return new Item(producto, cantidad);
	}

	@Override
	public Producto crearProducto(Producto producto) {
		return this.productoClientREST.crearProducto(producto);
	}

	@Override
	public Producto actualizarProducto(Long id, Producto producto) {
		return this.productoClientREST.actualizarProducto(id, producto);
	}

	@Override
	public void eliminarProducto(Long id) {
		this.productoClientREST.eliminarProducto(id);
	}

}
