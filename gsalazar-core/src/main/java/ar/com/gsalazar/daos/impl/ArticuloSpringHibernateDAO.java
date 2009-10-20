/**
 * 
 */
package ar.com.gsalazar.daos.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;

import ar.com.gsalazar.beans.Articulo;
import ar.com.gsalazar.daos.ArticuloDAO;
import ar.com.gsalazar.daos.queryBuilder.QueryBeanFactory;
import ar.com.gsalazar.daos.queryBuilder.impl.ArticulosBusquedaInfoQueryBeanFactory;
import ar.com.gsalazar.daos.queryBuilder.impl.TagsSearchQueryBeanFactory;
import ar.com.gsalazar.dtos.BusquedaInfo;

import com.angel.architecture.persistence.beans.TagSearch;
import com.angel.architecture.persistence.ids.ObjectId;
import com.angel.dao.generic.impl.GenericSpringHibernateDAO;
import com.angel.dao.generic.query.builder.QueryBuilder;
import com.angel.dao.generic.query.builder.impl.HQLQueryBuilder;
import com.angel.dao.generic.query.clauses.impl.FromClause;
import com.angel.dao.generic.query.clauses.impl.OrderByClause;

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
		QueryBuilder queryBuilder = new HQLQueryBuilder();
		queryBuilder.setMaxResult(cantidadAgregada);

		FromClause fromClause = (FromClause) queryBuilder.buildFromClause();
		fromClause.add(super.getPersistentClass(), "articulo");
		OrderByClause orderByClause = (OrderByClause) queryBuilder.buildOrderByClause();
		orderByClause.desc("articulo", "creationDate");

		List<Articulo> articulos = (List<Articulo>) super.findAllByQueryBuilder(queryBuilder);
		return articulos;
	}

	public List<Articulo> buscarTodosMasComentados(int cantidadComentada) {
		QueryBuilder queryBuilder = new HQLQueryBuilder();
		queryBuilder.setMaxResult(cantidadComentada);
		
		FromClause fromClause = (FromClause) queryBuilder.buildFromClause();
		fromClause.add(super.getPersistentClass(), "articulo");
		OrderByClause orderByClause = (OrderByClause) queryBuilder.buildOrderByClause();
		orderByClause.desc("size(comentarios)");

		List<Articulo> articulos = (List<Articulo>) super.findAllByQuery(queryBuilder.buildQuery());
		return articulos;
	}
}
