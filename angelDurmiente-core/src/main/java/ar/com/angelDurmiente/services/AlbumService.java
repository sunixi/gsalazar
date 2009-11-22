/**
 * 
 */
package ar.com.angelDurmiente.services;

import ar.com.angelDurmiente.beans.Album;
import ar.com.angelDurmiente.beans.Cancion;

import com.angel.architecture.services.interfaces.GenericService;

/**
 * @author Guillermo Salazar
 *
 */
public interface AlbumService extends GenericService {

	
	public Album buscarUnicoPorTitulo(String titulo);
	
	public Album buscarUnicoPorCancion(String cancion);
	
	public Album buscarUnicoPorCancion(Cancion cancion);
}
