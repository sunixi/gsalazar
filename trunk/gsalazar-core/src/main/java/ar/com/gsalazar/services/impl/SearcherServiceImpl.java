/**
 * 
 */
package ar.com.gsalazar.services.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import ar.com.gsalazar.beans.Articulo;
import ar.com.gsalazar.beans.Buscable;
import ar.com.gsalazar.beans.Persona;
import ar.com.gsalazar.beans.Proyecto;
import ar.com.gsalazar.beans.TagSearch;
import ar.com.gsalazar.services.ArticuloService;
import ar.com.gsalazar.services.PersonaService;
import ar.com.gsalazar.services.ProyectoService;
import ar.com.gsalazar.services.SearcherService;

import com.angel.architecture.services.impl.GenericServiceImpl;

/**
 * @author Guillermo Salazar
 *
 */
public class SearcherServiceImpl extends GenericServiceImpl implements SearcherService{

	@Autowired
	private ArticuloService articuloService;
	
	@Autowired
	private ProyectoService proyectoService;
	
	@Autowired
	private PersonaService personaService;
	
	
	public List<Buscable> buscarTodosPorTagsNames(List<String> tagsNames) {
		List<Articulo> articulosBuscables = this.getArticuloService().buscarTodosPorTagsNames(tagsNames);
		List<Proyecto> proyectosBuscables = this.getProyectoService().buscarTodosPorTagsNames(tagsNames);
		List<Persona> personasBuscables = this.getPersonaService().buscarTodosPorTagsNames(tagsNames);
		
		List<Buscable> buscables = new ArrayList<Buscable>();
		buscables.addAll(articulosBuscables);
		buscables.addAll(proyectosBuscables);
		buscables.addAll(personasBuscables);
		return buscables;
	}

	public List<Buscable> buscarTodosPorTagsSearch(List<TagSearch> tagsSearch) {
		List<Articulo> articulosBuscables = this.getArticuloService().buscarTodosPorTagsSearch(tagsSearch);
		List<Proyecto> proyectosBuscables = this.getProyectoService().buscarTodosPorTagsSearch(tagsSearch);
		List<Persona> personasBuscables = this.getPersonaService().buscarTodosPorTagsSearch(tagsSearch);

		List<Buscable> buscables = new ArrayList<Buscable>();
		buscables.addAll(articulosBuscables);
		buscables.addAll(proyectosBuscables);
		buscables.addAll(personasBuscables);
		return buscables;
	}

	/**
	 * @return the articuloService
	 */
	public ArticuloService getArticuloService() {
		return articuloService;
	}

	/**
	 * @param articuloService the articuloService to set
	 */
	public void setArticuloService(ArticuloService articuloService) {
		this.articuloService = articuloService;
	}

	/**
	 * @return the proyectoService
	 */
	public ProyectoService getProyectoService() {
		return proyectoService;
	}

	/**
	 * @param proyectoService the proyectoService to set
	 */
	public void setProyectoService(ProyectoService proyectoService) {
		this.proyectoService = proyectoService;
	}

	/**
	 * @return the personaService
	 */
	public PersonaService getPersonaService() {
		return personaService;
	}

	/**
	 * @param personaService the personaService to set
	 */
	public void setPersonaService(PersonaService personaService) {
		this.personaService = personaService;
	}
}
