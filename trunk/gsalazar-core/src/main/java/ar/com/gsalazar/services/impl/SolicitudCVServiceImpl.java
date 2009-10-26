/**
 * 
 */
package ar.com.gsalazar.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import ar.com.gsalazar.beans.SolicitudCV;
import ar.com.gsalazar.daos.SolicitudCVDAO;
import ar.com.gsalazar.services.SolicitudCVService;

import com.angel.architecture.services.impl.GenericServiceImpl;

/**
 * @author Guillermo Salazar
 *
 */
public class SolicitudCVServiceImpl extends GenericServiceImpl implements SolicitudCVService{

	@Autowired
	private SolicitudCVDAO solicitudCVDAO;

	public List<SolicitudCV> buscarTodosEnviados() {
		return this.getSolicitudCVDAO().buscarTodosEnviados();
	}

	public List<SolicitudCV> buscarTodosNoEnviados() {
		return this.getSolicitudCVDAO().buscarTodosNoEnviados();
	}

	public SolicitudCV createSolicitudCV(String email, String motivo) {
		return (SolicitudCV) super.create(new SolicitudCV(email, motivo));
	}

	/**
	 * @return the solicitudCVDAO
	 */
	public SolicitudCVDAO getSolicitudCVDAO() {
		return solicitudCVDAO;
	}

	/**
	 * @param solicitudCVDAO the solicitudCVDAO to set
	 */
	public void setSolicitudCVDAO(SolicitudCVDAO solicitudCVDAO) {
		this.solicitudCVDAO = solicitudCVDAO;
	}
}
