/**
 *
 */
package ar.com.gsalazar.rowProcessors;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import ar.com.gsalazar.beans.Articulo;
import ar.com.gsalazar.daos.ArticuloDAO;

import com.angel.architecture.daos.TagSearchDAO;
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
			@ColumnRow( columnName = ArticuloAnnotationRowProcessorCommand.TITULO_COLUMN, aliases = {"Titulo del Articulo"} ),
			@ColumnRow( columnName = ArticuloAnnotationRowProcessorCommand.DESCRIPCION_COLUMN, aliases = {"Descripcion del Articulo"} ),
			@ColumnRow( columnName = ArticuloAnnotationRowProcessorCommand.TAGS_DE_BUSQUEDA_COLUMN, aliases = {"Tags de Busqueda"} ),
			@ColumnRow( columnName = ArticuloAnnotationRowProcessorCommand.ARCHIVO_CONTENIDO_COLUMN, aliases = {"Archivo de Contenido"} )
		}
	)
public class ArticuloAnnotationRowProcessorCommand {

	public static final String TITULO_COLUMN = "titulo";
	public static final String DESCRIPCION_COLUMN = "descripcion";
	public static final String TAGS_DE_BUSQUEDA_COLUMN = "tags";
	public static final String ARCHIVO_CONTENIDO_COLUMN = "archivoContenido";
	/*
	private List<TagSearch> tagsBuscables;
	private List<Fuente> fuentes;
	*/
	@Inject
	private ArticuloDAO articuloDAO;
	@Inject
	private TagSearchDAO tagSearchDAO;
	
	@RowChecker(columnsParameters = {})
    public void checkRowData(String tiulo, String descripcion, String tags, String archivoContenido) throws InvalidRowDataException {
		boolean areAllNotEmpty = StringHelper.areAllNotEmpty(tiulo, descripcion, tags, archivoContenido);
		if(!areAllNotEmpty){
			throw new InvalidRowDataException("Some row data are NULL - " +
					"tiulo: [" + tiulo + "] - " +
					"descripcion: [" + descripcion + "] - " +
					"tags: [" + tags + "] - " +
					"archivoContenido: [" + archivoContenido + "]."		
			);
		}
    }

	@RowProcessor(columnsParameters = {}, object = Articulo.class, inject = true)
	public Articulo processRow(Articulo articulo, String tiulo, String descripcion, String tags, String archivoContenido) {
		InputStream inputStream;
		try {
			inputStream = FileHelper.findInputStreamInClasspath(archivoContenido);
			String contenido = this.buildContent((FileInputStream) inputStream);
			articulo.setContenido(contenido);
		} catch (FileNotFoundException e) {
			throw new NonBusinessException("File not found processing row for articulo.", e);
		}
        return articulo;
    }
	
	private String buildContent(FileInputStream inputStream){
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
	 * @return the articuloDAO
	 */
	public ArticuloDAO getArticuloDAO() {
		return articuloDAO;
	}

	/**
	 * @param articuloDAO the articuloDAO to set
	 */
	public void setArticuloDAO(ArticuloDAO articuloDAO) {
		this.articuloDAO = articuloDAO;
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
