/**
 * 
 */
package ar.com.angelDurmiente.documentoBuilder;

import ar.com.angelDurmiente.beans.Documento;
import ar.com.angelDurmiente.beans.Texto;
import ar.com.angelDurmiente.dtos.TextoCompartirInfoDTO;

/**
 * 
 * @author Guillermo Salazar
 * @since 22/Noviembre/2009
 *
 */
public interface DocumentoBuilder {

	public Documento buildDocumento(TextoCompartirInfoDTO textoCompartirInfoDTO, Texto texto);
	
}
