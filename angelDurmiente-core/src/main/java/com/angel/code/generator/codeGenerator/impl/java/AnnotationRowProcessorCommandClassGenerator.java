/**
 * 
 */
package com.angel.code.generator.codeGenerator.impl.java;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

import com.angel.code.generator.CodesGenerator;
import com.angel.code.generator.annotations.Accesor;
import com.angel.code.generator.builders.method.impl.AccesorServiceImplAnnotationMethodBuilder;
import com.angel.code.generator.codeGenerator.ClassGenerator;
import com.angel.code.generator.data.DataType;
import com.angel.code.generator.data.impl.java.JavaClassDataType;
import com.angel.code.generator.data.impl.java.annotations.JavaAnnotation;
import com.angel.code.generator.data.impl.java.annotations.JavaAnnotationMultiValueProperty;
import com.angel.code.generator.data.impl.java.annotations.JavaAnnotationPropertyAnnotation;
import com.angel.code.generator.data.impl.java.properties.JavaParameter;
import com.angel.code.generator.data.impl.java.properties.JavaProperty;
import com.angel.code.generator.data.types.CodeBlock;
import com.angel.code.generator.data.types.DataMethod;
import com.angel.code.generator.data.types.DataParameter;
import com.angel.code.generator.data.types.codeLine.AssignableCodeLine;
import com.angel.code.generator.data.types.codeLine.ControlStructureCodeLine;
import com.angel.code.generator.data.types.codeLine.ExecutableReturnCodeLine;
import com.angel.code.generator.data.types.codeLine.ReturnableCodeLine;
import com.angel.code.generator.data.types.codeLine.ThrowExceptionCodeLine;
import com.angel.code.generator.helpers.PackageHelper;
import com.angel.common.helpers.ReflectionHelper;
import com.angel.common.helpers.StringHelper;
import com.angel.data.generator.annotations.Inject;
import com.angel.io.annotations.ColumnRow;
import com.angel.io.annotations.RowChecker;
import com.angel.io.annotations.RowProcessor;
import com.angel.io.annotations.RowProcessorCommand;
import com.angel.io.exceptions.InvalidRowDataException;


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

		JavaAnnotation rowProcessorCommandAnnotation = this.getDataType().createDataAnnotation(RowProcessorCommand.class.getCanonicalName());
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
		List<DataParameter> processRowDataParameters = new ArrayList<DataParameter>();
		processRowDataParameters.add(new JavaParameter(domainClass.getSimpleName(), domainClass.getCanonicalName()));

		List<DataParameter> checkRowDataParameters = new ArrayList<DataParameter>();
		
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
	
	protected void processCheckRowDataMethod(List<DataParameter> parameters){
		DataMethod checkRodDataTypeMethod = super.getDataType().createDataMethod("checkRowData");
		checkRodDataTypeMethod.setParameters(parameters);
		CodeBlock blockCode = checkRodDataTypeMethod.createCodeBlock();

		ExecutableReturnCodeLine stringHelperExecutableReturnVariableCodeLine = 
				new ExecutableReturnCodeLine("areAllNotEmpty", Boolean.class.getCanonicalName());
		stringHelperExecutableReturnVariableCodeLine.setVariableName("StringHelper");
		stringHelperExecutableReturnVariableCodeLine.addGlobalImport(StringHelper.class.getCanonicalName());
		for(DataParameter jp: parameters){
			stringHelperExecutableReturnVariableCodeLine.addParameterName(jp.getName());
		}
		AssignableCodeLine assignableCodeLine =
			new AssignableCodeLine("areAllNotEmpty", stringHelperExecutableReturnVariableCodeLine, Boolean.class.getCanonicalName());
		blockCode.addCodeLine(assignableCodeLine);
		
		ControlStructureCodeLine ifControl = new ControlStructureCodeLine("if", "!areAllNotEmpty");
		
		String messageException = "Some row data are NULL - \" + \n";
		for(DataParameter jp: parameters){
			if(parameters.indexOf(jp) == parameters.size() - 1){
				messageException += "\t\t\t\t\t\"" + jp.getName() + ": [\" + " +  jp.getName() + " + \"].";
			} else {
				messageException += "\t\t\t\t\t\"" + jp.getName() + ": [\" + " +  jp.getName() + " + \"] - \"+ \n";
			}
		}
		ThrowExceptionCodeLine throwExceptionCodeLine = new ThrowExceptionCodeLine(InvalidRowDataException.class.getCanonicalName(), messageException);
		ifControl.addCodeLines(throwExceptionCodeLine);
		blockCode.addCodeLine(ifControl);

		JavaAnnotation rowCheckerAnnotation = checkRodDataTypeMethod.createAnnotation(RowChecker.class.getCanonicalName());
		rowCheckerAnnotation.createJavaAnnotationMultiValuePropertyEmpty("columnsParameters");
		
	}
	
	protected void processProcessRowMethod(List<DataParameter> parameters, Class<?> domainClass){
		//public Usuario processRow(Usuario usuario, String nombre, String apellido, String nombreUsuario, String password, String email, String imagenPerfil, String fechaNacimiento) {
		DataMethod processRowMethodTypeMethod = super.getDataType().createDataMethod("processRow");
		processRowMethodTypeMethod.setParameters(parameters);
		processRowMethodTypeMethod.createReturnParameter(domainClass.getCanonicalName());
		
		CodeBlock blockCode = processRowMethodTypeMethod.createCodeBlock();
		blockCode.addCodeLine(new ReturnableCodeLine(domainClass.getCanonicalName()));

		//@RowProcessor(columnsParameters = {}, object = Usuario.class, inject = true).
		JavaAnnotation rowProcessorAnnotation = processRowMethodTypeMethod.createAnnotation(RowProcessor.class.getCanonicalName());
		rowProcessorAnnotation.createJavaAnnotationMultiValuePropertyEmpty("columnsParameters");
		rowProcessorAnnotation.createJavaAnnotationPropertyClass("object", domainClass.getCanonicalName());
		rowProcessorAnnotation.createjavaAnnotationPropertyBoolean("inject", Boolean.TRUE);
	}
	
	private void createStaticJavaPropertyFor(Field f) {
		String propertyName = buildStaticPropertyName(f.getName());
		JavaProperty javaProperty = super.getDataType().createDataProperty(propertyName + "_COLUMN");
		javaProperty.setCanonicalType(String.class.getCanonicalName());
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

		JavaProperty javaProperty = super.getDataType().createDataProperty(
				PackageHelper.getClassSimpleVariableName(propertyName)
				);
		javaProperty.setCanonicalType(propertyType);
		javaProperty.addAnnotation(Inject.class.getCanonicalName());

		super.getDataType().createDataMethodGetterSetter(javaProperty);
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
	protected DataType buildDataType() {
		return new JavaClassDataType();
	}	
}
