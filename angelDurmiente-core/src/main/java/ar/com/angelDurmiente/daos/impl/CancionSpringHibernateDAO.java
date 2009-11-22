/**
 * 
 */
package ar.com.angelDurmiente.daos.impl;

import java.util.Collection;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;

import ar.com.angelDurmiente.beans.Artista;
import ar.com.angelDurmiente.beans.Cancion;
import ar.com.angelDurmiente.daos.CancionDAO;

import com.angel.architecture.persistence.ids.ObjectId;
import com.angel.dao.generic.exceptions.GenericDAOException;
import com.angel.dao.generic.impl.GenericSpringHibernateDAO;
import com.angel.dao.generic.query.builder.QueryBuilder;
import com.angel.dao.generic.query.builder.impl.QueryBuilderImpl;
import com.angel.dao.generic.query.factory.impl.HQLClauseFactory;
import com.angel.dao.generic.query.params.ParamType;
import com.angel.dao.generic.query.params.QueryConditionParam;

/**
 * 
 * 
 * @author Guillermo Salazar
 * @since 28/Agosto/2009.
 *
 */
public class CancionSpringHibernateDAO extends GenericSpringHibernateDAO<Cancion, ObjectId> implements CancionDAO {

	public CancionSpringHibernateDAO() {
		super(Cancion.class, ObjectId.class);
	}

	public List<Cancion> buscarTodosPorTitulo(String titulo) {
		return (List<Cancion>) super.findAll("titulo", titulo);
	}

	public List<Cancion> buscarTodosPorContenido(String contenido) {
		return (List<Cancion>) super.findAllByCriteria(Restrictions.like("texto", contenido));
	}
	
	public Cancion buscarUnicoPorArtistaYNombre(Artista artista, String nombreCancion) {
		QueryBuilder queryBuilder = new QueryBuilderImpl(new HQLClauseFactory());
		queryBuilder.getSelectClause()
			.add("cancion");
		queryBuilder.getFromClause()
			.from(this.getPersistentClass()	, "cancion")
			.innerJoin("cancion.artista"	, "artista");
		queryBuilder.getWhereClause()
			.equals("artista", artista)
			.equals("cancion", "titulo", nombreCancion);
		return (Cancion) this.findUniqueByQueryBuilder(queryBuilder);
	}
	
	@SuppressWarnings("unchecked")
	public Object findUniqueByQueryBuilder2(QueryBuilder queryBuilder) {
		com.angel.dao.generic.query.Query query = queryBuilder.buildQuery();
		List<Object> entities = null;
		try {
			
			Query q = super.getSession().createQuery(query.getQuery());
			List<QueryConditionParam> conditions = query.getConditions();
			Object[] params = query.getParams();
			for(int i = 0; i < conditions.size(); i++){
				QueryConditionParam qcp = conditions.get(i);
				if(ParamType.OBJECT_PARAMETER == qcp.getParamType()){
					q.setParameter("param_" + i, params[i]);
				} else {
					q.setParameterList("param_" + i, (Collection) params[i]);
				}
			}
			entities = q.list();
		} catch(Exception e){
			throw new GenericDAOException("Error during finding query [" + query.getQuery() + "]", e);
		}
		if(entities.size() > 1){
			throw new GenericDAOException("Not unique result for query [" + query.getQuery() + "].");
		}
		if(entities.isEmpty()){
			throw new GenericDAOException("Query [" + query.getQuery() + "] didn't return a unique and NOT NULL result.");
		}
		return entities.get(0);
	}
}
