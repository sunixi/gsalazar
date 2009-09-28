/**
 * 
 */
package ar.com.gsalazar.services;

import java.util.List;

import ar.com.gsalazar.beans.Buscable;
import ar.com.gsalazar.beans.TagSearch;

import com.angel.architecture.services.interfaces.GenericService;

/**
 * @author Guillermo Salazar
 *
 */
public interface SearcherService extends GenericService {

	public List<Buscable> buscarTodosPorTagsNames(List<String> tagsNames);
	
	public List<Buscable> buscarTodosPorTagsSearch(List<TagSearch> tagsSearch);
	
}
