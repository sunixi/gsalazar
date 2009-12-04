/**
 * 
 */
package ar.com.angelDurmiente.services.impl;

import ar.com.angelDurmiente.beans.Artista;
import ar.com.angelDurmiente.daos.ArtistaDAO;
import ar.com.angelDurmiente.services.ArtistaService;

import com.angel.architecture.services.impl.GenericServiceImpl;

/**
 * @author Guillermo Salazar
 *
 */
public class ArtistaServiceImpl extends GenericServiceImpl implements ArtistaService {


	protected ArtistaDAO getArtistaDAO(){
		return (ArtistaDAO) super.getGenericDAO();
	}

	public Artista buscarUnicoPorNombre(String nombre) {
		return this.getArtistaDAO().buscarUnicoPorNombre(nombre);
	}

}
