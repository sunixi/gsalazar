/**
 * 
 */
package ar.com.gsalazar.daos.impl;

import java.util.List;

import ar.com.gsalazar.beans.SolicitudCV;
import ar.com.gsalazar.daos.SolicitudCVDAO;

import com.angel.architecture.persistence.ids.ObjectId;
import com.angel.dao.generic.impl.GenericSpringHibernateDAO;

/**
 * 
 * 
 * @author Guillermo Salazar
 * @since 28/Agosto/2009.
 *
 */
public class SolicitudCVSpringHibernateDAO extends GenericSpringHibernateDAO<SolicitudCV, ObjectId> implements SolicitudCVDAO {

	public SolicitudCVSpringHibernateDAO() {
		super(SolicitudCV.class, ObjectId.class);
	}

	public List<SolicitudCV> buscarTodosEnviados() {
		return (List<SolicitudCV>) super.findAll("enviado", Boolean.TRUE);
	}

	public List<SolicitudCV> buscarTodosNoEnviados() {
		return (List<SolicitudCV>) super.findAll("enviado", Boolean.FALSE);
	}
}
