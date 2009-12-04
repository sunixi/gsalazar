/**
 * 
 */
package ar.com.angelDurmiente.beans;

import java.sql.Blob;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;

import com.angel.architecture.persistence.base.PersistentObject;

/**
 * @author Guillermo Salazar
 * @since 19/Noviembre/2009.
 *
 */
@Entity
public class Acorde extends PersistentObject {

	private static final long serialVersionUID = -1457278499744128408L;

	@Column(nullable = false)
	private String nombre;

	@Lob
	@Column(nullable = true)
	private Blob imagen;
	
	@Column(nullable = false)
	private int opcion;

	public Acorde(){
		super();
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
	 * @return the opcion
	 */
	public int getOpcion() {
		return opcion;
	}

	/**
	 * @param opcion the opcion to set
	 */
	public void setOpcion(int opcion) {
		this.opcion = opcion;
	}
}
