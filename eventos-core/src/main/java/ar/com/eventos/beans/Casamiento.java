/**
 * 
 */
package ar.com.eventos.beans;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.OneToOne;

/**
 * 
 *	@author Guillermo Daniel Salazar.
 *	@since 16/Semptiembre/2009.
 */
@Entity
@DiscriminatorValue("casamiento")
public class Casamiento extends Evento {

	private static final long serialVersionUID = -6968492110376646351L;

	@OneToOne
	private Persona mujer;

	@OneToOne
	private Persona hombre;

	public Casamiento(Persona hombre, Persona mujer){
		super();
		this.setMujer(mujer);
		this.setHombre(hombre);
	}
	
	/**
	 * @return the mujer
	 */
	public Persona getMujer() {
		return mujer;
	}

	/**
	 * @param mujer the mujer to set
	 */
	public void setMujer(Persona mujer) {
		this.mujer = mujer;
	}

	/**
	 * @return the hombre
	 */
	public Persona getHombre() {
		return hombre;
	}

	/**
	 * @param hombre the hombre to set
	 */
	public void setHombre(Persona hombre) {
		this.hombre = hombre;
	}
}
