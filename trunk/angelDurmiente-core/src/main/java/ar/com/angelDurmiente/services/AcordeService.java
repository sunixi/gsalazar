/**
 * 
 */
package ar.com.angelDurmiente.services;

import java.util.List;

import ar.com.angelDurmiente.beans.Acorde;

import com.angel.architecture.services.interfaces.GenericService;

/**
 * @author Guillermo Salazar
 *
 */
public interface AcordeService extends GenericService {

	public List<Acorde> buscarTodosPorNombre(String nombre);
	
	public Acorde buscarUnicoPorNombreYOpcion(String nombre, int opcion);
	
	public Acorde buscarUnicoONuloPorNombreYOpcion(String nombre, int opcion);

}
