/**
 * 
 */
package ar.com.angelDurmiente.daos;

import java.util.List;

import ar.com.angelDurmiente.beans.Acorde;

import com.angel.architecture.persistence.ids.ObjectId;
import com.angel.dao.generic.interfaces.GenericDAO;

/**
 * 
 * @author Guillermo Salazar.
 * @since 19/Noviembre/2009.
 *
 */
public interface AcordeDAO extends GenericDAO<Acorde, ObjectId>{

	public List<Acorde> buscarTodosPorNombre(String nombre);
	
	public Acorde buscarUnicoPorNombreYOpcion(String nombre, int opcion);
	
	public Acorde buscarUnicoONuloPorNombreYOpcion(String nombre, int opcion);

}
