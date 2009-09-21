/**
 *
 */
package ar.com.gsalazar.rowProcessors;

import ar.com.gsalazar.beans.Categoria;
import ar.com.gsalazar.daos.CategoriaDAO;

import com.angel.architecture.exceptions.NonBusinessException;
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
			@ColumnRow( columnName = SubCategoriasAnnotationRowProcessorCommand.NOMBRE_CATEGORIA_COLUMN, aliases = {"Nombre de Categoria"} ),
			@ColumnRow( columnName = SubCategoriasAnnotationRowProcessorCommand.NOMBRE_SUB_CATEGORIA_COLUMN, aliases = {"Nombre de la Sub Categoria"} )
		}
	)
public class SubCategoriasAnnotationRowProcessorCommand {

	public static final String NOMBRE_CATEGORIA_COLUMN = "nombreCategoria";
	public static final String NOMBRE_SUB_CATEGORIA_COLUMN = "nombreSubCategoria";
	
	@Inject
	private CategoriaDAO categoriaDAO;
	
	@RowChecker(columnsParameters = {})
    public void checkRowData(String nombreCategoria, String nombreSubCategoria) throws InvalidRowDataException {
		boolean areAllNotEmpty = StringHelper.areAllNotEmpty(nombreCategoria, nombreSubCategoria);
		if(!areAllNotEmpty){
			throw new InvalidRowDataException("Some row data are NULL - " +
					"nombreCategoria: [" + nombreCategoria + "] - " +
					"nombreSubCategoria: [" + nombreSubCategoria + "].");
		}
		Categoria categoria = this.getCategoriaDAO().buscarUnicoONuloPorNombre(nombreCategoria);
		if(categoria == null){
			throw new NonBusinessException("Categoria doesn't exist with name [" + nombreCategoria + "].");
		}
		Categoria subCategoria = this.getCategoriaDAO().buscarUnicoONuloPorNombre(nombreSubCategoria);
		if(subCategoria == null){
			throw new NonBusinessException("Categoria doesn't exist with name [" + nombreSubCategoria + "].");
		}

    }

	@RowProcessor(columnsParameters = {})
	public Categoria processRow(String nombreCategoria, String nombreSubCategoria) {
		Categoria categoria = this.getCategoriaDAO().buscarUnicoPorNombre(nombreCategoria);
		Categoria subCategoria = this.getCategoriaDAO().buscarUnicoPorNombre(nombreSubCategoria);
		categoria.addSubCategoria(subCategoria);
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
