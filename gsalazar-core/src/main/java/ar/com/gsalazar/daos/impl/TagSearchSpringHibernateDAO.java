/**
 * 
 */
package ar.com.gsalazar.daos.impl;

import ar.com.gsalazar.beans.TagSearch;
import ar.com.gsalazar.daos.TagSearchDAO;

import com.angel.architecture.persistence.ids.ObjectId;
import com.angel.dao.generic.impl.GenericSpringHibernateDAO;

/**
 * 
 * 
 * @author Guillermo Salazar
 * @since 28/Agosto/2009.
 *
 */
public class TagSearchSpringHibernateDAO extends GenericSpringHibernateDAO<TagSearch, ObjectId> implements TagSearchDAO {

	public TagSearchSpringHibernateDAO() {
		super(TagSearch.class, ObjectId.class);
	}

	public TagSearch buscarUnicoONuloPorLabel(String label) {
		return super.findUniqueOrNull("label", label);
	}

	public TagSearch buscarUnicoPorDescripcion(String descripcion) {
		return super.findUnique("descripcion", descripcion);
	}

	public TagSearch buscarUnicoPorLabel(String label) {
		return super.findUnique("label", label);
	}

	public TagSearch buscarUnicoONuloPorDescripcion(String descripcion) {
		return super.findUniqueOrNull("descripcion", descripcion);
	}
}
