/**
 * 
 */
package ar.com.gsalazar.services;

import com.angel.architecture.services.interfaces.GenericService;

/**
 * @author Guillermo Salazar
 *
 */
public interface EnviarEmailService extends GenericService {

	public void solicitarCV(String email, String motivo);
	

}
