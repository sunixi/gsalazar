/**
 * 
 */
package ar.com.gsalazar.services;

import ar.com.gsalazar.dtos.ContactoWebDTO;

import com.angel.architecture.services.interfaces.GenericService;

/**
 * @author Guillermo Salazar
 *
 */
public interface ContactoWebService extends GenericService {

	public ContactoWebDTO buscarUnicoONuloPorNombre(String nombre);
	
	public ContactoWebDTO buscarUnicoPorNombre(String nombre);
}
