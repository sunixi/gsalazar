/**
 * 
 */
package ar.com.gsalazar.daos;

import ar.com.gsalazar.beans.TagSearch;

import com.angel.architecture.persistence.ids.ObjectId;
import com.angel.dao.generic.interfaces.GenericDAO;

/**
 * @author Guillermo Salazar.
 * @since 27/Agosto/2009.
 *
 */
public interface TagSearchDAO extends GenericDAO<TagSearch, ObjectId>{

	public TagSearch buscarUnicoONuloPorLabel(String label);
	
	public TagSearch buscarUnicoPorLabel(String label);
	
	public TagSearch buscarUnicoPorDescripcion(String descripcion);
	
	public TagSearch buscarUnicoONuloPorDescripcion(String descripcion);

}
