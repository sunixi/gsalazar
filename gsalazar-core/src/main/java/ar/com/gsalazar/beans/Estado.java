/**
 * 
 */
package ar.com.gsalazar.beans;

/**
 * @author Guille Salazar
 * @since 21/Jul/2009
 *
 */
public enum Estado {
	
	NO_COMENZADO("No comenzado"),
	EN_DESARROLLO("En desarrollo"),
	PARADO("Parado"),
	BLOQUEADO("Bloqueado"),
	FINALIZADO("Finalizado");

	private String value;

	public String getValue() {
		return value;
	}

	private Estado(String value) {
		this.value = value;
	}
}
