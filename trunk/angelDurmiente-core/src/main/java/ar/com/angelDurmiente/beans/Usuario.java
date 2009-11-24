/**
 * 
 */
package ar.com.angelDurmiente.beans;

import java.sql.Blob;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;

import com.angel.architecture.persistence.beans.User;

/**
 * @author Guillermo Salazar
 * @since 19/Noviembre/2009.
 *
 */
@Entity
public class Usuario extends User {

	private static final long serialVersionUID = -6441190120910247706L;

	@Column(length = 40)
	private String nombre;
	@Column(length = 40)
	private String apellido;
	
	@Lob
	@Column(nullable = true)
	private Blob imagen;

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

	/**
	 * @return the imagen
	 */
	public Blob getImagen() {
		return imagen;
	}

	/**
	 * @param imagen the imagen to set
	 */
	public void setImagen(Blob imagen) {
		this.imagen = imagen;
	}
	
	public String getNombreCompleto(){
		return this.getNombre() + " " + this.getApellido();
	}
}
