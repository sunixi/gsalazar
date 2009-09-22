/**
 *
 */
package ar.com.gsalazar.rowProcessors;

import ar.com.gsalazar.beans.TagSearch;
import ar.com.gsalazar.daos.TagSearchDAO;

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
			@ColumnRow( columnName = TagSearchAnnotationRowProcessorCommand.LABEL_COLUMN, aliases = {"Nombre del Tag"} ),
			@ColumnRow( columnName = TagSearchAnnotationRowProcessorCommand.DESCRIPCION_COLUMN, aliases = {"Descripcion del Tag"} ),
			@ColumnRow( columnName = TagSearchAnnotationRowProcessorCommand.OCURRENCIAS_COLUMN, aliases = {"Ocurrencias del Tag"} , type = Long.class)
		}
	)
public class TagSearchAnnotationRowProcessorCommand {

	public static final String LABEL_COLUMN = "label";
	public static final String DESCRIPCION_COLUMN = "description";
	public static final String OCURRENCIAS_COLUMN = "occurrences";
	
	@Inject
	private TagSearchDAO tagSearchDAO;
	
	@RowChecker(columnsParameters = {})
    public void checkRowData(String label, String descripcion, Long ocurrencias) throws InvalidRowDataException {
		boolean areAllNotEmpty = StringHelper.areAllNotEmpty(label, descripcion, ocurrencias.toString());
		if(!areAllNotEmpty){
			throw new InvalidRowDataException("Some row data are NULL - " +
					"label: [" + label + "] - " +
					"descripcion: [" + descripcion + "] - " +
					"ocurrencias: [" + ocurrencias + "] - "		
			);
		}
		TagSearch tagSearch = this.getTagSearchDAO().buscarUnicoONuloPorLabel(label);
		if(tagSearch != null){
			throw new NonBusinessException("Tag de busqueda ya existe con el nombre [" + label + "].");
		}
    }

	@RowProcessor(columnsParameters = {}, object = TagSearch.class, inject = true)
	public TagSearch processRow(TagSearch tagSearch, String label, String descripcion, Long ocurrencias) {
        return tagSearch;
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
