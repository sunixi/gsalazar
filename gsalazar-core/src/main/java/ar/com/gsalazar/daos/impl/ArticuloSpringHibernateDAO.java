/**
 * 
 */
package ar.com.gsalazar.daos.impl;

import java.util.List;

import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;

import ar.com.gsalazar.beans.Articulo;
import ar.com.gsalazar.daos.ArticuloDAO;

import com.angel.architecture.persistence.ids.ObjectId;
import com.angel.dao.generic.impl.GenericSpringHibernateDAO;

/**
 * 
 * 
 * @author Guillermo Salazar
 * @since 28/Agosto/2009.
 *
 */
public class ArticuloSpringHibernateDAO extends GenericSpringHibernateDAO<Articulo, ObjectId> implements ArticuloDAO {

	public ArticuloSpringHibernateDAO() {
		super(Articulo.class, ObjectId.class);
	}

	public List<Articulo> buscarTodosCantidadFuentes(Integer cantidadFuentes) {
		return (List<Articulo>) super.findAllByCriteria(Restrictions.sizeEq("fuentes", cantidadFuentes));
	}

	public List<Articulo> buscarTodosContenganContenido(String contenido) {
		return (List<Articulo>) super.findAllByCriteria(Restrictions.like("contenido", contenido, MatchMode.ANYWHERE));
	}
	
	public List<Articulo> buscarTodosContenganDescripcion(String descripcion) {
		return (List<Articulo>) super.findAllByCriteria(Restrictions.like("descripcion", descripcion, MatchMode.ANYWHERE));
	}

	public Articulo buscarUnicoPorTitulo(String titulo) {
		return super.findUnique("titulo", titulo);
	}
}
