/**
 * 
 */
package ar.com.gsalazar.dtos;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import ar.com.gsalazar.beans.Persona;
import ar.com.gsalazar.beans.Proyecto;

import com.angel.architecture.exceptions.NonBusinessException;

/**
 * 
 *	@author Guillermo Daniel Salazar.
 *	@since 16/Semptiembre/2009.
 */
public class ProyectoDTO implements Serializable{
	private static final long serialVersionUID = 7954520141032044952L;

	private List<PersonaDTO> desarrolladores;
	
	private byte[] imagen;

	public ProyectoDTO(){
		super();
		this.setDesarrolladores(new ArrayList<PersonaDTO>());
	}
	
	public ProyectoDTO(Proyecto proyecto){
		this();
		for(Persona p: proyecto.getDesarrolladores()){
			this.getDesarrolladores().add(new PersonaDTO(p));
		}
		try {
			if(proyecto.getImagen() != null) {
				this.setImagen(proyecto.getImagen().getBytes(1, (int) proyecto.getImagen().length()));
			}
		} catch (SQLException e) {
			throw new NonBusinessException("Error getting logo image bytes for proyecto [" + proyecto.getTitulo() + "].", e );
		}
	}

	/**
	 * @return the desarrolladores
	 */
	public List<PersonaDTO> getDesarrolladores() {
		return desarrolladores;
	}

	/**
	 * @param desarrolladores the desarrolladores to set
	 */
	public void setDesarrolladores(List<PersonaDTO> desarrolladores) {
		this.desarrolladores = desarrolladores;
	}

	/**
	 * @return the imagen
	 */
	public byte[] getImagen() {
		return imagen;
	}

	/**
	 * @param imagen the imagen to set
	 */
	public void setImagen(byte[] imagen) {
		this.imagen = imagen;
	}
}
