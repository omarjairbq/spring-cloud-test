package mx.com.omarjbq.item.clientes;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import mx.com.omarjbq.item.model.Producto;

@FeignClient(name = "servicio-productos")
public interface ProductoClienteRest {

	@GetMapping(value = "/productos")
	public List<Producto> obtenerProductos();

	@GetMapping(value = "/productos/{id}")
	public Producto obtenerProducto(@PathVariable Long id);

	@PostMapping(value = "/productos")
	public Producto crearProducto(@RequestBody Producto producto);

	@PutMapping(value = "/productos/{id}")
	public Producto actualizarProducto(@PathVariable Long id, @RequestBody Producto producto);

	@DeleteMapping(value = "/productos/{id}")
	public void eliminarProducto(@PathVariable Long id);
}
