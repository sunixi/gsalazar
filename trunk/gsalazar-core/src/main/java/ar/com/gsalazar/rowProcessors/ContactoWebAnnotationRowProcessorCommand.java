/**
 *
 */
package ar.com.gsalazar.rowProcessors;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;

import org.hibernate.Hibernate;

import ar.com.gsalazar.beans.ContactoWeb;
import ar.com.gsalazar.daos.CategoriaDAO;

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
			@ColumnRow( columnName = ContactoWebAnnotationRowProcessorCommand.NOMBRE_COLUMN, aliases = {"Nombre de la Web"} ),
			@ColumnRow( columnName = ContactoWebAnnotationRowProcessorCommand.DESCRIPCION_COLUMN, aliases = {"Descripcion de la Web"} ),
			@ColumnRow( columnName = ContactoWebAnnotationRowProcessorCommand.LINK_COLUMN, aliases = {"Enlance a Sitio"} ),
			@ColumnRow( columnName = ContactoWebAnnotationRowProcessorCommand.LOGO_SITIO_COLUMN, aliases = {"Logo del Sitio"} )
		}
	)
public class ContactoWebAnnotationRowProcessorCommand {

	public static final String NOMBRE_COLUMN = "nombre";
	public static final String DESCRIPCION_COLUMN = "descripcion";
	public static final String LINK_COLUMN = "link";
	public static final String LOGO_SITIO_COLUMN = "logo";
	
	@Inject
	private CategoriaDAO categoriaDAO;
	
	@RowChecker(columnsParameters = {})
    public void checkRowData(String nombre, String descripcion, String link, String logo) throws InvalidRowDataException {
		boolean areAllNotEmpty = StringHelper.areAllNotEmpty(nombre, descripcion, link, logo);
		if(!areAllNotEmpty){
			throw new InvalidRowDataException("Some row data are NULL - " +
					"nombre: [" + nombre + "] - " +
					"descripcion: [" + descripcion + "] - " +
					"link: [" + link + "] - " +
					"logo: [" + logo + "]."		
			);
		}
    }

	@RowProcessor(columnsParameters = {}, object = ContactoWeb.class, inject = true)
	public ContactoWeb processRow(ContactoWeb contactoWeb, String nombre, String descripcion, String link, String logo) {
		InputStream inputStream;
		try {
			inputStream = FileHelper.findInputStreamInClasspath(logo);
			Blob imagenLogo = Hibernate.createBlob(inputStream);
			contactoWeb.setLogo(imagenLogo);
		} catch (FileNotFoundException e) {
			throw new NonBusinessException("File not found processing row for ContactoWeb.", e);
		} catch (IOException e) {
			throw new NonBusinessException("IO Error processing row for ContactoWeb.", e);
		}
        return contactoWeb;
    }

	/**
	 * @return the categoriaDAO
	 */
	public CategoriaDAO getCategoriaDAO() {
		return categoriaDAO;
	}

	/**
	 * @param categoriaDAO the categoriaDAO to set
	 */
	public void setCategoriaDAO(CategoriaDAO categoriaDAO) {
		this.categoriaDAO = categoriaDAO;
	}
}
