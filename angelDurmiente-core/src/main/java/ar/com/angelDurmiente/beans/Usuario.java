/**
 * 
 */
package ar.com.angelDurmiente.beans;

import java.util.Date;

import com.angel.architecture.persistence.beans.User;

/**
 * @author Guillermo Salazar
 * 
 *
 */
public class Usuario extends User {

	private static final long serialVersionUID = -6441190120910247706L;
	private String nombre;
	private String apellido;
	private Date nacimiento;

	/**
	 * 
	 */
	public Usuario() {
		super();
	}

	/**
	 * @param name
	 */
	public Usuario(String name) {
		super(name);
	}

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
}
