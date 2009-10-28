/**
 * 
 */
package ar.com.eventos.daos.impl;

import java.util.List;

import ar.com.eventos.beans.Salon;
import ar.com.eventos.daos.SalonDAO;

import com.angel.architecture.persistence.ids.ObjectId;
import com.angel.dao.generic.impl.GenericSpringHibernateDAO;

/**
 * 
 * 
 * @author Guillermo Salazar
 * @since 28/Agosto/2009.
 *
 */
public class SalonSpringHibernateDAO extends GenericSpringHibernateDAO<Salon, ObjectId> implements SalonDAO {

	public SalonSpringHibernateDAO() {
		super(Salon.class, ObjectId.class);
	}

	public List<Salon> buscarTodosPorCapacidad(int capacidad) {
		return (List<Salon>) super.findAll("capacidad", capacidad);
	}

	public List<Salon> buscarTodosPorLocalidad(String localidad) {
		return (List<Salon>) super.findAll("localidad", localidad);
	}

	public Salon buscarUnicoPorDireccion(String direccion) {
		return super.findUnique("direccion", direccion);
	}

	public Salon buscarUnicoPorNombre(String nombre) {
		return super.findUnique("nombre", nombre);
	}
	
	public Salon buscarUnicoONuloPorNombre(String nombre) {
		return super.findUniqueOrNull("nombre", nombre);
	}	
}
