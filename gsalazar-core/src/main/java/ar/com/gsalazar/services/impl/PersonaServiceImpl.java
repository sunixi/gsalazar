/**
 * 
 */
package ar.com.gsalazar.services.impl;

import java.util.ArrayList;
import java.util.List;

import ar.com.gsalazar.beans.Persona;
import ar.com.gsalazar.daos.PersonaDAO;
import ar.com.gsalazar.dtos.PersonaDTO;
import ar.com.gsalazar.services.PersonaService;

import com.angel.architecture.services.impl.GenericServiceImpl;

/**
 * @author Guillermo Salazar
 *
 */
public class PersonaServiceImpl extends GenericServiceImpl implements PersonaService{

	protected PersonaDAO getPersonaDAO(){
		return (PersonaDAO) super.getGenericDAO();
	}

	public List<PersonaDTO> buscarTodosPorApellido(String apellido) {
		List<Persona> personas = this.getPersonaDAO().buscarTodosPorApellido(apellido);
		List<PersonaDTO> personasDTO = new ArrayList<PersonaDTO>();
		for(Persona p: personas){
			personasDTO.add(new PersonaDTO(p));
		}
		return personasDTO;
	}

	public PersonaDTO buscarUnicoPorEmail(String email) {
		Persona persona = this.getPersonaDAO().buscarUnicoPorEmail(email);
		return new PersonaDTO(persona);
	}

	public PersonaDTO buscarUnicoPorNombre(String nombre) {
		Persona persona = this.getPersonaDAO().buscarUnicoPorNombre(nombre);
		return new PersonaDTO(persona);
	}	
}
