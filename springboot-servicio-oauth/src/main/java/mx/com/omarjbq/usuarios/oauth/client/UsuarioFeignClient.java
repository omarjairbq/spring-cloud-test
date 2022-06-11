package mx.com.omarjbq.usuarios.oauth.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import mx.com.omarjbq.commons.usuarios.entity.UsuarioEntity;

@FeignClient(name = "servicio-usuarios")
public interface UsuarioFeignClient {

	@GetMapping("/usuarios/search/find-user")
	UsuarioEntity findByUsername(@RequestParam("nombre") String username);
}
