/**
 * 
 */
package com.angel.object.generator.java;

import java.util.List;

import com.angel.object.generator.types.CodeConvertible;



/**
 * @author Guillermo D. Salazar
 * @since 26/Noviembre/2009.
 *
 */
public class JavaAnnotation implements CodeConvertible {
	
	private String name;
	private List<JavaProperty> properties;
	
	public JavaAnnotation(String name){
		super();
		this.setName("@" + name);
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the properties
	 */
	public List<JavaProperty> getProperties() {
		return properties;
	}

	/**
	 * @param properties the properties to set
	 */
	public void setProperties(List<JavaProperty> properties) {
		this.properties = properties;
	}

	public boolean hasProperties(){
		return this.getProperties().size() > 0;
	}

	public String convert() {
		String codeConverter = "";
		codeConverter += this.getName();
		codeConverter += this.hasProperties() ? "(\n\t" + this.convertProperties() + "\n)\n" : "";
		return codeConverter;
	}

	public String convertProperties() {
		String codeConverter = "";
		for(CodeConvertible cc: this.getProperties()){
			codeConverter += cc.convert();			
		}
		return codeConverter;
	}
	
/*
@RowProcessorCommand
	(
		columnsRow = {
			@ColumnRow( columnName = UsuarioAnnotationRowProcessorCommand.NOMBRE_COLUMN, aliases = {"Nombre del Usuario"} ),
			@ColumnRow( columnName = UsuarioAnnotationRowProcessorCommand.APELLIDO_COLUMN, aliases = {"Apellido del Usuario"} ),
			@ColumnRow( columnName = UsuarioAnnotationRowProcessorCommand.NOMBRE_USUARIO_COLUMN, aliases = {"Usuario"} ),
			@ColumnRow( columnName = UsuarioAnnotationRowProcessorCommand.PASSWORD_COLUMN, aliases = {"Password"} ),
			@ColumnRow( columnName = UsuarioAnnotationRowProcessorCommand.EMAIL_COLUMN, aliases = {"Email"} ),
			@ColumnRow( columnName = UsuarioAnnotationRowProcessorCommand.IMAGEN_PERFIL_COLUMN, aliases = {"Imagen de perfil"} ),
			@ColumnRow( columnName = UsuarioAnnotationRowProcessorCommand.FECHA_NACIMIENTO_COLUMN, aliases = {"Fecha de Nacimiento"} )
		}
	)
 */
	
}
