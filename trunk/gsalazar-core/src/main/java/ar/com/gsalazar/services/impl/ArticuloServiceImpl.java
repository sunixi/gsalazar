/**
 * 
 */
package ar.com.gsalazar.services.impl;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import ar.com.gsalazar.beans.Articulo;
import ar.com.gsalazar.beans.Comentario;
import ar.com.gsalazar.beans.TagSearch;
import ar.com.gsalazar.daos.ArticuloDAO;
import ar.com.gsalazar.dtos.BusquedaInfo;
import ar.com.gsalazar.services.ArticuloService;
import ar.com.gsalazar.services.TagSearchService;

import com.angel.architecture.services.impl.GenericServiceImpl;

/**
 * @author Guillermo Salazar
 *
 */
public class ArticuloServiceImpl extends GenericServiceImpl implements ArticuloService{

	@Autowired
	private TagSearchService tagSearchService;
	
	/**
	 * @return the categoriaDAO
	 */
	public ArticuloDAO getArticuloDAO() {
		return (ArticuloDAO) super.getGenericDAO();
	}

	public Collection<Articulo> buscarTodosContengaContenido(String contenido) {
		return this.getArticuloDAO().buscarTodosContenganContenido(contenido);
	}
	
	public Collection<Articulo> buscarTodosContengaDescripcion(String descripcion) {
		return this.getArticuloDAO().buscarTodosContenganDescripcion(descripcion);
	}

	public Articulo buscarUnicoPorTitulo(String titulo) {
		return this.getArticuloDAO().buscarUnicoPorTitulo(titulo);
	}
	
	public List<Articulo> buscarTodosPorTagsNames(List<String> tagsNames){
		List<TagSearch> tagsSearch = this.getTagSearchService().buscarTodosPorLabels(tagsNames); 
		return 	this.buscarTodosPorTagsSearch(tagsSearch);
	}

	public Articulo updateVisualizarArticulo(String tituloArticulo){
		Articulo articulo = this.getArticuloDAO().buscarUnicoPorTitulo(tituloArticulo);
		articulo.visualizar();
		return (Articulo) super.update(articulo);
	}
	
	public Articulo updateArticulo(String tituloArticulo, Comentario comentario){
		comentario.updateNullAttributes();
		Articulo articulo = this.getArticuloDAO().buscarUnicoPorTitulo(tituloArticulo);
		articulo.addComentario(comentario);
		return 	(Articulo) super.update(articulo);
	}
	
	public List<Articulo> buscarTodosPorTagsSearch(List<TagSearch> tagsSearch){ 
		return 	this.getArticuloDAO().buscarTodosPorTagsSearch(tagsSearch);
	}

	public List<Articulo> buscarTodosPorBusquedaInfo(BusquedaInfo busquedaInfo) {
		return 	this.getArticuloDAO().buscarTodosPorBusquedaInfo(busquedaInfo);
	}

	public List<Articulo> buscarTodosUltimosAgregados(int cantidadAgregada){
		return 	this.getArticuloDAO().buscarTodosUltimosAgregados(cantidadAgregada);
	}
	
	public List<Articulo> buscarTodosUltimosComentados(int cantidadComentada){
		return 	this.getArticuloDAO().buscarTodosUltimosComentados(cantidadComentada);
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
