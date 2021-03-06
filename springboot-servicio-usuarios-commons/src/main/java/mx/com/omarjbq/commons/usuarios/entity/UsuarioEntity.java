package mx.com.omarjbq.commons.usuarios.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * Entidad del usuario.
 * 
 * @author Omar Balbuena
 *
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "usuarios")
public class UsuarioEntity implements Serializable {
	/**
	 * Serialización.
	 */
	private static final long serialVersionUID = 6071109645872501569L;

	/**
	 * Identificador.
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_usuario", nullable = false)
	private Long id;

	/**
	 * Password.
	 */
	@Column(name = "password", length = 60, nullable = false)
	private String password;

	/**
	 * Usuario.
	 */
	@Column(name = "user_name", unique = true, length = 20, nullable = false)
	private String username;

	/**
	 * Nombre.
	 */
	@Column(name = "nombre")
	private String nombre;

	/**
	 * Apellido.
	 */
	@Column(name = "apellido")
	private String apellido;

	/**
	 * Correo.
	 */
	@Column(name = "email", unique = true, length = 100)
	private String email;

	/**
	 * Bandera.
	 */
	@Column(name = "enabled")
	private Boolean activo;
	
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "usuarios_roles", joinColumns = @JoinColumn(name = "id_usuario"), 
	inverseJoinColumns = @JoinColumn(name = "id_rol"), 
	uniqueConstraints = {@UniqueConstraint(columnNames = { "id_usuario", "id_rol" }) })
	private List<RolEntity> roles;

}
