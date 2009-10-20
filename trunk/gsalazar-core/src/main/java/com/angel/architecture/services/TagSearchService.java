/**
 * 
 */
package com.angel.architecture.services;

import java.util.List;


import com.angel.architecture.persistence.beans.TagSearch;
import com.angel.architecture.persistence.beans.TagSearchContainer;
import com.angel.architecture.services.interfaces.GenericService;

/**
 * @author Guillermo Salazar
 *
 */
public interface TagSearchService extends GenericService {

	public TagSearch buscarUnicoONuloPorLabel(String label);
	
	public List<TagSearch> buscarTodosPorLabels(List<String> labels);
	
	public TagSearch buscarUnicoPorLabel(String label);
	
	public TagSearch buscarUnicoPorDescripcion(String descripcion);
	
	public TagSearch buscarUnicoONuloPorDescripcion(String descripcion);
	
	public TagSearchContainer buscarTodos();
}
