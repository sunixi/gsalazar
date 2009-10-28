/**
 * 
 */
package ar.com.eventos.daos.impl;

import java.util.Date;
import java.util.List;

import org.hibernate.criterion.Restrictions;

import ar.com.eventos.beans.Evento;
import ar.com.eventos.beans.Salon;
import ar.com.eventos.daos.EventoDAO;

import com.angel.architecture.persistence.ids.ObjectId;
import com.angel.dao.generic.impl.GenericSpringHibernateDAO;

/**
 * 
 * 
 * @author Guillermo Salazar
 * @since 28/Agosto/2009.
 *
 */
public class EventoSpringHibernateDAO extends GenericSpringHibernateDAO<Evento, ObjectId> implements EventoDAO {

	public EventoSpringHibernateDAO() {
		super(Evento.class, ObjectId.class);
	}
	/*
	public List<Articulo> buscarTodosMayorRating(int cantidadTotal) {
		QueryBuilder queryBuilder = new QueryBuilderImpl(new HQLClauseFactory());
		queryBuilder.setMaxResult(cantidadTotal);
		
		SelectClause selectClause = queryBuilder.getSelectClause();
		selectClause.add("articulos");
	
		FromClause fromClause = queryBuilder.getFromClause();
		fromClause
			.from(super.getPersistentClass(), "articulos")
			.innerJoin("articulos.comentarios", "c");

		OrderByClause orderByClause = queryBuilder.getOrderByClause();
		orderByClause.desc("avg(c.rating)");
		
		List<Articulo> articulos = (List<Articulo>) super.findAllByQuery(queryBuilder.buildQuery());
		return articulos;
	}*/

	public List<Evento> buscarTodosPorCantidadImagenes(int cantidadImagenes) {
		return (List<Evento>) super.findAllByCriteria(Restrictions.sizeEq("imagenes", cantidadImagenes));
	}

	public List<Evento> buscarTodosPorFechaRealizacion(Date fechaRealizacion) {
		return (List<Evento>) super.findAll("realizacion", fechaRealizacion);
	}

	public List<Evento> buscarTodosPorSalon(Salon salon) {
		return (List<Evento>) super.findAll("salon", salon);
	}
}
