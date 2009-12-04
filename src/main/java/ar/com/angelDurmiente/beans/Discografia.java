/**
 * 
 */
package ar.com.angelDurmiente.beans;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToOne;

import com.angel.architecture.persistence.base.PersistentObject;

/**
 * @author Guillermo Salazar
 * @since 19/Noviembre/2009.
 *
 */
@Entity
public class Discografia extends PersistentObject {

	private static final long serialVersionUID = -1457278499744128408L;

	@Column(nullable = false)
	private Date desde;

	@Column(nullable = false)
	private Date hasta;

	@OneToOne
	private Artista artista;

	public Discografia(){
		super();
	}
	
	public Discografia(Artista artista){
		this();
		this.setArtista(artista);
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

	/**
	 * @return the desde
	 */
	public Date getDesde() {
		return desde;
	}

	/**
	 * @param desde the desde to set
	 */
	public void setDesde(Date desde) {
		this.desde = desde;
	}

	/**
	 * @return the hasta
	 */
	public Date getHasta() {
		return hasta;
	}

	/**
	 * @param hasta the hasta to set
	 */
	public void setHasta(Date hasta) {
		this.hasta = hasta;
	}
}
