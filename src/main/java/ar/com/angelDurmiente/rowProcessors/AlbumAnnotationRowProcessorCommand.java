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
			@ColumnRow( columnName = AlbumAnnotationRowProcessorCommand.TITULO_ALBUM_COLUMN, aliases = {"Titulo del Album"} ),
			@ColumnRow( columnName = AlbumAnnotationRowProcessorCommand.ANIO_ALBUM_COLUMN, aliases = {"Año del Album"}, type = Integer.class ),
			@ColumnRow( columnName = AlbumAnnotationRowProcessorCommand.NOMBRE_ARTISTA_COLUMN, aliases = {"Nombre del Artista"} )
		}
	)
public class AlbumAnnotationRowProcessorCommand {

	public static final String TITULO_ALBUM_COLUMN = "tituloAlbum";
	public static final String ANIO_ALBUM_COLUMN = "year";
	public static final String NOMBRE_ARTISTA_COLUMN = "nombreArtista";

	@Inject
	private AlbumDAO albumDAO;
	@Inject
	private ArtistaDAO artistaDAO;

	@RowChecker(columnsParameters = {})
    public void checkRowData(String tituloAlbum, Integer anio, String nombreArtista) throws InvalidRowDataException {
		boolean areAllNotEmpty = StringHelper.areAllNotEmpty(tituloAlbum, anio.toString(), nombreArtista);
		if(!areAllNotEmpty){
			throw new InvalidRowDataException("Some row data are NULL - " +
					"tituloAlbum" + tituloAlbum + "] - " +
					"anio: [" + anio + "] - " +
					"nombreArtista: [" + nombreArtista + "]."
			);
		}
		Artista artista = this.getArtistaDAO().buscarUnicoONuloPorNombre(nombreArtista);
		if(artista == null){
			throw new InvalidRowDataException("Artista with name [" + nombreArtista + "] doens't EXIST.");
		}
    }

	@RowProcessor(columnsParameters = {}, object = Album.class, inject = true)
	public Album processRow(Album album, String tituloAlbum, Integer anio, String nombreArtista) {
		//Artista artista = this.getArtistaDAO().buscarUnicoPorNombre(nombreArtista);
		album.setNombre(tituloAlbum);
		album.setYear(Integer.valueOf(anio));
		//album.setArtista(artista);
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
}
