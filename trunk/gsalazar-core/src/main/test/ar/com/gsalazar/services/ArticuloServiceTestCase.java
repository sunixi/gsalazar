/**
 * 
 */
package ar.com.gsalazar.services;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import ar.com.gsalazar.GSalazarBaseTestCase;
import ar.com.gsalazar.beans.Articulo;
import ar.com.gsalazar.beans.Comentario;
import ar.com.gsalazar.factories.ComentarioFactory;


/**
 * 
 *	@author Guillermo Daniel Salazar.
 *	@since 16/Semptiembre/2009.
 */
public class ArticuloServiceTestCase extends GSalazarBaseTestCase{

	@Autowired
	private ArticuloService articuloService;

	@Test
	public void testBuscarArticulosMasVisualizados(){
		this.beforeTestBuscarArticulosMasVisualizados();
		List<Articulo> articulosMasVisualizados = this.getArticuloService().buscarTodosMasVisualizados(2);
		
		assertNotNull("Los articulos buscados max visualizados no deben ser nulos.", articulosMasVisualizados);
		assertEquals("Deben haberse buscado 2 articulos mas visualizados.", 2, articulosMasVisualizados.size());
		
		assertEquals("El titulo del primer articulo mas visualizado debe ser 'Diseño de patrones en Flex'.",
				"Configurando Flex y Java desde Cero.", articulosMasVisualizados.get(0).getTitulo());
		assertEquals("El titulo del primer articulo mas visualizado debe ser 'Maven desde cero.'.",
				"Diseño de patrones en Flex", articulosMasVisualizados.get(1).getTitulo());
	}
	
	@Test
	public void testBuscarArticulosMayorRating(){
		this.beforeTestBuscarArticulosMayorRating();
		List<Articulo> articulosMayorRating = this.getArticuloService().buscarTodosMayorRating(2);
		
		assertNotNull("Los articulos buscados con mayor rating no deben ser nulos.", articulosMayorRating);
		assertEquals("Deben haberse buscado 2 articulos con mayor rating.", 2, articulosMayorRating.size());
		
		assertEquals("El titulo del primer articulo con mayor rating debe ser 'Diseño de patrones en Flex'.",
				"Diseño de patrones en Flex", articulosMayorRating.get(0).getTitulo());
		assertEquals("El titulo del primer articulo con mayor rating debe ser 'Maven desde cero.'.",
				"Maven desde cero.", articulosMayorRating.get(1).getTitulo());
	}
	
	protected void beforeTestBuscarArticulosMayorRating(){
		String tituloMavenDesdeCero = "Maven desde cero.";//Rating Promedio 5
		Comentario comentario1MavenDesdeCero = ComentarioFactory.createComentario(7, true);
		Comentario comentario2MavenDesdeCero = ComentarioFactory.createComentario(3, true);
		this.getArticuloService().updateArticulo(tituloMavenDesdeCero, comentario1MavenDesdeCero);
		this.getArticuloService().updateArticulo(tituloMavenDesdeCero, comentario2MavenDesdeCero);
		
		String tituloDisenioPatronesFlex = "Diseño de patrones en Flex";//Rating Promedio 7
		Comentario comentario1DisenioPatronesFlex = ComentarioFactory.createComentario(9, true);
		Comentario comentario2DisenioPatronesFlex = ComentarioFactory.createComentario(6, true);
		Comentario comentario3DisenioPatronesFlex = ComentarioFactory.createComentario(6, true);
		this.getArticuloService().updateArticulo(tituloDisenioPatronesFlex, comentario1DisenioPatronesFlex);
		this.getArticuloService().updateArticulo(tituloDisenioPatronesFlex, comentario2DisenioPatronesFlex);
		this.getArticuloService().updateArticulo(tituloDisenioPatronesFlex, comentario3DisenioPatronesFlex);

		String tituloIniciandoseEnFlex = "Iniciandose en Flex";//Rating Promedio 3
		Comentario comentario1IniciandoseEnFlex = ComentarioFactory.createComentario(2, true);
		Comentario comentario2IniciandoseEnFlex = ComentarioFactory.createComentario(4, true);
		Comentario comentario3IniciandoseEnFlex = ComentarioFactory.createComentario(3, true);
		this.getArticuloService().updateArticulo(tituloIniciandoseEnFlex, comentario1IniciandoseEnFlex);
		this.getArticuloService().updateArticulo(tituloIniciandoseEnFlex, comentario2IniciandoseEnFlex);
		this.getArticuloService().updateArticulo(tituloIniciandoseEnFlex, comentario3IniciandoseEnFlex);
		
		//String tituloConfigurandoFlexYJavaDesdeCero = "Configurando Flex y Java desde Cero.";
	}
	
	protected void beforeTestBuscarArticulosMasVisualizados(){
		String tituloMavenDesdeCero = "Maven desde cero.";//Visualizado 1
		this.getArticuloService().updateVisualizarArticulo(tituloMavenDesdeCero);
		
		String tituloDisenioPatronesFlex = "Diseño de patrones en Flex";//Visualizado 5
		this.getArticuloService().updateVisualizarArticulo(tituloDisenioPatronesFlex);
		this.getArticuloService().updateVisualizarArticulo(tituloDisenioPatronesFlex);
		this.getArticuloService().updateVisualizarArticulo(tituloDisenioPatronesFlex);
		this.getArticuloService().updateVisualizarArticulo(tituloDisenioPatronesFlex);
		this.getArticuloService().updateVisualizarArticulo(tituloDisenioPatronesFlex);

		String tituloIniciandoseEnFlex = "Iniciandose en Flex";//Rating Promedio 3
		this.getArticuloService().updateVisualizarArticulo(tituloIniciandoseEnFlex);
		this.getArticuloService().updateVisualizarArticulo(tituloIniciandoseEnFlex);
		this.getArticuloService().updateVisualizarArticulo(tituloIniciandoseEnFlex);
		
		String tituloConfigurandoFlexYJavaDesdeCero = "Configurando Flex y Java desde Cero."; //Visualizado 7
		this.getArticuloService().updateVisualizarArticulo(tituloConfigurandoFlexYJavaDesdeCero);
		this.getArticuloService().updateVisualizarArticulo(tituloConfigurandoFlexYJavaDesdeCero);
		this.getArticuloService().updateVisualizarArticulo(tituloConfigurandoFlexYJavaDesdeCero);
		this.getArticuloService().updateVisualizarArticulo(tituloConfigurandoFlexYJavaDesdeCero);
		this.getArticuloService().updateVisualizarArticulo(tituloConfigurandoFlexYJavaDesdeCero);
		this.getArticuloService().updateVisualizarArticulo(tituloConfigurandoFlexYJavaDesdeCero);
		this.getArticuloService().updateVisualizarArticulo(tituloConfigurandoFlexYJavaDesdeCero);
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
