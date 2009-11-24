/**
 *
 */
package ar.com.angelDurmiente.rowProcessors;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import ar.com.angelDurmiente.beans.Album;
import ar.com.angelDurmiente.beans.Artista;
import ar.com.angelDurmiente.beans.Cancion;
import ar.com.angelDurmiente.beans.Dedicatoria;
import ar.com.angelDurmiente.beans.Texto;
import ar.com.angelDurmiente.beans.Usuario;
import ar.com.angelDurmiente.daos.AlbumDAO;
import ar.com.angelDurmiente.daos.ArtistaDAO;
import ar.com.angelDurmiente.daos.CancionDAO;
import ar.com.angelDurmiente.daos.TextoDAO;
import ar.com.angelDurmiente.daos.UsuarioDAO;
import ar.com.angelDurmiente.helpers.TextoHelper;

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
			@ColumnRow( columnName = DedicatoriaAnnotationRowProcessorCommand.NOMBRE_ARTISTA_COLUMN, aliases = {"Nombre del Artista"} ),
			@ColumnRow( columnName = DedicatoriaAnnotationRowProcessorCommand.NOMBRE_ALBUM_COLUMN, aliases = {"Nombre del Album"} ),
			@ColumnRow( columnName = DedicatoriaAnnotationRowProcessorCommand.TITULO_CANCION_COLUMN, aliases = {"Titulo Cancion"} ),
			@ColumnRow( columnName = DedicatoriaAnnotationRowProcessorCommand.NOMBRE_USUARIO_COLUMN, aliases = {"Usuario"} ),
			@ColumnRow( columnName = DedicatoriaAnnotationRowProcessorCommand.NOMBRE_USUARIO_DEDICATARIO_COLUMN, aliases = {"Nombre del Dedicatario"} ),
			@ColumnRow( columnName = DedicatoriaAnnotationRowProcessorCommand.NOMBRE_USUARIO_DEDICADOR_COLUMN, aliases = {"Nombre del Dedicador"} ),
			@ColumnRow( columnName = DedicatoriaAnnotationRowProcessorCommand.FECHA_DESDE_COLUMN, aliases = {"Fecha Desde"} ),
			@ColumnRow( columnName = DedicatoriaAnnotationRowProcessorCommand.FECHA_HASTA_COLUMN, aliases = {"Fecha Hasta"} )
		}
	)
public class DedicatoriaAnnotationRowProcessorCommand {

	public static final String NOMBRE_ARTISTA_COLUMN = "nombreArtista";
	public static final String NOMBRE_ALBUM_COLUMN = "nombreAlbum";
	public static final String TITULO_CANCION_COLUMN = "tituloCancion";
	public static final String NOMBRE_USUARIO_COLUMN = "usuario";
	public static final String NOMBRE_USUARIO_DEDICATARIO_COLUMN = "nombreUsuarioDedicatario";
	public static final String NOMBRE_USUARIO_DEDICADOR_COLUMN = "nombreUsuarioDedicador";
	public static final String FECHA_DESDE_COLUMN = "fechaDesde";
	public static final String FECHA_HASTA_COLUMN = "fechaHasta";

	@Inject
	private UsuarioDAO usuarioDAO;
	@Inject
	private CancionDAO cancionDAO;
	@Inject
	private ArtistaDAO artistaDAO;
	@Inject
	private TextoDAO textoDAO;
	@Inject
	private AlbumDAO albumDAO;
	
	
	@RowChecker(columnsParameters = {})
    public void checkRowData(String nombreArtista, String nombreAlbum, String tituloCancion, String nombreUsuario, 
    		String nombreUsuarioDedicatario, String nombreUsuarioDedicador, String fechaDesde, String fechaHasta) throws InvalidRowDataException {
		boolean areAllNotEmpty = StringHelper.areAllNotEmpty(nombreArtista, nombreAlbum, tituloCancion, nombreUsuario, 
	    		nombreUsuarioDedicatario, nombreUsuarioDedicador, fechaDesde, fechaHasta);
		if(!areAllNotEmpty){
			throw new InvalidRowDataException("Some row data are NULL - " +
					"nombreArtista: [" + nombreArtista + "] - " +
					"nombreAlbum: [" + nombreAlbum + "] - " +
					"tituloCancion: [" + tituloCancion + "] - " +
					"nombreUsuario: [" + nombreUsuario + "] - " +
					"nombreUsuarioDedicatario: [" + nombreUsuarioDedicatario + "] - " +
					"nombreUsuarioDedicador: [" + nombreUsuarioDedicador + "] - " +
					"fechaDesde: [" + fechaDesde + "] - " +
					"fechaHasta: [" + fechaHasta + "]."
			);
		}
    }

	@RowProcessor(columnsParameters = {}, inject = false)
	public Dedicatoria processRow(String nombreArtista, String nombreAlbum, String tituloCancion, String nombreUsuario, 
    		String nombreUsuarioDedicatario, String nombreUsuarioDedicador, String fechaDesde, String fechaHasta) {
		
		Usuario dedicatario = this.getUsuarioDAO().buscarUnicoPorNombreUsuario(nombreUsuarioDedicatario);
		Usuario dedicador = this.getUsuarioDAO().buscarUnicoPorNombreUsuario(nombreUsuarioDedicatario);
		Usuario usuarioTexto = this.getUsuarioDAO().buscarUnicoPorNombreUsuario(nombreUsuario);
		Artista artista = this.getArtistaDAO().buscarUnicoPorNombre(nombreArtista);
		Album album = this.getAlbumDAO().buscarUnicoPorTitulo(nombreAlbum);
		Cancion cancion = this.getCancionDAO().buscarUnicoPorTituloArtistaYAlbum(tituloCancion, artista, album);
		
		String tituloTexto = TextoHelper.crearTituloPara(artista, cancion, usuarioTexto);
		Texto texto = this.getTextoDAO().buscarUnicoPorTitulo(tituloTexto);
		Dedicatoria dedicatoria = new Dedicatoria();
		try {
			DateFormat dateFormat = new SimpleDateFormat("dd/mm/yyyy");
			dedicatoria.setDesde(dateFormat.parse(fechaDesde));
			dedicatoria.setHasta(dateFormat.parse(fechaHasta));
			dedicatoria.setDedicador(dedicador);
			dedicatoria.setDedicatario(dedicatario);
			dedicatoria.setTexto(texto);
		} catch (ParseException e) {
			throw new NonBusinessException("Error parsing fecha desde o fecha hasta [" + fechaDesde + " - " + fechaHasta + "] processing dedicatoria row.", e);
		}
        return dedicatoria;
    }

	/**
	 * @return the usuarioDAO
	 */
	public UsuarioDAO getUsuarioDAO() {
		return usuarioDAO;
	}

	/**
	 * @param usuarioDAO the usuarioDAO to set
	 */
	public void setUsuarioDAO(UsuarioDAO usuarioDAO) {
		this.usuarioDAO = usuarioDAO;
	}

	/**
	 * @return the textoDAO
	 */
	public TextoDAO getTextoDAO() {
		return textoDAO;
	}

	/**
	 * @param textoDAO the textoDAO to set
	 */
	public void setTextoDAO(TextoDAO textoDAO) {
		this.textoDAO = textoDAO;
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
}
