/**
 * 
 */
package ar.com.angelDurmiente.services;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import ar.com.angelDurmiente.AngelDurmienteBaseTestCase;
import ar.com.angelDurmiente.beans.Album;
import ar.com.angelDurmiente.beans.Artista;
import ar.com.angelDurmiente.beans.Cancion;
import ar.com.angelDurmiente.beans.Usuario;
import ar.com.angelDurmiente.documentoBuilder.impl.CancionBuilder;
import ar.com.angelDurmiente.dtos.TextoCompartirInfoDTO;

/**
 * 
 *	@author Guillermo Daniel Salazar.
 *	@since 16/Semptiembre/2009.
 */
public class TextoServiceTestCase extends AngelDurmienteBaseTestCase{

	@Autowired
	private UsuarioService usuarioService;
	@Autowired
	private TextoService textoService;
	@Autowired
	private CancionService cancionService;
	@Autowired
	private ArtistaService artistaService;
	@Autowired
	private AlbumService albumService;

	@Test
	public void testCompartirTextoValido(){
		String TITULO_CANCION = "Bonus Track 14";
		String NOMBRE_ARTISTA = "Maná";
		String NOMBRE_ALBUM = "MTV Unplugged";
		String documentoContenido = "";
		documentoContenido += "Texto de prueba para el contenido del documento.\n\n";
		documentoContenido += "\t\tSegundo Texto de prueba para el contenido del documento.\n\n";

		Usuario usuario = this.getUsuarioService().buscarUnicoPorNombreUsuario("angeldurmiente");
		TextoCompartirInfoDTO textoCompartirInfoDTO = new TextoCompartirInfoDTO();
		textoCompartirInfoDTO.setEstrategiaDocumentoBuilder(CancionBuilder.CANCION_BUILDER);		
		textoCompartirInfoDTO.setContenidoDocumento(documentoContenido);
		textoCompartirInfoDTO.setNombreAlbum(NOMBRE_ALBUM);
		textoCompartirInfoDTO.setNombreArtista(NOMBRE_ARTISTA);
		textoCompartirInfoDTO.setTituloDocumento(TITULO_CANCION);
		
		this.getTextoService().compartirTexto(textoCompartirInfoDTO, usuario);
		
		Artista artista = this.getArtistaService().buscarUnicoPorNombre(NOMBRE_ARTISTA);
		Album album = this.getAlbumService().buscarUnicoPorTitulo(NOMBRE_ALBUM);
		Cancion cancion = this.getCancionService().buscarUnicoPorTituloArtistaYAlbum(TITULO_CANCION, artista, album);
		assertNotNull("La cancion buscada NO debe ser nula, ya que se creo recientemente.", cancion);
		assertEquals("La cantidad de textos deben ser igual a 1, ya que recien se creo la cancion.", 1, cancion.cantidadTextos());
	}

	/**
	 * @return the usuarioService
	 */
	public UsuarioService getUsuarioService() {
		return usuarioService;
	}

	/**
	 * @param usuarioService the usuarioService to set
	 */
	public void setUsuarioService(UsuarioService usuarioService) {
		this.usuarioService = usuarioService;
	}

	/**
	 * @return the textoService
	 */
	public TextoService getTextoService() {
		return textoService;
	}

	/**
	 * @param textoService the textoService to set
	 */
	public void setTextoService(TextoService textoService) {
		this.textoService = textoService;
	}

	/**
	 * @return the cancionService
	 */
	public CancionService getCancionService() {
		return cancionService;
	}

	/**
	 * @param cancionService the cancionService to set
	 */
	public void setCancionService(CancionService cancionService) {
		this.cancionService = cancionService;
	}

	/**
	 * @return the artistaService
	 */
	public ArtistaService getArtistaService() {
		return artistaService;
	}

	/**
	 * @param artistaService the artistaService to set
	 */
	public void setArtistaService(ArtistaService artistaService) {
		this.artistaService = artistaService;
	}

	/**
	 * @return the albumService
	 */
	public AlbumService getAlbumService() {
		return albumService;
	}

	/**
	 * @param albumService the albumService to set
	 */
	public void setAlbumService(AlbumService albumService) {
		this.albumService = albumService;
	}
}
