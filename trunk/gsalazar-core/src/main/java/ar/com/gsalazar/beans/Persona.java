/**
 * 
 */
package ar.com.gsalazar.beans;

import java.sql.Blob;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Transient;

import com.angel.architecture.flex.annotations.serialization.FlexTransient;

/**
 * 
 *	@author Guillermo Daniel Salazar.
 *	@since 16/Semptiembre/2009.
 */
@Entity
public class Persona extends Buscable {

	private static final long serialVersionUID = -462238529934628758L;
	
	@Lob
	@Column(nullable = true)
	private Blob imagen;
	
	@Transient
	private byte[] imagenArray;
	
	@Column(nullable = false, unique = false, length = 70)
	private String nombre;
	
	@Column(nullable = false, unique = false, length = 50)
	private String apellido;
	
	@Column(nullable = false, unique = true, length = 100)
	private String email;

	/**
	 * @return the imagen
	 */
	@FlexTransient
	public Blob getImagen() {
		return imagen;
	}

	/**
	 * @param imagen the imagen to set
	 */
	public void setImagen(Blob imagen) {
		this.imagen = imagen;
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
	 * @return the imagenArray
	 */
	public byte[] getImagenArray() {
		return imagenArray;
	}

	/**
	 * @param imagenArray the imagenArray to set
	 */
	public void setImagenArray(byte[] imagenArray) {
		this.imagenArray = imagenArray;
	}
}
