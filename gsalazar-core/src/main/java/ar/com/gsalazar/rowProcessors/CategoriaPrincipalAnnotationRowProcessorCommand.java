/**
 *
 */
package ar.com.gsalazar.rowProcessors;

import ar.com.gsalazar.beans.Categoria;
import ar.com.gsalazar.daos.CategoriaDAO;

import com.angel.common.helpers.StringHelper;
import com.angel.data.generator.annotations.Inject;
import com.angel.io.annotations.ColumnRow;
import com.angel.io.annotations.RowChecker;
import com.angel.io.annotations.RowProcessor;
import com.angel.io.annotations.RowProcessorCommand;
import com.angel.io.exceptions.InvalidRowDataException;

/**
 * @author William
 * @since 17/September/2009.
 *
 */
@RowProcessorCommand
	(
		columnsRow = {
			@ColumnRow( columnName = CategoriaPrincipalAnnotationRowProcessorCommand.NOMBRE_COLUMN, aliases = {"Nombre de Categoria"} ),
			@ColumnRow( columnName = CategoriaPrincipalAnnotationRowProcessorCommand.DESCRIPCION_COLUMN, aliases = {"Descripcion de la Categoria"} ),
			@ColumnRow( columnName = CategoriaPrincipalAnnotationRowProcessorCommand.NOMBRE_SUB_CATEGORIA_COLUMN, aliases = {"Categoria Padre"} )
		}
	)
public class CategoriaPrincipalAnnotationRowProcessorCommand {

	public static final String NOMBRE_COLUMN = "nombre";
	public static final String DESCRIPCION_COLUMN = "descripcion";
	public static final String NOMBRE_SUB_CATEGORIA_COLUMN = "categoriaPadre";
	
	@Inject
	private CategoriaDAO categoriaDAO;
	
	@RowChecker(columnsParameters = {})
    public void checkRowData(String nombre, String descripcion, String categoriaPadre) throws InvalidRowDataException {
		boolean areAllNotEmpty = StringHelper.areAllNotEmpty(nombre, descripcion);
		/*if(!"Ninguna".equals(categoriaPadre)){
			Categoria subCategoria = this.getCategoriaDAO().buscarUnicoONuloPorNombre(nombre);
			if(subCategoria == null){
				throw new NonBusinessException("Sub categoria doesn't exist with name [" + nombre + "].");
			}
		}*/
		if(!areAllNotEmpty){
			throw new InvalidRowDataException("Some row data are NULL - " +
					"nombre: [" + nombre + "] - " +
					"descripcion: [" + descripcion + "].");
		}
    }

	@RowProcessor(columnsParameters = {}, object = Categoria.class, inject = true)
	public Categoria processRow(Categoria categoria, String nombre, String descripcion, String categoriaPadre) {
		if(!"Ninguna".equals(categoriaPadre)){
			Categoria subCategoria = this.getCategoriaDAO().buscarUnicoONuloPorNombre(nombre);
			categoria.addSubCategoria(subCategoria);
		}
        return categoria;
    }

	/**
	 * @return the categoriaDAO
	 */
	public CategoriaDAO getCategoriaDAO() {
		return categoriaDAO;
	}

	/**
	 * @param categoriaDAO the categoriaDAO to set
	 */
	public void setCategoriaDAO(CategoriaDAO categoriaDAO) {
		this.categoriaDAO = categoriaDAO;
	}
}
