/**
 *
 */
package ar.com.angelDurmiente.helpers;


/**
 * @author William
 *
 */
public class ResourceLocation {

	public final static String BASE_RESOURCES_INIT_FILES = "/initialData/";
	public final static String BASE_RESOURCES_TEST_FILES = "/testData/";

	public static final String USUARIOS_INIT_DATA_EXCEL_FILE = BASE_RESOURCES_INIT_FILES + "Usuarios.xls";
	
	public static final String CANCIONES_INIT_DATA_EXCEL_FILE = BASE_RESOURCES_INIT_FILES + "Canciones.xls";


	public final static Class<ResourceLocation> clazz = ResourceLocation.class;

	private ResourceLocation(){
		super();
	}
}
