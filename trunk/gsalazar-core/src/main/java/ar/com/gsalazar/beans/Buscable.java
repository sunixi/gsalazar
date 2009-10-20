/**
 * 
 */
package ar.com.gsalazar.beans;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ManyToMany;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;

import com.angel.architecture.persistence.base.PersistentObject;
import com.angel.architecture.persistence.beans.TagSearch;

/**
 * 
 *	@author Guillermo Daniel Salazar.
 *	@since 16/Semptiembre/2009.
 */
@MappedSuperclass
public abstract class Buscable extends PersistentObject {

	private static final long serialVersionUID = -462238529934628758L;
	@Column(nullable = false)
	private String titulo;

	@Column(nullable = false)
	private String descripcion;
	
	@ManyToMany(cascade = CascadeType.ALL)
	@Column(unique = false)
	private List<TagSearch> tagsBuscables;
	
	private long visualizado = 0;

	@OneToMany(cascade = CascadeType.ALL)
	@OrderBy(value = "creationDate desc, rating desc")
	private List<Comentario> comentarios;
	
	public Buscable(){
		super();
		this.setTagsBuscables(new ArrayList<TagSearch>());
		this.setComentarios(new ArrayList<Comentario>());
	}
	
	public Buscable(String titulo, String descripcion){
		this();
		this.setTitulo(titulo);
		this.setDescripcion(descripcion);
	}
	
	public Buscable(String titulo, String descripcion, List<TagSearch> tagsBuscables){
		this();
		this.setTitulo(titulo);
		this.setDescripcion(descripcion);
		this.getTagsBuscables().addAll(tagsBuscables);
	}
	
	public Buscable(String titulo, String descripcion, List<TagSearch> tagsBuscables, List<Comentario> comentarios){
		this();
		this.setTitulo(titulo);
		this.setDescripcion(descripcion);
		this.getTagsBuscables().addAll(tagsBuscables);
		this.getComentarios().addAll(comentarios);
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
	 * @return the tagsBuscables
	 */
	public List<TagSearch> getTagsBuscables() {
		return tagsBuscables;
	}

	/**
	 * @param tagsBuscables the tagsBuscables to set
	 */
	public void setTagsBuscables(List<TagSearch> tagsBuscables) {
		this.tagsBuscables = tagsBuscables;
	}
	
	public void addTagBuscable(TagSearch tagSearch){
		this.getTagsBuscables().add(tagSearch);
	}
	
	public void addTagBuscable(String label, String descripcion){
		this.addTagBuscable(new TagSearch(label, descripcion));
	}
	
	public void removeTagBuscable(TagSearch tagSearch){
		this.getTagsBuscables().remove(tagSearch);
	}
	
	public boolean containsTagBuscable(TagSearch tagSearch){
		return this.getTagsBuscables().contains(tagSearch);
	}

	public boolean containsTagBuscable(String label){
		for(TagSearch tag: this.getTagsBuscables()){
			if(tag.hasLabel(label)){
				return true;
			}
		}
		return false;
	}

	/**
	 * @return the visualizado
	 */
	public long getVisualizado() {
		return visualizado;
	}

	/**
	 * @param visualizado the visualizado to set
	 */
	public void setVisualizado(long visualizado) {
		this.visualizado = visualizado;
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

	public void addComentario(Comentario comentario){
		this.getComentarios().add(comentario);
	}
	
	public void addComentario(String comentarioString, String nombre, int rating){
		Comentario comentario = new Comentario();
		comentario.setComentario(comentarioString);
		comentario.setNombre(nombre);
		comentario.setRating(rating);
		this.getComentarios().add(comentario);
	}
	
	public long visualizar() {
		this.visualizado++;
		return this.getVisualizado();
	}
}
