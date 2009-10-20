/**
 * 
 */
package com.angel.architecture.services.impl;

import java.util.List;


import com.angel.architecture.daos.TagSearchDAO;
import com.angel.architecture.persistence.beans.TagSearch;
import com.angel.architecture.persistence.beans.TagSearchContainer;
import com.angel.architecture.services.TagSearchService;
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
