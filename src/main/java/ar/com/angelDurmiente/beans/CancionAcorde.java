/**
 * 
 */
package ar.com.angelDurmiente.beans;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;

/**
 * @author Guillermo Salazar
 * @since 19/Noviembre/2009.
 *
 */
@Entity
public class CancionAcorde extends Cancion {

	private static final long serialVersionUID = -1457278499744128408L;
	
	@ManyToMany
	private List<Acorde> acordes;

	public CancionAcorde(){
		super();
		this.setAcordes(new ArrayList<Acorde>());
	}

	/**
	 * @return the acordes
	 */
	public List<Acorde> getAcordes() {
		return acordes;
	}

	/**
	 * @param acordes the acordes to set
	 */
	public void setAcordes(List<Acorde> acordes) {
		this.acordes = acordes;
	}
	
	public void agregarAcorde(Acorde acorde){
		this.getAcordes().add(acorde);
	}

	public void agregarAcordes(List<Acorde> acordes) {
		for(Acorde a: acordes){
			this.agregarAcorde(a);
		}
	}
}
