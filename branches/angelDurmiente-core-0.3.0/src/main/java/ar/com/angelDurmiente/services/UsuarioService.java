/**
 * 
 */
package ar.com.angelDurmiente.services;

import java.util.List;

import ar.com.angelDurmiente.beans.Usuario;

import com.angel.architecture.services.interfaces.GenericService;

/**
 * @author Guillermo Salazar
 *
 */
public interface UsuarioService extends GenericService {

	public Usuario buscarUnicoPorNombreUsuario(String nombreUsuario);
	
	public Usuario buscarUnicoONuloPorNombreUsuario(String nombreUsuario);
	
	public List<Usuario> buscarTodosPorNombre(String nombre);

}
