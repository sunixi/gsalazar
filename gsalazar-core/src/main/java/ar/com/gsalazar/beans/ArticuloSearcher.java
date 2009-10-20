/**
 * 
 */
package ar.com.gsalazar.beans;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import com.angel.architecture.persistence.beans.TagSearch;

/**
 * 
 *	@author Guillermo Daniel Salazar.
 *	@since 16/Semptiembre/2009.
 */
@Entity
@DiscriminatorValue("articulo")
public class ArticuloSearcher extends Searcher {

	private static final long serialVersionUID = -462238529934628758L;
	@Column(nullable = true, unique = false)
	private Articulo articulo;
	
	public ArticuloSearcher(){
		super();
	}
	
	public ArticuloSearcher(Articulo articulo, TagSearch tagSearch){
		super(tagSearch);
		this.setArticulo(articulo);
	}

	/**
	 * @return the articulo
	 */
	public Articulo getArticulo() {
		return articulo;
	}

	/**
	 * @param articulo the articulo to set
	 */
	public void setArticulo(Articulo articulo) {
		this.articulo = articulo;
	}
}
