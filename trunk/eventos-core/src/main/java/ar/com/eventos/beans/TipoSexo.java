/**
 * 
 */
package ar.com.eventos.beans;

/**
 * @author Guille Salazar
 * @since 21/Jul/2009
 *
 */
public enum TipoSexo {
	
	FEMENINNO("FEMENINNO"),
	MASCULINO("MASCULINO");

	private String value;

	public String getValue() {
		return value;
	}

	private TipoSexo(String value) {
		this.value = value;
	}
}
