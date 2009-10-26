/**
 * 
 */
package ar.com.gsalazar.services;

import java.util.List;

import ar.com.gsalazar.beans.SolicitudCV;

import com.angel.architecture.services.interfaces.GenericService;

/**
 * @author Guillermo Salazar
 *
 */
public interface SolicitudCVService extends GenericService {

	public List<SolicitudCV> buscarTodosEnviados();
	
	public List<SolicitudCV> buscarTodosNoEnviados();
	
	public SolicitudCV createSolicitudCV(String email, String motivo);
}
