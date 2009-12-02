/**
 * 
 */
package com.angel.object.generator.classGenerator.impl;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import com.angel.common.helpers.FileHelper;
import com.angel.data.generator.annotations.Generator;
import com.angel.data.generator.annotations.importFileProcessorRunner.ImportFileProcessorRunnerBuilder;
import com.angel.data.generator.annotations.inputStream.InputStreamBuilder;
import com.angel.data.generator.exceptions.DataGeneratorException;
import com.angel.io.descriptor.FileProcessorDescriptor;
import com.angel.io.processors.commands.impl.ExcelFileProcessorCommand;
import com.angel.io.processors.runners.imports.impl.ImportFileAnnotationProcessorRunner;
import com.angel.io.processors.runners.imports.impl.ImportFileProcessorRunner;
import com.angel.object.generator.CodesGenerator;
import com.angel.object.generator.annotations.Accesor;
import com.angel.object.generator.classGenerator.ClassGenerator;
import com.angel.object.generator.java.JavaAnnotation;
import com.angel.object.generator.java.JavaBlockCode;
import com.angel.object.generator.java.JavaLineCode;
import com.angel.object.generator.java.TypeMethod;
import com.angel.object.generator.java.properties.JavaParameter;
import com.angel.object.generator.java.types.JavaClass;
import com.angel.object.generator.java.types.JavaType;
import com.angel.object.generator.methodBuilder.impl.AccesorServiceImplAnnotationMethodBuilder;


/**
 * @author Guillermo Salazar.
 * @since 27/Noviembre/2009.
 *
 */
public class AnnotationDataGeneratorClassGenerator extends ClassGenerator {

	public AnnotationDataGeneratorClassGenerator(String basePackage) {
		super(basePackage);
		this.getMethodBuilderStrategies().put(Accesor.class, new AccesorServiceImplAnnotationMethodBuilder());
	}

	protected void buildInterfacesClasses(){
		//Do Nothing.
	}


	@Override
	protected void generateContentClass(CodesGenerator generator, Class<?> domainClass) {
		this.processGeneratorAnnotation(generator, domainClass);
		this.processPrepareImportFileProcessorRunnerTypeMethod(generator, domainClass);
		this.processPrepareInputStream(generator, domainClass);
	}
	/*
    @InputStreamBuilder
    public InputStream prepareInputStream() {
        try {
            return FileHelper.findInputStreamInClasspath(ResourceLocation.USUARIOS_INIT_DATA_EXCEL_FILE);
        } catch (FileNotFoundException e) {
            throw new DataGeneratorException("File not found [" + ResourceLocation.USUARIOS_INIT_DATA_EXCEL_FILE + "].", e);
        }
    }
	 */
	protected void processPrepareInputStream(CodesGenerator generator,
			Class<?> domainClass) {
		String methodName = "prepareInputStream";
		JavaParameter returnParameter = new JavaParameter(InputStream.class.getCanonicalName());
		TypeMethod prepareImportFileTypeMethod = super.getJavaType().addTypeMethodPublicWithoutParametersImplemented(methodName, returnParameter);
		prepareImportFileTypeMethod.addJavaAnnotation(InputStreamBuilder.class.getCanonicalName());
		
		JavaBlockCode prepareInputStreamMethodConent = prepareImportFileTypeMethod.getContent();
		prepareInputStreamMethodConent.addLineCodeCommented("TODO Change file name.");
		prepareInputStreamMethodConent.addLineCodeCommented("TODO You can create a static class with files names.");
		
		String initialDataFile = "\"/initialData/" + domainClass.getSimpleName() + ".xls\"";
		List<String> parametersNames = new ArrayList<String>();
		parametersNames.add(initialDataFile);
		JavaLineCode returnPrepareInputStream = 
			prepareInputStreamMethodConent
				.getLineCodeReturnCalledStaticMethod(FileHelper.class.getCanonicalName(), "findInputStreamInClasspath", parametersNames);
		
		String exceptionMessage ="File not found [\" + " + initialDataFile + "+ \"].";
		JavaLineCode throwDataGeneratorException = prepareInputStreamMethodConent
			.getLineCodethrowNewException(DataGeneratorException.class.getCanonicalName(), exceptionMessage);
		
		prepareInputStreamMethodConent.addLineCodeTryCatch(
				returnPrepareInputStream, 
				FileNotFoundException.class.getCanonicalName(),
				throwDataGeneratorException);
	}

	
/*
@ImportFileProcessorRunnerBuilder(fileProcessorDescriptor = FileProcessorDescriptor.class, name = "Importacion de Usuarios")
    public ImportFileProcessorRunner prepareImportFileProcessorRunner(
    		FileProcessorDescriptor fileProcessorDescriptor) {
		ImportFileAnnotationProcessorRunner a = new ImportFileAnnotationProcessorRunner(fileProcessorDescriptor, new ExcelFileProcessorCommand(), new UsuarioAnnotationRowProcessorCommand());
        return a;
    }
 */
	protected void processPrepareImportFileProcessorRunnerTypeMethod(
			CodesGenerator generator, Class<?> domainClass) {
		String methodName = "prepareImportFileProcessorRunner";
		JavaParameter returnParameter = new JavaParameter(ImportFileProcessorRunner.class.getCanonicalName());
		List<JavaParameter> methodParameters = new ArrayList<JavaParameter>();
		methodParameters.add(new JavaParameter("fileProcessorDescriptor", FileProcessorDescriptor.class.getCanonicalName()));
		
		TypeMethod prepareImportFileTypeMethod = super.getJavaType()
				.addTypeMethodPublicImplemented(methodName, methodParameters, returnParameter);
		JavaAnnotation importFileProcessorBuilderAnnotation = prepareImportFileTypeMethod.addJavaAnnotation(ImportFileProcessorRunnerBuilder.class.getCanonicalName());
		importFileProcessorBuilderAnnotation.createJavaAnnotationPropertyClass("fileProcessorDescriptor", FileProcessorDescriptor.class.getCanonicalName());
		importFileProcessorBuilderAnnotation.createJavaAnnotationPropertyString("name", "Importación de " + domainClass.getSimpleName());
		
		String newInstanceObjectType = ImportFileAnnotationProcessorRunner.class.getCanonicalName();
		JavaBlockCode prepareImportFileContent = prepareImportFileTypeMethod.getContent();
		JavaLineCode createExcelVariable = prepareImportFileContent.getLineCodeCreateObject("excelFileProcessorCommand", ExcelFileProcessorCommand.class.getCanonicalName() );

		String domainObjectRowProcessorCanonicalType = generator.getImportForClassName( domainClass.getSimpleName() + "AnnotationRowProcessorCommand");
		JavaLineCode createDomainObjectRowProcessorVariable = prepareImportFileContent.getLineCodeCreateObject("domainObjectRowProcessor", domainObjectRowProcessorCanonicalType );
		
		prepareImportFileContent.addJavaLineCode(createExcelVariable);
		prepareImportFileContent.addJavaLineCode(createDomainObjectRowProcessorVariable);
		
		List<String> parametersNames = new ArrayList<String>();
		parametersNames.add("fileProcessorDescriptor");
		parametersNames.add("excelFileProcessorCommand");
		parametersNames.add("domainObjectRowProcessor");
		JavaLineCode newImportFileAnnotationProcessorRunner = 
				prepareImportFileContent.getLineCodeNewInstanceObject(newInstanceObjectType, parametersNames);
		prepareImportFileContent.addLineCodeReturn(newImportFileAnnotationProcessorRunner);
	}

	protected void processGeneratorAnnotation(CodesGenerator generator, Class<?> domainClass) {
		JavaAnnotation generatorAnnotation = super.getJavaType().createJavaAnnotation(Generator.class.getCanonicalName());
		generatorAnnotation.createJavaAnnotationPropertyClass("objectClass", domainClass.getCanonicalName());
		generatorAnnotation.createJavaAnnotationMultiValuePropertyEmpty("dependencies");
		generatorAnnotation.createJavaAnnotationPropertyString("daoName", domainClass.getSimpleName() + "DAO");
		generatorAnnotation.createJavaAnnotationMultiValuePropertyEmpty("pages");
	}

	@Override
	protected String buildClassName(Class<?> domainClass) {
		return domainClass.getSimpleName() + "AnnotationDataGenerator";
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
