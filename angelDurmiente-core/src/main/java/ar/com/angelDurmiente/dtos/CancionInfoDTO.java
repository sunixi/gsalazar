/**
 * 
 */
package ar.com.angelDurmiente.dtos;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Guillermo Salazar
 * @since 19/Noviembre/2009.
 *
 */
public class CancionInfoDTO implements Serializable {

	private static final long serialVersionUID = 6936830093090143041L;

	private String cancion;
	private String artista;
	private List<CancionDTO> cancionesDTO;
	private String album;
	
	public CancionInfoDTO(){
		super();
		this.setCancionesDTO(new ArrayList<CancionDTO>());
	}
	
	public CancionInfoDTO(String titulo, String nombreArtista, Object textos, String nombreAlbum){
		this();
		this.setCancion(titulo);
		this.setArtista(nombreArtista);
		this.setAlbum(nombreAlbum);
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
	 * @return the cancionesDTO
	 */
	public List<CancionDTO> getCancionesDTO() {
		return cancionesDTO;
	}

	/**
	 * @param cancionesDTO the cancionesDTO to set
	 */
	public void setCancionesDTO(List<CancionDTO> cancionesDTO) {
		this.cancionesDTO = cancionesDTO;
	}
}
