/**
 *
 */
package ar.com.angelDurmiente.rowProcessors;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.hibernate.Hibernate;

import ar.com.angelDurmiente.beans.Usuario;
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
			@ColumnRow( columnName = UsuarioAnnotationRowProcessorCommand.NOMBRE_COLUMN, aliases = {"Nombre del Usuario"} ),
			@ColumnRow( columnName = UsuarioAnnotationRowProcessorCommand.APELLIDO_COLUMN, aliases = {"Apellido del Usuario"} ),
			@ColumnRow( columnName = UsuarioAnnotationRowProcessorCommand.NOMBRE_USUARIO_COLUMN, aliases = {"Usuario"} ),
			@ColumnRow( columnName = UsuarioAnnotationRowProcessorCommand.PASSWORD_COLUMN, aliases = {"Password"} ),
			@ColumnRow( columnName = UsuarioAnnotationRowProcessorCommand.EMAIL_COLUMN, aliases = {"Email"} ),
			@ColumnRow( columnName = UsuarioAnnotationRowProcessorCommand.IMAGEN_PERFIL_COLUMN, aliases = {"Imagen de perfil"} ),
			@ColumnRow( columnName = UsuarioAnnotationRowProcessorCommand.FECHA_NACIMIENTO_COLUMN, aliases = {"Fecha de Nacimiento"} )
		}
	)
public class UsuarioAnnotationRowProcessorCommand {

	public static final String NOMBRE_COLUMN = "nombre";
	public static final String APELLIDO_COLUMN = "apellido";
	public static final String NOMBRE_USUARIO_COLUMN = "name";
	public static final String PASSWORD_COLUMN = "password";
	public static final String EMAIL_COLUMN = "email";
	public static final String IMAGEN_PERFIL_COLUMN = "imagenPerfil";
	public static final String FECHA_NACIMIENTO_COLUMN = "fechaNacimiento";


	@Inject
	private UsuarioDAO usuarioDAO;
	
	@RowChecker(columnsParameters = {})
    public void checkRowData(String nombre, String apellido, String nombreUsuario, String password, String email, String imagenPerfil, String fechaNacimiento) throws InvalidRowDataException {
		boolean areAllNotEmpty = StringHelper.areAllNotEmpty(nombre, apellido, nombreUsuario, password, email, imagenPerfil, fechaNacimiento);
		if(!areAllNotEmpty){
			throw new InvalidRowDataException("Some row data are NULL - " +
					"nombre" + nombre + "] - " +
					"apellido: [" + apellido + "] - " +
					"nombreUsuario: [" + nombreUsuario + "] - " +
					"password: [" + password + "] - " +
					"email: [" + email + "] - " +
					"imagenPerfil: [" + imagenPerfil + "] - " +
					"fechaNacimiento: [" + fechaNacimiento + "]."
			);
		}
    }

	@RowProcessor(columnsParameters = {}, object = Usuario.class, inject = true)
	public Usuario processRow(Usuario usuario, String nombre, String apellido, String nombreUsuario, String password, String email, String imagenPerfil, String fechaNacimiento) {
		usuario.setNombre(nombre);
		usuario.setApellido(apellido);
		usuario.setName(nombreUsuario);
		usuario.setEmail(email);
		usuario.setPassword(password);
		InputStream inputStream;
		try {
			DateFormat dateFormat = new SimpleDateFormat("dd/mm/yyyy");
			usuario.setNacimiento(dateFormat.parse(fechaNacimiento));
			if(!"Ninguna".equalsIgnoreCase(imagenPerfil)){
				inputStream = FileHelper.findInputStreamInClasspath(imagenPerfil);
				Blob logo = Hibernate.createBlob(inputStream);
				usuario.setImagen(logo);
			}
		} catch (FileNotFoundException e) {
			throw new NonBusinessException("File not found processing row for persona.", e);
		} catch (IOException e) {
			throw new NonBusinessException("IO error creating blob with imagen processing persona row.", e);
		} catch (ParseException e) {
			throw new NonBusinessException("Error parsing fecha nacimiento [" + fechaNacimiento + "] processing persona row.", e);
		}
        return usuario;
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
}
