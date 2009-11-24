/**
 * 
 */
package ar.com.angelDurmiente.services.impl;

import ar.com.angelDurmiente.beans.Texto;
import ar.com.angelDurmiente.daos.TextoDAO;
import ar.com.angelDurmiente.services.TextoService;

import com.angel.architecture.services.impl.GenericServiceImpl;

/**
 * @author Guillermo Salazar
 * @since 23/Noviembre/2009.
 *
 */
public class TextoServiceImpl extends GenericServiceImpl implements TextoService {

	protected TextoDAO getTextoDAO(){
		return (TextoDAO) super.getGenericDAO();
	}
	
	public Texto buscarUnicoPorTitulo(String titulo) {
		return this.getTextoDAO().buscarUnicoPorTitulo(titulo);
	}



}
