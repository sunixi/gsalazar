/**
 *
 */
package ar.com.angelDurmiente.rowProcessors;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;

import org.hibernate.Hibernate;

import ar.com.angelDurmiente.beans.Acorde;
import ar.com.angelDurmiente.services.AcordeService;

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
			@ColumnRow( columnName = AcordeAnnotationRowProcessorCommand.NOMBRE_ACORDE_COLUMN, aliases = {"Nombre del Acorde"} ),
			//@ColumnRow( columnName = AcordeAnnotationRowProcessorCommand.IMAGEN_ACORDE_COLUMN, aliases = {"Imagen del Acorde"} ),
			@ColumnRow( columnName = AcordeAnnotationRowProcessorCommand.OPCION_ACORDE_COLUMN, aliases = {"Opcion de Acorde"}, type = Integer.class )
		}
	)
public class AcordeAnnotationRowProcessorCommand {

	public static final String NOMBRE_ACORDE_COLUMN = "nombre";
	public static final String IMAGEN_ACORDE_COLUMN = "imagenAcorde";
	public static final String OPCION_ACORDE_COLUMN = "opcion";
	public final static String BASE_ACORDES_DIRECTORY = "/initialData/acordes/";

	@Inject
	private AcordeService acordeService;
	
	@RowChecker(columnsParameters = {})
    public void checkRowData(String nombreAcorde, Integer opcion) throws InvalidRowDataException {
		boolean areAllNotEmpty = StringHelper.areAllNotEmpty(nombreAcorde, opcion.toString());
		if(!areAllNotEmpty){
			throw new InvalidRowDataException("Some row data are NULL - " +
					"nombreAcorde: [" + nombreAcorde + "] - " +
					"opcion: [" + opcion.toString() + "]."
			);
		}
		
		Acorde acorde = this.getAcordeService().buscarUnicoONuloPorNombreYOpcion(nombreAcorde, Integer.valueOf(opcion));
		if(acorde != null){
			throw new InvalidRowDataException("Ya existe el acorde [" + nombreAcorde + "] con la " +
					"opcion [" + opcion + "]."
			);
		}
    }

	@RowProcessor(columnsParameters = {}, object = Acorde.class, inject = false)
	public Acorde processRow(Acorde acorde, String nombreAcorde, Integer opcion) {
		String nombreAcordeSimple = nombreAcorde.replaceAll("#", "H").replace("-", "_");
		String archivoImagen = BASE_ACORDES_DIRECTORY + nombreAcordeSimple + "/" + nombreAcorde + "_" + opcion.toString() + ".jpg";
		acorde.setNombre(nombreAcorde);
		acorde.setOpcion(opcion);

		InputStream inputStream;
		try {
			inputStream = FileHelper.findInputStreamInClasspath(archivoImagen);
			Blob imagenAcorde = Hibernate.createBlob(inputStream);
			acorde.setImagen(imagenAcorde);
		} catch (FileNotFoundException e) {
			throw new NonBusinessException("File not found processing row for persona.", e);
		} catch (IOException e) {
			throw new NonBusinessException("IO Error during creating blob for acorde image [" + nombreAcordeSimple + " - " + nombreAcorde + "]", e);
		}
        return acorde;
    }

	/**
	 * @return the acordeService
	 */
	public AcordeService getAcordeService() {
		return acordeService;
	}

	/**
	 * @param acordeService the acordeService to set
	 */
	public void setAcordeService(AcordeService acordeService) {
		this.acordeService = acordeService;
	}
}