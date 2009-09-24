/**
 *
 */
package ar.com.gsalazar.rowProcessors;

import ar.com.gsalazar.beans.Articulo;
import ar.com.gsalazar.beans.ArticuloSearcher;
import ar.com.gsalazar.beans.TagSearch;
import ar.com.gsalazar.daos.ArticuloDAO;
import ar.com.gsalazar.daos.TagSearchDAO;

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
			@ColumnRow( columnName = ArticuloSearcherAnnotationRowProcessorCommand.TITULO_ARTICULO_COLUMN, aliases = {"Titulo del Articulo"} ),
			@ColumnRow( columnName = ArticuloSearcherAnnotationRowProcessorCommand.TAG_DE_BUSQUEDA_COLUMN, aliases = {"Tag de Busqueda"} )
		}
	)
public class ArticuloSearcherAnnotationRowProcessorCommand {

	public static final String TITULO_ARTICULO_COLUMN = "titulo";
	public static final String TAG_DE_BUSQUEDA_COLUMN = "tag";

	@Inject
	private ArticuloDAO articuloDAO;
	@Inject
	private TagSearchDAO tagSearchDAO;
	
	@RowChecker(columnsParameters = {})
    public void checkRowData(String tiulo, String tag) throws InvalidRowDataException {
		boolean areAllNotEmpty = StringHelper.areAllNotEmpty(tiulo, tag);
		if(!areAllNotEmpty){
			throw new InvalidRowDataException("Some row data are NULL - " +
					"tiulo: [" + tiulo + "] - " +
					"tag: [" + tag + "] - "		
			);
		}
    }

	@RowProcessor(columnsParameters = {}, object = ArticuloSearcher.class, inject = false)
	public ArticuloSearcher processRow(ArticuloSearcher articuloSearcher, String tiulo, String tag) {
		Articulo articulo = this.getArticuloDAO().buscarUnicoPorTitulo(tiulo);
		TagSearch tagSearch = this.getTagSearchDAO().buscarUnicoPorLabel(tag);
		articuloSearcher.setArticulo(articulo);
		articuloSearcher.setTagSearch(tagSearch);		
        return articuloSearcher;
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
