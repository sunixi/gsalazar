/**
 * 
 */
package ar.com.gsalazar.daos.impl;

import java.util.Collection;

import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;

import ar.com.gsalazar.beans.Contacto;
import ar.com.gsalazar.daos.ContactoDAO;

import com.angel.architecture.persistence.ids.ObjectId;
import com.angel.dao.generic.impl.GenericSpringHibernateDAO;

/**
 * 
 * 
 * @author Guillermo Salazar
 * @since 28/Agosto/2009.
 *
 */
public class ContactoSpringHibernateDAO extends GenericSpringHibernateDAO<Contacto, ObjectId> implements ContactoDAO {

	/**
	 * 
	 */
	public ContactoSpringHibernateDAO() {
		super(Contacto.class, ObjectId.class);
	}

	public Collection<Contacto> buscarTodosPorContieneDescripcion(String descripcion) {
		return super.findAllByCriteria(Restrictions.like("descripcion", descripcion, MatchMode.ANYWHERE));
	}

	public Contacto buscarUnicoOrNuloPorAsunto(String asunto) {
		return super.findUniqueOrNull("asunto", asunto);
	}

	public Contacto buscarUnicoOrNuloPorDireccion(String direccion) {
		return super.findUniqueOrNull("direccion", direccion);
	}

	public Contacto buscarUnicoOrNuloPorLocalidad(String localidad) {
		return super.findUniqueOrNull("localidad", localidad);
	}

	public Contacto buscarUnicoOrNuloPorTelefono(String telefono) {
		return super.findUniqueOrNull("telefono", telefono);
	}

	public Contacto buscarUnicoPorApellido(String apellido) {
		return super.findUnique("apellido", apellido);
	}

	public Contacto buscarUnicoPorDescripcion(String descripcion) {
		return super.findUnique("descripcion", descripcion);
	}

	public Contacto buscarUnicoPorEmail(String email) {
		return super.findUnique("email", email);
	}

	public Contacto buscarUnicoPorNombreCompania(String nombreCompania) {
		return super.findUnique("nombreCompania", nombreCompania);
	}

	public Contacto buscarUnicoPorNombres(String nombres) {
		return super.findUnique("nombres", nombres);
	}
}
