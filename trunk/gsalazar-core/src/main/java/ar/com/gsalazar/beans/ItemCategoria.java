/**
 * 
 */
package ar.com.gsalazar.beans;

import javax.persistence.Entity;

import com.angel.architecture.persistence.base.PersistentObject;
import com.mysql.jdbc.Blob;

/**
 * 
 *	@author Guillermo Daniel Salazar.
 *	@since 16/Semptiembre/2009.
 */
@Entity
public class ItemCategoria extends PersistentObject {

	private static final long serialVersionUID = -462238529934628758L;

	private String nombre;
	private String descripcion;
	private Blob image;

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
	 * @return the descripcion
	 */
	public String getDescripcion() {
		return descripcion;
	}
	/**
	 * @param descripcion the descripcion to set
	 */
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	/**
	 * @return the image
	 */
	public Blob getImage() {
		return image;
	}
	/**
	 * @param image the image to set
	 */
	public void setImage(Blob image) {
		this.image = image;
	}
}
