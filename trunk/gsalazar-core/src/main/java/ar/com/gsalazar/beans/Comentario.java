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
public class Comentario extends PersistentObject{

	private static final long serialVersionUID = -462238529934628758L;

	@Column(nullable = false)
	private String nombre;
	
	@Column(nullable = false, length = 200)
	private String comentario;

	@Column(nullable = false)
	private int rating;
	
	private boolean validado = false;
	
	public Comentario(){
		super();
	}
	
	public Comentario(String nombre, String comentario){
		this();
		this.setNombre(nombre);
		this.setComentario(comentario);
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
	 * @return the comentario
	 */
	public String getComentario() {
		return comentario;
	}

	/**
	 * @param comentario the comentario to set
	 */
	public void setComentario(String comentario) {
		this.comentario = comentario;
	}

	/**
	 * @return the rating
	 */
	public int getRating() {
		return rating;
	}

	/**
	 * @param rating the rating to set
	 */
	public void setRating(int rating) {
		this.rating = rating;
	}

	/**
	 * @return the validado
	 */
	public boolean isValidado() {
		return validado;
	}

	/**
	 * @param validado the validado to set
	 */
	public void setValidado(boolean validado) {
		this.validado = validado;
	}
}
