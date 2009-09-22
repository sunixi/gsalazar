/**
 * 
 */
package ar.com.gsalazar.beans;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

import org.hibernate.annotations.Type;

/**
 * 
 *	@author Guillermo Daniel Salazar.
 *	@since 16/Semptiembre/2009.
 */
@Entity
public class Articulo extends Buscable {

	private static final long serialVersionUID = -462238529934628758L;
	@Column(nullable = false)
	@Type(type = "text")
	private String contenido;
	
	@OneToMany(cascade = CascadeType.ALL)
	private List<Fuente> fuentes;
	
	public Articulo(){
		super();
		this.setFuentes(new ArrayList<Fuente>());
	}
	
	public Articulo(String contenido){
		this();
		this.setContenido(contenido);
	}
	
	public Articulo(String titulo, String descripcion, String contenido){
		super(titulo, descripcion);
		this.setFuentes(new ArrayList<Fuente>());
		this.setContenido(contenido);
	}
	
	public Articulo(String titulo, String descripcion, List<TagSearch> tagsBuscables, String contenido){
		super(titulo, descripcion, tagsBuscables);
		this.setFuentes(new ArrayList<Fuente>());
		this.setContenido(contenido);
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
	 * @return the fuentes
	 */
	public List<Fuente> getFuentes() {
		return fuentes;
	}
	/**
	 * @param fuentes the fuentes to set
	 */
	public void setFuentes(List<Fuente> fuentes) {
		this.fuentes = fuentes;
	}
	
	public void addFuente(Fuente fuente){
		this.getFuentes().add(fuente);
	}
}
