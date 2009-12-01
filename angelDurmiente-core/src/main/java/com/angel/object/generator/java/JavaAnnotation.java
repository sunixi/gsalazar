/**
 * 
 */
package com.angel.object.generator.java;

import java.util.ArrayList;
import java.util.List;

import com.angel.object.generator.helper.PackageHelper;
import com.angel.object.generator.java.properties.JavaAnnotationProperty;
import com.angel.object.generator.types.CodeConvertible;
import com.angel.object.generator.types.Importable;



/**
 * @author Guillermo D. Salazar
 * @since 26/Noviembre/2009.
 *
 */
public class JavaAnnotation implements CodeConvertible, Importable {
	
	private String name;
	private List<JavaAnnotationProperty> properties;
	private List<String> importsType;
	
	public JavaAnnotation(String annotationClass){
		super();
		this.setName("@" + PackageHelper.getClassSimpleName(annotationClass) + "\n");
		this.setImportsType(new ArrayList<String>());
		this.getImportsType().add(annotationClass);
		this.setProperties(new ArrayList<JavaAnnotationProperty>());
		this.getImportsType().add(annotationClass);
	}
	
	public JavaAnnotation(String annotationClass, List<JavaAnnotationProperty> properties){
		this(annotationClass);
		this.getProperties().addAll(properties);
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
	public List<JavaAnnotationProperty> getProperties() {
		return properties;
	}

	/**
	 * @param properties the properties to set
	 */
	public void setProperties(List<JavaAnnotationProperty> properties) {
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

	/**
	 * @return the importsType
	 */
	public List<String> getImportsType() {
		return importsType;
	}

	/**
	 * @param importsType the importsType to set
	 */
	public void setImportsType(List<String> importsType) {
		this.importsType = importsType;
	}

	public void addAnnotationProperty(JavaAnnotationProperty javaAnnotationProperty) {
		this.getProperties().add(javaAnnotationProperty);
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
