/**
 *
 */
package ar.com.angelDurmiente.rowProcessors;

import ar.com.angelDurmiente.beans.Artista;
import ar.com.angelDurmiente.beans.Cancion;
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
			@ColumnRow( columnName = CancionAnnotationRowProcessorCommand.TITULO_COLUMN, aliases = {"Titulo Cancion"} ),
			@ColumnRow( columnName = CancionAnnotationRowProcessorCommand.NOMBRE_ARTISTA_COLUMN, aliases = {"Nombre del Artista"} )
		}
	)
public class CancionAnnotationRowProcessorCommand {

	public static final String TITULO_COLUMN = "titulo";
	public static final String CONTENIDO_COLUMN = "contenido";
	public static final String NOMBRE_USUARIO_COLUMN = "usuario";
	public static final String NOMBRE_ARTISTA_COLUMN = "nombreArtista";

	@Inject
	private ArtistaDAO artistaDAO;
	
	@RowChecker(columnsParameters = {})
    public void checkRowData(String titulo, String nombreArtista) throws InvalidRowDataException {
		boolean areAllNotEmpty = StringHelper.areAllNotEmpty(titulo, nombreArtista);
		if(!areAllNotEmpty){
			throw new InvalidRowDataException("Some row data are NULL - " +
					"titulo" + titulo + "] - " +
					"nombreArtista: [" + nombreArtista + "]."
			);
		}
    }

	@RowProcessor(columnsParameters = {}, object = Cancion.class, inject = true)
	public Cancion processRow(Cancion cancion, String titulo, String nombreArtista) {
		Artista artista = this.getArtistaDAO().buscarUnicoPorNombre(nombreArtista);
		cancion.setTitulo(titulo);
		cancion.setArtista(artista);
        return cancion;
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