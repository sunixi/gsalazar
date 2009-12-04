/**
 * 
 */
package ar.com.angelDurmiente.daos;

import java.util.List;

import ar.com.angelDurmiente.beans.Usuario;

import com.angel.architecture.persistence.ids.ObjectId;
import com.angel.dao.generic.interfaces.GenericDAO;

/**
 * 
 * @author Guillermo Salazar.
 * @since 19/Noviembre/2009.
 *
 */
public interface UsuarioDAO extends GenericDAO<Usuario, ObjectId>{

	public Usuario buscarUnicoPorNombreUsuario(String nombreUsuario);
	
	public Usuario buscarUnicoONuloPorNombreUsuario(String nombreUsuario);
	
	public List<Usuario> buscarTodosPorNombre(String nombre);
	
}
