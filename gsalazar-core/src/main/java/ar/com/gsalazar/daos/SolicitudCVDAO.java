/**
 * 
 */
package ar.com.gsalazar.daos;

import java.util.List;

import ar.com.gsalazar.beans.SolicitudCV;

import com.angel.architecture.persistence.ids.ObjectId;
import com.angel.dao.generic.interfaces.GenericDAO;

/**
 * @author Guillermo Salazar.
 * @since 27/Agosto/2009.
 *
 */
public interface SolicitudCVDAO extends GenericDAO<SolicitudCV, ObjectId>{
	
	public List<SolicitudCV> buscarTodosEnviados();
	
	public List<SolicitudCV> buscarTodosNoEnviados();
	
}
