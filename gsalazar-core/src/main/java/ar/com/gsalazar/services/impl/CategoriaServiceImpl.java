/**
 * 
 */
package ar.com.gsalazar.services.impl;

import java.util.List;

import ar.com.gsalazar.beans.Categoria;
import ar.com.gsalazar.daos.CategoriaDAO;
import ar.com.gsalazar.services.CategoriaService;

import com.angel.architecture.services.impl.GenericServiceImpl;

/**
 * @author Guillermo Salazar
 *
 */
public class CategoriaServiceImpl extends GenericServiceImpl implements CategoriaService{

	public List<Categoria> buscarSubCategoriasPara(String nombreCategoria) {
		Categoria categoria = this.getCategoriaDAO().buscarUnicoPorNombre(nombreCategoria);
		return categoria.getSubCategorias();
	}

	/**
	 * @return the categoriaDAO
	 */
	public CategoriaDAO getCategoriaDAO() {
		return (CategoriaDAO) super.getGenericDAO();
	}
}
