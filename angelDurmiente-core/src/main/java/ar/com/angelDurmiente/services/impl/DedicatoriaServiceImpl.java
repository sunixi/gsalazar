/**
 * 
 */
package ar.com.angelDurmiente.services.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import ar.com.angelDurmiente.beans.Dedicatoria;
import ar.com.angelDurmiente.beans.Texto;
import ar.com.angelDurmiente.beans.Usuario;
import ar.com.angelDurmiente.daos.DedicatoriaDAO;
import ar.com.angelDurmiente.dtos.TextoDTO;
import ar.com.angelDurmiente.services.DedicatoriaService;
import ar.com.angelDurmiente.services.TextoService;

import com.angel.architecture.services.impl.GenericServiceImpl;

/**
 * @author Guillermo Salazar
 *
 */
public class DedicatoriaServiceImpl extends GenericServiceImpl implements DedicatoriaService {

	@Autowired
	private TextoService textoService;

	protected DedicatoriaDAO getDedicatoriaDAO(){
		return (DedicatoriaDAO) super.getGenericDAO();
	}

	public List<Dedicatoria> buscarTodosPorDedicador(Usuario dedicador) {
		return this.getDedicatoriaDAO().buscarTodosPorDedicador(dedicador);
	}

	public List<Dedicatoria> buscarTodosPorDedicatorio(Usuario dedicatorio) {
		return this.getDedicatoriaDAO().buscarTodosPorDedicatorio(dedicatorio);
	}

	public List<Dedicatoria> buscarTodosPorRangoFechas(Date desde, Date hasta) {
		return this.getDedicatoriaDAO().buscarTodosPorRangoFechas(desde, hasta);
	}

	public List<Dedicatoria> buscarTodosPorTexto(Texto texto) {
		return this.getDedicatoriaDAO().buscarTodosPorTexto(texto);
	}

	public List<Dedicatoria> buscarTodosPorUsuarioTexto(String nombreUsuario) {
		return this.getDedicatoriaDAO().buscarTodosPorUsuarioTexto(nombreUsuario);
	}

	public Dedicatoria dedicarTexto(TextoDTO textoDTO, Usuario dedicatario, Usuario dedicador, Date desde, Date hasta) {
		Texto texto = this.getTextoService().buscarUnicoPorTitulo(textoDTO.getTitulo());
		Dedicatoria dedicatoria = new Dedicatoria();
		dedicatoria.setDedicador(dedicador);
		dedicatoria.setDedicatario(dedicatario);
		dedicatoria.setTexto(texto);
		dedicatoria.setDesde(desde);
		dedicatoria.setHasta(hasta);
		return (Dedicatoria) super.create(dedicatoria);
	}

	/**
	 * @return the textoService
	 */
	public TextoService getTextoService() {
		return textoService;
	}

	/**
	 * @param textoService the textoService to set
	 */
	public void setTextoService(TextoService textoService) {
		this.textoService = textoService;
	}
}
