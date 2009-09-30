/**
 * 
 */
package com.angel.gsalazar.htmlcleaner;

import java.io.IOException;

import org.htmlcleaner.HtmlCleaner;
import org.htmlcleaner.TagNode;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import ar.com.gsalazar.beans.Articulo;
import ar.com.gsalazar.services.ArticuloService;

import com.angel.gsalazar.GSalazarBaseTestCase;

/**
 * 
 *	@author Guillermo Daniel Salazar.
 *	@since 16/Semptiembre/2009.
 */
public class SearcherServiceTestCase extends GSalazarBaseTestCase{
	
	@Autowired
	private ArticuloService articuloService;
	
	@Test
	public void testBuscarArticulosPorTagsSearchConDAOs() throws IOException{
		Articulo articulo = this.getArticuloService().buscarUnicoPorTitulo("Configurando Flex y Java desde Cero.");
		HtmlCleaner cleaner = new HtmlCleaner();
		TagNode node = cleaner.clean(articulo.getContenido());
		System.out.println(node.getText());
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
