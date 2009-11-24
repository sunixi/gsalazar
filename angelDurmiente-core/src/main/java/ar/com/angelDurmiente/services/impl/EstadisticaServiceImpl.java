/**
 * 
 */
package ar.com.angelDurmiente.services.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;

import ar.com.angelDurmiente.daos.CancionDAO;
import ar.com.angelDurmiente.daos.UsuarioDAO;
import ar.com.angelDurmiente.dtos.AngelDurmienteInfoDTO;
import ar.com.angelDurmiente.services.EstadisticaService;

import com.angel.architecture.services.impl.GenericServiceImpl;

/**
 * @author Guillermo Salazar
 * @since 23/Noviembre/2009.
 *
 */
public class EstadisticaServiceImpl extends GenericServiceImpl implements EstadisticaService {

	@Autowired
	private CancionDAO cancionDAO;
	@Autowired
	private UsuarioDAO usuarioDAO;

	public AngelDurmienteInfoDTO buscarInformacionAngelDurmiente() {
		AngelDurmienteInfoDTO angelDurmienteInfoDTO = new AngelDurmienteInfoDTO();
		angelDurmienteInfoDTO.setCanciones(this.getCancionDAO().count());
		angelDurmienteInfoDTO.setUsuarios(this.getUsuarioDAO().count());
		angelDurmienteInfoDTO.setFecha(new Date());
		return angelDurmienteInfoDTO;
	}

	/**
	 * @return the cancionDAO
	 */
	public CancionDAO getCancionDAO() {
		return cancionDAO;
	}

	/**
	 * @param cancionDAO the cancionDAO to set
	 */
	public void setCancionDAO(CancionDAO cancionDAO) {
		this.cancionDAO = cancionDAO;
	}

	/**
	 * @return the usuarioDAO
	 */
	public UsuarioDAO getUsuarioDAO() {
		return usuarioDAO;
	}

	/**
	 * @param usuarioDAO the usuarioDAO to set
	 */
	public void setUsuarioDAO(UsuarioDAO usuarioDAO) {
		this.usuarioDAO = usuarioDAO;
	}
}
