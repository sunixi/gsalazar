/**
 * 
 */
package ar.com.gsalazar.factories;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.RandomStringUtils;

import ar.com.gsalazar.beans.Comentario;


/**
 * 
 *	@author Guillermo Daniel Salazar.
 *	@since 16/Semptiembre/2009.
 */
public class ComentarioFactory {

	public static Comentario createComentario(int rating, boolean validado){
		Comentario comentario = new Comentario();
		comentario.setComentario(RandomStringUtils.randomAlphabetic(25));
		comentario.setNombre(RandomStringUtils.randomAlphabetic(11));
		comentario.setRating(rating);
		comentario.setValidado(validado);
		return comentario;
	}

	public static List<Comentario> createComentario(int cantidad){
		List<Comentario> list = new ArrayList<Comentario>();
		for(int i = 0; i < cantidad; i++){
			Comentario c = createComentario
							(
								Integer.valueOf(RandomStringUtils.randomNumeric(1)),
								Integer.valueOf(RandomStringUtils.randomNumeric(1)) > 5? true: false
							);
			list.add(c);
		}
		return list;
	}
}
