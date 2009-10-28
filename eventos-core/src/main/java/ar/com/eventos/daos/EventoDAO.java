/**
 * 
 */
package ar.com.eventos.daos;

import java.util.Date;
import java.util.List;

import ar.com.eventos.beans.Evento;
import ar.com.eventos.beans.Salon;

import com.angel.architecture.persistence.ids.ObjectId;
import com.angel.dao.generic.interfaces.GenericDAO;

/**
 * @author Guillermo Salazar.
 * @since 27/Agosto/2009.
 *
 */
public interface EventoDAO extends GenericDAO<Evento, ObjectId>{
	
	public List<Evento> buscarTodosPorSalon(Salon salon);

	public List<Evento> buscarTodosPorCantidadImagenes(int cantidadImagenes);
	
	public List<Evento> buscarTodosPorFechaRealizacion(Date fechaRealizacion);
}
