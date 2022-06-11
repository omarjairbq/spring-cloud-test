/**
 * 
 */
package mx.com.omarjbq.productos.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;

import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.com.omarjbq.productos.entity.ProductoEntity;
import mx.com.omarjbq.productos.model.ProductoDTO;
import mx.com.omarjbq.productos.repository.ProductoRepository;
import mx.com.omarjbq.productos.service.ProductoService;

/**
 * Implementación servicio de productos.
 * 
 * @author Omar Balbuena
 *
 */
@Service
public class ProductoServiceImpl implements ProductoService {

	/**
	 * Repositorio.
	 */
	private ProductoRepository productoRepository;

	/**
	 * Objeto Dozer para mapear.
	 */
	@Autowired
	private Mapper dozerMapper;

	/**
	 * Constructor.
	 * 
	 * @param productoRepository repositorio de productos
	 */
	public ProductoServiceImpl(final ProductoRepository productoRepository) {
		this.productoRepository = productoRepository;
	}

	@Override
	public List<ProductoDTO> obtenerProductos() {
		return this.productoRepository.findAll().stream()
				.map(producto -> this.dozerMapper.map(producto, ProductoDTO.class)).collect(Collectors.toList());
	}

	@Override
	public ProductoDTO obtenerProductoPorID(Long id) {
		ProductoEntity entity = this.productoRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException(id.toString()));

		return this.dozerMapper.map(entity, ProductoDTO.class);
	}

	@Override
	@Transactional
	public ProductoDTO guardar(ProductoDTO producto) {
		ProductoEntity entity = this.dozerMapper.map(producto, ProductoEntity.class);

		return this.dozerMapper.map(this.productoRepository.save(entity), ProductoDTO.class);
	}

	@Override
	@Transactional
	public void eliminarPorId(Long id) {
		ProductoEntity entity = this.productoRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException(id.toString()));

		this.productoRepository.delete(entity);
	}

	@Override
	public ProductoDTO actualizarProducto(Long id, ProductoDTO producto) {
		ProductoEntity entity = this.productoRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException(id.toString()));

		entity.setCreatedAt(producto.getCreatedAt());
		entity.setNombre(producto.getNombre());
		entity.setPrecio(producto.getPrecio());

		return this.dozerMapper.map(this.productoRepository.save(entity), ProductoDTO.class);
	}

}
