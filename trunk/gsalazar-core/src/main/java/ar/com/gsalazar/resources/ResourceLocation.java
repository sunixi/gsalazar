/**
 *
 */
package ar.com.gsalazar.resources;


/**
 * @author William
 *
 */
public class ResourceLocation {

	public final static String BASE_RESOURCES_TEST_FILES = "/";

	public final static String BASE_EXCEL_FILES = BASE_RESOURCES_TEST_FILES;
	public final static String BASE_PLAIN_TXT_FILES = BASE_RESOURCES_TEST_FILES;

	public static final String ITEM_CATEGORIAS_EXCEL_FILE = BASE_EXCEL_FILES + "ItemCategorias.xls";
	
	public static final String CATEGORIAS_PRINCIPALES_EXCEL_FILE = BASE_EXCEL_FILES + "CategoriasPrincipales.xls";
	
	public static final String SUB_CATEGORIAS_EXCEL_FILE = BASE_EXCEL_FILES + "SubCategorias.xls";
	
	public static final String CONTACTO_WEB_EXCEL_FILE = BASE_EXCEL_FILES + "WebAmigas.xls";

	public final static Class<ResourceLocation> clazz = ResourceLocation.class;

	private ResourceLocation(){
		super();
	}
}
