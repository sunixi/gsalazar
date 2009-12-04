/**
 * 
 */
package ar.com.angelDurmiente.types;

import ar.com.angelDurmiente.beans.Comentario;

/**
 * 
 * @author Guillermo Salazar
 * @since 22/Noviembre/2009
 *
 */
public interface Comentable {

	
	public void comentar(Comentario comentario);
	
	public void comentar(String nombre, String valorComentario, int rating);
}
