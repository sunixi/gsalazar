/**
 * 
 */
package ar.com.angelDurmiente.dtos;

import java.io.Serializable;

/**
 * @author Guillermo Salazar
 * @since 19/Noviembre/2009.
 *
 */
public class AcordeDTO implements Serializable {

	private static final long serialVersionUID = 6936830093090143041L;

	private String nombre;
	private int opcion;
	
	public AcordeDTO(){
		super();
	}

	public AcordeDTO(String nombre, int opcion) {
		this();		
	}

	/**
	 * @return the nombre
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * @param nombre the nombre to set
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	/**
	 * @return the opcion
	 */
	public int getOpcion() {
		return opcion;
	}

	/**
	 * @param opcion the opcion to set
	 */
	public void setOpcion(int opcion) {
		this.opcion = opcion;
	}
}
