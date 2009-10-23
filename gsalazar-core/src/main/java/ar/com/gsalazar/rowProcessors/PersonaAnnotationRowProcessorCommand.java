/**
 *
 */
package ar.com.gsalazar.rowProcessors;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.util.List;

import org.hibernate.Hibernate;

import ar.com.gsalazar.beans.Persona;
import ar.com.gsalazar.daos.PersonaDAO;

import com.angel.architecture.daos.TagSearchDAO;
import com.angel.architecture.exceptions.NonBusinessException;
import com.angel.architecture.persistence.beans.TagSearch;
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
			@ColumnRow( columnName = PersonaAnnotationRowProcessorCommand.NOMBRE_COLUMN, aliases = {"Nombre de la Persona"} ),
			@ColumnRow( columnName = PersonaAnnotationRowProcessorCommand.APELLIDO_COLUMN, aliases = {"Apellido de la Persona"} ),
			@ColumnRow( columnName = PersonaAnnotationRowProcessorCommand.EMAIL_COLUMN, aliases = {"Email"} ),
			@ColumnRow( columnName = PersonaAnnotationRowProcessorCommand.IMAGEN_PERFIL_COLUMN, aliases = {"Imagen de perfil"} ),
			@ColumnRow( columnName = PersonaAnnotationRowProcessorCommand.TAGS_BUSQUEDA_COLUMN, aliases = {"Tags de Busqueda"} )
		}
	)
public class PersonaAnnotationRowProcessorCommand {

	public static final String NOMBRE_COLUMN = "nombre";
	public static final String APELLIDO_COLUMN = "apellido";
	public static final String EMAIL_COLUMN = "email";
	public static final String IMAGEN_PERFIL_COLUMN = "imagen";
	public static final String TAGS_BUSQUEDA_COLUMN = "tagsBuscables";

	@Inject
	private PersonaDAO personaDAO;
	@Inject
	private TagSearchDAO tagSearchDAO;
	
	@RowChecker(columnsParameters = {})
    public void checkRowData(String nombre, String apellido, String email, String imagenPerfil, String tags) throws InvalidRowDataException {
		boolean areAllNotEmpty = StringHelper.areAllNotEmpty(nombre, apellido, email, imagenPerfil);
		if(!areAllNotEmpty){
			throw new InvalidRowDataException("Some row data are NULL - " +
					"nombre" + nombre + "] - " +
					"apellido: [" + apellido + "] - " +
					"email: [" + email + "] - " +
					"imagenPerfil: [" + imagenPerfil + "]." 
			);
		}
    }

	@RowProcessor(columnsParameters = {}, object = Persona.class, inject = true)
	public Persona processRow(Persona persona, String nombre, String apellido, String email, String imagenPerfil, String tags) {
		InputStream inputStream;
		String descripcion = nombre + ", " + apellido;
		persona.setDescripcion(descripcion);
		persona.setTitulo(descripcion);
		try {
			if(!"Ninguna".equalsIgnoreCase(imagenPerfil)){
				inputStream = FileHelper.findInputStreamInClasspath(imagenPerfil);
				Blob logo = Hibernate.createBlob(inputStream);
				persona.setImagen(logo);
			}
			List<String> tagsNames = StringHelper.split(tags, ",");
			for(String tagName: tagsNames){
				TagSearch tagSearch = this.getTagSearchDAO().buscarUnicoONuloPorLabel(tagName);
				if(tagSearch != null){
					persona.addTagBuscable(tagSearch);
				} else {
					persona.addTagBuscable(tagName, tagName);
				}
			}
		} catch (FileNotFoundException e) {
			throw new NonBusinessException("File not found processing row for proyecto.", e);
		} catch (IOException e) {
			throw new NonBusinessException("IO error creating blob with imagen processing proyecto row.", e);
		}
        return persona;
    }

	/**
	 * @return the personaDAO
	 */
	public PersonaDAO getPersonaDAO() {
		return personaDAO;
	}

	/**
	 * @param personaDAO the personaDAO to set
	 */
	public void setPersonaDAO(PersonaDAO personaDAO) {
		this.personaDAO = personaDAO;
	}

	/**
	 * @return the tagSearchDAO
	 */
	public TagSearchDAO getTagSearchDAO() {
		return tagSearchDAO;
	}

	/**
	 * @param tagSearchDAO the tagSearchDAO to set
	 */
	public void setTagSearchDAO(TagSearchDAO tagSearchDAO) {
		this.tagSearchDAO = tagSearchDAO;
	}
}