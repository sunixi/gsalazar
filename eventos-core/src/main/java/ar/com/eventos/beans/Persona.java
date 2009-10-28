/**
 * 
 */
package ar.com.eventos.beans;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import com.angel.architecture.persistence.base.PersistentObject;

/**
 * 
 *	@author Guillermo Daniel Salazar.
 *	@since 16/Semptiembre/2009.
 */
@Entity
public class Persona extends PersistentObject {

	private static final long serialVersionUID = -6202140311792640102L;

	@Column(nullable = false, unique = false, length = 70)
	private String nombre;
	
	@Column(nullable = false, unique = false, length = 50)
	private String apellido;
	
	@Column(nullable = false, unique = true, length = 100)
	private String email;

	@Column(nullable = false)
	private Date nacimiento;
	
	private String urlFotolog;

	@Enumerated(EnumType.STRING)
	private TipoSexo tipoSexo;

	/**
	 * @return the nombre
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * @param nombre the nombre to set
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	/**
	 * @return the apellido
	 */
	public String getApellido() {
		return apellido;
	}

	/**
	 * @param apellido the apellido to set
	 */
	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return the nacimiento
	 */
	public Date getNacimiento() {
		return nacimiento;
	}

	/**
	 * @param nacimiento the nacimiento to set
	 */
	public void setNacimiento(Date nacimiento) {
		this.nacimiento = nacimiento;
	}

	/**
	 * @return the urlFotolog
	 */
	public String getUrlFotolog() {
		return urlFotolog;
	}

	/**
	 * @param urlFotolog the urlFotolog to set
	 */
	public void setUrlFotolog(String urlFotolog) {
		this.urlFotolog = urlFotolog;
	}

	/**
	 * @return the tipoSexo
	 */
	public TipoSexo getTipoSexo() {
		return tipoSexo;
	}

	/**
	 * @param tipoSexo the tipoSexo to set
	 */
	public void setTipoSexo(TipoSexo tipoSexo) {
		this.tipoSexo = tipoSexo;
	}
}
