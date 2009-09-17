/**
 * 
 */
package ar.com.gsalazar.daos;

import java.util.Collection;

import ar.com.gsalazar.beans.Contacto;

import com.angel.architecture.persistence.ids.ObjectId;
import com.angel.dao.generic.interfaces.GenericDAO;

/**
 * @author Guillermo Salazar.
 * @since 27/Agosto/2009.
 *
 */
public interface ContactoDAO extends GenericDAO<Contacto, ObjectId>{

	public Contacto buscarUnicoPorNombres(String nombres);
	
	public Contacto buscarUnicoPorApellido(String apellido);
	
	public Contacto buscarUnicoPorNombreCompania(String nombreCompania);
	
	public Contacto buscarUnicoPorEmail(String email);
	
	public Contacto buscarUnicoOrNuloPorTelefono(String telefono);
	
	public Contacto buscarUnicoOrNuloPorDireccion(String direccion);
	
	public Contacto buscarUnicoOrNuloPorLocalidad(String localidad);
	
	public Contacto buscarUnicoOrNuloPorAsunto(String asunto);
	
	public Contacto buscarUnicoPorDescripcion(String descripcion);
	
	public Collection<Contacto> buscarTodosPorContieneDescripcion(String descripcion);

}
