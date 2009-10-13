/**
 * 
 */
package ar.com.gsalazar.dtos;

import java.io.Serializable;
import java.sql.SQLException;

import ar.com.gsalazar.beans.Persona;

import com.angel.architecture.exceptions.NonBusinessException;

/**
 * 
 *	@author Guillermo Daniel Salazar.
 *	@since 16/Semptiembre/2009.
 */
public class PersonaDTO implements Serializable{
	private static final long serialVersionUID = 7954520141032044952L;

//	private byte[] imagen;

	private String nombre;
	
	private String apellido;

	private String email;

	public PersonaDTO(){
		super();
	}
	
	public PersonaDTO(Persona persona){
		super();
		//persona
		this.setApellido(persona.getApellido());
		this.setEmail(persona.getEmail());
		this.setNombre(persona.getNombre());
		try {
			if(persona.getImagen() != null){
				this.setImagen(persona.getImagen().getBytes(1, (int) persona.getImagen().length()));
			}
		} catch (SQLException e) {
			throw new NonBusinessException("Error getting image bytes for persona [" + persona.getNombre() + "].", e );
		}
	}

	/**
	 * @return the imagen
	 */
	public byte[] getImagen() {
//		return imagen;
		return null;
	}

	/**
	 * @param imagen the imagen to set
	 */
	public void setImagen(byte[] imagen) {
//		this.imagen = imagen;
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
	 * @return the apellido
	 */
	public String getApellido() {
		return apellido;
	}

	/**
	 * @param apellido the apellido to set
	 */
	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}
}
