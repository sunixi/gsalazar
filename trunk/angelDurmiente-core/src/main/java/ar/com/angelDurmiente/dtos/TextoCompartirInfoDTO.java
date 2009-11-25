/**
 * 
 */
package ar.com.angelDurmiente.dtos;

import java.io.Serializable;
import java.util.List;

/**
 * @author Guillermo Salazar
 * @since 19/Noviembre/2009.
 *
 */
public class TextoCompartirInfoDTO implements Serializable {

	private static final long serialVersionUID = 6936830093090143041L;

	private String estrategiaDocumentoBuilder;

	private String contenido;
	
	private String nombreArtista;

	private String nombreAlbum;
	
	private String tituloDocumento;
	
	private String contenidoDocumento;
	
	private List<String> nombreAcordes;

	public TextoCompartirInfoDTO(){
		super();
	}

	/**
	 * @return the estrategiaDocumentoBuilder
	 */
	public String getEstrategiaDocumentoBuilder() {
		return estrategiaDocumentoBuilder;
	}

	/**
	 * @param estrategiaDocumentoBuilder the estrategiaDocumentoBuilder to set
	 */
	public void setEstrategiaDocumentoBuilder(String estrategiaDocumentoBuilder) {
		this.estrategiaDocumentoBuilder = estrategiaDocumentoBuilder;
	}

	/**
	 * @return the contenido
	 */
	protected String getContenido() {
		return contenido;
	}

	/**
	 * @param contenido the contenido to set
	 */
	protected void setContenido(String contenido) {
		this.contenido = contenido;
	}

	/**
	 * @return the nombreArtista
	 */
	public String getNombreArtista() {
		return nombreArtista;
	}

	/**
	 * @param nombreArtista the nombreArtista to set
	 */
	public void setNombreArtista(String nombreArtista) {
		this.nombreArtista = nombreArtista;
	}

	/**
	 * @return the nombreAlbum
	 */
	public String getNombreAlbum() {
		return nombreAlbum;
	}

	/**
	 * @param nombreAlbum the nombreAlbum to set
	 */
	public void setNombreAlbum(String nombreAlbum) {
		this.nombreAlbum = nombreAlbum;
	}

	/**
	 * @return the tituloDocumento
	 */
	public String getTituloDocumento() {
		return tituloDocumento;
	}

	/**
	 * @param tituloDocumento the tituloDocumento to set
	 */
	public void setTituloDocumento(String tituloDocumento) {
		this.tituloDocumento = tituloDocumento;
	}

	/**
	 * @return the contenidoDocumento
	 */
	public String getContenidoDocumento() {
		return contenidoDocumento;
	}

	/**
	 * @param contenidoDocumento the contenidoDocumento to set
	 */
	public void setContenidoDocumento(String contenidoDocumento) {
		this.contenidoDocumento = contenidoDocumento;
	}

	/**
	 * @return the nombreAcordes
	 */
	public List<String> getNombreAcordes() {
		return nombreAcordes;
	}

	/**
	 * @param nombreAcordes the nombreAcordes to set
	 */
	public void setNombreAcordes(List<String> nombreAcordes) {
		this.nombreAcordes = nombreAcordes;
	}
}
