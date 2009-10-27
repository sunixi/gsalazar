/**
 * 
 */
package ar.com.gsalazar.daos.impl;

import java.util.List;

import ar.com.gsalazar.beans.Persona;
import ar.com.gsalazar.daos.PersonaDAO;
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
	
	public List<Persona> buscarTodosPorTagsSearch(List<TagSearch> tagsSearch) {
		QueryBuilder queryBuilder = this.createQueryBuilderFor(tagsSearch);
		/*QueryBeanFactory queryBeanFactory = new TagsSearchQueryBeanFactory();
		String q = queryBeanFactory.createQueryBean(this.getPersistentClass(), tagsSearch);
		Query query = super.getSession().createQuery(q);*/
		return (List<Persona>) super.findAllByQuery(queryBuilder.buildQuery());
	}
	
	public List<Persona> buscarTodosPorBusquedaInfo(BusquedaInfo busquedaInfo) {
		QueryBuilder queryBuilder = this.createQueryBuilderFor(busquedaInfo.getTagsBuscables());
		return (List<Persona>) super.findAllByQuery(queryBuilder.buildQuery());
	}
	
	/**
	 * Construye un query builder para buscar todas las personas que tengan los tags de busqueda
	 * que se pasan como parametro.
	 * 
	 * @param tagsSearch a buscar en las personas.
	 * @return todas las personas que tengas los tags de busquedas.
	 */
	protected QueryBuilder createQueryBuilderFor(List<TagSearch> tagsSearch){
		QueryBuilder queryBuilder = new QueryBuilderImpl(new HQLClauseFactory());
		HQLFromClause fromClause = (HQLFromClause) queryBuilder.getFromClause();
		fromClause
			.from(this.getPersistentClass(), "personas")
			.innerJoin("personas.tagsBuscables", "tags");
		HQLWhereClause whereClause = (HQLWhereClause) queryBuilder.getWhereClause();
		whereClause
			.in("tags", tagsSearch);
		return queryBuilder;
	}
}
