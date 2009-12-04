/**
 * 
 */
package ar.com.angelDurmiente.dtos;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import ar.com.angelDurmiente.beans.Album;
import ar.com.angelDurmiente.beans.Cancion;
import ar.com.angelDurmiente.beans.Texto;

/**
 * @author Guillermo Salazar
 * @since 19/Noviembre/2009.
 *
 */
public class CancionInfoDTO implements Serializable {

	private static final long serialVersionUID = 6936830093090143041L;

	private String cancion;
	private String artista;
	private List<TextoDTO> textosDTO;
	private String album;
	
	public CancionInfoDTO(){
		super();
		this.setTextosDTO(new ArrayList<TextoDTO>());
	}
	
	public CancionInfoDTO(Cancion cancion, Album album){
		this();
		this.setCancion(cancion.getTitulo());
		this.setArtista(cancion.getArtista().getNombre());
		this.setAlbum(album.getNombre());
		
		for(Texto t: cancion.getTextos()){
			this.getTextosDTO().add(new TextoDTO(t));
		}
	}
	
	/**
	 * @return the cancion
	 */
	public String getCancion() {
		return cancion;
	}
	/**
	 * @param cancion the cancion to set
	 */
	public void setCancion(String cancion) {
		this.cancion = cancion;
	}
	/**
	 * @return the artista
	 */
	public String getArtista() {
		return artista;
	}
	/**
	 * @param artista the artista to set
	 */
	public void setArtista(String artista) {
		this.artista = artista;
	}

	/**
	 * @return the album
	 */
	public String getAlbum() {
		return album;
	}
	/**
	 * @param album the album to set
	 */
	public void setAlbum(String album) {
		this.album = album;
	}

	/**
	 * @return the textosDTO
	 */
	public List<TextoDTO> getTextosDTO() {
		return textosDTO;
	}

	/**
	 * @param textosDTO the textosDTO to set
	 */
	public void setTextosDTO(List<TextoDTO> textosDTO) {
		this.textosDTO = textosDTO;
	}
}
