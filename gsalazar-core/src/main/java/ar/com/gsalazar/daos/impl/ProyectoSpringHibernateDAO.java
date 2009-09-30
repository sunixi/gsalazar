/**
 * 
 */
package ar.com.gsalazar.daos.impl;

import java.util.List;

import org.hibernate.criterion.Restrictions;

import ar.com.gsalazar.beans.Proyecto;
import ar.com.gsalazar.daos.ProyectoDAO;

import com.angel.architecture.persistence.ids.ObjectId;
import com.angel.dao.generic.impl.GenericSpringHibernateDAO;

/**
 * 
 * 
 * @author Guillermo Salazar
 * @since 28/Agosto/2009.
 *
 */
public class ProyectoSpringHibernateDAO extends GenericSpringHibernateDAO<Proyecto, ObjectId> implements ProyectoDAO {

	public ProyectoSpringHibernateDAO() {
		super(Proyecto.class, ObjectId.class);
	}

	public List<Proyecto> buscarTodosPorCantidadDesarrolladores(int cantidad) {
		return (List<Proyecto>) super.findAllByCriteria(Restrictions.sizeEq("desarrolladores", cantidad));
	}
}
