/**
 * 
 */
package ar.com.angelDurmiente.daos.impl;

import java.sql.Date;
import java.util.List;

import org.hibernate.criterion.Restrictions;

import ar.com.angelDurmiente.beans.Discografia;
import ar.com.angelDurmiente.daos.DiscografiaDAO;

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
public class DiscografiaSpringHibernateDAO extends GenericSpringHibernateDAO<Discografia, ObjectId> implements DiscografiaDAO {

	public DiscografiaSpringHibernateDAO() {
		super(Discografia.class, ObjectId.class);
	}

	public List<Discografia> buscarTodosEntre(Date desde, Date hasta) {
		return (List<Discografia>) super.findAllByCriteria(Restrictions.between("desde", desde, hasta), Restrictions.between("hasta", desde, hasta));
	}

	public Discografia buscarUnicoPorNombreArtista(String nombre) {
		QueryBuilder queryBuilder = new QueryBuilderImpl(new HQLClauseFactory());
		queryBuilder.getFromClause()
			.from(super.getPersistentClass());
		queryBuilder.getWhereClause()
			.equals("artista.nombre", nombre);
		return super.findUniqueByQueryBuilder(queryBuilder);
	}
}
