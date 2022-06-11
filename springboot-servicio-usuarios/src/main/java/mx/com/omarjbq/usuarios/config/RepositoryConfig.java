package mx.com.omarjbq.usuarios.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.springframework.web.servlet.config.annotation.CorsRegistry;

import mx.com.omarjbq.commons.usuarios.entity.RolEntity;
import mx.com.omarjbq.commons.usuarios.entity.UsuarioEntity;

@Configuration
public class RepositoryConfig implements RepositoryRestConfigurer {

	@Override
	public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config, CorsRegistry cors) {
		config.exposeIdsFor(UsuarioEntity.class, RolEntity.class);
	}

	
	
}
