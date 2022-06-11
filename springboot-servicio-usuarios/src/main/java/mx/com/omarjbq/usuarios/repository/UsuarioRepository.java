package mx.com.omarjbq.usuarios.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

import mx.com.omarjbq.commons.usuarios.entity.UsuarioEntity;

@RepositoryRestResource(path = "usuarios")
public interface UsuarioRepository extends JpaRepository<UsuarioEntity, Long> {

	@RestResource(path = "find-user")
	Optional<UsuarioEntity> findByUsername(@Param("nombre") String username);
}
