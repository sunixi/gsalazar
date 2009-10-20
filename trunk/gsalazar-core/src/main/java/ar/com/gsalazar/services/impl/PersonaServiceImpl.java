/**
 * 
 */
package ar.com.gsalazar.services.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import ar.com.gsalazar.beans.Persona;
import ar.com.gsalazar.daos.PersonaDAO;
import ar.com.gsalazar.dtos.BusquedaInfo;
import ar.com.gsalazar.dtos.PersonaDTO;
import ar.com.gsalazar.services.PersonaService;

import com.angel.architecture.persistence.beans.TagSearch;
import com.angel.architecture.services.TagSearchService;
import com.angel.architecture.services.impl.GenericServiceImpl;

/**
 * @author Guillermo Salazar
 *
 */
public class PersonaServiceImpl extends GenericServiceImpl implements PersonaService{

	@Autowired
	private TagSearchService tagSearchService;
	
	protected PersonaDAO getPersonaDAO(){
		return (PersonaDAO) super.getGenericDAO();
	}

	public List<PersonaDTO> buscarTodosPorApellido(String apellido) {
		List<Persona> personas = this.getPersonaDAO().buscarTodosPorApellido(apellido);
		List<PersonaDTO> personasDTO = new ArrayList<PersonaDTO>();
		for(Persona p: personas){
			personasDTO.add(new PersonaDTO(p));
		}
		return personasDTO;
	}

	public PersonaDTO buscarUnicoPorEmail(String email) {
		Persona persona = this.getPersonaDAO().buscarUnicoPorEmail(email);
		return new PersonaDTO(persona);
	}

	public PersonaDTO buscarUnicoPorNombre(String nombre) {
		Persona persona = this.getPersonaDAO().buscarUnicoPorNombre(nombre);
		return new PersonaDTO(persona);
	}

	public List<PersonaDTO> buscarTodos() {
		List<Persona> personas = (List<Persona>) this.getPersonaDAO().findAll();
		List<PersonaDTO> personasDTO = new ArrayList<PersonaDTO>();
		for(Persona p: personas){
			personasDTO.add(new PersonaDTO(p));
		}
		return personasDTO;		
	}

	public List<Persona> buscarTodosPorTagsNames(List<String> tagsNames) {
		List<TagSearch> tagsSearch = this.getTagSearchService().buscarTodosPorLabels(tagsNames); 
		return 	this.buscarTodosPorTagsSearch(tagsSearch);
	}

	public List<Persona> buscarTodosPorTagsSearch(List<TagSearch> tagsSearch) {
		return this.getPersonaDAO().buscarTodosPorTagsSearch(tagsSearch);
	}
	
	public List<Persona> buscarTodosPorBusquedaInfo(BusquedaInfo busquedaInfo) {
		return this.getPersonaDAO().buscarTodosPorBusquedaInfo(busquedaInfo);
	}

	/**
	 * @return the tagSearchService
	 */
	public TagSearchService getTagSearchService() {
		return tagSearchService;
	}

	/**
	 * @param tagSearchService the tagSearchService to set
	 */
	public void setTagSearchService(TagSearchService tagSearchService) {
		this.tagSearchService = tagSearchService;
	}
}
