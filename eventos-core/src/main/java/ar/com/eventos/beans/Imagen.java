/**
 * 
 */
package ar.com.eventos.beans;

import java.sql.Blob;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Transient;

import org.hibernate.annotations.Type;

import com.angel.architecture.persistence.base.PersistentObject;

/**
 * 
 *	@author Guillermo Daniel Salazar.
 *	@since 16/Semptiembre/2009.
 */
@Entity
public class Imagen extends PersistentObject {

	private static final long serialVersionUID = -6202140311792640102L;

	@Lob
	private Blob imagen;

	@Transient
	private byte[] imagenArray;
	
	@Column(nullable = false)
	@Type(type = "text")
	private String desccripcion;

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

	/**
	 * @return the desccripcion
	 */
	public String getDesccripcion() {
		return desccripcion;
	}

	/**
	 * @param desccripcion the desccripcion to set
	 */
	public void setDesccripcion(String desccripcion) {
		this.desccripcion = desccripcion;
	}
}
