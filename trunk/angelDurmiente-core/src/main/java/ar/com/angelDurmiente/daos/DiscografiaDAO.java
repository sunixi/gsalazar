/**
 * 
 */
package ar.com.angelDurmiente.daos;

import java.sql.Date;
import java.util.List;

import ar.com.angelDurmiente.beans.Discografia;

import com.angel.architecture.persistence.ids.ObjectId;
import com.angel.dao.generic.interfaces.GenericDAO;

/**
 * 
 * @author Guillermo Salazar.
 * @since 19/Noviembre/2009.
 *
 */
public interface DiscografiaDAO extends GenericDAO<Discografia, ObjectId>{

	public Discografia buscarUnicoPorNombreArtista(String nombre);
	
	public List<Discografia> buscarTodosEntre(Date desde, Date hasta);
	
}
