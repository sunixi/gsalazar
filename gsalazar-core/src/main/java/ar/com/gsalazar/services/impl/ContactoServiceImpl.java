/**
 * 
 */
package ar.com.gsalazar.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.com.gsalazar.beans.Contacto;
import ar.com.gsalazar.daos.ContactoDAO;
import ar.com.gsalazar.services.ContactoService;

import com.angel.architecture.persistence.base.PersistentObject;
import com.angel.architecture.services.impl.GenericServiceImpl;

/**
 * @author Guillermo Salazar
 *
 */
@Service(value = "contactoService")
public class ContactoServiceImpl extends GenericServiceImpl implements ContactoService{
	
	@Autowired
	private ContactoDAO contactoDAO;
	
	@Override
	public PersistentObject create(PersistentObject persistentObject){
		persistentObject.updateNullAttributes();
		return this.getContactoDAO().persist((Contacto) persistentObject);
	}

	/**
	 * @return the contactoDAO
	 */
	public ContactoDAO getContactoDAO() {
		return contactoDAO;
	}

	/**
	 * @param contactoDAO the contactoDAO to set
	 */
	public void setContactoDAO(ContactoDAO contactoDAO) {
		this.contactoDAO = contactoDAO;
	}
}
