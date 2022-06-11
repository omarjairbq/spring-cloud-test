package mx.com.omarjbq.usuarios.oauth.services;

import mx.com.omarjbq.commons.usuarios.entity.UsuarioEntity;

public interface IUserService {

	/**
	 * Busqueda del usuario.
	 * 
	 * @param username nombre de usuario
	 * @return UsuarioEntity
	 */
	UsuarioEntity findByUsername(String username);
}
