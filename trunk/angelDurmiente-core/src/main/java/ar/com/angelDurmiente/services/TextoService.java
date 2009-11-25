/**
 * 
 */
package ar.com.angelDurmiente.services;

import ar.com.angelDurmiente.beans.Texto;
import ar.com.angelDurmiente.beans.Usuario;
import ar.com.angelDurmiente.dtos.TextoCompartirInfoDTO;

import com.angel.architecture.services.interfaces.GenericService;

/**
 * @author Guillermo Salazar
 *
 */
public interface TextoService extends GenericService {

	public Texto buscarUnicoPorTitulo(String titulo);

	public void compartirTexto(TextoCompartirInfoDTO textoCompartirInfoDTO, Usuario usuario);
}
