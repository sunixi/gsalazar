/**
 * 
 */
package ar.com.angelDurmiente.types;

import ar.com.angelDurmiente.beans.Texto;


/**
 * 
 * @author Guillermo Salazar
 * @since 22/Noviembre/2009
 *
 */
public interface Documentable {
	
	public long cantidadTextos();

	public void agregarTexto(Texto texto);
	
	public void removerTexto(Texto texto);
	
	public void removerTexto(String titulo);
}
