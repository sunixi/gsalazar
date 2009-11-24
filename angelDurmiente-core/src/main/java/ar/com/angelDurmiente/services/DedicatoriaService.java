/**
 * 
 */
package ar.com.angelDurmiente.services;

import java.util.Date;
import java.util.List;

import ar.com.angelDurmiente.beans.Dedicatoria;
import ar.com.angelDurmiente.beans.Texto;
import ar.com.angelDurmiente.beans.Usuario;

import com.angel.architecture.services.interfaces.GenericService;

/**
 * @author Guillermo Salazar
 *
 */
public interface DedicatoriaService extends GenericService {
	
	public List<Dedicatoria> buscarTodosPorUsuarioTexto(String nombreUsuario);

	public List<Dedicatoria> buscarTodosPorTexto(Texto texto);
	
	public List<Dedicatoria> buscarTodosPorDedicador(Usuario dedicador);
	
	public List<Dedicatoria> buscarTodosPorDedicatorio(Usuario dedicatorio);
	
	public List<Dedicatoria> buscarTodosPorRangoFechas(Date desde, Date hasta);
}
