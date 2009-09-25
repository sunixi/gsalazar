/**
 * 
 */
package ar.com.gsalazar.beans;

import javax.persistence.Column;
import javax.persistence.Entity;

/**
 * 
 *	@author Guillermo Daniel Salazar.
 *	@since 16/Semptiembre/2009.
 */
@Entity
public class CategoriaSearcher extends Searcher {

	private static final long serialVersionUID = -462238529934628758L;

	@Column(nullable = true)
	private Categoria categoria;

	public CategoriaSearcher() {
		super();
	}
	
	public CategoriaSearcher(Categoria categoria, TagSearch tagSearch) {
		super(tagSearch);
		this.setCategoria(categoria);
	}

	/**
	 * @return the categoria
	 */
	public Categoria getCategoria() {
		return categoria;
	}

	/**
	 * @param categoria the categoria to set
	 */
	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}
}
