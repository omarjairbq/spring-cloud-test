/**
 * 
 */
package mx.com.omarjbq.item.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import mx.com.omarjbq.item.model.Item;
import mx.com.omarjbq.item.model.Producto;
import mx.com.omarjbq.item.service.ItemService;

/**
 * Implementación del servicio de items.
 * 
 * @author Omar Balbuena
 *
 */
@Service("itemServiceRestTemplate")
public class ItemServiceImpl implements ItemService {

	/**
	 * Cliente REST.
	 */
	@Autowired
	private RestTemplate restTemplate;

	@Override
	public List<Item> obtenerItems() {
		Producto[] productos = this.restTemplate.getForObject("http://servicio-productos/productos", Producto[].class);

		return Stream.of(productos).map(Item::new).collect(Collectors.toList());
	}

	@Override
	public Item obtenerItemPorProductoId(Long id, Long cantidad) {
		Map<String, String> uriVars = new HashMap<>();
		uriVars.put("id", id.toString());

		Producto producto = this.restTemplate.getForObject("http://servicio-productos/productos/{id}", Producto.class,
				uriVars);

		return new Item(producto, cantidad);
	}

	@Override
	public Producto crearProducto(Producto producto) {
		return this.restTemplate.postForObject("http://servicio-productos/productos", producto, Producto.class);
	}

	@Override
	public Producto actualizarProducto(Long id, Producto producto) {
		Map<String, String> uriVars = new HashMap<>();
		uriVars.put("id", id.toString());

		ResponseEntity<Producto> response = this.restTemplate.exchange("http://servicio-productos/productos/{id}",
				HttpMethod.PUT, new HttpEntity<Producto>(producto), Producto.class, uriVars);

		return response.getBody();
	}

	@Override
	public void eliminarProducto(Long id) {
		Map<String, String> uriVars = new HashMap<>();
		uriVars.put("id", id.toString());

		this.restTemplate.delete("http://servicio-productos/productos/{id}", uriVars);
	}

}
