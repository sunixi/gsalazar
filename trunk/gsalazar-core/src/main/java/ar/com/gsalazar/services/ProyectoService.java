/**
 * 
 */
package ar.com.gsalazar.services;

import java.util.List;

import ar.com.gsalazar.beans.Proyecto;
import ar.com.gsalazar.beans.TagSearch;
import ar.com.gsalazar.dtos.BusquedaInfo;

import com.angel.architecture.services.interfaces.GenericService;

/**
 * @author Guillermo Salazar
 *
 */
public interface ProyectoService extends GenericService {

	public List<Proyecto> buscarTodosPorCantidadDesarrolladores(int cantidad);
	
	public List<Proyecto> buscarTodosPorTagsNames(List<String> tagsNames);
	
	public List<Proyecto> buscarTodosPorTagsSearch(List<TagSearch> tagsSearch);
	
	public List<Proyecto> buscarTodos();
	
	public List<Proyecto> buscarTodosPorBusquedaInfo(BusquedaInfo busquedaInfo);
	
	public List<Proyecto> buscarTodosEnDesarrollo(int cantidadMaxima);

}
