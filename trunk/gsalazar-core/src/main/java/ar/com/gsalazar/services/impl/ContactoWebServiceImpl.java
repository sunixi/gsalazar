/**
 * 
 */
package ar.com.gsalazar.services.impl;

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
	
	
}
