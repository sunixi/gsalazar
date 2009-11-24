/**
 * 
 */
package ar.com.angelDurmiente.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import ar.com.angelDurmiente.beans.Album;
import ar.com.angelDurmiente.beans.Artista;
import ar.com.angelDurmiente.beans.Cancion;
import ar.com.angelDurmiente.daos.CancionDAO;
import ar.com.angelDurmiente.dtos.CancionInfoDTO;
import ar.com.angelDurmiente.services.AlbumService;
import ar.com.angelDurmiente.services.ArtistaService;
import ar.com.angelDurmiente.services.CancionService;

import com.angel.architecture.services.impl.GenericServiceImpl;

/**
 * @author Guillermo Salazar
 *
 */
public class CancionServiceImpl extends GenericServiceImpl implements CancionService{

	@Autowired
	private ArtistaService artistaService;
	@Autowired
	private AlbumService albumService;
	
	public List<Cancion> buscarTodosPorContenido(String contenido) {
		return this.getCancionDAO().buscarTodosPorContenido(contenido);
	}

	public List<Cancion> buscarTodosPorTitulo(String titulo) {
		return this.getCancionDAO().buscarTodosPorTitulo(titulo);
	}
	
	public List<CancionInfoDTO> buscarTodos(){
		return this.getCancionDAO().buscarTodos();
	}

	protected CancionDAO getCancionDAO(){
		return (CancionDAO) super.getGenericDAO();
	}

	public Cancion buscarUnicoPorTituloArtistaYAlbum(String titulo, Artista artista, Album album) {
		return this.getCancionDAO().buscarUnicoPorTituloArtistaYAlbum(titulo, artista, album);
	}
	
	public Cancion buscarUnicoONuloPorTituloArtistaYAlbum(String titulo, Artista artista, Album album) {
		return this.getCancionDAO().buscarUnicoONuloPorTituloArtistaYAlbum(titulo, artista, album);
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
