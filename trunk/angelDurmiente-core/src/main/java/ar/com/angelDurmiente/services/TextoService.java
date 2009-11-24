/**
 * 
 */
package ar.com.angelDurmiente.services;

import ar.com.angelDurmiente.beans.Texto;

import com.angel.architecture.services.interfaces.GenericService;

/**
 * @author Guillermo Salazar
 *
 */
public interface TextoService extends GenericService {

	public Texto buscarUnicoPorTitulo(String titulo);

}
