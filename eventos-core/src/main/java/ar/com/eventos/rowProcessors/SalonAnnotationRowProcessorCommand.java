/**
 *
 */
package ar.com.eventos.rowProcessors;

import ar.com.eventos.beans.Salon;
import ar.com.eventos.daos.SalonDAO;

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
			@ColumnRow( columnName = SalonAnnotationRowProcessorCommand.NOMBRE_SALON_COLUMN, aliases = {"Nombre del Salon"} ),
			@ColumnRow( columnName = SalonAnnotationRowProcessorCommand.DIRECCION_COLUMN, aliases = {"Dirección"} ),
			@ColumnRow( columnName = SalonAnnotationRowProcessorCommand.CAPACIDAD_COLUMN, aliases = {"Capacidad"}, type = Integer.class),
			@ColumnRow( columnName = SalonAnnotationRowProcessorCommand.LOCALIDAD_COLUMN, aliases = {"Localidad"} )
		}
	)
public class SalonAnnotationRowProcessorCommand {

	public static final String NOMBRE_SALON_COLUMN = "nombre";
	public static final String DIRECCION_COLUMN = "direccion";
	public static final String CAPACIDAD_COLUMN = "capacidad";
	public static final String LOCALIDAD_COLUMN = "localidad";

	@Inject
	private SalonDAO salonDAO;
	
	@RowChecker(columnsParameters = {})
    public void checkRowData(String nombreSalon, String direccion, Integer capacidad, String localidad) throws InvalidRowDataException {
		boolean areAllNotEmpty = StringHelper.areAllNotEmpty(nombreSalon, direccion, capacidad.toString(), localidad);
		if(!areAllNotEmpty){
			throw new InvalidRowDataException("Some row data are NULL - " +
					"nombreSalon: [" + nombreSalon + "] - " +
					"direccion: [" + direccion + "] - " +
					"capacidad: [" + capacidad + "] - " +
					"localidad: [" + localidad + "]."		
			);
		}
		
		Salon salon = this.getSalonDAO().buscarUnicoONuloPorNombre(nombreSalon);
		if(salon != null){
			throw new InvalidRowDataException("Ya existe un salon con el nombre [" + nombreSalon + "].");
		}
    }

	@RowProcessor(columnsParameters = {}, object = Salon.class)
	public Salon processRow(Salon salon, String nombreSalon, String direccion, Integer capacidad, String localidad) {
		salon.setNombre(nombreSalon);
		salon.setDireccion(direccion);
		salon.setCapacidad(Integer.valueOf(capacidad));
		salon.setLocalidad(localidad);
		salon.setCapacidad(Integer.valueOf(capacidad));
        return salon;
    }
	/*
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
	}*/

	/**
	 * @return the salonDAO
	 */
	public SalonDAO getSalonDAO() {
		return salonDAO;
	}

	/**
	 * @param salonDAO the salonDAO to set
	 */
	public void setSalonDAO(SalonDAO salonDAO) {
		this.salonDAO = salonDAO;
	}
}
