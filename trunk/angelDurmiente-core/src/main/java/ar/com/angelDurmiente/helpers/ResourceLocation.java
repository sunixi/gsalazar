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
	
	public static final String TEXTOS_CANCIONES_INIT_DATA_EXCEL_FILE = BASE_RESOURCES_INIT_FILES + "Textos.xls";
	
	public static final String DEDICATORIAS_INIT_DATA_EXCEL_FILE = BASE_RESOURCES_INIT_FILES + "Dedicatorias.xls";
	
	public static final String ALBUMS_CANCIONES_INIT_DATA_EXCEL_FILE = BASE_RESOURCES_INIT_FILES + "AlbumsCanciones.xls";
	
	public static final String ALBUMS_INIT_DATA_EXCEL_FILE = BASE_RESOURCES_INIT_FILES + "Albums.xls";
	
	public static final String ARTISTAS_INIT_DATA_EXCEL_FILE = BASE_RESOURCES_INIT_FILES + "Artistas.xls";
	
	public static final String ARTISTAS_ALBUMS_INIT_DATA_EXCEL_FILE = BASE_RESOURCES_INIT_FILES + "ArtistasAlbums.xls";

	public final static Class<ResourceLocation> clazz = ResourceLocation.class;

	private ResourceLocation(){
		super();
	}
}
