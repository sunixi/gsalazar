/**
 * 
 */
package ar.com.gsalazar.daos;

import java.util.List;

import ar.com.gsalazar.beans.Articulo;
import ar.com.gsalazar.beans.TagSearch;

import com.angel.architecture.persistence.ids.ObjectId;
import com.angel.dao.generic.interfaces.GenericDAO;

/**
 * @author Guillermo Salazar.
 * @since 27/Agosto/2009.
 *
 */
public interface ArticuloDAO extends GenericDAO<Articulo, ObjectId>{

	public Articulo buscarUnicoPorTitulo(String titulo);

	public List<Articulo> buscarTodosPorTags(List<TagSearch> tagsSearch);
	
	public List<Articulo> buscarTodosContenganContenido(String contenido);
	
	public List<Articulo> buscarTodosContenganDescripcion(String descripcion);
	
	public List<Articulo> buscarTodosCantidadFuentes(Integer cantidadFuentes);	

}
