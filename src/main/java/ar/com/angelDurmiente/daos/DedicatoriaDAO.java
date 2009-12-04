/**
 * 
 */
package ar.com.angelDurmiente.daos;

import java.util.Date;
import java.util.List;

import ar.com.angelDurmiente.beans.Dedicatoria;
import ar.com.angelDurmiente.beans.Texto;
import ar.com.angelDurmiente.beans.Usuario;

import com.angel.architecture.persistence.ids.ObjectId;
import com.angel.dao.generic.interfaces.GenericDAO;

/**
 * 
 * @author Guillermo Salazar.
 * @since 19/Noviembre/2009.
 *
 */
public interface DedicatoriaDAO extends GenericDAO<Dedicatoria, ObjectId>{

	public List<Dedicatoria> buscarTodosPorUsuarioTexto(String nombreUsuario);

	public List<Dedicatoria> buscarTodosPorTexto(Texto texto);
	
	public List<Dedicatoria> buscarTodosPorDedicador(Usuario dedicador);
	
	public List<Dedicatoria> buscarTodosPorDedicatorio(Usuario dedicatorio);
	
	public List<Dedicatoria> buscarTodosPorRangoFechas(Date desde, Date hasta);
}
