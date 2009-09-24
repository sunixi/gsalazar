/**
 * 
 */
package ar.com.gsalazar.services;

import java.util.Collection;

import ar.com.gsalazar.beans.Articulo;

import com.angel.architecture.services.interfaces.GenericService;

/**
 * @author Guillermo Salazar
 *
 */
public interface ArticuloService extends GenericService {

	public Collection<Articulo> buscarTodosContengaContenido(String contenido);
	
	public Articulo buscarUnicoPorTitulo(String titulo);
	
	public Collection<Articulo> buscarTodosContengaDescripcion(String descripcion);
	
}
