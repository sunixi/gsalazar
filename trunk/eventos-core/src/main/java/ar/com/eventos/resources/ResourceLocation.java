/**
 *
 */
package ar.com.eventos.resources;


/**
 * @author William
 *
 */
public class ResourceLocation {

	public final static String BASE_RESOURCES_TEST_FILES = "/";

	public final static String BASE_EXCEL_FILES = BASE_RESOURCES_TEST_FILES;
	
	public final static String BASE_EXCEL_INITIAL_DATA_FILES = BASE_RESOURCES_TEST_FILES + "initialData/";
	
	public final static String BASE_EXCEL_TEST_DATA_FILES = BASE_RESOURCES_TEST_FILES + "testData/";

	public static final String SALONES_EXCEL_FILE = BASE_EXCEL_INITIAL_DATA_FILES + "Salones.xls";
	
	public static final String EVENTOS_EXCEL_FILE = BASE_EXCEL_INITIAL_DATA_FILES + "Eventos.xls";
	
	public static final String PERSONAS_EXCEL_FILE = BASE_EXCEL_INITIAL_DATA_FILES + "Personas.xls";

	public final static Class<ResourceLocation> clazz = ResourceLocation.class;

	private ResourceLocation(){
		super();
	}
}
