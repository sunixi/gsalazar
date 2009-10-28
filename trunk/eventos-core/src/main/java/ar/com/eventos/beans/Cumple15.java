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
@DiscriminatorValue("cumple15")
public class Cumple15 extends Evento {

	private static final long serialVersionUID = -6968492110376646351L;

	@OneToOne
	private Persona mujer;

	public Cumple15(Persona mujer){
		super();
		this.setMujer(mujer);
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
}
