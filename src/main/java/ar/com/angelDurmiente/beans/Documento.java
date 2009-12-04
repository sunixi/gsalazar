/**
 * 
 */
package ar.com.angelDurmiente.beans;

import javax.persistence.MappedSuperclass;

import ar.com.angelDurmiente.types.Documentable;

import com.angel.architecture.persistence.base.PersistentObject;

/**
 * @author Guillermo Salazar
 * @since 19/Noviembre/2009.
 *
 */
@MappedSuperclass
public abstract class Documento extends PersistentObject implements Documentable {

	private static final long serialVersionUID = -1457278499744128408L;

	private String titulo;
	
	public Documento(){
		super();
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
}
