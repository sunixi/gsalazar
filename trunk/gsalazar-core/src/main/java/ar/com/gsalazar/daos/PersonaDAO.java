/**
 * 
 */
package ar.com.gsalazar.daos;

import java.util.List;

import ar.com.gsalazar.beans.Persona;
import ar.com.gsalazar.beans.TagSearch;

import com.angel.architecture.persistence.ids.ObjectId;
import com.angel.dao.generic.interfaces.GenericDAO;

/**
 * @author Guillermo Salazar.
 * @since 27/Agosto/2009.
 *
 */
public interface PersonaDAO extends GenericDAO<Persona, ObjectId>{

	public Persona buscarUnicoPorNombre(String nombre);
	
	public List<Persona> buscarTodosPorApellido(String apellido);
	
	public Persona buscarUnicoPorEmail(String email);
	
	public List<Persona> buscarTodosPorTagsSearch(List<TagSearch> tagsSearch);

}
