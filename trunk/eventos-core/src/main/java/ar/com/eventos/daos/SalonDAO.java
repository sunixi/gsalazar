/**
 * 
 */
package ar.com.eventos.daos;

import java.util.List;

import ar.com.eventos.beans.Salon;

import com.angel.architecture.persistence.ids.ObjectId;
import com.angel.dao.generic.interfaces.GenericDAO;

/**
 * @author Guillermo Salazar.
 * @since 27/Agosto/2009.
 *
 */
public interface SalonDAO extends GenericDAO<Salon, ObjectId>{
	
	public List<Salon> buscarTodosPorLocalidad(String localidad);
	
	public List<Salon> buscarTodosPorCapacidad(int capacidad);

	public Salon buscarUnicoPorDireccion(String direccion);
	
	public Salon buscarUnicoPorNombre(String nombre);
	
	public Salon buscarUnicoONuloPorNombre(String nombre);
}
