/**
 * 
 */
package ar.com.gsalazar.beans;

import java.sql.Blob;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;

import com.angel.architecture.persistence.base.PersistentObject;

/**
 * 
 *	@author Guillermo Daniel Salazar.
 *	@since 16/Semptiembre/2009.
 */
@Entity
public class ImagenItemCategoria extends PersistentObject {

	private static final long serialVersionUID = -462238529934628758L;
	
	@Lob
	@Column(nullable = false)
	private Blob imagen;
	
	@Column(nullable = false)
	private String fileName;

	public ImagenItemCategoria(){
		super();
	}
	
	public ImagenItemCategoria(Blob imagen, String nombreArchivo){
		this();
		this.setFileName(nombreArchivo);
		this.setImagen(imagen);
	}
	
	/**
	 * @return the image
	 */
	public Blob getImagen() {
		return imagen;
	}

	/**
	 * @param image the image to set
	 */
	public void setImagen(Blob imagen) {
		this.imagen = imagen;
	}

	/**
	 * @return the fileName
	 */
	public String getFileName() {
		return fileName;
	}

	/**
	 * @param fileName the fileName to set
	 */
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
}
