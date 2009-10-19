/**
 * 
 */
package ar.com.gsalazar.daos.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;

import ar.com.gsalazar.beans.Articulo;
import ar.com.gsalazar.beans.TagSearch;
import ar.com.gsalazar.daos.ArticuloDAO;
import ar.com.gsalazar.daos.queryBuilder.QueryBeanFactory;
import ar.com.gsalazar.daos.queryBuilder.impl.ArticulosBusquedaInfoQueryBeanFactory;
import ar.com.gsalazar.daos.queryBuilder.impl.TagsSearchQueryBeanFactory;
import ar.com.gsalazar.dtos.BusquedaInfo;

import com.angel.architecture.persistence.ids.ObjectId;
import com.angel.dao.generic.impl.GenericSpringHibernateDAO;
import com.angel.dao.generic.query.builder.QueryBuilder;
import com.angel.dao.generic.query.builder.impl.HQLQueryBuilder;
import com.angel.dao.generic.query.clauses.impl.FromClause;
import com.angel.dao.generic.query.clauses.impl.OrderByClause;
import com.angel.dao.generic.query.clauses.impl.SelectClause;

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
		QueryBeanFactory queryBeanFactory = new TagsSearchQueryBeanFactory();
		String q = queryBeanFactory.createQueryBean(this.getPersistentClass(), tagsSearch);
		Query query = super.getSession().createQuery(q);
		return query.list();
	}

	@SuppressWarnings("unchecked")
	public List<Articulo> buscarTodosPorBusquedaInfo(BusquedaInfo busquedaInfo) {
		QueryBeanFactory queryBeanFactory = new ArticulosBusquedaInfoQueryBeanFactory();
		String q = queryBeanFactory.createQueryBean(this.getPersistentClass(), busquedaInfo);
		Query query = super.getSession().createQuery(q);
		return query.list();
	}

	public List<Articulo> buscarTodosUltimosAgregados(int cantidadAgregada) {
		//super.clearFilters();
		//super.addInstacesFilter(new MaximumQuantityInstanceFilter<Articulo>(Long.valueOf(String.valueOf(cantidadAgregada)), 3, 0L));
		/*List<Articulo> articulos = (List<Articulo>) super.findAllByQuery(new HashMap<String, Object>(), " where 1 = 1 order by creationDate desc");
		List<Articulo> articulosFiltrados = new ArrayList<Articulo>();
		int i = 0;
		for(Articulo a: articulos){
			if(i < cantidadAgregada){
				articulosFiltrados.add(a);
			} else {
				break;
			}
			i++;
		}
		return articulosFiltrados;*/
		FromClause fromClause = new FromClause();
		fromClause.add(super.getPersistentClass(), "articulo");
		OrderByClause orderByClause = new OrderByClause();
		orderByClause.desc("articulo", "creationDate");
		HQLQueryBuilder queryBuilder = new HQLQueryBuilder(fromClause);
		queryBuilder.setOrderByClause(orderByClause);
		List<Articulo> articulos = (List<Articulo>) super.findAllByQueryBuilder(queryBuilder);
		return articulos;
	}

	public List<Articulo> buscarTodosUltimosComentados(int cantidadComentada) {
		//"size(order.items)"
		List<Articulo> articulos = (List<Articulo>) super.findAllByQuery(new HashMap<String, Object>(), " where 1 = 1 order by size(comentarios) desc");
		List<Articulo> articulosFiltrados = new ArrayList<Articulo>();
		int i = 0;
		for(Articulo a: articulos){
			if(i < cantidadComentada){
				articulosFiltrados.add(a);
			} else {
				break;
			}
			i++;
		}
		return articulosFiltrados;
	}
}
