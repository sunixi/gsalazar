/**
 * 
 */
package ar.com.gsalazar.daos;

import java.util.List;

import ar.com.gsalazar.beans.Articulo;
import ar.com.gsalazar.beans.ArticuloSearcher;
import ar.com.gsalazar.beans.TagSearch;

import com.angel.architecture.persistence.ids.ObjectId;
import com.angel.dao.generic.interfaces.GenericDAO;

/**
 * @author Guillermo Salazar.
 * @since 27/Agosto/2009.
 *
 */
public interface ArticuloSearcherDAO extends GenericDAO<ArticuloSearcher, ObjectId>{

	public List<ArticuloSearcher> buscarTodosPorArticulo(Articulo articulo);
	
	public List<ArticuloSearcher> buscarTodosPorTag(TagSearch tagSearch);

}
