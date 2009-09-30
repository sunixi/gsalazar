/**
 * 
 */
package ar.com.gsalazar.daos;

import java.util.List;

import ar.com.gsalazar.beans.Proyecto;

import com.angel.architecture.persistence.ids.ObjectId;
import com.angel.dao.generic.interfaces.GenericDAO;

/**
 * @author Guillermo Salazar.
 * @since 27/Agosto/2009.
 *
 */
public interface ProyectoDAO extends GenericDAO<Proyecto, ObjectId>{

	public List<Proyecto> buscarTodosPorCantidadDesarrolladores(int cantidad);

}
