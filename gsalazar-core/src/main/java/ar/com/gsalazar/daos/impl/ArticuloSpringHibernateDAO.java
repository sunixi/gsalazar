/**
 * 
 */
package ar.com.gsalazar.daos.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;

import ar.com.gsalazar.beans.Articulo;
import ar.com.gsalazar.beans.TagSearch;
import ar.com.gsalazar.daos.ArticuloDAO;

import com.angel.architecture.persistence.ids.ObjectId;
import com.angel.dao.generic.impl.GenericSpringHibernateDAO;

/**
 * 
 * 
 * @author Guillermo Salazar
 * @since 28/Agosto/2009.
 *
 */
public class ArticuloSpringHibernateDAO extends GenericSpringHibernateDAO<Articulo, ObjectId> implements ArticuloDAO {

	public ArticuloSpringHibernateDAO() {
		super(Articulo.class, ObjectId.class);
	}

	public List<Articulo> buscarTodosCantidadFuentes(Integer cantidadFuentes) {
		return (List<Articulo>) super.findAllByCriteria(Restrictions.sizeEq("fuentes", cantidadFuentes));
	}

	public List<Articulo> buscarTodosContenganContenido(String contenido) {
		return (List<Articulo>) super.findAllByCriteria(Restrictions.like("contenido", contenido, MatchMode.ANYWHERE));
	}
	
	public List<Articulo> buscarTodosContenganDescripcion(String descripcion) {
		return (List<Articulo>) super.findAllByCriteria(Restrictions.like("descripcion", descripcion, MatchMode.ANYWHERE));
	}

	public Articulo buscarUnicoPorTitulo(String titulo) {
		return super.findUnique("titulo", titulo);
	}

	@SuppressWarnings("unchecked")
	public List<Articulo> buscarTodosPorTags(List<TagSearch> tagsSearch) {
		
		Query query = super.getSession().createQuery("select l from " + Articulo.class.getName() + " l " +
" where tagsBuscables in elements (:tags)");
		query.setParameterList("tags", tagsSearch);
//		Map<String, Object> parameters = new HashMap<String, Object>();
//		parameters.put("tagsBuscables", tagsSearch);
//		String query = " tagsBuscables in elements(:tagsBuscables) ";
//		List<Articulo> a = (List<Articulo>) super.findAllByQuery(parameters, query);
		List<Articulo> a = query.list();
		return a;
	}
}
