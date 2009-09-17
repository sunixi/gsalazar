/**
 * 
 */
package ar.com.gsalazar.beans;

import javax.persistence.Column;
import javax.persistence.Entity;

import com.angel.architecture.persistence.base.PersistentObject;

/**
 * 
 *	@author Guillermo Daniel Salazar.
 *	@since 16/Semptiembre/2009.
 */
@Entity
public class Contacto extends PersistentObject {

	private static final long serialVersionUID = -462238529934628758L;
	@Column(nullable = false)
	public String nombres;
	@Column(nullable = false)
	public String apellido;
	@Column(nullable = false)
	public String nombreCompania;
	@Column(nullable = false)
	public String email;
	public String telefono;
	public String direccion;
	public String localidad;
	@Column(nullable = false)
	public String asunto;
	@Column(nullable = false)
	public String descripcion;
}
