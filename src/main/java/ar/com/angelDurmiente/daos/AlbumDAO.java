/**
 * 
 */
package ar.com.angelDurmiente.daos;

import ar.com.angelDurmiente.beans.Album;
import ar.com.angelDurmiente.beans.Cancion;

import com.angel.architecture.persistence.ids.ObjectId;
import com.angel.dao.generic.interfaces.GenericDAO;

/**
 * 
 * @author Guillermo Salazar.
 * @since 19/Noviembre/2009.
 *
 */
public interface AlbumDAO extends GenericDAO<Album, ObjectId>{

	public Album buscarUnicoPorTitulo(String titulo);
	
	public Album buscarUnicoPorCancion(String cancion);
	
	public Album buscarUnicoPorCancion(Cancion cancion);
}
