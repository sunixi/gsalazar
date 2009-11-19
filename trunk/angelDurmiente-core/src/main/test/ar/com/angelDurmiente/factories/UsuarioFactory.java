/**
 *
 */
package ar.com.angelDurmiente.factories;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.RandomStringUtils;

import ar.com.angelDurmiente.beans.Usuario;
import ar.com.angelDurmiente.helpers.CalendarHelper;


/**
 * @author Guillermo Salazar
 * @since 19/Noviembre/2009.
 *
 */
public class UsuarioFactory {

	public static Usuario crearEmptyUsuario(){
		return new Usuario();
	}
	
	public static Usuario crearUsuario(){
		Usuario usuario = crearEmptyUsuario();
		usuario.setActive(true);
		usuario.setApellido(RandomStringUtils.randomAlphabetic(10));
		usuario.setEmail(	RandomStringUtils.randomAlphabetic(5) + "@" + 
							RandomStringUtils.randomAlphabetic(4) + "." +
							RandomStringUtils.randomAlphabetic(3));
		usuario.setNacimiento(CalendarHelper.createDate(10, 9, 1981));
		usuario.setName(RandomStringUtils.randomAlphabetic(10));
		usuario.setNombre(RandomStringUtils.randomAlphabetic(10));
		usuario.setPassword(RandomStringUtils.randomAlphabetic(9));
		return usuario;
	}

	public static List<Usuario> crearUsuario(int quantity){
		List<Usuario> usuarios = new ArrayList<Usuario>();
		for(int i = 0; i < quantity; i++){
			usuarios.add(crearUsuario());
		}
		return usuarios;
	}
	
	private UsuarioFactory(){
		super();
	}
}
