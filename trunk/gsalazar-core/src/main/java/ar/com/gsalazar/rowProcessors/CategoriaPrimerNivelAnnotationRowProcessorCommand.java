/**
 *
 */
package ar.com.gsalazar.rowProcessors;

import ar.com.gsalazar.beans.Categoria;

import com.angel.common.helpers.StringHelper;
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
			@ColumnRow( columnName = CategoriaPrimerNivelAnnotationRowProcessorCommand.NOMBRE_MUNICIPALIDAD_COLUMN, aliases = {"Nombre de la Municipalidad"} ),
			@ColumnRow( columnName = CategoriaPrimerNivelAnnotationRowProcessorCommand.NUMERO_COLUMN, aliases = {"Numero del expediente"} ),
			@ColumnRow( columnName = CategoriaPrimerNivelAnnotationRowProcessorCommand.CREADOR_COLUMN, aliases = {"Creador del Expediente"} ),
			@ColumnRow( columnName = CategoriaPrimerNivelAnnotationRowProcessorCommand.NOMBRE_MOTIVO_COLUMN, aliases = {"Nombre del Motivo"} ),
			@ColumnRow( columnName = CategoriaPrimerNivelAnnotationRowProcessorCommand.ALCANCE_COLUMN, aliases = {"Alcance"}, type = Integer.class )
		}
	)
public class CategoriaPrimerNivelAnnotationRowProcessorCommand {

	public static final String NOMBRE_MUNICIPALIDAD_COLUMN = "municipalidad";
	public static final String NUMERO_COLUMN = "numero";
	public static final String CREADOR_COLUMN = "creador";
	public static final String NOMBRE_MOTIVO_COLUMN = "nombreMotivo";
	public static final String ALCANCE_COLUMN = "alcance";

	
	
	@RowChecker(columnsParameters = {})
    public void checkRowData(String nombreMunicipalidad, String numero, String nombreCreador, String nombreMotivo, Integer alcance) throws InvalidRowDataException {
		boolean areAllNotEmpty = StringHelper.areAllNotEmpty(nombreMunicipalidad, numero, nombreCreador, nombreMotivo, alcance.toString());
		
		
		
		if(!areAllNotEmpty){
			throw new InvalidRowDataException("Some row data are NULL - " +
					"nombreMunicipalidad: [" + nombreMunicipalidad + "] - " +
					"numero: [" + numero + "] - " +
					"nombreCreador: [" + nombreCreador + "] - " +
					"nombreMotivo: [" + nombreMotivo + "] - " +
					"alcance: [" + alcance + "].");
		}
    }

	@RowProcessor(columnsParameters = {}, object = Categoria.class, inject = true)
	public Categoria processRow(Categoria categoria, String nombreMunicipalidad, String numero, String nombreCreador, String nombreMotivo, Integer alcance) {
		
        return null;
    }
}
