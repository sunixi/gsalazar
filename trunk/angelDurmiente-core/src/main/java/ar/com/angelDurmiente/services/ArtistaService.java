/**
 * 
 */
package ar.com.angelDurmiente.services;

import ar.com.angelDurmiente.beans.Artista;

import com.angel.architecture.services.interfaces.GenericService;

/**
 * @author Guillermo Salazar
 *
 */
public interface ArtistaService extends GenericService {

	public Artista buscarUnicoPorNombre(String nombre);
}
