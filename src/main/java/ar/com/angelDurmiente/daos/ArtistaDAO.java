/**
 * 
 */
package ar.com.angelDurmiente.daos;

import java.util.List;

import ar.com.angelDurmiente.beans.Album;
import ar.com.angelDurmiente.beans.Artista;

import com.angel.architecture.persistence.ids.ObjectId;
import com.angel.dao.generic.interfaces.GenericDAO;

/**
 * 
 * @author Guillermo Salazar.
 * @since 19/Noviembre/2009.
 *
 */
public interface ArtistaDAO extends GenericDAO<Artista, ObjectId>{

	public Artista buscarUnicoPorNombre(String nombre);
	
	public Artista buscarUnicoONuloPorNombre(String nombre);
	
	public List<Artista> buscarTodosPorAlbum(String album);
	
	public List<Artista> buscarTodosPorAlbum(Album album);
	
	public Artista buscarUnicoPorNombreYAlbum(String nombre, Album album);
}
