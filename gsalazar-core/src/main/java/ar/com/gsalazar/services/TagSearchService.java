/**
 * 
 */
package ar.com.gsalazar.services;

import ar.com.gsalazar.beans.TagSearch;
import ar.com.gsalazar.beans.TagSearchContainer;

import com.angel.architecture.services.interfaces.GenericService;

/**
 * @author Guillermo Salazar
 *
 */
public interface TagSearchService extends GenericService {

	public TagSearch buscarUnicoONuloPorLabel(String label);
	
	public TagSearch buscarUnicoPorLabel(String label);
	
	public TagSearch buscarUnicoPorDescripcion(String descripcion);
	
	public TagSearch buscarUnicoONuloPorDescripcion(String descripcion);
	
	public TagSearchContainer buscarTodos();
}
