/**
 * 
 */
package ar.com.angelDurmiente.services;

import java.util.List;

import ar.com.angelDurmiente.beans.Acorde;
import ar.com.angelDurmiente.beans.Album;
import ar.com.angelDurmiente.beans.Artista;
import ar.com.angelDurmiente.beans.CancionAcorde;
import ar.com.angelDurmiente.dtos.CancionInfoDTO;

import com.angel.architecture.services.interfaces.GenericService;

/**
 * @author Guillermo Salazar
 *
 */
public interface CancionAcordeService extends GenericService {

	public List<CancionInfoDTO> buscarTodos();
	
	public List<CancionAcorde> buscarTodosPorTitulo(String titulo);
	
	public List<CancionAcorde> buscarTodosPorContenido(String contenido);
	
	public CancionAcorde buscarUnicoPorTituloArtistaYAlbum(String titulo, Artista artista, Album album);
	
	public CancionAcorde buscarUnicoONuloPorTituloArtistaYAlbum(String titulo, Artista artista, Album album);
	
	public List<CancionAcorde> buscarTodosPorAcordes(List<Acorde> acordes);
}
