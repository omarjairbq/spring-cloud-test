package mx.com.omarjbq.productos.controller;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;
import mx.com.omarjbq.productos.model.ProductoDTO;
import mx.com.omarjbq.productos.service.ProductoService;

/**
 * Controlador de productos.
 * 
 * @author Omar Balbuena
 *
 */
@RestController
@Slf4j
public class ProductoController {

	/**
	 * Servicio productos.
	 */
	private ProductoService service;

	/**
	 * Constructor.
	 * 
	 * @param service servicio
	 */
	public ProductoController(final ProductoService service) {
		this.service = service;
	}

	/**
	 * Consulta de todos los productos.
	 * 
	 * @return List<ProductoDTO>
	 */
	@ResponseStatus(value = org.springframework.http.HttpStatus.OK)
	@GetMapping(value = "/productos")
	public List<ProductoDTO> obtenerProductos() {
		log.info("obtenerProductos");
		return this.service.obtenerProductos();
	}

	/**
	 * Consulta de todos los productos.
	 * 
	 * @return List<ProductoDTO>
	 * @throws InterruptedException
	 */
	@ResponseStatus(value = org.springframework.http.HttpStatus.OK)
	@GetMapping(value = "/productos/{id}")
	public ProductoDTO obtenerProducto(@PathVariable("id") Long id) throws InterruptedException {
		log.info("obtenerProducto");

		if (id.equals(10L)) {
			throw new IllegalStateException("Producto no encontrado");
		} else if (id.equals(7L)) {
			TimeUnit.SECONDS.sleep(5L);
		}

		return this.service.obtenerProductoPorID(id);
	}

	/**
	 * Crear un producto.
	 * 
	 * @param producto producto
	 * @return ProductoDTO
	 */
	@ResponseStatus(value = org.springframework.http.HttpStatus.OK)
	@PostMapping(value = "/productos")
	public ProductoDTO crearProducto(@RequestBody ProductoDTO producto) {
		return this.service.guardar(producto);
	}

	/**
	 * Eliminar un producto.
	 * 
	 * @param id identificador
	 */
	@ResponseStatus(value = org.springframework.http.HttpStatus.NO_CONTENT)
	@DeleteMapping(value = "/productos/{id}")
	public void eliminarProducto(@PathVariable("id") Long id) {
		this.service.eliminarPorId(id);
	}

	/**
	 * Actualizar un producto
	 * 
	 * @param id       identificador
	 * @param producto request
	 * @return ProductoDTO
	 */
	@ResponseStatus(value = org.springframework.http.HttpStatus.OK)
	@PutMapping(value = "/productos/{id}")
	public ProductoDTO actualizarProducto(@PathVariable("id") Long id, @RequestBody ProductoDTO producto) {
		return this.service.actualizarProducto(id, producto);
	}
}
