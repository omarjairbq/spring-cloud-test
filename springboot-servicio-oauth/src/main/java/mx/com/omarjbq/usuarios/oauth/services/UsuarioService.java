package mx.com.omarjbq.usuarios.oauth.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import brave.Tracer;
import lombok.extern.slf4j.Slf4j;
import mx.com.omarjbq.commons.usuarios.entity.RolEntity;
import mx.com.omarjbq.commons.usuarios.entity.UsuarioEntity;
import mx.com.omarjbq.usuarios.oauth.client.UsuarioFeignClient;

@Service
@Slf4j
public class UsuarioService implements UserDetailsService, IUserService {

	@Autowired
	private UsuarioFeignClient usuarioClient;
	
	@Autowired
	private Tracer tracer;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UsuarioEntity userEntity = this.usuarioClient.findByUsername(username);

		if (ObjectUtils.isEmpty(userEntity)) {
			final String error = "Usuario no encontrado";
			log.error(error);
			tracer.currentSpan().tag("error", error);
			throw new UsernameNotFoundException(error);
		}

		List<SimpleGrantedAuthority> roles = userEntity.getRoles().stream().map(RolEntity::getNombre)
				.map(SimpleGrantedAuthority::new).collect(Collectors.toList());

		return new User(userEntity.getUsername(), userEntity.getPassword(), userEntity.getActivo(), true, true, true,
				roles);
	}

	@Override
	public UsuarioEntity findByUsername(String username) {
		return this.usuarioClient.findByUsername(username);
	}

}
