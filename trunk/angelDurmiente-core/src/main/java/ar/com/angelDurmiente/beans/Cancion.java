/**
 * 
 */
package ar.com.angelDurmiente.beans;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

/**
 * @author Guillermo Salazar
 * @since 19/Noviembre/2009.
 *
 */
@Entity
public class Cancion extends DocumentoAvanzado {

	private static final long serialVersionUID = -1457278499744128408L;

	@ManyToOne(optional = false)
	private Artista artista;
	
	public Cancion(){
		super();
	}
	
	public Cancion(List<Texto> textos){
		this();
		super.agregarTextos(textos);
	}

	/**
	 * @return the artista
	 */
	public Artista getArtista() {
		return artista;
	}

	/**
	 * @param artista the artista to set
	 */
	public void setArtista(Artista artista) {
		this.artista = artista;
	}
}
