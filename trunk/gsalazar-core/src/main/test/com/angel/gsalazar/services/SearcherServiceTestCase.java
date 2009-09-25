/**
 * 
 */
package com.angel.gsalazar.services;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import ar.com.gsalazar.beans.Articulo;
import ar.com.gsalazar.beans.ArticuloSearcher;
import ar.com.gsalazar.beans.TagSearch;
import ar.com.gsalazar.daos.ArticuloDAO;
import ar.com.gsalazar.daos.ArticuloSearcherDAO;
import ar.com.gsalazar.daos.TagSearchDAO;

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
	
	@Test
	public void testSearch(){
		List<TagSearch> tagsSearch = new ArrayList<TagSearch>();
		tagsSearch.add(this.getTagSearchDAO().buscarUnicoPorLabel("Flex 3"));
		tagsSearch.add(this.getTagSearchDAO().buscarUnicoPorLabel("Hibernate"));
		List<Articulo> list = this.getArticuloDAO().buscarTodosPorTags(tagsSearch);

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
}
