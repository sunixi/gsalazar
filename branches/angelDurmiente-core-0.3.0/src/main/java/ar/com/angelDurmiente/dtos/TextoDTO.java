/**
 * 
 */
package ar.com.angelDurmiente.dtos;

import java.io.Serializable;
import java.util.Date;

import ar.com.angelDurmiente.beans.Texto;

/**
 * @author Guillermo Salazar
 * @since 19/Noviembre/2009.
 *
 */
public class TextoDTO implements Serializable {

	private static final long serialVersionUID = 6936830093090143041L;

	private String usuario;
	private Date subido;
	private String titulo;
	
	public TextoDTO(){
		super();
	}

	public TextoDTO(Texto texto) {
		this();
		this.setUsuario(texto.getNombreUsuario());
		this.setSubido(texto.getCreationDate());
		this.setTitulo(texto.getTitulo());
	}

	/**
	 * @return the usuario
	 */
	public String getUsuario() {
		return usuario;
	}

	/**
	 * @param usuario the usuario to set
	 */
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	/**
	 * @return the subido
	 */
	public Date getSubido() {
		return subido;
	}

	/**
	 * @param subido the subido to set
	 */
	public void setSubido(Date subido) {
		this.subido = subido;
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
