/**
 * 
 */
package ar.com.gsalazar.beans;

import java.sql.Blob;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToOne;

import com.angel.architecture.persistence.base.PersistentObject;

/**
 * 
 *	@author Guillermo Daniel Salazar.
 *	@since 16/Semptiembre/2009.
 */
@Entity
public class ItemCategoria extends PersistentObject {

	private static final long serialVersionUID = -462238529934628758L;

	@Column(nullable = false)
	private String nombre;
	@Column(nullable = false)
	private String descripcion;
	@OneToOne(cascade = CascadeType.ALL, optional = false)
	private ImagenItemCategoria imagenItemCategoria;

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
	 * @return the imagenItemCategoria
	 */
	public ImagenItemCategoria getImagenItemCategoria() {
		return imagenItemCategoria;
	}
	/**
	 * @param imagenItemCategoria the imagenItemCategoria to set
	 */
	public void setImagenItemCategoria(ImagenItemCategoria imagenItemCategoria) {
		this.imagenItemCategoria = imagenItemCategoria;
	}
	
	public void setImagenItemCategoria(String fileName, Blob imagen) {
		this.setImagenItemCategoria(new ImagenItemCategoria(imagen, fileName));
	}
}
