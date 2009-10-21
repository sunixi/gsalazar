/**
 * 
 */
package ar.com.gsalazar.daos;

import java.util.List;

import ar.com.gsalazar.beans.Persona;
import ar.com.gsalazar.dtos.BusquedaInfo;

import com.angel.architecture.persistence.beans.TagSearch;
import com.angel.architecture.persistence.ids.ObjectId;
import com.angel.dao.generic.exceptions.NotFoundUniqueObjectException;
import com.angel.dao.generic.interfaces.GenericDAO;

/**
 * Clase que tiene como objetivo tener acceso a los datos de las personas (ver {@link Persona}), 
 * ya sea mediante la busqueda de ciertos datos, o mediante la creacion y/o actualizacion de personas.
 * 
 * @author Guillermo Salazar.
 * @since 27/Agosto/2009.
 *
 */
public interface PersonaDAO extends GenericDAO<Persona, ObjectId>{

	/**
	 * Busca una persona con un nombre determinado.
	 * 
	 * @param nombre de la persona a buscar.
	 * @return la persona con ese nombre, o arroja la excepcion {@link NotFoundUniqueObjectException}.
	 */
	public Persona buscarUnicoPorNombre(String nombre);
	
	/**
	 * Busca todas las personas que tengan el apellido.
	 * 
	 * @param apellido a buscar en las personas.
	 * @return un listado de personas que tengan ese apellido. En caso de no existir ese apellido, retorna una lista vacia.
	 */
	public List<Persona> buscarTodosPorApellido(String apellido);

	/**
	 * Busca una persona con un email determinado.
	 * 
	 * @param email de la persona a buscar.
	 * @return la persona con ese email, o arroja la excepcion {@link NotFoundUniqueObjectException}.
	 */
	public Persona buscarUnicoPorEmail(String email);
	
	/**
	 * Busca todas las personas que tengan asociado los tags de busquedas determinados.
	 * Para poder encontrar una persona, este debe tener asociado todos los tags de busqueda por los que se
	 * esta buscando.
	 * 
	 * @param tagsSearch tags de busquedas con los que se realizará la busqueda.
	 * @return una lista de personas. En caso de no encontrar personas, retorna una lista vacia.
	 */
	public List<Persona> buscarTodosPorTagsSearch(List<TagSearch> tagsSearch);

	/**
	 * Realiza una busqueda de personas con los datos de {@link BusquedaInfo}. Busca una lista de personas
	 * que cumpla con los datos que contiene {@link BusquedaInfo}.
	 * 
	 * @param busquedaInfo informacion y datos con los que se desea buscar las personas.
	 * @return una lista de personas que cumplan con el criterio de {@link BusquedaInfo}.
	 * 	En caso de no encontrar ninguna persona, devuelve una lista vacia.
	 */
	public List<Persona> buscarTodosPorBusquedaInfo(BusquedaInfo busquedaInfo);
}
