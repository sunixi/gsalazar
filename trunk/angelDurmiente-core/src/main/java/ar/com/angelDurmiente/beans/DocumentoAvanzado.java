/**
 * 
 */
package ar.com.angelDurmiente.beans;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;

/**
 * @author Guillermo Salazar
 * @since 19/Noviembre/2009.
 *
 */
@MappedSuperclass
public abstract class DocumentoAvanzado extends Documento {

	private static final long serialVersionUID = -1457278499744128408L;
	
	@OneToMany(cascade = CascadeType.ALL)
	@OrderBy(value = "creationDate desc")
	private List<Texto> textos;
	
	public DocumentoAvanzado(){
		super();
		this.setTextos(new ArrayList<Texto>());
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

	public long cantidadTextos(){
		return this.getTextos().size();
	}

	public void agregarTexto(Texto texto){
		if(!this.contieneTexto(texto)){
			this.getTextos().add(texto);
		}
	}
	
	public void removerTexto(Texto texto){
		if(this.getTextos().contains(texto)){
			this.getTextos().remove(texto);
		}
	}
	
	public boolean contieneTexto(Texto texto){
		return this.tieneTexto() && this.getTextos().contains(texto);
	}
	
	public boolean contieneTexto(String titulo){
		for(Texto t: this.getTextos()){
			if(t.tieneTitulo(titulo)){
				return true;
			}
		}
		return false;
	}
	
	public Texto buscarTexto(String titulo){
		for(Texto t: this.getTextos()){
			if(t.tieneTitulo(titulo)){
				return t;
			}
		}
		return null;
	}
	
	public void removerTexto(String titulo){
		if(this.contieneTexto(titulo)){
			Texto texto = this.buscarTexto(titulo);
			this.removerTexto(texto);
		}
	}

	public boolean tieneTexto(){
		return !this.getTextos().isEmpty();
	}
	
	public void agregarTextos(List<Texto> textos){
		for(Texto t: textos){
			this.agregarTexto(t);
		}
	}
}
