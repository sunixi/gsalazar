/**
 * 
 */
package ar.com.angelDurmiente.daos.impl;

import ar.com.angelDurmiente.beans.Album;
import ar.com.angelDurmiente.beans.Cancion;
import ar.com.angelDurmiente.daos.AlbumDAO;

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
public class AlbumSpringHibernateDAO extends GenericSpringHibernateDAO<Album, ObjectId> implements AlbumDAO {

	public AlbumSpringHibernateDAO() {
		super(Album.class, ObjectId.class);
	}

	public Album buscarUnicoPorCancion(String nombreCancion) {
		QueryBuilder queryBuilder = new QueryBuilderImpl(new HQLClauseFactory());
		queryBuilder.getSelectClause()
			.add("album");
		queryBuilder.getFromClause()
			.from(Album.class,				"album")
			.innerJoin("album.canciones", "cancion");
		queryBuilder.getWhereClause()
			.equals("cancion", "titulo", nombreCancion);
		return super.findUniqueByQueryBuilder(queryBuilder);
	}

	public Album buscarUnicoPorCancion(Cancion cancion) {
		QueryBuilder queryBuilder = new QueryBuilderImpl(new HQLClauseFactory());
		queryBuilder.getSelectClause()
			.add("album");
		queryBuilder.getFromClause()
			.from(Album.class,				"album")
			.innerJoin("album.canciones", "cancion");
		queryBuilder.getWhereClause()
			.equals("cancion", cancion);
		return super.findUniqueByQueryBuilder(queryBuilder);
	}

	public Album buscarUnicoPorTitulo(String titulo) {
		return super.findUnique("nombre", titulo);
	}
}
