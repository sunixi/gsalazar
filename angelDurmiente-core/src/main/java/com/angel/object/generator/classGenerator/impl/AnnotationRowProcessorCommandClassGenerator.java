/**
 * 
 */
package com.angel.object.generator.classGenerator.impl;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

import com.angel.common.helpers.ReflectionHelper;
import com.angel.data.generator.annotations.Inject;
import com.angel.io.annotations.ColumnRow;
import com.angel.io.annotations.RowProcessorCommand;
import com.angel.object.generator.ClassesGenerator;
import com.angel.object.generator.annotations.Accesor;
import com.angel.object.generator.classGenerator.ClassGenerator;
import com.angel.object.generator.java.JavaAnnotation;
import com.angel.object.generator.java.JavaBlockCode;
import com.angel.object.generator.java.JavaLineCode;
import com.angel.object.generator.java.properties.JavaAnnotationProperty;
import com.angel.object.generator.java.properties.JavaParameter;
import com.angel.object.generator.java.properties.JavaProperty;
import com.angel.object.generator.java.types.JavaClass;
import com.angel.object.generator.java.types.JavaType;
import com.angel.object.generator.methodBuilder.impl.AccesorServiceImplAnnotationMethodBuilder;


/**
 * @author Guillermo Salazar.
 * @since 27/Noviembre/2009.
 *
 */
public class AnnotationRowProcessorCommandClassGenerator extends ClassGenerator {

	public AnnotationRowProcessorCommandClassGenerator(String basePackage) {
		super(basePackage);
		this.getMethodBuilderStrategies().put(Accesor.class, new AccesorServiceImplAnnotationMethodBuilder());
	}

	protected void buildInterfacesClasses(){
		//Do Nothing.
	}
	
	@Override
	protected void generateContentClass(ClassesGenerator generator, Class<?> domainClass) {
		Field[] fields = ReflectionHelper.getFieldsDeclaredFor(domainClass);

		JavaAnnotationProperty columnsRowJavaProperty = new JavaAnnotationProperty("columnsRow");
		List<JavaAnnotationProperty> columnsRowProperties = new ArrayList<JavaAnnotationProperty>();
		for(Field f : fields){
			if(f.getModifiers() < Modifier.STATIC){
				//columnName = UsuarioAnnotationRowProcessorCommand.NOMBRE_COLUMN, aliases = {"Nombre del Usuario"}
				JavaAnnotationProperty columnsRowValue = new JavaAnnotationProperty("columnName");
				columnsRowValue.addPropertyValueCode(domainClass.getSimpleName() + "AnnotationRowProcessorCommand."
						+ buildStaticPropertyName(f.getName()) + "_COLUMN");
				columnsRowProperties.add(columnsRowValue);
			}
		}
		columnsRowJavaProperty.addPropertyValueAnnotation(ColumnRow.class.getCanonicalName(), columnsRowProperties);

		JavaAnnotation javaAnnotation = this.getJavaType().createJavaAnnotation(RowProcessorCommand.class.getCanonicalName());
		javaAnnotation.addAnnotationProperty(columnsRowJavaProperty);
		
		//this.getJavaType().createaddAnnotation(RowProcessorCommand.class.getCanonicalName(), rowProcessorAnnotationProperties);


		this.buildServiceProperty(generator, domainClass);
		List<JavaParameter> processRowDataParameters = new ArrayList<JavaParameter>();
		processRowDataParameters.add(new JavaParameter(domainClass.getSimpleName(), domainClass.getCanonicalName()));

		List<JavaParameter> checkRowDataParameters = new ArrayList<JavaParameter>();
		
		for(Field f : fields){
			if(f.getModifiers() < Modifier.STATIC){
				checkRowDataParameters.add(new JavaParameter(f.getName(), String.class.getCanonicalName()));
				processRowDataParameters.add(new JavaParameter(f.getName(), String.class.getCanonicalName()));
				
				this.createStaticJavaPropertyFor(f);
			}
		}
		this.processCheckRowDataMethod(checkRowDataParameters);
		this.processProcessRowMethod(processRowDataParameters, domainClass);
	}
	
	protected void processCheckRowDataMethod(List<JavaParameter> parameters){
		//public void checkRowData(String nombre, String apellido, String nombreUsuario, String password, String email, String imagenPerfil, String fechaNacimiento) throws InvalidRowDataException {
		JavaBlockCode javaBlockCode = super.getJavaType().addTypeMethodPublicVoid("checkRowData", parameters, true);

		//boolean areAllNotEmpty = StringHelper.areAllNotEmpty(nombre, apellido, nombreUsuario, password, email, imagenPerfil, fechaNacimiento);
		List<String> parametersNames = new ArrayList<String>();
		for(JavaParameter jp: parameters){
			parametersNames.add(jp.getParameterName());
		}
		JavaLineCode javaLineCode = javaBlockCode.getLineCodeCalledMethod("StringHelper.areAllNotEmpty", parametersNames);
		javaBlockCode.addLineCodeAssigmentTypedVariable(Boolean.class.getCanonicalName(), "areAllNotEmpty", javaLineCode);
		
	}
	
	protected void processProcessRowMethod(List<JavaParameter> parameters, Class<?> domainClass){
		//public Usuario processRow(Usuario usuario, String nombre, String apellido, String nombreUsuario, String password, String email, String imagenPerfil, String fechaNacimiento) {
		super.getJavaType().addTypeMethodPublicImplemented("processRow", parameters, new JavaParameter(domainClass.getSimpleName(), domainClass.getCanonicalName()));
	}
	
	private void createStaticJavaPropertyFor(Field f) {
		String propertyName = buildStaticPropertyName(f.getName());
		JavaProperty javaProperty = super.createJavaProperty(propertyName + "_COLUMN", String.class.getCanonicalName());
		javaProperty.setPropertyValue("\"" + f.getName() + "\"");
		javaProperty.setPublicVisibility();
		javaProperty.setFinalStaticTypeModifier();		
	}

	public static String buildStaticPropertyName(String propertyName){
		int lenght = propertyName.length();
		StringBuffer stringBuffer = new StringBuffer();
		for(int i = 0; i < lenght; i++){
			Character c = propertyName.charAt(i);
			if(Character.isLowerCase(c)){
				c = Character.toUpperCase(c);
			} else {
				if(Character.isUpperCase(c)){
					stringBuffer = stringBuffer.append("_");
				}
			}
			stringBuffer = stringBuffer.append(c);
		}
		return stringBuffer.toString();
	}

	protected void buildServiceProperty(ClassesGenerator generator, Class<?> domainClass) {
		String propertyName = domainClass.getSimpleName() + "DAO";
		String propertyType = generator.getImportForClassName(propertyName);
		JavaProperty javaProperty = super.createJavaProperty(propertyName, propertyType);
		javaProperty.addAnnotation(Inject.class.getCanonicalName());
	}

	@Override
	protected String buildClassName(Class<?> domainClass) {
		return domainClass.getSimpleName() + "AnnotationRowProcessorCommand";
	}

	@Override
	protected void updateCurrentJavaType(ClassesGenerator generator, Class<?> domainClass) {
		// Do nothing. 
	}

	@Override
	protected JavaType buildJavaType() {
		return new JavaClass();
	}	
}
