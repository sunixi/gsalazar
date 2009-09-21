/**
 * 
 */
package ar.com.gsalazar.dtos;

import java.io.Serializable;
import java.sql.SQLException;

import com.angel.architecture.exceptions.NonBusinessException;

import ar.com.gsalazar.beans.ContactoWeb;

/**
 * 
 *	@author Guillermo Daniel Salazar.
 *	@since 16/Semptiembre/2009.
 */
public class ContactoWebDTO implements Serializable{
	private static final long serialVersionUID = 7954520141032044952L;

	private String nombre;

	private String descripcion;

	private String link;

	private byte[] logo;

	public ContactoWebDTO(ContactoWeb contactoWeb){
		super();
		this.setDescripcion(contactoWeb.getDescripcion());
		this.setLink(contactoWeb.getLink());
		this.setNombre(contactoWeb.getNombre());
		try {
			this.setLogo(contactoWeb.getLogo().getBytes(1, (int) contactoWeb.getLogo().length()));
		} catch (SQLException e) {
			throw new NonBusinessException("Error getting logo image bytes for web contact [" + contactoWeb.getNombre() + "].", e );
		}
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
	 * @return the descripcion
	 */
	public String getDescripcion() {
		return descripcion;
	}

	/**
	 * @param descripcion the descripcion to set
	 */
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	/**
	 * @return the link
	 */
	public String getLink() {
		return link;
	}

	/**
	 * @param link the link to set
	 */
	public void setLink(String link) {
		this.link = link;
	}

	/**
	 * @return the logo
	 */
	public byte[] getLogo() {
		return logo;
	}

	/**
	 * @param logo the logo to set
	 */
	public void setLogo(byte[] logo) {
		this.logo = logo;
	}
}
