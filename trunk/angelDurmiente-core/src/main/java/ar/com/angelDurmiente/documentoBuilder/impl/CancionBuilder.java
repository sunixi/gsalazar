/**
 * 
 */
package ar.com.angelDurmiente.documentoBuilder.impl;

import org.springframework.beans.factory.annotation.Autowired;

import ar.com.angelDurmiente.beans.Album;
import ar.com.angelDurmiente.beans.Artista;
import ar.com.angelDurmiente.beans.Cancion;
import ar.com.angelDurmiente.beans.Documento;
import ar.com.angelDurmiente.beans.Texto;
import ar.com.angelDurmiente.documentoBuilder.DocumentoBuilder;
import ar.com.angelDurmiente.dtos.TextoCompartirInfoDTO;
import ar.com.angelDurmiente.services.AlbumService;
import ar.com.angelDurmiente.services.ArtistaService;
import ar.com.angelDurmiente.services.CancionService;

/**
 * @author Guillermo Salazar
 * @since 23/Noviembre/2009.
 *
 */
public class CancionBuilder implements DocumentoBuilder{

	@Autowired
	private CancionService cancionService;
	@Autowired
	private AlbumService albumService;
	@Autowired
	private ArtistaService artistaService;

	public Documento buildDocumento(TextoCompartirInfoDTO textoCompartirInfoDTO, Texto texto) {
		Artista artista = this.getArtistaService()
					.buscarUnicoPorNombre(textoCompartirInfoDTO.getNombreArtista());
		Album album = this.getAlbumService().buscarUnicoPorTitulo(textoCompartirInfoDTO.getNombreAlbum());

		Cancion cancion = this.getCancionService()
				.buscarUnicoONuloPorTituloArtistaYAlbum(textoCompartirInfoDTO.getTituloDocumento(), artista, album);
		
		if(cancion == null){
			cancion = new Cancion();
		}

		cancion.setArtista(artista);
		cancion.setTitulo(textoCompartirInfoDTO.getTituloDocumento());
		cancion.agregarTexto(texto);

		return (Documento) this.getCancionService().create(cancion);
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
}
