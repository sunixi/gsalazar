/**
 * 
 */
package ar.com.eventos.beans;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**
 * 
 *	@author Guillermo Daniel Salazar.
 *	@since 16/Semptiembre/2009.
 */
@Entity
@DiscriminatorValue("cumple21")
public class Cumple21 extends Evento {

	private static final long serialVersionUID = -6968492110376646351L;


}
