/**
 * 
 */
package ar.com.gsalazar.daos.impl;

import java.util.List;

import org.hibernate.Query;

import ar.com.gsalazar.beans.Persona;
import ar.com.gsalazar.daos.PersonaDAO;
import ar.com.gsalazar.daos.queryBuilder.QueryBeanFactory;
import ar.com.gsalazar.daos.queryBuilder.impl.TagsSearchQueryBeanFactory;
import ar.com.gsalazar.dtos.BusquedaInfo;

import com.angel.architecture.persistence.beans.TagSearch;
import com.angel.architecture.persistence.ids.ObjectId;
import com.angel.dao.generic.impl.GenericSpringHibernateDAO;
import com.angel.dao.generic.query.builder.QueryBuilder;
import com.angel.dao.generic.query.builder.impl.QueryBuilderImpl;
import com.angel.dao.generic.query.clauses.impl.HQLFromClause;
import com.angel.dao.generic.query.clauses.impl.HQLWhereClause;
import com.angel.dao.generic.query.factory.impl.HQLClauseFactory;

/**
 * 
 * 
 * @author Guillermo Salazar
 * @since 28/Agosto/2009.
 *
 */
public class PersonaSpringHibernateDAO extends GenericSpringHibernateDAO<Persona, ObjectId> implements PersonaDAO {

	public PersonaSpringHibernateDAO() {
		super(Persona.class, ObjectId.class);
	}

	public List<Persona> buscarTodosPorApellido(String apellido) {
		return (List<Persona>) super.findAll("apellido", apellido);
	}

	public Persona buscarUnicoPorEmail(String email) {
		return super.findUnique("email", email);
	}

	public Persona buscarUnicoPorNombre(String nombre) {
		return super.findUnique("nombre", nombre);
	}
	
	@SuppressWarnings("unchecked")
	public List<Persona> buscarTodosPorTagsSearch(List<TagSearch> tagsSearch) {
		QueryBeanFactory queryBeanFactory = new TagsSearchQueryBeanFactory();
		String q = queryBeanFactory.createQueryBean(this.getPersistentClass(), tagsSearch);
		Query query = super.getSession().createQuery(q);
		return query.list();
	}
	
	public List<Persona> buscarTodosPorBusquedaInfo(BusquedaInfo busquedaInfo) {
		QueryBuilder queryBuilder = new QueryBuilderImpl(new HQLClauseFactory());
		HQLFromClause fromClause = (HQLFromClause) queryBuilder.getFromClause();
		fromClause
			.add(this.getPersistentClass(), "personas")
			.innerJoin("personas.tagsBuscables", "tags");
		HQLWhereClause whereClause = (HQLWhereClause) queryBuilder.getWhereClause();
		whereClause
			.in("tags", busquedaInfo.getTagsBuscables());
		return (List<Persona>) super.findAllByQuery(queryBuilder.buildQuery());
		/*
		String q = "from ar.com.gsalazar.beans.Proyecto personas inner join personas.tagsBuscables tags where tags in ( :parameterList )";
		Query query = super.getSession().createQuery(q);
		query.setParameterList("parameterList", busquedaInfo.getTagsBuscables());
		return query.list();
		*/
	}
}
