/**
 * 
 */
package ar.com.gsalazar.daos.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;

import ar.com.gsalazar.beans.Estado;
import ar.com.gsalazar.beans.Proyecto;
import ar.com.gsalazar.beans.TagSearch;
import ar.com.gsalazar.daos.ProyectoDAO;
import ar.com.gsalazar.daos.queryBuilder.QueryBeanFactory;
import ar.com.gsalazar.daos.queryBuilder.impl.BusquedaInfoQueryBeanFactory;
import ar.com.gsalazar.daos.queryBuilder.impl.TagsSearchQueryBeanFactory;
import ar.com.gsalazar.dtos.BusquedaInfo;

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
		QueryBeanFactory queryBeanFactory = new TagsSearchQueryBeanFactory();
		String q = queryBeanFactory.createQueryBean(this.getPersistentClass(), tagsSearch);
		Query query = super.getSession().createQuery(q);
		return query.list();
	}

	@SuppressWarnings("unchecked")
	public List<Proyecto> buscarTodosPorBusquedaInfo(BusquedaInfo busquedaInfo) {
		QueryBeanFactory queryBeanFactory = new BusquedaInfoQueryBeanFactory();
		String q = queryBeanFactory.createQueryBean(this.getPersistentClass(), busquedaInfo);
		Query query = super.getSession().createQuery(q);
		return query.list();
	}

	public List<Proyecto> buscarTodosEnDesarrollo(int cantidadMaxima) {
		List<Proyecto> proyectos  = (List<Proyecto>) super.findAll("estado", Estado.EN_DESARROLLO);
		List<Proyecto> proyectosFiltrados = new ArrayList<Proyecto>();
		int i = 0;
		for(Proyecto a: proyectos){
			if(i < cantidadMaxima){
				proyectosFiltrados.add(a);
			} else {
				break;
			}
			i++;
		}
		return proyectosFiltrados;
	}
}
