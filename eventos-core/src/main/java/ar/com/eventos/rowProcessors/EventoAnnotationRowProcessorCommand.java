/**
 *
 */
package ar.com.eventos.rowProcessors;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import ar.com.eventos.beans.Evento;
import ar.com.eventos.beans.Salon;
import ar.com.eventos.daos.EventoDAO;
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
			@ColumnRow( columnName = EventoAnnotationRowProcessorCommand.NOMBRE_SALON_COLUMN, aliases = {"Nombre del Salon"} ),
			@ColumnRow( columnName = EventoAnnotationRowProcessorCommand.DESCRIPCION_COLUMN, aliases = {"Descripcion del Evento"} ),
			@ColumnRow( columnName = EventoAnnotationRowProcessorCommand.FECHA_REALIZACION_COLUMN, aliases = {"Fecha de Realizacion"})
		}
	)
public class EventoAnnotationRowProcessorCommand {

	public static final String NOMBRE_SALON_COLUMN = "nombreSalon";
	public static final String DESCRIPCION_COLUMN = "descripcion";
	public static final String FECHA_REALIZACION_COLUMN = "realizacion";

	@Inject
	private EventoDAO eventoDAO;
	
	@Inject
	private SalonDAO salonDAO;
	
	@RowChecker(columnsParameters = {})
    public void checkRowData(String nombreSalon, String descripcion, String realizacion) throws InvalidRowDataException {
		boolean areAllNotEmpty = StringHelper.areAllNotEmpty(nombreSalon, descripcion, realizacion);
		if(!areAllNotEmpty){
			throw new InvalidRowDataException("Some row data are NULL - " +
					"nombreSalon: [" + nombreSalon + "] - " +
					"descripcion: [" + descripcion + "] - " +
					"realizacion: [" + realizacion + "]."		
			);
		}
    }

	@RowProcessor(columnsParameters = {}, object = Evento.class)
	public Evento processRow(Evento evento, String nombreSalon, String descripcion, String realizacion) {
		Salon salon = this.getSalonDAO().buscarUnicoPorNombre(nombreSalon);
		evento.setSalon(salon);
		evento.setDescripcion(descripcion);
		
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
		Date fechaRealizacion = null;
		try {
			fechaRealizacion = simpleDateFormat.parse(realizacion);
			evento.setRealizacion(fechaRealizacion);
		} catch (ParseException e) {
			throw new InvalidRowDataException("No se pudo parsear la fecha de realizacion [" + realizacion + "] " +
					"para el evento [nombreSalon: " + nombreSalon + " - Descripcion: " + descripcion + "].");
		}
		return evento;
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
	 * @return the eventoDAO
	 */
	public EventoDAO getEventoDAO() {
		return eventoDAO;
	}

	/**
	 * @param eventoDAO the eventoDAO to set
	 */
	public void setEventoDAO(EventoDAO eventoDAO) {
		this.eventoDAO = eventoDAO;
	}

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
