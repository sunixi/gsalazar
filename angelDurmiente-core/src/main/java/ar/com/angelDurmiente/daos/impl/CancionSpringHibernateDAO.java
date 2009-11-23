/**
 * 
 */
package ar.com.angelDurmiente.daos.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;

import ar.com.angelDurmiente.beans.Artista;
import ar.com.angelDurmiente.beans.Cancion;
import ar.com.angelDurmiente.daos.CancionDAO;
import ar.com.angelDurmiente.dtos.CancionInfoDTO;

import com.angel.architecture.persistence.ids.ObjectId;
import com.angel.dao.generic.exceptions.GenericDAOException;
import com.angel.dao.generic.impl.GenericSpringHibernateDAO;
import com.angel.dao.generic.query.builder.QueryBuilder;
import com.angel.dao.generic.query.builder.impl.QueryBuilderImpl;
import com.angel.dao.generic.query.factory.impl.HQLClauseFactory;

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

	public List<CancionInfoDTO> buscarTodos() {
		/*
		String cancion;
		private String artista;
		private String usuario;
		private String album;
		 */
		QueryBuilder subQueryBuilder = new QueryBuilderImpl(new HQLClauseFactory());
		subQueryBuilder.getSelectClause()
			.add("us.name",			"as usuario")
			.add("us.creationDate", "as subido");
		
		QueryBuilder queryBuilder = new QueryBuilderImpl(new HQLClauseFactory());
		queryBuilder.getSelectClause()
			.add("new ar.com.angelDurmiente.dtos.CancionInfoDTO(ca.titulo")
			.add("ar.nombre")
			.add("te")
			.add("al.nombre)");
		queryBuilder.getFromClause()
			.from(Artista.class			, "ar")
			.innerJoin("ar.albums"		, "al")
			.innerJoin("al.canciones"	, "ca")
			.innerJoin("ca.textos"		, "te")
			.innerJoin("te.usuario"		, "us");
		com.angel.dao.generic.query.Query query = queryBuilder.buildQuery();
		List<?> entities = null;
		try {
			super.getHibernateTemplate().setFetchSize(query.getFetchSize());
			super.getHibernateTemplate().setMaxResults(query.getMaxResult());
			if(query.hasParams()){
				Query q = this.buildQuery(query);
				entities = q.list();
			} else {
				entities = super.getHibernateTemplate().find(query.getQuery());
			}
		} catch(Exception e){
			throw new GenericDAOException("Error during finding query [" + query.getQuery() + "]", e);
		}
		List<CancionInfoDTO> cancionesDTO = (List<CancionInfoDTO>) entities;
		return cancionesDTO;
	}
}
