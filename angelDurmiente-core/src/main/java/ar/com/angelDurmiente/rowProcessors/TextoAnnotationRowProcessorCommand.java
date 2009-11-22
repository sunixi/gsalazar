/**
 *
 */
package ar.com.angelDurmiente.rowProcessors;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import ar.com.angelDurmiente.beans.Artista;
import ar.com.angelDurmiente.beans.Cancion;
import ar.com.angelDurmiente.beans.Texto;
import ar.com.angelDurmiente.beans.Usuario;
import ar.com.angelDurmiente.daos.ArtistaDAO;
import ar.com.angelDurmiente.daos.CancionDAO;
import ar.com.angelDurmiente.daos.UsuarioDAO;

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
			@ColumnRow( columnName = TextoAnnotationRowProcessorCommand.NOMBRE_ARTISTA_COLUMN, aliases = {"Nombre del Artista"} ),
			@ColumnRow( columnName = TextoAnnotationRowProcessorCommand.NOMBRE_ALBUM_COLUMN, aliases = {"Nombre del Album"} ),
			@ColumnRow( columnName = TextoAnnotationRowProcessorCommand.TITULO_CANCION_COLUMN, aliases = {"Titulo Cancion"} ),
			@ColumnRow( columnName = TextoAnnotationRowProcessorCommand.NOMBRE_USUARIO_COLUMN, aliases = {"Usuario"} ),
			@ColumnRow( columnName = TextoAnnotationRowProcessorCommand.CONTENIDO_COLUMN, aliases = {"Contenido"} )
		}
	)
public class TextoAnnotationRowProcessorCommand {

	public static final String NOMBRE_ARTISTA_COLUMN = "nombreArtista";
	public static final String NOMBRE_ALBUM_COLUMN = "nombreAlbum";
	public static final String TITULO_CANCION_COLUMN = "titulo";
	public static final String NOMBRE_USUARIO_COLUMN = "usuario";
	public static final String CONTENIDO_COLUMN = "contenido";


	@Inject
	private UsuarioDAO usuarioDAO;
	@Inject
	private CancionDAO cancionDAO;
	@Inject
	private ArtistaDAO artistaDAO;
	
	@RowChecker(columnsParameters = {})
    public void checkRowData(String nombreArtista, String nombreAlbum, String tituloCancion, String nombreUsuario, String archivoContenido) throws InvalidRowDataException {
		boolean areAllNotEmpty = StringHelper.areAllNotEmpty(nombreArtista, nombreAlbum, tituloCancion, nombreUsuario, archivoContenido);
		if(!areAllNotEmpty){
			throw new InvalidRowDataException("Some row data are NULL - " +
					"nombreArtista: [" + nombreArtista + "] - " +
					"nombreAlbum: [" + nombreAlbum + "] - " +
					"tituloCancion: [" + tituloCancion + "] - " +
					"nombreUsuario: [" + nombreUsuario + "] - " +
					"archivoContenido: [" + archivoContenido + "]."
			);
		}
    }

	@RowProcessor(columnsParameters = {}, inject = false)
	public Cancion processRow(String nombreArtista, String nombreAlbum, String tituloCancion, String nombreUsuario, String archivoContenido) {
		Artista artista = this.getArtistaDAO().buscarUnicoPorNombre(nombreArtista);		
		Cancion cancion = this.getCancionDAO().buscarUnicoPorArtistaYNombre(artista, tituloCancion);
		Usuario usuario = this.getUsuarioDAO().buscarUnicoPorNombreUsuario(nombreUsuario);
		
		Texto texto = new Texto();
		texto.setRating(0);
		texto.setVerificado(true);
		texto.setVisitas(0);
		texto.setUsuario(usuario);

		InputStream inputStream;
		try {
			inputStream = FileHelper.findInputStreamInClasspath(archivoContenido);
			String content = this.buildContent(inputStream);
			texto.setContenido(content);
		} catch (FileNotFoundException e) {
			throw new NonBusinessException("File not found processing row for persona.", e);
		}
		cancion.agregarTexto(texto);
        return cancion;
    }
	
	private String buildContent(InputStream inputStream){
		StringBuffer stringBuffer = new StringBuffer();
        try {
            byte[] buf = new byte[1024];
            while (inputStream.read(buf) >= 0) {
            	for(byte a: buf){
            		stringBuffer.append((char)a);
            	}
            }
        } catch (IOException e) {
            throw new NonBusinessException(e);
        } finally {
            try {
                if (inputStream != null) {
                	inputStream.close();
                }
            } catch (IOException e) { }
        }
        return stringBuffer.toString();
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
}