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
	public List<Articulo> buscarTodosPorTagsSearch(List<TagSearch> tagsSearch) {
		
		/*
		 select p from Eg.NameList list, Eg.Person p
where p.Name = some elements(list.Names)
*/
		int size = tagsSearch != null ? tagsSearch.size() : 0;
		String q = "select l from " + Articulo.class.getName() + " l ";
		int current = 0;
		for(TagSearch ts: tagsSearch){
			if(size > 1 && current == 0){
				q += " where '" + ts.getIdAsString() + "' = some elements(l.tagsBuscables)";
			} else {
				if(size == 1){
					q += " where '" + ts.getIdAsString() + "' = some elements(l.tagsBuscables)";	
				} else {
					if(current > 0 && current != size){
						q += " and '" + ts.getIdAsString() + "' = some elements(l.tagsBuscables)";
					} else {
						q += " and '" + ts.getIdAsString() + "' = some elements(l.tagsBuscables)";
					}
				}
			}
			current++;
		}
		Query query = super.getSession().createQuery(q);
		return query.list();
	}
}
