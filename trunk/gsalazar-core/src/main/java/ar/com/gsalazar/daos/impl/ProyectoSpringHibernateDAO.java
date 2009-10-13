/**
 * 
 */
package ar.com.gsalazar.daos.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;

import ar.com.gsalazar.beans.Proyecto;
import ar.com.gsalazar.beans.TagSearch;
import ar.com.gsalazar.daos.ProyectoDAO;

import com.angel.architecture.persistence.ids.ObjectId;
import com.angel.dao.generic.impl.GenericSpringHibernateDAO;

/**
 * 
 * 
 * @author Guillermo Salazar
 * @since 28/Agosto/2009.
 *
 */
public class ProyectoSpringHibernateDAO extends GenericSpringHibernateDAO<Proyecto, ObjectId> implements ProyectoDAO {

	public ProyectoSpringHibernateDAO() {
		super(Proyecto.class, ObjectId.class);
	}

	public List<Proyecto> buscarTodosPorCantidadDesarrolladores(int cantidad) {
		return (List<Proyecto>) super.findAllByCriteria(Restrictions.sizeEq("desarrolladores", cantidad));
	}
	
	@SuppressWarnings("unchecked")
	public List<Proyecto> buscarTodosPorTagsSearch(List<TagSearch> tagsSearch) {
		int size = tagsSearch != null ? tagsSearch.size() : 0;
		String q = "select l from " + this.getPersistentClass().getName() + " l ";
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
