/**
 * 
 */
package ar.com.eventos.daos.impl;

import java.util.Date;
import java.util.List;

import ar.com.eventos.beans.Persona;
import ar.com.eventos.daos.PersonaDAO;

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

	public List<Persona> buscarTodosPorFechaNacimiento(Date nacimiento) {
		return (List<Persona>) super.findAll("nacimiento", nacimiento);
	}

	public List<Persona> buscarTodosPorNombre(String nombre) {
		return (List<Persona>) super.findAll("nombre", nombre);
	}

	public Persona buscarUnicoONuloPorEmail(String email) {
		return super.findUniqueOrNull("email", email);
	}

	public Persona buscarUnicoONuloPorFotolog(String fotolog) {
		return super.findUniqueOrNull("urlFotolog", fotolog);
	}

	public Persona buscarUnicoPorEmail(String email) {
		return super.findUnique("email", email);
	}

	public Persona buscarUnicoPorFotolog(String fotolog) {
		return super.findUnique("urlFotolog", fotolog);
	}	
}
