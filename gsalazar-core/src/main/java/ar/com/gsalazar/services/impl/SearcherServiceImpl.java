/**
 * 
 */
package ar.com.gsalazar.services.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import ar.com.gsalazar.beans.Articulo;
import ar.com.gsalazar.beans.Buscable;
import ar.com.gsalazar.beans.TagSearch;
import ar.com.gsalazar.services.ArticuloService;
import ar.com.gsalazar.services.SearcherService;

import com.angel.architecture.services.impl.GenericServiceImpl;

/**
 * @author Guillermo Salazar
 *
 */
public class SearcherServiceImpl extends GenericServiceImpl implements SearcherService{

	@Autowired
	private ArticuloService articuloService;
	
	public List<Buscable> buscarTodosPorTagsNames(List<String> tagsNames) {
		List<Articulo> articulosBuscables = this.getArticuloService().buscarTodosPorTagsNames(tagsNames);

		List<Buscable> buscables = new ArrayList<Buscable>();
		buscables.addAll(articulosBuscables);
		return buscables;
	}

	public List<Buscable> buscarTodosPorTagsSearch(List<TagSearch> tagsSearch) {
		List<Articulo> articulosBuscables = this.getArticuloService().buscarTodosPorTagsSearch(tagsSearch);

		List<Buscable> buscables = new ArrayList<Buscable>();
		buscables.addAll(articulosBuscables);
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
}
