/**
 * 
 */
package ar.com.gsalazar.services;

import java.util.Collection;
import java.util.List;

import ar.com.gsalazar.beans.Articulo;
import ar.com.gsalazar.beans.Comentario;
import ar.com.gsalazar.beans.TagSearch;
import ar.com.gsalazar.dtos.BusquedaInfo;

import com.angel.architecture.services.interfaces.GenericService;

/**
 * @author Guillermo Salazar
 *
 */
public interface ArticuloService extends GenericService {

	public Collection<Articulo> buscarTodosContengaContenido(String contenido);
	
	public Articulo buscarUnicoPorTitulo(String titulo);
	
	public Collection<Articulo> buscarTodosContengaDescripcion(String descripcion);

	public List<Articulo> buscarTodosPorTagsNames(List<String> tagsNames);
	
	public List<Articulo> buscarTodosPorTagsSearch(List<TagSearch> tagsSearch);
	
	public Articulo updateArticulo(String tituloArticulo, Comentario comentario);

	public Articulo updateVisualizarArticulo(String tituloArticulo);
	
	public List<Articulo> buscarTodosPorBusquedaInfo(BusquedaInfo busquedaInfo);
}
