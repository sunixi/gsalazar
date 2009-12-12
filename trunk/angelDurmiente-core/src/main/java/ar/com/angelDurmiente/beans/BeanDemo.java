/**
 * 
 */
package ar.com.angelDurmiente.beans;

import java.sql.Blob;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;

import com.angel.architecture.persistence.beans.User;
import com.angel.code.generator.annotations.Accesor;
import com.angel.code.generator.annotations.MetadataAccesors;
import com.angel.code.generator.annotations.MetadataAccess;
import com.angel.code.generator.annotations.MetadataGenerator;

/**
 * @author Guillermo Salazar
 * @since 19/Noviembre/2009.
 *
 */
@MetadataGenerator
@MetadataAccesors(
accessStrategies = {
		@MetadataAccess(
				properties = {"apellido", "nombre"},
				uniqueResult = true
				)
}
)
@Entity
public class BeanDemo extends User {

	private static final long serialVersionUID = -6441190120910247706L;

	@Accesor
	@Column(length = 40)
	private String nombre;

	@Accesor
	@Column(length = 40)
	private String apellido;
	
	@Accesor
	@Lob
	@Column(nullable = true)
	private Blob imagen;

	@Accesor
	private Date nacimiento;

	/**
	 * 
	 */
	public BeanDemo() {
		super();
	}

	/**
	 * @param name
	 */
	public BeanDemo(String name) {
		super(name);
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
	 * @return the nacimiento
	 */
	public Date getNacimiento() {
		return nacimiento;
	}

	/**
	 * @param nacimiento the nacimiento to set
	 */
	public void setNacimiento(Date nacimiento) {
		this.nacimiento = nacimiento;
	}

	/**
	 * @return the imagen
	 */
	public Blob getImagen() {
		return imagen;
	}

	/**
	 * @param imagen the imagen to set
	 */
	public void setImagen(Blob imagen) {
		this.imagen = imagen;
	}
	
	public String getNombreCompleto(){
		return this.getNombre() + " " + this.getApellido();
	}
}
