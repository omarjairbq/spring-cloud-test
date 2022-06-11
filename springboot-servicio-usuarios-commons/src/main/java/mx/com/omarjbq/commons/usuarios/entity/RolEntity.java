package mx.com.omarjbq.commons.usuarios.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.Setter;

/**
 * Entidad de los roles.
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
@Table(name = "roles")
public class RolEntity implements Serializable {

	/**
	 * Serialización.
	 */
	private static final long serialVersionUID = 4546216616639379779L;

	/**
	 * Identificador.
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_rol", nullable = false)
	private Long id;

	/**
	 * Nombre.
	 */
	@Column(name = "nombre", unique = true, length = 30, nullable = false)
	private String nombre;
}
