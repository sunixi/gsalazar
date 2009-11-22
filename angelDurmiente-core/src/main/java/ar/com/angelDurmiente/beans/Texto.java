/**
 * 
 */
package ar.com.angelDurmiente.beans;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.Type;

import com.angel.architecture.persistence.base.PersistentObject;

/**
 * @author Guillermo Salazar
 * @since 19/Noviembre/2009.
 *
 */
@Entity
public class Texto extends PersistentObject {

	private static final long serialVersionUID = -1457278499744128408L;

	@Column(nullable = false)
	@Type(type = "text")
	private String contenido;

	private int rating;
	
	private double visitas;
	
	private boolean verificado = false;
	
	@ManyToOne(optional = false, fetch = FetchType.EAGER)
	private Usuario usuario;
	
	public Texto(){
		super();
	}
	
	public Texto(String contenido){
		this();
		this.setContenido(contenido);
	}
	public Texto(String contenido, Usuario usuario){
		this(contenido);
		this.setUsuario(usuario);
	}

	/**
	 * @return the contenido
	 */
	public String getContenido() {
		return contenido;
	}

	/**
	 * @param contenido the contenido to set
	 */
	public void setContenido(String contenido) {
		this.contenido = contenido;
	}

	/**
	 * @return the verificado
	 */
	public boolean isVerificado() {
		return verificado;
	}

	/**
	 * @param verificado the verificado to set
	 */
	public void setVerificado(boolean verificado) {
		this.verificado = verificado;
	}

	/**
	 * @return the usuario
	 */
	public Usuario getUsuario() {
		return usuario;
	}

	/**
	 * @param usuario the usuario to set
	 */
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
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
	 * @return the visitas
	 */
	public double getVisitas() {
		return visitas;
	}

	/**
	 * @param visitas the visitas to set
	 */
	public void setVisitas(double visitas) {
		this.visitas = visitas;
	}
}
