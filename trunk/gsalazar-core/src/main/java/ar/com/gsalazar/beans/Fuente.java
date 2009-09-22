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
public class Fuente extends PersistentObject {

	private static final long serialVersionUID = -462238529934628758L;
	@Column(nullable = false)
	private String nombre;
	@Column(nullable = false)
	private String descripcion;
	@Column(nullable = false)
	private String enlace;
	
	public Fuente(){
		super();
	}
	
	public Fuente(String nombre, String descripcion, String enlace){
		this();
		this.setNombre(nombre);
		this.setDescripcion(descripcion);
		this.setEnlace(enlace);
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
	 * @return the enlace
	 */
	public String getEnlace() {
		return enlace;
	}
	/**
	 * @param enlace the enlace to set
	 */
	public void setEnlace(String enlace) {
		this.enlace = enlace;
	}
}
