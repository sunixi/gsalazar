/**
 * 
 */
package ar.com.angelDurmiente.daos;

import ar.com.angelDurmiente.beans.Texto;

import com.angel.architecture.persistence.ids.ObjectId;
import com.angel.dao.generic.interfaces.GenericDAO;

/**
 * 
 * @author Guillermo Salazar.
 * @since 19/Noviembre/2009.
 *
 */
public interface TextoDAO extends GenericDAO<Texto, ObjectId>{

	public Texto buscarUnicoPorTitulo(String titulo);

}
