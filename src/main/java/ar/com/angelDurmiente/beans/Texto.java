/**
 * 
 */
package ar.com.angelDurmiente.beans;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;

import org.hibernate.annotations.Type;

import ar.com.angelDurmiente.types.Comentable;

import com.angel.architecture.persistence.base.PersistentObject;

/**
 * @author Guillermo Salazar
 * @since 19/Noviembre/2009.
 *
 */
@Entity
public class Texto extends PersistentObject implements Comentable {

	private static final long serialVersionUID = -1457278499744128408L;

	@Column(unique = true)
	private String titulo;

	@Column(nullable = false)
	@Type(type = "text")
	private String contenido;

	private int rating;
	
	private double visitas;
	
	private boolean verificado = false;
	
	@ManyToOne(optional = false, fetch = FetchType.EAGER)
	private Usuario usuario;
	
	@OneToMany(cascade = CascadeType.ALL)
	@OrderBy(value = "creationDate desc, rating desc")
	private List<Comentario> comentarios;
	
	public Texto(){
		super();
		this.setComentarios(new ArrayList<Comentario>());
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

	/**
	 * @return the comentarios
	 */
	public List<Comentario> getComentarios() {
		return comentarios;
	}

	/**
	 * @param comentarios the comentarios to set
	 */
	public void setComentarios(List<Comentario> comentarios) {
		this.comentarios = comentarios;
	}

	public void comentar(Comentario comentario) {
		this.getComentarios().add(comentario);
	}

	public void comentar(String nombre, String valorComentario, int rating) {
		Comentario comentario = new Comentario();
		comentario.setComentario(valorComentario);
		comentario.setNombre(nombre);
		comentario.setRating(rating);
		this.comentar(comentario);		
	}

	public String getNombreUsuario(){
		return this.getUsuario().getName();
	}

	/**
	 * @return the titulo
	 */
	public String getTitulo() {
		return titulo;
	}

	/**
	 * @param titulo the titulo to set
	 */
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((titulo == null) ? 0 : titulo.hashCode());
		result = prime * result + ((usuario == null) ? 0 : usuario.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Texto other = (Texto) obj;
		if (titulo == null) {
			if (other.titulo != null)
				return false;
		} else if (!titulo.equals(other.titulo))
			return false;
		if (usuario == null) {
			if (other.usuario != null)
				return false;
		} else if (!usuario.equals(other.usuario))
			return false;
		return true;
	}

	public boolean tieneTitulo(String otroTitulo) {
		return this.getTitulo().equalsIgnoreCase(otroTitulo);
	}
}
