/**
 *
 */
package ar.com.eventos.rowProcessors;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import ar.com.eventos.beans.Persona;
import ar.com.eventos.beans.TipoSexo;
import ar.com.eventos.daos.PersonaDAO;

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
			@ColumnRow( columnName = PersonaAnnotationRowProcessorCommand.NOMBRE_COLUMN, aliases = {"Nombre"} ),
			@ColumnRow( columnName = PersonaAnnotationRowProcessorCommand.APELLIDO_COLUMN, aliases = {"Apellido"} ),
			@ColumnRow( columnName = PersonaAnnotationRowProcessorCommand.EMAIL_COLUMN, aliases = {"Email"} ),
			@ColumnRow( columnName = PersonaAnnotationRowProcessorCommand.NACIMIENTO_COLUMN, aliases = {"Fecha Nacimiento"} ),
			@ColumnRow( columnName = PersonaAnnotationRowProcessorCommand.URL_FOTOLOG_COLUMN, aliases = {"Dirección de Fotolog"} ),
			@ColumnRow( columnName = PersonaAnnotationRowProcessorCommand.TIPO_SEXO_COLUMN, aliases = {"Tipo Sexo"} )
		}
	)
public class PersonaAnnotationRowProcessorCommand {

	public static final String NOMBRE_COLUMN = "nombre";
	public static final String APELLIDO_COLUMN = "apellido";
	public static final String EMAIL_COLUMN = "email";
	public static final String NACIMIENTO_COLUMN = "nacimiento";
	public static final String URL_FOTOLOG_COLUMN = "urlFotolog";
	public static final String TIPO_SEXO_COLUMN = "tipoSexo";

	@Inject
	private PersonaDAO personaDAO;
	
	@RowChecker(columnsParameters = {})
    public void checkRowData(String nombre, String apellido, String email, String nacimiento, 
    		String urlFotolog, String tipoSexo) throws InvalidRowDataException {
		boolean areAllNotEmpty = StringHelper.areAllNotEmpty(nombre, apellido, email, nacimiento, 
	    		urlFotolog, tipoSexo);

		if(!areAllNotEmpty){
			throw new InvalidRowDataException("Some row data are NULL - " +
					"nombre: [" + nombre + "] - " +
					"apellido: [" + apellido + "] - " +
					"email: [" + email + "] - " +
					"nacimiento: [" + nacimiento + "] - " +
					"urlFotolog: [" + urlFotolog + "] - " +
					"tipoSexo: [" + tipoSexo + "]."		
			);
		}
		/*
		Salon salon = this.getSalonDAO().buscarUnicoONuloPorNombre(nombreSalon);
		if(salon != null){
			throw new InvalidRowDataException("Ya existe un salon con el nombre [" + nombreSalon + "].");
		}*/
    }

	@RowProcessor(columnsParameters = {}, object = Persona.class)
	public Persona processRow(Persona persona, String nombre, String apellido, String email, String nacimiento,
    		String urlFotolog, String tipoSexo) {
		
		persona.setNombre(nombre);
		persona.setApellido(apellido);
		persona.setEmail(email);
		persona.setUrlFotolog(urlFotolog);
		persona.setTipoSexo(TipoSexo.valueOf(tipoSexo));
		
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
		Date fechaNacimiento;
		try {
			fechaNacimiento = simpleDateFormat.parse(nacimiento);
			persona.setNacimiento(fechaNacimiento);
		} catch (ParseException e) {
			throw new InvalidRowDataException("No se pudo parsear la fecha de nacimiento [" + nacimiento + "] " +
					"para la persona [nombre: " + nombre + " - Apellido: " + apellido + "].");
		}
        return persona;
    }

	/**
	 * @return the personaDAO
	 */
	public PersonaDAO getPersonaDAO() {
		return personaDAO;
	}

	/**
	 * @param personaDAO the personaDAO to set
	 */
	public void setPersonaDAO(PersonaDAO personaDAO) {
		this.personaDAO = personaDAO;
	}
}
