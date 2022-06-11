package mx.com.omarjbq.productos.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import mx.com.omarjbq.productos.entity.ProductoEntity;

/**
 * Repositorio de la entidad de productos.
 * 
 * @author Omar Balbuena
 *
 */
@Repository
public interface ProductoRepository extends JpaRepository<ProductoEntity, Long> {

}
