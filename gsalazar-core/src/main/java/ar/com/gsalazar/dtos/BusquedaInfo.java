/**
 * 
 */
package ar.com.gsalazar.dtos;

import java.io.Serializable;
import java.util.List;

import com.angel.common.helpers.StringHelper;

import ar.com.gsalazar.beans.TagSearch;

/**
 * 
 *	@author Guillermo Daniel Salazar.
 *	@since 16/Semptiembre/2009.
 */
public class BusquedaInfo implements Serializable{
	private static final long serialVersionUID = 7954520141032044952L;

	private String contenido;
	private int rating;
	private String titulo;
	private String descripcion;
	private long visualizado;
	private List<TagSearch> tagsBuscables;

	public BusquedaInfo(){
		super();
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
	
	public boolean contieneTagsBuscables(){
		return this.getTagsBuscables() != null && this.getTagsBuscables().size() > 0;
	}
	
	public boolean filtraPorTitulo(){
		return StringHelper.isNotEmpty(this.getTitulo());
	}
	
	public boolean filtraPorDescripcion(){
		return StringHelper.isNotEmpty(this.getDescripcion());
	}
	
	public boolean filtraPorContenido(){
		return StringHelper.isNotEmpty(this.getContenido());
	}
	
	public boolean filtraPorRating(){
		return this.getRating() > 0;
	}
}
