/**
 * 
 */
package ar.com.gsalazar.daos;

import ar.com.gsalazar.beans.ContactoWeb;

import com.angel.architecture.persistence.ids.ObjectId;
import com.angel.dao.generic.interfaces.GenericDAO;

/**
 * @author Guillermo Salazar.
 * @since 27/Agosto/2009.
 *
 */
public interface ContactoWebDAO extends GenericDAO<ContactoWeb, ObjectId>{

	public ContactoWeb buscarUnicoPorNombre(String nombre);
	
	public ContactoWeb buscarUnicoONuloPorNombre(String nombre);

}
