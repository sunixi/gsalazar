/**
 * 
 */
package ar.com.angelDurmiente.daos.impl;

import ar.com.angelDurmiente.beans.Texto;
import ar.com.angelDurmiente.daos.TextoDAO;

import com.angel.architecture.persistence.ids.ObjectId;
import com.angel.dao.generic.impl.GenericSpringHibernateDAO;

/**
 * 
 * 
 * @author Guillermo Salazar
 * @since 28/Agosto/2009.
 *
 */
public class TextoSpringHibernateDAO extends GenericSpringHibernateDAO<Texto, ObjectId> implements TextoDAO {

	public TextoSpringHibernateDAO() {
		super(Texto.class, ObjectId.class);
	}

	public Texto buscarUnicoPorTitulo(String titulo) {
		return super.findUnique("titulo", titulo);
	}	
}
