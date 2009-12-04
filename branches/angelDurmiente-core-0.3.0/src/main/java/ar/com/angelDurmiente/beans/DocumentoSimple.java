/**
 * 
 */
package ar.com.angelDurmiente.beans;

import javax.persistence.MappedSuperclass;

/**
 * @author Guillermo Salazar
 * @since 19/Noviembre/2009.
 *
 */
@MappedSuperclass
public abstract class DocumentoSimple extends Documento {

	private static final long serialVersionUID = -1457278499744128408L;

	private Texto texto;
	
	public DocumentoSimple(){
		super();
	}

	/**
	 * @return the texto
	 */
	public Texto getTexto() {
		return texto;
	}

	/**
	 * @param texto the texto to set
	 */
	public void setTexto(Texto texto) {
		this.texto = texto;
	}
	
	public long cantidadTextos(){
		return 1;
	}

	public void agregarTexto(Texto texto){
		this.setTexto(texto);
	}
	
	public void removerTexto(Texto texto){
		if(this.getTexto().equals(texto)){
			this.texto = null;
		}
	}
	
	public void removerTexto(String titulo){
		if(this.tieneTexto() && this.getTexto().tieneTitulo(titulo)){
			this.removerTexto(this.getTexto());
		}
	}
	
	public boolean tieneTexto(){
		return this.getTexto() != null;
	}
}
