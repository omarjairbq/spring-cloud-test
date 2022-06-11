package mx.com.omarjbq.item.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
import lombok.extern.slf4j.Slf4j;
import mx.com.omarjbq.item.model.Item;
import mx.com.omarjbq.item.model.Producto;
import mx.com.omarjbq.item.service.ItemService;

/**
 * Controlador de items.
 * 
 * @author Omar Balbuena
 *
 */
@RefreshScope
@RestController
@Slf4j
public class ItemController {

	@Autowired
	private CircuitBreakerFactory cvFactory;

	@Value("${configuracion.texto}")
	private String text;

	/**
	 * Servicio de items.
	 */
	private ItemService service;

	/**
	 * Constructor
	 * 
	 * @param service
	 */
	public ItemController(@Qualifier("itemServiceFeign") ItemService service) {
		this.service = service;
	}

	/**
	 * Consulta de items.
	 * 
	 * @return List<Item>
	 */
	@GetMapping(value = "/items")
	@ResponseStatus(value = org.springframework.http.HttpStatus.OK)
	public List<Item> obtenerItems(@RequestParam(name = "user", required = false) String user,
			@RequestHeader(name = "token-request", required = false) String token) {
		log.info("REQUEST PARAM: {}", user);
		log.info("REQUEST HEADER: {}", token);
		return this.service.obtenerItems();
	}

	/**
	 * Consulta de un item.
	 * 
	 * @param id       identificador
	 * @param cantidad cantidad
	 * @return Item
	 */
	@GetMapping(value = "/items/{id}/cantidad/{cantidad}")
	@ResponseStatus(value = org.springframework.http.HttpStatus.OK)
	public Item obtenerItem(@PathVariable("id") Long id, @PathVariable("cantidad") Long cantidad) {
		return cvFactory.create("items").run(() -> this.service.obtenerItemPorProductoId(id, cantidad),
				ex -> this.metodoAlternativo(id, cantidad, ex));
	}

	@CircuitBreaker(name = "items", fallbackMethod = "metodoAlternativo")
	@GetMapping(value = "/v2/items/{id}/cantidad/{cantidad}")
	@ResponseStatus(value = org.springframework.http.HttpStatus.OK)
	public Item obtenerItemV2(@PathVariable("id") Long id, @PathVariable("cantidad") Long cantidad) {
		return this.service.obtenerItemPorProductoId(id, cantidad);
	}

	@CircuitBreaker(name = "items", fallbackMethod = "metodoAlternativoV3")
	@TimeLimiter(name = "items")
	@GetMapping(value = "/v3/items/{id}/cantidad/{cantidad}")
	@ResponseStatus(value = org.springframework.http.HttpStatus.OK)
	public CompletableFuture<Item> obtenerItemV3(@PathVariable("id") Long id, @PathVariable("cantidad") Long cantidad) {
		return CompletableFuture.supplyAsync(() -> this.service.obtenerItemPorProductoId(id, cantidad));
	}

	@GetMapping(value = "/configuraciones")
	@ResponseStatus(value = org.springframework.http.HttpStatus.OK)
	public ResponseEntity<Map<String, String>> obtenerConfiguraciones(@Value("${server.port}") String port) {

		Map<String, String> json = new HashMap<>();
		json.put("texto", this.text);
		json.put("port", port);

		return ResponseEntity.ok(json);
	}

	/**
	 * Creación de un producto.
	 * 
	 * @param producto nuevo producto
	 * @return Producto
	 */
	@PostMapping(value = "/productos")
	@ResponseStatus(value = org.springframework.http.HttpStatus.OK)
	public Producto crearProducto(@RequestBody Producto producto) {
		return this.service.crearProducto(producto);
	}

	@PutMapping(value = "/productos/{id}")
	@ResponseStatus(value = org.springframework.http.HttpStatus.OK)
	public Producto actualizarProducto(@PathVariable("id") Long id, @RequestBody Producto producto) {
		return this.service.actualizarProducto(id, producto);
	}

	@DeleteMapping(value = "/productos/{id}")
	@ResponseStatus(value = org.springframework.http.HttpStatus.NO_CONTENT)
	public void eliminarProducto(@PathVariable("id") Long id) {
		this.service.eliminarProducto(id);
	}

	/**
	 * Método alternativo hystrix
	 * 
	 * @param id       identificador
	 * @param cantidad cantidad
	 * @return Item
	 */
	public Item metodoAlternativo(Long id, Long cantidad, Throwable ex) {
		log.error("Error detalle circuitbreaker: {}", ex);

		Producto producto = new Producto();
		producto.setId(id);
		producto.setNombre("Cámara Sony");
		producto.setPrecio(500.00);
		return new Item(producto, cantidad);
	}

	/**
	 * Método alternativo hystrix
	 * 
	 * @param id       identificador
	 * @param cantidad cantidad
	 * @return Item
	 */
	public CompletableFuture<Item> metodoAlternativoV3(Long id, Long cantidad, Throwable ex) {
		log.error("Error detalle circuitbreaker: {}", ex);

		Producto producto = new Producto();
		producto.setId(id);
		producto.setNombre("Cámara Sony");
		producto.setPrecio(500.00);
		return CompletableFuture.supplyAsync(() -> new Item(producto, cantidad));
	}
}
