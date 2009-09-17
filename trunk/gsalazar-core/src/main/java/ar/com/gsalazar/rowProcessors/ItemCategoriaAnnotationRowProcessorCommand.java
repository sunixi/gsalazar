/**
 *
 */
package ar.com.gsalazar.rowProcessors;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;

import org.hibernate.Hibernate;

import ar.com.gsalazar.beans.ItemCategoria;
import ar.com.gsalazar.daos.ItemCategoriaDAO;

import com.angel.architecture.exceptions.NonBusinessException;
import com.angel.common.helpers.FileHelper;
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
			@ColumnRow( columnName = ItemCategoriaAnnotationRowProcessorCommand.NOMBRE_COLUMN, aliases = {"Nombre del Item Categoria"} ),
			@ColumnRow( columnName = ItemCategoriaAnnotationRowProcessorCommand.DESCRIPCION_COLUMN, aliases = {"Descripcion del Item Categoria"} ),
			@ColumnRow( columnName = ItemCategoriaAnnotationRowProcessorCommand.FILE_PATH_COLUMN, aliases = {"Camino Al Archivo"} ),
			@ColumnRow( columnName = ItemCategoriaAnnotationRowProcessorCommand.NOMBRE_ARCHIVO_COLUMN, aliases = {"Nombre del Archivo"} )
		}
	)
public class ItemCategoriaAnnotationRowProcessorCommand {

	public static final String NOMBRE_COLUMN = "nombre";
	public static final String DESCRIPCION_COLUMN = "descripcion";
	public static final String FILE_PATH_COLUMN = "filePath";
	public static final String NOMBRE_ARCHIVO_COLUMN = "nombreArchivo";

	@Inject
	private ItemCategoriaDAO itemCategoriaDAO;
	
	@RowChecker(columnsParameters = {})
    public void checkRowData(String nombre, String descripcion, String filePath, String nombreArchivo) throws InvalidRowDataException {
		boolean areAllNotEmpty = StringHelper.areAllNotEmpty(nombre, descripcion, filePath, nombreArchivo);
		
		if(!areAllNotEmpty){
			throw new InvalidRowDataException("Some row data are NULL - " +
					"nombre: [" + nombre + "] - " +
					"descripcion: [" + descripcion + "] - " +
					"filePath: [" + filePath + "] - " +
					"nombreArchivo: [" + nombreArchivo + "].");
		}
		
		ItemCategoria itemCategoria = this.getItemCategoriaDAO().buscarUnicoONuloPorNombre(nombre);
		if(itemCategoria != null){
			throw new InvalidRowDataException("Item categoria with name [" + nombre + "] exist.");
		}
    }

	@RowProcessor(columnsParameters = {}, object = ItemCategoria.class, inject = true)
	public ItemCategoria processRow(ItemCategoria itemCategoria, String nombre, String descripcion, String filePath, String nombreArchivo) {
		try {
			InputStream inputStream = FileHelper.findInputStreamInClasspath(filePath);
			Blob imagen = Hibernate.createBlob(inputStream);
			itemCategoria.setImagenItemCategoria(nombreArchivo, imagen);
		} catch (FileNotFoundException e) {
			throw new NonBusinessException("File not found [" + filePath + "] processing row item categoria.", e);
		} catch (IOException e) {
			throw new NonBusinessException("File cannot found [" + filePath + "] processing row item categoria.", e);
		}
        return itemCategoria;
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
}
