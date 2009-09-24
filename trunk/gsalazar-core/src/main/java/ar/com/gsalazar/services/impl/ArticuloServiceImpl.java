/**
 * 
 */
package ar.com.gsalazar.services.impl;

import java.util.Collection;

import ar.com.gsalazar.beans.Articulo;
import ar.com.gsalazar.daos.ArticuloDAO;
import ar.com.gsalazar.services.ArticuloService;

import com.angel.architecture.services.impl.GenericServiceImpl;

/**
 * @author Guillermo Salazar
 *
 */
public class ArticuloServiceImpl extends GenericServiceImpl implements ArticuloService{

	
	/**
	 * @return the categoriaDAO
	 */
	public ArticuloDAO getArticuloDAO() {
		return (ArticuloDAO) super.getGenericDAO();
	}

	public Collection<Articulo> buscarTodosContengaContenido(String contenido) {
		return this.getArticuloDAO().buscarTodosContenganContenido(contenido);
	}
	
	public Collection<Articulo> buscarTodosContengaDescripcion(String descripcion) {
		return this.getArticuloDAO().buscarTodosContenganDescripcion(descripcion);
	}

	public Articulo buscarUnicoPorTitulo(String titulo) {
		return this.getArticuloDAO().buscarUnicoPorTitulo(titulo);
	}
}
