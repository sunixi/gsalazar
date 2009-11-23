/**
 * 
 */
package ar.com.angelDurmiente.daos;

import java.util.List;

import ar.com.angelDurmiente.beans.Artista;
import ar.com.angelDurmiente.beans.Cancion;
import ar.com.angelDurmiente.dtos.CancionInfoDTO;

import com.angel.architecture.persistence.ids.ObjectId;
import com.angel.dao.generic.interfaces.GenericDAO;

/**
 * 
 * @author Guillermo Salazar.
 * @since 19/Noviembre/2009.
 *
 */
public interface CancionDAO extends GenericDAO<Cancion, ObjectId>{

	public List<CancionInfoDTO> buscarTodos();
	
	public List<Cancion> buscarTodosPorTitulo(String titulo);
	
	public List<Cancion> buscarTodosPorContenido(String contenido);

	public Cancion buscarUnicoPorArtistaYNombre(Artista artista, String nombreCancion);

}
