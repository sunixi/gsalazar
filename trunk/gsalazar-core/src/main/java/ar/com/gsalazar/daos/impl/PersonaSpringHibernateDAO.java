/**
 * 
 */
package ar.com.gsalazar.daos.impl;

import java.util.List;

import ar.com.gsalazar.beans.Persona;
import ar.com.gsalazar.daos.PersonaDAO;

import com.angel.architecture.persistence.ids.ObjectId;
import com.angel.dao.generic.impl.GenericSpringHibernateDAO;

/**
 * 
 * 
 * @author Guillermo Salazar
 * @since 28/Agosto/2009.
 *
 */
public class PersonaSpringHibernateDAO extends GenericSpringHibernateDAO<Persona, ObjectId> implements PersonaDAO {

	public PersonaSpringHibernateDAO() {
		super(Persona.class, ObjectId.class);
	}

	public List<Persona> buscarTodosPorApellido(String apellido) {
		return (List<Persona>) super.findAll("apellido", apellido);
	}

	public Persona buscarUnicoPorEmail(String email) {
		return super.findUnique("email", email);
	}

	public Persona buscarUnicoPorNombre(String nombre) {
		return super.findUnique("nombre", nombre);
	}
}
