/**
 * 
 */
package com.angel.gsalazar.services;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import ar.com.gsalazar.beans.Articulo;
import ar.com.gsalazar.beans.TagSearch;
import ar.com.gsalazar.daos.ArticuloDAO;
import ar.com.gsalazar.daos.TagSearchDAO;
import ar.com.gsalazar.services.ArticuloService;

import com.angel.gsalazar.GSalazarBaseTestCase;

/**
 * 
 *	@author Guillermo Daniel Salazar.
 *	@since 16/Semptiembre/2009.
 */
public class SearcherServiceTestCase extends GSalazarBaseTestCase{

	@Autowired
	private ArticuloDAO articuloDAO;
	@Autowired
	private TagSearchDAO tagSearchDAO;
	
	@Autowired
	private ArticuloService articuloService;
	
	@Test
	public void testBuscarArticulosPorTagsSearchConDAOs(){
		List<TagSearch> tagsSearch = new ArrayList<TagSearch>();
		tagsSearch.add(this.getTagSearchDAO().buscarUnicoPorLabel("Flex 3"));
		tagsSearch.add(this.getTagSearchDAO().buscarUnicoPorLabel("Hibernate"));
		List<Articulo> list = this.getArticuloDAO().buscarTodosPorTagsSearch(tagsSearch);

		assertNotNull("Articulos searchers shouldn't be null.", list);
		assertTrue("Articulos searchers size shouldn't be more than 0.", list.size() > 0);
	}
	
	@Test
	public void testBuscarArticulosPorTagsSearchConService(){
		List<String> tagsNames = new ArrayList<String>();
		tagsNames.add("Flex 3");
		tagsNames.add("Hibernate");
		List<Articulo> list = this.getArticuloService().buscarTodosPorTagsNames(tagsNames);

		assertNotNull("Articulos searchers shouldn't be null.", list);
		assertTrue("Articulos searchers size shouldn't be more than 0.", list.size() > 0);
	}

	/**
	 * @return the articuloDAO
	 */
	public ArticuloDAO getArticuloDAO() {
		return articuloDAO;
	}

	/**
	 * @param articuloDAO the articuloDAO to set
	 */
	public void setArticuloDAO(ArticuloDAO articuloDAO) {
		this.articuloDAO = articuloDAO;
	}

	/**
	 * @return the tagSearchDAO
	 */
	public TagSearchDAO getTagSearchDAO() {
		return tagSearchDAO;
	}

	/**
	 * @param tagSearchDAO the tagSearchDAO to set
	 */
	public void setTagSearchDAO(TagSearchDAO tagSearchDAO) {
		this.tagSearchDAO = tagSearchDAO;
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
