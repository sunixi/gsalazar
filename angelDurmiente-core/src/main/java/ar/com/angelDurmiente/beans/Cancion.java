/**
 * 
 */
package ar.com.angelDurmiente.beans;

import javax.persistence.Entity;

/**
 * @author Guillermo Salazar
 * @since 19/Noviembre/2009.
 *
 */
@Entity
public class Cancion extends Texto {

	private static final long serialVersionUID = -1457278499744128408L;
	
	private String titulo;
	
	public Cancion(){
		super();
	}
	
	public Cancion(String contenido){
		this();
	}

	/**
	 * @return the titulo
	 */
	public String getTitulo() {
		return titulo;
	}

	/**
	 * @param titulo the titulo to set
	 */
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
}
