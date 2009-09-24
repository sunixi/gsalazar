/**
 * 
 */
package ar.com.gsalazar.daos.impl;

import java.util.List;

import ar.com.gsalazar.beans.Articulo;
import ar.com.gsalazar.beans.ArticuloSearcher;
import ar.com.gsalazar.beans.TagSearch;
import ar.com.gsalazar.daos.ArticuloSearcherDAO;

import com.angel.architecture.persistence.ids.ObjectId;
import com.angel.dao.generic.impl.GenericSpringHibernateDAO;

/**
 * 
 * 
 * @author Guillermo Salazar
 * @since 28/Agosto/2009.
 *
 */
public class ArticuloSearcherSpringHibernateDAO extends GenericSpringHibernateDAO<ArticuloSearcher, ObjectId> implements ArticuloSearcherDAO {

	public ArticuloSearcherSpringHibernateDAO() {
		super(ArticuloSearcher.class, ObjectId.class);
	}

	public List<ArticuloSearcher> buscarTodosPorArticulo(Articulo articulo) {
		return (List<ArticuloSearcher>) super.findAll("articulo", articulo);
	}

	public List<ArticuloSearcher> buscarTodosPorTag(TagSearch tagSearch) {
		return (List<ArticuloSearcher>) super.findAll("tagSearch", tagSearch);
	}
}
