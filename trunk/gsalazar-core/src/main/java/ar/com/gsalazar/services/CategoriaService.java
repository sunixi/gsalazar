/**
 * 
 */
package ar.com.gsalazar.services;

import java.util.List;

import ar.com.gsalazar.beans.Categoria;

import com.angel.architecture.services.interfaces.GenericService;

/**
 * @author Guillermo Salazar
 *
 */
public interface CategoriaService extends GenericService {

	
	public List<Categoria> buscarSubCategoriasPara(String nombreCategoria);
	
}
