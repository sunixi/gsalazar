/**
 *
 */
package ar.com.angelDurmiente.rowProcessors;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import ar.com.angelDurmiente.beans.Cancion;
import ar.com.angelDurmiente.beans.Usuario;
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
			@ColumnRow( columnName = CancionAnnotationRowProcessorCommand.TITULO_COLUMN, aliases = {"Titulo Cancion"} ),
			@ColumnRow( columnName = CancionAnnotationRowProcessorCommand.CONTENIDO_COLUMN, aliases = {"Contenido"} ),
			@ColumnRow( columnName = CancionAnnotationRowProcessorCommand.NOMBRE_USUARIO_COLUMN, aliases = {"Usuario"} )
		}
	)
public class CancionAnnotationRowProcessorCommand {

	public static final String TITULO_COLUMN = "titulo";
	public static final String CONTENIDO_COLUMN = "contenido";
	public static final String NOMBRE_USUARIO_COLUMN = "usuario";


	@Inject
	private UsuarioDAO usuarioDAO;
	@Inject
	private CancionDAO cancionDAO;
	
	@RowChecker(columnsParameters = {})
    public void checkRowData(String titulo, String contenido, String nombreUsuario) throws InvalidRowDataException {
		boolean areAllNotEmpty = StringHelper.areAllNotEmpty(titulo, contenido, nombreUsuario);
		if(!areAllNotEmpty){
			throw new InvalidRowDataException("Some row data are NULL - " +
					"titulo" + titulo + "] - " +
					"contenido: [" + contenido + "] - " +
					"nombreUsuario: [" + nombreUsuario + "]."
			);
		}
    }

	@RowProcessor(columnsParameters = {}, object = Cancion.class, inject = true)
	public Cancion processRow(Cancion cancion, String titulo, String contenido, String nombreUsuario) {
		cancion.setTitulo(titulo);
		cancion.setRating(0);
		cancion.setVerificado(true);
		cancion.setVisitas(0);

		Usuario usuario = this.getUsuarioDAO().buscarUnicoPorNombreUsuario(nombreUsuario);
		cancion.setUsuario(usuario);
		InputStream inputStream;
		try {
			inputStream = FileHelper.findInputStreamInClasspath(contenido);
			String content = this.buildContent(inputStream);
			cancion.setContenido(content);
		} catch (FileNotFoundException e) {
			throw new NonBusinessException("File not found processing row for persona.", e);
		}
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
}
