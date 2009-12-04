/**
 * 
 */
package ar.com.angelDurmiente.daos.impl;

import java.util.List;

import ar.com.angelDurmiente.beans.Usuario;
import ar.com.angelDurmiente.daos.UsuarioDAO;

import com.angel.architecture.persistence.ids.ObjectId;
import com.angel.dao.generic.impl.GenericSpringHibernateDAO;

/**
 * 
 * 
 * @author Guillermo Salazar
 * @since 28/Agosto/2009.
 *
 */
public class UsuarioSpringHibernateDAO extends GenericSpringHibernateDAO<Usuario, ObjectId> implements UsuarioDAO {

	public UsuarioSpringHibernateDAO() {
		super(Usuario.class, ObjectId.class);
	}

	public List<Usuario> buscarTodosPorNombre(String nombre) {
		return (List<Usuario>) super.findAll("nombre", nombre);
	}

	public Usuario buscarUnicoONuloPorNombreUsuario(String nombreUsuario) {
		return super.findUniqueOrNull("name", nombreUsuario);
	}

	public Usuario buscarUnicoPorNombreUsuario(String nombreUsuario) {
		return super.findUnique("name", nombreUsuario);
	}

}
