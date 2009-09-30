/**
 * 
 */
package ar.com.gsalazar.services;

import java.util.List;

import ar.com.gsalazar.dtos.ProyectoDTO;

import com.angel.architecture.services.interfaces.GenericService;

/**
 * @author Guillermo Salazar
 *
 */
public interface ProyectoService extends GenericService {

	public List<ProyectoDTO> buscarTodosPorCantidadDesarrolladores(int cantidad);
	
	public List<ProyectoDTO> buscarTodos();
}
