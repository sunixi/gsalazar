/**
 * 
 */
package ar.com.gsalazar.daos.impl;

import ar.com.gsalazar.beans.ContactoWeb;
import ar.com.gsalazar.daos.ContactoWebDAO;

import com.angel.architecture.persistence.ids.ObjectId;
import com.angel.dao.generic.impl.GenericSpringHibernateDAO;

/**
 * 
 * 
 * @author Guillermo Salazar
 * @since 28/Agosto/2009.
 *
 */
public class ContactoWebSpringHibernateDAO extends GenericSpringHibernateDAO<ContactoWeb, ObjectId> implements ContactoWebDAO {

	public ContactoWebSpringHibernateDAO() {
		super(ContactoWeb.class, ObjectId.class);
	}

	public ContactoWeb buscarUnicoONuloPorNombre(String nombre) {
		return super.findUniqueOrNull("nombre", nombre);
	}

	public ContactoWeb buscarUnicoPorNombre(String nombre) {
		return super.findUnique("nombre", nombre);
	}
}
