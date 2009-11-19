/**
 * 
 */
package ar.com.angelDurmiente.beans;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

import com.angel.architecture.persistence.base.PersistentObject;

/**
 * @author Guillermo Salazar
 * @since 19/Noviembre/2009.
 *
 */
@Entity
public class Artista extends PersistentObject {

	private static final long serialVersionUID = -1457278499744128408L;

	@Column(nullable = false)
	private String nombre;

	@OneToMany
	private List<Album> albums;

	public Artista(){
		super();
		this.setAlbums(new ArrayList<Album>());
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
	 * @return the albums
	 */
	public List<Album> getAlbums() {
		return albums;
	}


	/**
	 * @param albums the albums to set
	 */
	public void setAlbums(List<Album> albums) {
		this.albums = albums;
	}
}
