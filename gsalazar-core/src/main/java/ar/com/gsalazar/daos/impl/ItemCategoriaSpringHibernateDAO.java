/**
 * 
 */
package ar.com.gsalazar.daos.impl;

import java.util.Collection;

import org.hibernate.criterion.Restrictions;

import ar.com.gsalazar.beans.ItemCategoria;
import ar.com.gsalazar.daos.ItemCategoriaDAO;

import com.angel.architecture.persistence.ids.ObjectId;
import com.angel.dao.generic.impl.GenericSpringHibernateDAO;

/**
 * 
 * 
 * @author Guillermo Salazar
 * @since 28/Agosto/2009.
 *
 */
public class ItemCategoriaSpringHibernateDAO extends GenericSpringHibernateDAO<ItemCategoria, ObjectId> implements ItemCategoriaDAO {

	/**
	 * 
	 */
	public ItemCategoriaSpringHibernateDAO() {
		super(ItemCategoria.class, ObjectId.class);
	}

	public Collection<ItemCategoria> buscarTodosPorContieneDescripcion(String descripcion) {
		return super.findAllByCriteria(Restrictions.like("descripcion", descripcion));
	}

	public ItemCategoria buscarUnicoPorDescripcion(String descripcion) {
		return super.findUnique("descripcion", descripcion);
	}

	public ItemCategoria buscarUnicoPorNombre(String nombre) {
		return super.findUnique("nombre", nombre);
	}

	public ItemCategoria buscarUnicoONuloPorNombre(String nombre) {
		return super.findUniqueOrNull("nombre", nombre);
	}
}
