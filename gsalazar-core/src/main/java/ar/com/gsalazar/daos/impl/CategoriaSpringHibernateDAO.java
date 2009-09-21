/**
 * 
 */
package ar.com.gsalazar.daos.impl;

import java.util.Collection;

import org.hibernate.criterion.Restrictions;

import ar.com.gsalazar.beans.Categoria;
import ar.com.gsalazar.daos.CategoriaDAO;

import com.angel.architecture.persistence.ids.ObjectId;
import com.angel.dao.generic.impl.GenericSpringHibernateDAO;

/**
 * 
 * 
 * @author Guillermo Salazar
 * @since 28/Agosto/2009.
 *
 */
public class CategoriaSpringHibernateDAO extends GenericSpringHibernateDAO<Categoria, ObjectId> implements CategoriaDAO {

	public CategoriaSpringHibernateDAO() {
		super(Categoria.class, ObjectId.class);
	}

	public Collection<Categoria> buscarTodosPorContieneDescripcion(String descripcion) {
		return super.findAllByCriteria(Restrictions.like("descripcion", descripcion));
	}

	public Categoria buscarUnicoPorDescripcion(String descripcion) {
		return super.findUnique("descripcion", descripcion);
	}

	public Categoria buscarUnicoPorNombre(String nombre) {
		Categoria categoria = super.findUnique("nombre", nombre);
		return categoria;
	}

	public Categoria buscarUnicoONuloPorNombre(String nombre) {
		return super.findUniqueOrNull("nombre", nombre);
	}
}
