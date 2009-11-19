/**
 * 
 */
package ar.com.angelDurmiente.daos.impl;

import java.util.List;

import org.hibernate.criterion.Restrictions;

import ar.com.angelDurmiente.beans.Cancion;
import ar.com.angelDurmiente.daos.CancionDAO;

import com.angel.architecture.persistence.ids.ObjectId;
import com.angel.dao.generic.impl.GenericSpringHibernateDAO;

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

}
