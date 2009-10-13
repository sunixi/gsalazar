/**
 * 
 */
package ar.com.gsalazar.services;

import java.util.List;

import ar.com.gsalazar.beans.Persona;
import ar.com.gsalazar.beans.TagSearch;
import ar.com.gsalazar.dtos.PersonaDTO;

import com.angel.architecture.services.interfaces.GenericService;

/**
 * @author Guillermo Salazar
 *
 */
public interface PersonaService extends GenericService {

	public PersonaDTO buscarUnicoPorNombre(String nombre);
	
	public List<PersonaDTO> buscarTodosPorApellido(String apellido);
	
	public PersonaDTO buscarUnicoPorEmail(String email);
	
	public List<PersonaDTO> buscarTodos();
	
	public List<Persona> buscarTodosPorTagsNames(List<String> tagsNames);
	
	public List<Persona> buscarTodosPorTagsSearch(List<TagSearch> tagsSearch);
}
