/**
 *
 */
package ar.com.gsalazar.rowProcessors;

import ar.com.gsalazar.beans.Categoria;
import ar.com.gsalazar.beans.ItemCategoria;
import ar.com.gsalazar.daos.CategoriaDAO;
import ar.com.gsalazar.daos.ItemCategoriaDAO;

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
			@ColumnRow( columnName = ItemCategoriaCategoriaAnnotationRowProcessorCommand.NOMBRE_CATEGORIA_COLUMN, aliases = {"Nombre de la Categoria"} ),
			@ColumnRow( columnName = ItemCategoriaCategoriaAnnotationRowProcessorCommand.NOMBRE_COLUMN, aliases = {"Nombre del Item Categoria"} ),
			@ColumnRow( columnName = ItemCategoriaCategoriaAnnotationRowProcessorCommand.DESCRIPCION_COLUMN, aliases = {"Descripcion del Item Categoria"} ),
			@ColumnRow( columnName = ItemCategoriaCategoriaAnnotationRowProcessorCommand.FILE_PATH_COLUMN, aliases = {"Camino Al Archivo"} ),
			@ColumnRow( columnName = ItemCategoriaCategoriaAnnotationRowProcessorCommand.NOMBRE_ARCHIVO_COLUMN, aliases = {"Nombre del Archivo"} ),
			@ColumnRow( columnName = ItemCategoriaCategoriaAnnotationRowProcessorCommand.FECHA_COMIENZO_COLUMN, aliases = {"Fecha de Comienzo"}),
			@ColumnRow( columnName = ItemCategoriaCategoriaAnnotationRowProcessorCommand.FECHA_FIN_COLUMN, aliases = {"Fecha de Fin"} )
		}
	)
public class ItemCategoriaCategoriaAnnotationRowProcessorCommand {

	public static final String NOMBRE_CATEGORIA_COLUMN = "categoria";
	public static final String NOMBRE_COLUMN = "nombre";
	public static final String DESCRIPCION_COLUMN = "descripcion";
	public static final String FILE_PATH_COLUMN = "filePath";
	public static final String NOMBRE_ARCHIVO_COLUMN = "nombreArchivo";
	public static final String FECHA_COMIENZO_COLUMN = "comienzo";
	public static final String FECHA_FIN_COLUMN = "fin";

	@Inject
	private ItemCategoriaDAO itemCategoriaDAO;
	@Inject
	private CategoriaDAO categoriaDAO;
	
	@RowChecker(columnsParameters = {})
    public void checkRowData(String nombreCategoria, String nombre, String descripcion, String filePath, String nombreArchivo, String comienzo, String fin) throws InvalidRowDataException {
		boolean areAllNotEmpty = StringHelper.areAllNotEmpty(nombre, descripcion, filePath, nombreArchivo);
		
		if(!areAllNotEmpty){
			throw new InvalidRowDataException("Some row data are NULL - " +
					"nombre: [" + nombre + "] - " +
					"descripcion: [" + descripcion + "] - " +
					"filePath: [" + filePath + "] - " +
					"nombreArchivo: [" + nombreArchivo + "].");
		}
		
		ItemCategoria itemCategoria = this.getItemCategoriaDAO().buscarUnicoPorNombre(nombre);
		if(itemCategoria == null){
			throw new InvalidRowDataException("Item categoria with name [" + nombre + "] NOT exist.");
		}
    }

	@RowProcessor(columnsParameters = {}, inject = false)
	public Categoria processRow(String nombreCategoria, String nombre, String descripcion, String filePath, String nombreArchivo, String comienzo, String fin) {
		Categoria categoria = this.getCategoriaDAO().buscarUnicoPorNombre(nombreCategoria);
		ItemCategoria itemCategoria = this.getItemCategoriaDAO().buscarUnicoPorNombre(nombre);
		categoria.addItemCategoria(itemCategoria);
        return categoria;
    }

	/**
	 * @return the itemCategoriaDAO
	 */
	public ItemCategoriaDAO getItemCategoriaDAO() {
		return itemCategoriaDAO;
	}

	/**
	 * @param itemCategoriaDAO the itemCategoriaDAO to set
	 */
	public void setItemCategoriaDAO(ItemCategoriaDAO itemCategoriaDAO) {
		this.itemCategoriaDAO = itemCategoriaDAO;
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
