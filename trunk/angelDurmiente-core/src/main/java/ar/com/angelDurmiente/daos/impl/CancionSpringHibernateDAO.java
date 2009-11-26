/**
 * 
 */
package ar.com.angelDurmiente.daos.impl;

import java.util.List;

import org.hibernate.criterion.Restrictions;

import ar.com.angelDurmiente.beans.Album;
import ar.com.angelDurmiente.beans.Artista;
import ar.com.angelDurmiente.beans.Cancion;
import ar.com.angelDurmiente.daos.CancionDAO;
import ar.com.angelDurmiente.dtos.CancionInfoDTO;

import com.angel.architecture.persistence.ids.ObjectId;
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
	
	public Cancion buscarUnicoPorTituloArtistaYAlbum(String titulo, Artista artista, Album album) {
		QueryBuilder queryBuilder = new QueryBuilderImpl(new HQLClauseFactory());
		queryBuilder.getSelectClause()
			.add("cancion");
		queryBuilder.getFromClause()
			.from(this.getPersistentClass()	, "cancion")
			.innerJoin("cancion.artista"	, "artista")
			.innerJoin("artista.albums", "album");
		queryBuilder.getWhereClause()
			.equals("artista", artista)
			.equals("album", album)
			.equals("cancion", "titulo", titulo);
		return (Cancion) this.findUniqueByQueryBuilder(queryBuilder);
	}
	
	public Cancion buscarUnicoONuloPorTituloArtistaYAlbum(String titulo, Artista artista, Album album) {
		QueryBuilder queryBuilder = new QueryBuilderImpl(new HQLClauseFactory());
		queryBuilder.getSelectClause()
			.add("cancion");
		queryBuilder.getFromClause()
			.from(this.getPersistentClass()	, "cancion")
			.innerJoin("cancion.artista"	, "artista")
			.innerJoin("artista.albums", "album");
		queryBuilder.getWhereClause()
			.equals("artista", artista)
			.equals("album", album)
			.equals("cancion", "titulo", titulo);
		return (Cancion) this.findUniqueOrNullByQueryBuilder(queryBuilder);
	}

	public List<CancionInfoDTO> buscarTodos() {
		QueryBuilder queryBuilder = new QueryBuilderImpl(new HQLClauseFactory());
		queryBuilder.getSelectClause()
			.add("ca")
			.add("al")
			.assignBeanClass(CancionInfoDTO.class);
		queryBuilder.getFromClause()
			.from(Artista.class			, "ar")
			.innerJoin("ar.albums"		, "al")
			.innerJoin("al.canciones"	, "ca");
		List<CancionInfoDTO> cancionesDTO = super.findAllEntitiesByQueryBuilder(queryBuilder);
		return cancionesDTO;
	}
}
