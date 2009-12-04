/**
 * 
 */
package ar.com.angelDurmiente.daos;

import java.util.List;

import ar.com.angelDurmiente.beans.Acorde;
import ar.com.angelDurmiente.beans.Album;
import ar.com.angelDurmiente.beans.Artista;
import ar.com.angelDurmiente.beans.CancionAcorde;
import ar.com.angelDurmiente.dtos.CancionInfoDTO;

import com.angel.architecture.persistence.ids.ObjectId;
import com.angel.dao.generic.interfaces.GenericDAO;

/**
 * 
 * @author Guillermo Salazar.
 * @since 19/Noviembre/2009.
 *
 */
public interface CancionAcordeDAO extends GenericDAO<CancionAcorde, ObjectId>{

	public List<CancionInfoDTO> buscarTodos();
	
	public List<CancionAcorde> buscarTodosPorTitulo(String titulo);
	
	public List<CancionAcorde> buscarTodosPorAcordes(List<Acorde> acordes);
	
	public List<CancionAcorde> buscarTodosPorContenido(String contenido);

	public CancionAcorde buscarUnicoPorTituloArtistaYAlbum(String titulo, Artista artista, Album album);
	
	public CancionAcorde buscarUnicoONuloPorTituloArtistaYAlbum(String titulo, Artista artista, Album album);

}
