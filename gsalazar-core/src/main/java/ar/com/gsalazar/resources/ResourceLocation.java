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
	
	public static final String TAGS_BUSQUEDAS_EXCEL_FILE = BASE_EXCEL_FILES + "TagsBusquedas.xls";
	
	public static final String ARTICULOS_EXCEL_FILE = BASE_EXCEL_FILES + "Articulos.xls";
	
	public static final String ARTICULOS_TAGS_EXCEL_FILE = BASE_EXCEL_FILES + "TagsArticulos.xls";
	
	public static final String FUENTES_ARTICULOS_EXCEL_FILE = BASE_EXCEL_FILES + "FuentesArticulos.xls";
	
	public static final String PERSONAS_EXCEL_FILE = BASE_EXCEL_FILES + "Personas.xls";
	
	public static final String PROYECTOS_EXCEL_FILE = BASE_EXCEL_FILES + "Proyectos.xls";

	public final static Class<ResourceLocation> clazz = ResourceLocation.class;

	private ResourceLocation(){
		super();
	}
}
