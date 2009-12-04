/**
 *
 */
package ar.com.angelDurmiente.rowProcessors;

import ar.com.angelDurmiente.beans.Album;
import ar.com.angelDurmiente.beans.Artista;
import ar.com.angelDurmiente.beans.Cancion;
import ar.com.angelDurmiente.daos.AlbumDAO;
import ar.com.angelDurmiente.daos.ArtistaDAO;
import ar.com.angelDurmiente.daos.CancionDAO;

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
			@ColumnRow( columnName = AlbumCancionAnnotationRowProcessorCommand.TITULO_COLUMN, aliases = {"Titulo Cancion"} ),
			@ColumnRow( columnName = AlbumCancionAnnotationRowProcessorCommand.NOMBRE_ALBUM_COLUMN, aliases = {"Nombre del Album"} ),
			@ColumnRow( columnName = AlbumCancionAnnotationRowProcessorCommand.NOMBRE_ARTISTA_COLUMN, aliases = {"Nombre del Artista"} )
		}
	)
public class AlbumCancionAnnotationRowProcessorCommand {

	//private static final Logger LOGGER = Logger.getLogger(AlbumCancionAnnotationRowProcessorCommand.class);

	public static final String TITULO_COLUMN = "tituloCancion";
	public static final String NOMBRE_ALBUM_COLUMN = "nombreAlbum";
	public static final String NOMBRE_ARTISTA_COLUMN = "nombreArtista";


	@Inject
	private AlbumDAO albumDAO;
	@Inject
	private ArtistaDAO artistaDAO;
	@Inject
	private CancionDAO cancionDAO;
	
	@RowChecker(columnsParameters = {})
    public void checkRowData(String tituloCancion, String nombreAlbum, String nombreArtista) throws InvalidRowDataException {
		boolean areAllNotEmpty = StringHelper.areAllNotEmpty(tituloCancion, nombreAlbum, nombreArtista);
		if(!areAllNotEmpty){
			throw new InvalidRowDataException("Some row data are NULL - " +
					"tituloCancion: [" + tituloCancion + "] - " +
					"nombreAlbum: [" + nombreAlbum + "] - " +
					"nombreArtista: [" + nombreArtista + "]."
			);
		}
		
		
    }

	@RowProcessor(columnsParameters = {}, inject = false)
	public Album processRow(String tituloCancion, String tituloAlbum, String nombreArtista) {
		System.out.println("Processing row with data: " +
					"tituloCancion: [" + tituloCancion + "] - " +
					"nombreAlbum: [" + tituloAlbum + "] - " +
					"nombreArtista: [" + nombreArtista + "].");

		Album album = this.getAlbumDAO().buscarUnicoPorTitulo(tituloAlbum);
		Artista artista = this.getArtistaDAO().buscarUnicoPorNombre(nombreArtista);
		Cancion cancion = this.getCancionDAO().buscarUnicoPorTituloArtistaYAlbum(tituloCancion, artista, album);
		album.agregarCancion(cancion);
        return album;
    }

	/**
	 * @return the albumDAO
	 */
	public AlbumDAO getAlbumDAO() {
		return albumDAO;
	}

	/**
	 * @param albumDAO the albumDAO to set
	 */
	public void setAlbumDAO(AlbumDAO albumDAO) {
		this.albumDAO = albumDAO;
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

	/**
	 * @return the cancionDAO
	 */
	public CancionDAO getCancionDAO() {
		return cancionDAO;
	}

	/**
	 * @param cancionDAO the cancionDAO to set
	 */
	public void setCancionDAO(CancionDAO cancionDAO) {
		this.cancionDAO = cancionDAO;
	}
}
