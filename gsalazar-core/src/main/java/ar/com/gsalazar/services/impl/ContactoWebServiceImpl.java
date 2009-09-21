/**
 * 
 */
package ar.com.gsalazar.services.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import ar.com.gsalazar.beans.ContactoWeb;
import ar.com.gsalazar.daos.ContactoWebDAO;
import ar.com.gsalazar.dtos.ContactoWebDTO;
import ar.com.gsalazar.services.ContactoWebService;

import com.angel.architecture.services.impl.GenericServiceImpl;

/**
 * @author Guillermo Salazar
 *
 */
public class ContactoWebServiceImpl extends GenericServiceImpl implements ContactoWebService{

	public ContactoWebDTO buscarUnicoONuloPorNombre(String nombre) {
		ContactoWeb contactoWeb = this.getContactoWebDAO().buscarUnicoONuloPorNombre(nombre);
		return new ContactoWebDTO(contactoWeb);
	}

	public ContactoWebDTO buscarUnicoPorNombre(String nombre) {
		ContactoWeb contactoWeb = this.getContactoWebDAO().buscarUnicoPorNombre(nombre);
		return new ContactoWebDTO(contactoWeb);
	}

	protected ContactoWebDAO getContactoWebDAO(){
		return (ContactoWebDAO) super.getGenericDAO();
	}

	public List<ContactoWebDTO> buscarTodos() {
		Collection<Object> domains = super.findAll();
		List<ContactoWebDTO> contactos = new ArrayList<ContactoWebDTO>();
		for(Object o: domains){
			contactos.add(new ContactoWebDTO((ContactoWeb) o));
		}
		return contactos;
	}
	
	
}
