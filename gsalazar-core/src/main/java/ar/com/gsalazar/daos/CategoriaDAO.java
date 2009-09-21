/**
 * 
 */
package ar.com.gsalazar.daos;

import java.util.Collection;

import ar.com.gsalazar.beans.Categoria;

import com.angel.architecture.persistence.ids.ObjectId;
import com.angel.dao.generic.interfaces.GenericDAO;

/**
 * @author Guillermo Salazar.
 * @since 27/Agosto/2009.
 *
 */
public interface CategoriaDAO extends GenericDAO<Categoria, ObjectId>{

	public Categoria buscarUnicoPorNombre(String nombre);
	
	public Categoria buscarUnicoONuloPorNombre(String nombre);

	public Categoria buscarUnicoPorDescripcion(String descripcion);
	
	public Collection<Categoria> buscarTodosPorContieneDescripcion(String descripcion);

}
