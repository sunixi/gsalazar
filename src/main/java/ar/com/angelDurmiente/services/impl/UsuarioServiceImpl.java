/**
 * 
 */
package ar.com.angelDurmiente.services.impl;

import java.util.List;

import ar.com.angelDurmiente.beans.Usuario;
import ar.com.angelDurmiente.daos.UsuarioDAO;
import ar.com.angelDurmiente.services.UsuarioService;

import com.angel.architecture.services.impl.GenericServiceImpl;

/**
 * @author Guillermo Salazar
 * @since 23/Noviembre/2009.
 *
 */
public class UsuarioServiceImpl extends GenericServiceImpl implements UsuarioService {

	protected UsuarioDAO getUsuarioDAO(){
		return (UsuarioDAO) super.getGenericDAO();
	}

	public List<Usuario> buscarTodosPorNombre(String nombre) {
		return this.getUsuarioDAO().buscarTodosPorNombre(nombre);
	}

	public Usuario buscarUnicoONuloPorNombreUsuario(String nombreUsuario) {
		return this.getUsuarioDAO().buscarUnicoONuloPorNombreUsuario(nombreUsuario);
	}

	public Usuario buscarUnicoPorNombreUsuario(String nombreUsuario) {
		return this.getUsuarioDAO().buscarUnicoPorNombreUsuario(nombreUsuario);
	}
}
