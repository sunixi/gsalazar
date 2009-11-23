/**
 * 
 */
package ar.com.angelDurmiente.services;

import java.util.List;

import ar.com.angelDurmiente.beans.Cancion;
import ar.com.angelDurmiente.dtos.CancionInfoDTO;

import com.angel.architecture.services.interfaces.GenericService;

/**
 * @author Guillermo Salazar
 *
 */
public interface CancionService extends GenericService {

	public List<CancionInfoDTO> buscarTodos();
	
	public List<Cancion> buscarTodosPorTitulo(String titulo);
	
	public List<Cancion> buscarTodosPorContenido(String contenido);
}
