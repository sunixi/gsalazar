/**
 * 
 */
package ar.com.eventos.beans;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.hibernate.annotations.Type;

import com.angel.architecture.persistence.base.PersistentObject;

/**
 * 
 *	@author Guillermo Daniel Salazar.
 *	@since 16/Semptiembre/2009.
 */
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(discriminatorType = DiscriminatorType.STRING, name ="discriminator")
@DiscriminatorValue("evento")
@Entity
public class Evento extends PersistentObject {

	private static final long serialVersionUID = -6968492110376646351L;

	@ManyToOne(optional = false)
	private Salon salon;

	@OneToMany
	private List<Imagen> imagenes;
	
	@Column(nullable = false)
	@Type(type = "text")
	private String descripcion;
	
	@Column(nullable = false)
	private Date realizacion;

	/**
	 * @return the salon
	 */
	public Salon getSalon() {
		return salon;
	}

	/**
	 * @param salon the salon to set
	 */
	public void setSalon(Salon salon) {
		this.salon = salon;
	}

	/**
	 * @return the imagenes
	 */
	public List<Imagen> getImagenes() {
		return imagenes;
	}

	/**
	 * @param imagenes the imagenes to set
	 */
	public void setImagenes(List<Imagen> imagenes) {
		this.imagenes = imagenes;
	}

	/**
	 * @return the realizacion
	 */
	public Date getRealizacion() {
		return realizacion;
	}

	/**
	 * @param realizacion the realizacion to set
	 */
	public void setRealizacion(Date realizacion) {
		this.realizacion = realizacion;
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

}
