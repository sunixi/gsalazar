/**
 * 
 */
package ar.com.angelDurmiente.beans;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;

import com.angel.architecture.persistence.base.PersistentObject;

/**
 * @author Guillermo Salazar
 * @since 19/Noviembre/2009.
 *
 */
@Entity
public class Cancion extends PersistentObject {

	private static final long serialVersionUID = -1457278499744128408L;
	
	private String titulo;

	@ManyToOne(optional = false)
	private Artista artista;
	
	@OneToMany(cascade = CascadeType.ALL)
	@OrderBy(value = "creationDate desc")
	private List<Texto> textos;

	public Cancion(){
		super();
		this.setTextos(new ArrayList<Texto>());
	}
	
	public Cancion(Texto texto){
		this();
		this.agregarTexto(texto);
	}
	
	public Cancion(List<Texto> textos){
		this();
		this.agregarTextos(textos);
	}

	/**
	 * @return the titulo
	 */
	public String getTitulo() {
		return titulo;
	}

	/**
	 * @param titulo the titulo to set
	 */
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	/**
	 * @return the artista
	 */
	public Artista getArtista() {
		return artista;
	}

	/**
	 * @param artista the artista to set
	 */
	public void setArtista(Artista artista) {
		this.artista = artista;
	}

	/**
	 * @return the textos
	 */
	public List<Texto> getTextos() {
		return textos;
	}

	/**
	 * @param textos the textos to set
	 */
	public void setTextos(List<Texto> textos) {
		this.textos = textos;
	}
	
	public void agregarTexto(Texto texto){
		this.getTextos().add(texto);
	}

	public void agregarTextos(List<Texto> textos){
		for(Texto texto: textos){
			this.agregarTexto(texto);
		}
	}

	public long cantidadTextos() {
		return this.getTextos().size();
	}
}
