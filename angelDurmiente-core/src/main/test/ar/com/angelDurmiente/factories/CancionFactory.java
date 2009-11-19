/**
 *
 */
package ar.com.angelDurmiente.factories;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.RandomStringUtils;

import ar.com.angelDurmiente.beans.Cancion;
import ar.com.angelDurmiente.beans.Usuario;


/**
 * @author Guillermo Salazar
 * @since 19/Noviembre/2009.
 *
 */
public class CancionFactory {

	public static Cancion crearEmptyCancion(){
		return new Cancion();
	}
	
	public static Cancion crearCancion(Usuario usuario){
		Cancion cancion = crearEmptyCancion();
		cancion.setContenido(RandomStringUtils.randomAlphabetic(250));
		cancion.setRating(Integer.valueOf(RandomStringUtils.randomNumeric(1)));
		cancion.setTitulo(RandomStringUtils.randomAlphabetic(25));
		cancion.setUsuario(usuario);
		cancion.setVerificado(true);
		cancion.setVisitas(Double.valueOf(RandomStringUtils.randomNumeric(5)));
		return cancion;
	}

	public static List<Cancion> crearCancion(Usuario usuario, int quantity){
		List<Cancion> canciones = new ArrayList<Cancion>();
		for(int i = 0; i < quantity; i++){
			canciones.add(crearCancion(usuario));
		}
		return canciones;
	}
	
	private CancionFactory(){
		super();
	}
}
