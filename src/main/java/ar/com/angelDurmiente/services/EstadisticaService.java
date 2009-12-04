/**
 * 
 */
package ar.com.angelDurmiente.services;

import ar.com.angelDurmiente.dtos.AngelDurmienteInfoDTO;

import com.angel.architecture.services.interfaces.GenericService;

/**
 * @author Guillermo Salazar
 *
 */
public interface EstadisticaService extends GenericService {

	public AngelDurmienteInfoDTO buscarInformacionAngelDurmiente();

}
