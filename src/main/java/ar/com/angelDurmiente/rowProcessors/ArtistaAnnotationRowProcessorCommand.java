/**
 *
 */
package ar.com.angelDurmiente.rowProcessors;

import ar.com.angelDurmiente.beans.Artista;
import ar.com.angelDurmiente.daos.ArtistaDAO;

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
			@ColumnRow( columnName = ArtistaAnnotationRowProcessorCommand.NOMBRE_ARTISTA_COLUMN, aliases = {"Nombre del Artista"} )
		}
	)
public class ArtistaAnnotationRowProcessorCommand {

	public static final String NOMBRE_ARTISTA_COLUMN = "nombre";

	@Inject
	private ArtistaDAO artistaDAO;

	@RowChecker(columnsParameters = {})
    public void checkRowData(String nombreArtista) throws InvalidRowDataException {
		boolean areAllNotEmpty = StringHelper.areAllNotEmpty(nombreArtista);
		if(!areAllNotEmpty){
			throw new InvalidRowDataException("Some row data are NULL - " +
					"nombreArtista" + nombreArtista + "]."
			);
		}
    }

	@RowProcessor(columnsParameters = {}, object = Artista.class, inject = true)
	public Artista processRow(Artista artista, String nombre) {
		artista.setNombre(nombre);
        return artista;
    }

	/**
	 * @return the artistaDAO
	 */
	public ArtistaDAO getArtistaDAO() {
		return artistaDAO;
	}

	/**
	 * @param artistaDAO the artistaDAO to set
	 */
	public void setArtistaDAO(ArtistaDAO artistaDAO) {
		this.artistaDAO = artistaDAO;
	}
}
