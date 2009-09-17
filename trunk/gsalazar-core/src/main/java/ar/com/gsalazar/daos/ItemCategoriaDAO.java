/**
 * 
 */
package ar.com.gsalazar.daos;

import java.util.Collection;

import ar.com.gsalazar.beans.ItemCategoria;

import com.angel.architecture.persistence.ids.ObjectId;
import com.angel.dao.generic.interfaces.GenericDAO;

/**
 * @author Guillermo Salazar.
 * @since 27/Agosto/2009.
 *
 */
public interface ItemCategoriaDAO extends GenericDAO<ItemCategoria, ObjectId>{

	public ItemCategoria buscarUnicoPorNombre(String nombre);
	
	public ItemCategoria buscarUnicoONuloPorNombre(String nombre);

	public ItemCategoria buscarUnicoPorDescripcion(String descripcion);
	
	public Collection<ItemCategoria> buscarTodosPorContieneDescripcion(String descripcion);

}
