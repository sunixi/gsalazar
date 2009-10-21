/**
 * 
 */
package ar.com.gsalazar.services;

import java.util.List;

import ar.com.gsalazar.beans.Articulo;
import ar.com.gsalazar.beans.Buscable;
import ar.com.gsalazar.beans.Persona;
import ar.com.gsalazar.dtos.BusquedaInfo;

import com.angel.architecture.persistence.beans.TagSearch;
import com.angel.architecture.services.interfaces.GenericService;

/**
 * @author Guillermo Salazar
 *
 */
public interface SearcherService extends GenericService {

	/**
	 * Busca todos los objetos buscables que tengan asociado los {@link TagSearch} cuyo label
	 * esten en la lista especificada. Los objetos buscables son:
	 * <ul>
	 * 	<li>{@link Articulo}</li>
	 * 	<li>{@link Persona}</li>
	 * 	<li>{@link Proyecto}</li>
	 * </ul>
	 * 
	 * @param tagsNames labels de los {@link TagSearch} que se quieren buscar en las buscables.
	 * @return listado de buscables que tienen asociado los {@link TagSearch} cuyos labels se especifican.
	 */
	public List<Buscable> buscarTodosPorTagsNames(List<String> tagsNames);
	
	public List<Buscable> buscarTodosPorTagsSearch(List<TagSearch> tagsSearch);
	
	public List<Buscable> buscarTodosPorBusquedaInfo(BusquedaInfo busquedaInfo);
	
}
