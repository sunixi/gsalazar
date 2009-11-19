/**
 * 
 */
package ar.com.angelDurmiente.services.impl;

import java.util.List;

import ar.com.angelDurmiente.beans.Cancion;
import ar.com.angelDurmiente.daos.CancionDAO;
import ar.com.angelDurmiente.services.CancionService;

import com.angel.architecture.services.impl.GenericServiceImpl;

/**
 * @author Guillermo Salazar
 *
 */
public class CancionServiceImpl extends GenericServiceImpl implements CancionService{

	public List<Cancion> buscarTodosPorContenido(String contenido) {
		return this.getCancionDAO().buscarTodosPorContenido(contenido);
	}

	public List<Cancion> buscarTodosPorTitulo(String titulo) {
		return this.getCancionDAO().buscarTodosPorTitulo(titulo);
	}

	protected CancionDAO getCancionDAO(){
		return (CancionDAO) super.getGenericDAO();
	}
}
