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
import ar.com.gsalazar.beans.Proyecto;
import ar.com.gsalazar.beans.TagSearch;
import ar.com.gsalazar.daos.PersonaDAO;
import ar.com.gsalazar.daos.ProyectoDAO;
import ar.com.gsalazar.daos.TagSearchDAO;

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
			@ColumnRow( columnName = ProyectoAnnotationRowProcessorCommand.TITULO_COLUMN, aliases = {"Titulo del Proyecto"} ),
			@ColumnRow( columnName = ProyectoAnnotationRowProcessorCommand.DESCRIPCION_COLUMN, aliases = {"Descripcion del Proyecto"} ),
			@ColumnRow( columnName = ProyectoAnnotationRowProcessorCommand.TAGS_DE_BUSQUEDA_COLUMN, aliases = {"Tags de Busqueda"} ),
			@ColumnRow( columnName = ProyectoAnnotationRowProcessorCommand.DESARROLLADORES_COLUMN, aliases = {"Desarrolladores"} ),
			@ColumnRow( columnName = ProyectoAnnotationRowProcessorCommand.IMAGEN_PROYECTO_COLUMN, aliases = {"Imagen"} )
		}
	)
public class ProyectoAnnotationRowProcessorCommand {

	public static final String TITULO_COLUMN = "titulo";
	public static final String DESCRIPCION_COLUMN = "descripcion";
	public static final String TAGS_DE_BUSQUEDA_COLUMN = "tags";
	public static final String DESARROLLADORES_COLUMN = "desarrolladores";
	public static final String IMAGEN_PROYECTO_COLUMN = "imagen";

	@Inject
	private ProyectoDAO proyectoDAO;
	@Inject
	private PersonaDAO personaDAO;
	@Inject
	private TagSearchDAO tagSearchDAO;
	
	@RowChecker(columnsParameters = {})
    public void checkRowData(String tiulo, String descripcion, String tags, String desarrolladores, String imagen) throws InvalidRowDataException {
		boolean areAllNotEmpty = StringHelper.areAllNotEmpty(tiulo, descripcion, tags, desarrolladores, imagen);
		if(!areAllNotEmpty){
			throw new InvalidRowDataException("Some row data are NULL - " +
					"tiulo: [" + tiulo + "] - " +
					"descripcion: [" + descripcion + "] - " +
					"tags: [" + tags + "] - " +
					"desarrolladores: [" + desarrolladores + "] - " +
					"imagen: [" + imagen + "]."
			);
		}
    }

	@RowProcessor(columnsParameters = {}, object = Proyecto.class, inject = true)
	public Proyecto processRow(Proyecto proyecto, String titulo, String descripcion, String tags, String desarrolladores, String imagen) {
		InputStream inputStream;
		try {
			if(!"Ninguna".equalsIgnoreCase(imagen)){
				inputStream = FileHelper.findInputStreamInClasspath(imagen);
				Blob logo = Hibernate.createBlob(inputStream);
				proyecto.setImagen(logo);
			}
			List<String> tagsNames = StringHelper.split(tags, ",");
			for(String tagName: tagsNames){
				TagSearch tagSearch = this.getTagSearchDAO().buscarUnicoONuloPorLabel(tagName);
				if(tagSearch != null){
					proyecto.addTagBuscable(tagSearch);
				} else {
					proyecto.addTagBuscable(tagName, tagName);
				}
			}
			List<String> desarrolladorNombres = StringHelper.split(desarrolladores, ",");
			for(String nombre: desarrolladorNombres){
				Persona desarrollador = this.getPersonaDAO().buscarUnicoPorNombre(nombre);
				TagSearch tagSearch = this.getTagSearchDAO().buscarUnicoONuloPorLabel(desarrollador.getApellido() + ", " + desarrollador.getNombre());
				if(tagSearch != null){
					proyecto.addDesarrollador(desarrollador);
				} else {
					proyecto.addDesarrolladorSinTagear(desarrollador);
					proyecto.addTagBuscable(tagSearch);
				}
			}
			
		} catch (FileNotFoundException e) {
			throw new NonBusinessException("File not found processing row for proyecto.", e);
		} catch (IOException e) {
			throw new NonBusinessException("IO error creating blob with imagen processing proyecto row.", e);
		}
        return proyecto;
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

	/**
	 * @return the proyectoDAO
	 */
	public ProyectoDAO getProyectoDAO() {
		return proyectoDAO;
	}

	/**
	 * @param proyectoDAO the proyectoDAO to set
	 */
	public void setProyectoDAO(ProyectoDAO proyectoDAO) {
		this.proyectoDAO = proyectoDAO;
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
}
