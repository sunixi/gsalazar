/**
 * 
 */
package ar.com.gsalazar.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import ar.com.gsalazar.beans.Proyecto;
import ar.com.gsalazar.beans.TagSearch;
import ar.com.gsalazar.daos.ProyectoDAO;
import ar.com.gsalazar.dtos.BusquedaInfo;
import ar.com.gsalazar.services.ProyectoService;
import ar.com.gsalazar.services.TagSearchService;

import com.angel.architecture.services.impl.GenericServiceImpl;

/**
 * @author Guillermo Salazar
 *
 */
public class ProyectoServiceImpl extends GenericServiceImpl implements ProyectoService{

	@Autowired
	private TagSearchService tagSearchService;

	protected ProyectoDAO getProyectoDAO(){
		return (ProyectoDAO) super.getGenericDAO();
	}

	public List<Proyecto> buscarTodosPorCantidadDesarrolladores(int cantidad) {
		return this.getProyectoDAO().buscarTodosPorCantidadDesarrolladores(cantidad);
	}

	public List<Proyecto> buscarTodos() {
		return (List<Proyecto>) this.getProyectoDAO().findAll();
	}

	public List<Proyecto> buscarTodosPorTagsNames(List<String> tagsNames) {
		List<TagSearch> tagsSearch = this.getTagSearchService().buscarTodosPorLabels(tagsNames); 
		return 	this.buscarTodosPorTagsSearch(tagsSearch);
	}

	public List<Proyecto> buscarTodosPorTagsSearch(List<TagSearch> tagsSearch) {
		return this.getProyectoDAO().buscarTodosPorTagsSearch(tagsSearch);
	}
	
	public List<Proyecto> buscarTodosPorBusquedaInfo(BusquedaInfo busquedaInfo) {
		return this.getProyectoDAO().buscarTodosPorBusquedaInfo(busquedaInfo);
	}

	public List<Proyecto> buscarTodosEnDesarrollo(int cantidadMaxima) {
		return this.getProyectoDAO().buscarTodosEnDesarrollo(cantidadMaxima);
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
