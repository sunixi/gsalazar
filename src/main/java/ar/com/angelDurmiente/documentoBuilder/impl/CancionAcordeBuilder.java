/**
 * 
 */
package ar.com.angelDurmiente.documentoBuilder.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import ar.com.angelDurmiente.beans.Acorde;
import ar.com.angelDurmiente.beans.Album;
import ar.com.angelDurmiente.beans.Artista;
import ar.com.angelDurmiente.beans.CancionAcorde;
import ar.com.angelDurmiente.beans.Documento;
import ar.com.angelDurmiente.beans.Texto;
import ar.com.angelDurmiente.documentoBuilder.DocumentoBuilder;
import ar.com.angelDurmiente.dtos.AcordeDTO;
import ar.com.angelDurmiente.dtos.TextoCompartirInfoDTO;
import ar.com.angelDurmiente.services.AcordeService;
import ar.com.angelDurmiente.services.AlbumService;
import ar.com.angelDurmiente.services.ArtistaService;
import ar.com.angelDurmiente.services.CancionAcordeService;

/**
 * @author Guillermo Salazar
 * @since 23/Noviembre/2009.
 *
 */
public class CancionAcordeBuilder implements DocumentoBuilder{

	public static final String CANCION_ACORDE_BUILDER = "cancionacordebuilder";
	
	@Autowired
	private CancionAcordeService cancionAcordeService;
	@Autowired
	private AlbumService albumService;
	@Autowired
	private ArtistaService artistaService;
	@Autowired
	private AcordeService acordeService;
	
	public Documento buildDocumento(TextoCompartirInfoDTO textoCompartirInfoDTO, Texto texto) {
		Artista artista = this.getArtistaService()
				.buscarUnicoPorNombre(textoCompartirInfoDTO.getNombreArtista());
		Album album = this.getAlbumService()
				.buscarUnicoPorTitulo(textoCompartirInfoDTO.getNombreAlbum());
		CancionAcorde cancionAcorde = this.getCancionAcordeService()
				.buscarUnicoONuloPorTituloArtistaYAlbum(textoCompartirInfoDTO.getTituloDocumento(), artista, album);
		
		if(cancionAcorde == null){
			cancionAcorde = new CancionAcorde();
			cancionAcorde.setArtista(artista);
			cancionAcorde.setTitulo(textoCompartirInfoDTO.getTituloDocumento());

			List<Acorde> acordes = new ArrayList<Acorde>();
			for(AcordeDTO acordeDTO: textoCompartirInfoDTO.getNombreAcordes()){
				Acorde acorde = this.getAcordeService()
					.buscarUnicoONuloPorNombreYOpcion(acordeDTO.getNombre(), acordeDTO.getOpcion());
				acordes.add(acorde);
			}
			cancionAcorde.agregarAcordes(acordes);
		}
		cancionAcorde.agregarTexto(texto);
		return (Documento) this.getCancionAcordeService().create(cancionAcorde);
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

	/**
	 * @return the cancionAcordeService
	 */
	public CancionAcordeService getCancionAcordeService() {
		return cancionAcordeService;
	}

	/**
	 * @param cancionAcordeService the cancionAcordeService to set
	 */
	public void setCancionAcordeService(CancionAcordeService cancionAcordeService) {
		this.cancionAcordeService = cancionAcordeService;
	}

	/**
	 * @return the acordeService
	 */
	public AcordeService getAcordeService() {
		return acordeService;
	}

	/**
	 * @param acordeService the acordeService to set
	 */
	public void setAcordeService(AcordeService acordeService) {
		this.acordeService = acordeService;
	}
}
