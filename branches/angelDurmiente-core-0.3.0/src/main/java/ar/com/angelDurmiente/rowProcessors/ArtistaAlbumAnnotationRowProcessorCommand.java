/**
 *
 */
package ar.com.angelDurmiente.rowProcessors;

import ar.com.angelDurmiente.beans.Album;
import ar.com.angelDurmiente.beans.Artista;
import ar.com.angelDurmiente.daos.AlbumDAO;
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
			@ColumnRow( columnName = ArtistaAlbumAnnotationRowProcessorCommand.NOMBRE_ARTISTA_COLUMN, aliases = {"Nombre del Artista"} ),
			@ColumnRow( columnName = ArtistaAlbumAnnotationRowProcessorCommand.TITULO_ALBUM_COLUMN, aliases = {"Titulo del Album"} )
		}
	)
public class ArtistaAlbumAnnotationRowProcessorCommand {

	public static final String NOMBRE_ARTISTA_COLUMN = "nombreArtista";
	public static final String TITULO_ALBUM_COLUMN = "tituloAlbum";


	@Inject
	private AlbumDAO albumDAO;
	@Inject
	private ArtistaDAO artistaDAO;

	@RowChecker(columnsParameters = {})
    public void checkRowData(String nombreArtista, String tituloAlbum) throws InvalidRowDataException {
		boolean areAllNotEmpty = StringHelper.areAllNotEmpty(nombreArtista, tituloAlbum);
		if(!areAllNotEmpty){
			throw new InvalidRowDataException("Some row data are NULL - " +
					"nombreArtista: [" + nombreArtista + "] - " +
					"tituloAlbum" + tituloAlbum + "]."
			);
		}
    }

	@RowProcessor(columnsParameters = {}, inject = false)
	public Artista processRow(String nombreArtista, String tituloAlbum) {
		Artista artista = this.getArtistaDAO().buscarUnicoPorNombre(nombreArtista);
		Album album = this.getAlbumDAO().buscarUnicoPorTitulo(tituloAlbum);
		artista.agregarAlbum(album);
        return artista;
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
}
