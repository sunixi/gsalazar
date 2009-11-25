/**
 * 
 */
package ar.com.angelDurmiente.daos.impl;

import java.util.List;

import org.hibernate.criterion.Restrictions;

import ar.com.angelDurmiente.beans.Acorde;
import ar.com.angelDurmiente.beans.Album;
import ar.com.angelDurmiente.beans.Artista;
import ar.com.angelDurmiente.beans.CancionAcorde;
import ar.com.angelDurmiente.daos.CancionAcordeDAO;
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
public class CancionAcordeSpringHibernateDAO extends GenericSpringHibernateDAO<CancionAcorde, ObjectId> implements CancionAcordeDAO {

	public CancionAcordeSpringHibernateDAO() {
		super(CancionAcorde.class, ObjectId.class);
	}

	public List<CancionAcorde> buscarTodosPorTitulo(String titulo) {
		return (List<CancionAcorde>) super.findAll("titulo", titulo);
	}

	public List<CancionAcorde> buscarTodosPorContenido(String contenido) {
		return (List<CancionAcorde>) super.findAllByCriteria(Restrictions.like("texto", contenido));
	}
	
	public CancionAcorde buscarUnicoPorTituloArtistaYAlbum(String titulo, Artista artista, Album album) {
		QueryBuilder queryBuilder = new QueryBuilderImpl(new HQLClauseFactory());
		queryBuilder.getSelectClause()
			.add("cancionAcorde");
		queryBuilder.getFromClause()
			.from(this.getPersistentClass()	, "cancionAcorde")
			.innerJoin("cancionAcorde.artista"	, "artista")
			.innerJoin("artista.albums", "album");
		queryBuilder.getWhereClause()
			.equals("artista", artista)
			.equals("album", album)
			.equals("cancionAcorde", "titulo", titulo);
		return (CancionAcorde) this.findUniqueByQueryBuilder(queryBuilder);
	}
	
	public CancionAcorde buscarUnicoONuloPorTituloArtistaYAlbum(String titulo, Artista artista, Album album) {
		QueryBuilder queryBuilder = new QueryBuilderImpl(new HQLClauseFactory());
		queryBuilder.getSelectClause()
			.add("cancionAcorde");
		queryBuilder.getFromClause()
			.from(this.getPersistentClass()	, "cancionAcorde")
			.innerJoin("cancionAcorde.artista"	, "artista")
			.innerJoin("artista.albums", "album");
		queryBuilder.getWhereClause()
			.equals("artista", artista)
			.equals("album", album)
			.equals("cancionAcorde", "titulo", titulo);
		return (CancionAcorde) this.findUniqueOrNullByQueryBuilder(queryBuilder);
	}

	public List<CancionInfoDTO> buscarTodos() {
		QueryBuilder queryBuilder = new QueryBuilderImpl(new HQLClauseFactory());
		queryBuilder.getSelectClause()
			.add("new ar.com.angelDurmiente.dtos.CancionInfoDTO(ca")
			.add("al)");
		queryBuilder.getFromClause()
			.from(Artista.class			, "ar")
			.innerJoin("ar.albums"		, "al")
			.innerJoin("al.canciones"	, "ca");
		List<CancionInfoDTO> cancionesDTO = super.findAll(queryBuilder);
		return cancionesDTO;
	}

	public List<CancionAcorde> buscarTodosPorAcordes(List<Acorde> acordes) {
		QueryBuilder queryBuilder = new QueryBuilderImpl(new HQLClauseFactory());
		queryBuilder.getSelectClause()
			.add("cancionAcorde");
		queryBuilder.getFromClause()
			.from(this.getPersistentClass()	, "cancionAcorde")
			.innerJoin("cancionAcorde.artista"	, "artista")
			.innerJoin("artista.albums", "album");
		queryBuilder.getWhereClause()
			.inElements("acordes", acordes);
		return (List<CancionAcorde>) this.findAllByQueryBuilder(queryBuilder);
	}
}
