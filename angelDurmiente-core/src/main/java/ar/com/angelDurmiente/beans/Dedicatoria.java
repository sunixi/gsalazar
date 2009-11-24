/**
 * 
 */
package ar.com.angelDurmiente.beans;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import com.angel.architecture.persistence.base.PersistentObject;

/**
 * @author Guillermo Salazar
 * @since 19/Noviembre/2009.
 *
 */
@Entity
public class Dedicatoria extends PersistentObject {

	private static final long serialVersionUID = -1457278499744128408L;

	@ManyToOne(optional = false)
	private Texto texto;

	@ManyToOne(optional = false)
	private Usuario dedicador;

	@ManyToOne(optional = false)
	private Usuario dedicatario;
	
	@Column(nullable = false)
	private Date desde;
	
	@Column(nullable = false)
	private Date hasta;

	public Dedicatoria(){
		super();
	}

	/**
	 * @return the texto
	 */
	public Texto getTexto() {
		return texto;
	}

	/**
	 * @param texto the texto to set
	 */
	public void setTexto(Texto texto) {
		this.texto = texto;
	}

	/**
	 * @return the dedicador
	 */
	public Usuario getDedicador() {
		return dedicador;
	}

	/**
	 * @param dedicador the dedicador to set
	 */
	public void setDedicador(Usuario dedicador) {
		this.dedicador = dedicador;
	}

	/**
	 * @return the dedicatario
	 */
	public Usuario getDedicatario() {
		return dedicatario;
	}

	/**
	 * @param dedicatario the dedicatario to set
	 */
	public void setDedicatario(Usuario dedicatario) {
		this.dedicatario = dedicatario;
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
