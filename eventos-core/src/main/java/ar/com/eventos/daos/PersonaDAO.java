/**
 * 
 */
package ar.com.eventos.daos;

import java.util.Date;
import java.util.List;

import ar.com.eventos.beans.Persona;

import com.angel.architecture.persistence.ids.ObjectId;
import com.angel.dao.generic.interfaces.GenericDAO;

/**
 * @author Guillermo Salazar.
 * @since 27/Agosto/2009.
 *
 */
public interface PersonaDAO extends GenericDAO<Persona, ObjectId>{
	
	public Persona buscarUnicoPorEmail(String email);
	
	public Persona buscarUnicoONuloPorEmail(String email);
	
	public List<Persona> buscarTodosPorNombre(String nombre);
	
	public List<Persona> buscarTodosPorFechaNacimiento(Date nacimiento);
	
	public Persona buscarUnicoONuloPorFotolog(String fotolog);
	
	public Persona buscarUnicoPorFotolog(String fotolog);
	
}