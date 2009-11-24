/**
 * 
 */
package ar.com.angelDurmiente.services.impl;

import java.util.Date;
import java.util.List;

import ar.com.angelDurmiente.beans.Dedicatoria;
import ar.com.angelDurmiente.beans.Texto;
import ar.com.angelDurmiente.beans.Usuario;
import ar.com.angelDurmiente.daos.DedicatoriaDAO;
import ar.com.angelDurmiente.services.DedicatoriaService;

import com.angel.architecture.services.impl.GenericServiceImpl;

/**
 * @author Guillermo Salazar
 *
 */
public class DedicatoriaServiceImpl extends GenericServiceImpl implements DedicatoriaService {

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

}
