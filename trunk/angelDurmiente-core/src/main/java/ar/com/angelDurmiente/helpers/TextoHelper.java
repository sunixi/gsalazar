/**
 *
 */
package ar.com.angelDurmiente.helpers;

import ar.com.angelDurmiente.beans.Artista;
import ar.com.angelDurmiente.beans.Cancion;
import ar.com.angelDurmiente.beans.Usuario;


/**
 * @author William
 *
 */
public class TextoHelper {

	public static final String TITULO_SEPARADOR = " - ";
	
	/**
	 * Crea el titulo único para un texto con el siguiente formato:
	 * 
	 *  NombreArtista SEPARADOR TituloCancion SEPARADOR NombreUsuario SEPARADOR CantidadTextos
	 *  
	 * @param artista para crear el titulo.
	 * @param cancion para crear el titulo.
	 * @param usuario para crear el titulo.
	 * @return el titulo unico para un texto.
	 */
	public static String crearTituloPara(Artista artista, Cancion cancion, Usuario usuario){
		String titulo = "";
		titulo += artista.getNombre() + TITULO_SEPARADOR;
		titulo += cancion.getTitulo() + TITULO_SEPARADOR;
		titulo += usuario.getName() + TITULO_SEPARADOR;
		titulo += cancion.cantidadTextos();
		return titulo;
	}

	private TextoHelper(){
		super();
	}
}
