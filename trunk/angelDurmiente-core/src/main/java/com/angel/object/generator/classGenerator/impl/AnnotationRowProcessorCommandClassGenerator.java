/**
 * 
 */
package com.angel.object.generator.classGenerator.impl;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

import com.angel.common.helpers.ReflectionHelper;
import com.angel.common.helpers.StringHelper;
import com.angel.data.generator.annotations.Inject;
import com.angel.io.annotations.ColumnRow;
import com.angel.io.annotations.RowChecker;
import com.angel.io.annotations.RowProcessor;
import com.angel.io.annotations.RowProcessorCommand;
import com.angel.io.exceptions.InvalidRowDataException;
import com.angel.object.generator.CodesGenerator;
import com.angel.object.generator.annotations.Accesor;
import com.angel.object.generator.classGenerator.ClassGenerator;
import com.angel.object.generator.java.JavaAnnotation;
import com.angel.object.generator.java.JavaBlockCode;
import com.angel.object.generator.java.JavaLineCode;
import com.angel.object.generator.java.TypeMethod;
import com.angel.object.generator.java.properties.JavaAnnotationMultiValueProperty;
import com.angel.object.generator.java.properties.JavaAnnotationPropertyAnnotation;
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

	/*
	@RowProcessorCommand
		(
			columnsRow = {
				@ColumnRow( columnName = UsuarioAnnotationRowProcessorCommand.NOMBRE_COLUMN, aliases = {"Nombre del Usuario"} ),
				@ColumnRow( columnName = UsuarioAnnotationRowProcessorCommand.APELLIDO_COLUMN, aliases = {"Apellido del Usuario"} ),
			},
			columnsRow = @ColumnRow( columnName = UsuarioAnnotationRowProcessorCommand.NOMBRE_COLUMN, aliases = {"Nombre del Usuario"} ),
			columnName = UsuarioAnnotationRowProcessorCommand.NOMBRE_COLUMN
		)
	 */
	@Override
	protected void generateContentClass(CodesGenerator generator, Class<?> domainClass) {
		Field[] fields = ReflectionHelper.getFieldsDeclaredFor(domainClass);

		JavaAnnotation rowProcessorCommandAnnotation = this.getJavaType().createJavaAnnotation(RowProcessorCommand.class.getCanonicalName());		
		JavaAnnotationMultiValueProperty columnsRowValues = rowProcessorCommandAnnotation.createJavaAnnotationMultiValueProperty("columnsRow");

		for(Field f : fields){
			if(f.getModifiers() < Modifier.STATIC){
				JavaAnnotationPropertyAnnotation columnRowAnnotation = columnsRowValues.createJavaAnnotationPropertyAnnotation(ColumnRow.class.getCanonicalName());
				String columnRowAnnotationValue = domainClass.getSimpleName() + "AnnotationRowProcessorCommand."
									+ buildStaticPropertyName(f.getName()) + "_COLUMN";
				columnRowAnnotation.createJavaAnnotationProperty("columnName", columnRowAnnotationValue);
				columnRowAnnotation.createJavaAnnotationMultiValuePropertyEmpty("aliases");
			}
		}

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
		TypeMethod checkRodDataTypeMethod = super.getJavaType().addTypeMethodPublicVoid("checkRowData", parameters, true);
		JavaBlockCode javaBlockCode = checkRodDataTypeMethod.getContent();

		List<String> parametersNames = new ArrayList<String>();
		for(JavaParameter jp: parameters){
			parametersNames.add(jp.getParameterName());
		}
		JavaLineCode javaLineCode = javaBlockCode.getLineCodeCalledStaticMethod(
				StringHelper.class.getCanonicalName(), "areAllNotEmpty", parametersNames);
		javaBlockCode.addLineCodeAssigmentTypedVariable(
				Boolean.class.getCanonicalName(), "areAllNotEmpty", javaLineCode);
		
		JavaBlockCode ifJavaBlockCode = new JavaBlockCode();
		
		String exceptionMessage = "Some row data are NULL - \" + \n";
		for(JavaParameter jp: parameters){
			if(parameters.indexOf(jp) == parameters.size() - 1){
				exceptionMessage += "\t\t\t\t\t\"" + jp.getParameterName() + ": [\" + " +  jp.getParameterName() + " + \"].";
			} else {
				exceptionMessage += "\t\t\t\t\t\"" + jp.getParameterName() + ": [\" + " +  jp.getParameterName() + " + \"] - \"+ \n";
			}
		}
		ifJavaBlockCode.throwNewException(InvalidRowDataException.class.getCanonicalName(), exceptionMessage);
		
		javaBlockCode.addLineCodeIf("!areAllNotEmpty", ifJavaBlockCode);


		JavaAnnotation rowCheckerAnnotation = checkRodDataTypeMethod.addJavaAnnotation(RowChecker.class.getCanonicalName());
		rowCheckerAnnotation.createJavaAnnotationMultiValuePropertyEmpty("columnsParameters");
		
	}
	
	protected void processProcessRowMethod(List<JavaParameter> parameters, Class<?> domainClass){
		//public Usuario processRow(Usuario usuario, String nombre, String apellido, String nombreUsuario, String password, String email, String imagenPerfil, String fechaNacimiento) {
		TypeMethod processRowMethodTypeMethod = super.getJavaType().addTypeMethodPublicImplemented("processRow", parameters, new JavaParameter(domainClass.getSimpleName(), domainClass.getCanonicalName()));
		JavaBlockCode javaBlockCode = processRowMethodTypeMethod.getContent();
		javaBlockCode.addLineCodeReturnNull();
		//@RowProcessor(columnsParameters = {}, object = Usuario.class, inject = true).
		JavaAnnotation rowProcessorAnnotation = processRowMethodTypeMethod.addJavaAnnotation(RowProcessor.class.getCanonicalName());
		rowProcessorAnnotation.createJavaAnnotationMultiValuePropertyEmpty("columnsParameters");
		rowProcessorAnnotation.createJavaAnnotationPropertyClass("object", domainClass.getCanonicalName());
		rowProcessorAnnotation.createjavaAnnotationPropertyBoolean("inject", Boolean.TRUE);
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

	protected void buildServiceProperty(CodesGenerator generator, Class<?> domainClass) {
		String propertyName = domainClass.getSimpleName() + "DAO";
		String propertyType = generator.getImportForClassName(propertyName);
		JavaProperty javaProperty = super.createJavaPropertyWithGetterAndSetter(propertyName, propertyType);
		javaProperty.addAnnotation(Inject.class.getCanonicalName());
	}

	@Override
	protected String buildClassName(Class<?> domainClass) {
		return domainClass.getSimpleName() + "AnnotationRowProcessorCommand";
	}

	@Override
	protected void updateCurrentJavaType(CodesGenerator generator, Class<?> domainClass) {
		// Do nothing. 
	}

	@Override
	protected JavaType buildJavaType() {
		return new JavaClass();
	}	
}
