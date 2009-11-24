/**
 * 
 */
package ar.com.angelDurmiente.services.impl;

import java.util.Map;

import org.apache.log4j.Logger;

import ar.com.angelDurmiente.beans.Documento;
import ar.com.angelDurmiente.beans.Texto;
import ar.com.angelDurmiente.beans.Usuario;
import ar.com.angelDurmiente.daos.TextoDAO;
import ar.com.angelDurmiente.documentoBuilder.DocumentoBuilder;
import ar.com.angelDurmiente.dtos.TextoCompartirInfoDTO;
import ar.com.angelDurmiente.services.TextoService;

import com.angel.architecture.services.impl.GenericServiceImpl;

/**
 * @author Guillermo Salazar
 * @since 23/Noviembre/2009.
 *
 */
public class TextoServiceImpl extends GenericServiceImpl implements TextoService {

	private static final Logger LOGGER = Logger.getLogger(TextoServiceImpl.class);

	private Map<String, DocumentoBuilder> documentosBuilders;
	
	protected TextoDAO getTextoDAO(){
		return (TextoDAO) super.getGenericDAO();
	}
	
	public Texto buscarUnicoPorTitulo(String titulo) {
		return this.getTextoDAO().buscarUnicoPorTitulo(titulo);
	}

	public void compartirTexto(TextoCompartirInfoDTO textoCompartirInfoDTO, Usuario usuario){
		DocumentoBuilder documentoBuilder = this.getDocumentosBuilders().get(textoCompartirInfoDTO.getEstrategiaDocumentoBuilder());
		Texto texto = this.crearTexto(textoCompartirInfoDTO.getContenidoDocumento(), usuario);
		Documento documento = documentoBuilder.buildDocumento(textoCompartirInfoDTO, texto);
		LOGGER.info("Se ha creado el documento con titulo [" + documento.getTitulo() + "] e identificacion [" + documento.getIdAsString() + "].");
	}

	/**
	 * @return the documentosBuilders
	 */
	public Map<String, DocumentoBuilder> getDocumentosBuilders() {
		return documentosBuilders;
	}

	/**
	 * @param documentosBuilders the documentosBuilders to set
	 */
	public void setDocumentosBuilders(
			Map<String, DocumentoBuilder> documentosBuilders) {
		this.documentosBuilders = documentosBuilders;
	}

	public Texto crearTexto(String contenido, Usuario usuario) {
		Texto texto = new Texto();
		texto.setRating(0);
		texto.setVerificado(false);
		texto.setContenido(contenido);
		texto.setUsuario(usuario);
		return texto;
	}
}
