/**
 * 
 */
package ar.com.gsalazar.beans;

import java.sql.Blob;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.Transient;

import com.angel.architecture.flex.annotations.serialization.FlexTransient;

/**
 * 
 *	@author Guillermo Daniel Salazar.
 *	@since 16/Semptiembre/2009.
 */
@Entity
public class Proyecto extends Buscable {

	private static final long serialVersionUID = -462238529934628758L;
	@Lob
	private Blob imagen;
	
	@Transient
	private byte[] imagenArray;

	@ManyToMany
	private List<Persona> desarrolladores;

	public Proyecto(){
		super();
		this.setDesarrolladores(new ArrayList<Persona>());
	}
	
	public Proyecto(Blob imagen){
		this();
		this.setImagen(imagen);
	}
	
	public Proyecto(Blob imagen, Collection<Persona> desarrolladores){
		this();
		this.setImagen(imagen);
		this.getDesarrolladores().addAll(desarrolladores);
	}

	/**
	 * @return the imagen
	 */
	@FlexTransient
	public Blob getImagen() {
		return imagen;
	}

	/**
	 * @param imagen the imagen to set
	 */
	public void setImagen(Blob imagen) {
		this.imagen = imagen;
	}

	/**
	 * @return the desarrolladores
	 */
	public List<Persona> getDesarrolladores() {
		return desarrolladores;
	}

	/**
	 * @param desarrolladores the desarrolladores to set
	 */
	public void setDesarrolladores(List<Persona> desarrolladores) {
		this.desarrolladores = desarrolladores;
	}

	public void addDesarrollador(Persona desarrollador, boolean tagear){
		if(tagear){
			String descripcion = desarrollador.getApellido() + ", " + desarrollador.getNombre() + " - " + desarrollador.getEmail(); 
			super.addTagBuscable(desarrollador.getApellido() + ", " + desarrollador.getNombre(), descripcion);
		}
		this.getDesarrolladores().add(desarrollador);
	}

	public void addDesarrolladorSinTagear(Persona desarrollador){
		this.addDesarrollador(desarrollador, false);
	}
	
	public void addDesarrollador(Persona desarrollador){
		this.addDesarrollador(desarrollador, true);
	}
	
	public void removeDesarrollador(Persona desarrollador){
		this.getDesarrolladores().remove(desarrollador);
	}
	
	public boolean containsDesarrollador(Persona desarrollador){
		return this.getDesarrolladores().contains(desarrollador);
	}

	/**
	 * @return the imagenArray
	 */
	public byte[] getImagenArray() {
		return imagenArray;
	}

	/**
	 * @param imagenArray the imagenArray to set
	 */
	public void setImagenArray(byte[] imagenArray) {
		this.imagenArray = imagenArray;
	}
}
