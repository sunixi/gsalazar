/**
 * 
 */
package ar.com.gsalazar.services.impl;

import java.util.List;

import ar.com.gsalazar.beans.TagSearch;
import ar.com.gsalazar.beans.TagSearchContainer;
import ar.com.gsalazar.daos.TagSearchDAO;
import ar.com.gsalazar.services.TagSearchService;

import com.angel.architecture.services.impl.GenericServiceImpl;

/**
 * @author Guillermo Salazar
 *
 */
public class TagSearchServiceImpl extends GenericServiceImpl implements TagSearchService{

	public TagSearchContainer buscarTodos() {
		List<TagSearch> tagsSearchs = (List<TagSearch>) this.getTagSearchDAO().findAll();
		return new TagSearchContainer(tagsSearchs);
	}

	public TagSearch buscarUnicoONuloPorDescripcion(String descripcion) {
		return this.getTagSearchDAO().buscarUnicoONuloPorDescripcion(descripcion);
	}

	public TagSearch buscarUnicoONuloPorLabel(String label) {
		return this.getTagSearchDAO().buscarUnicoONuloPorLabel(label);
	}

	public TagSearch buscarUnicoPorDescripcion(String descripcion) {
		return this.getTagSearchDAO().buscarUnicoPorDescripcion(descripcion);
	}

	public TagSearch buscarUnicoPorLabel(String label) {
		return this.getTagSearchDAO().buscarUnicoPorLabel(label);
	}
	
	public TagSearchDAO getTagSearchDAO(){
		return (TagSearchDAO) super.getGenericDAO();
	}

	public List<TagSearch> buscarTodosPorLabels(List<String> labels) {
		return this.getTagSearchDAO().buscarTodosPorLabels(labels);
	}	
}
