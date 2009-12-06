/**
 * 
 */
package com.angel.code.generator.codeGenerator.impl.java;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import com.angel.code.generator.CodesGenerator;
import com.angel.code.generator.annotations.Accesor;
import com.angel.code.generator.builders.method.impl.AccesorServiceImplAnnotationMethodBuilder;
import com.angel.code.generator.codeGenerator.ClassGenerator;
import com.angel.code.generator.data.DataType;
import com.angel.code.generator.data.impl.java.JavaClassDataType;
import com.angel.code.generator.data.impl.java.JavaCodeBlock;
import com.angel.code.generator.data.impl.java.JavaCodeLine;
import com.angel.code.generator.data.impl.java.JavaDataMethod;
import com.angel.code.generator.data.impl.java.annotations.JavaAnnotation;
import com.angel.code.generator.data.impl.java.properties.JavaParameter;
import com.angel.code.generator.data.types.DataParameter;
import com.angel.common.helpers.FileHelper;
import com.angel.data.generator.annotations.Generator;
import com.angel.data.generator.annotations.importFileProcessorRunner.ImportFileProcessorRunnerBuilder;
import com.angel.data.generator.annotations.inputStream.InputStreamBuilder;
import com.angel.data.generator.exceptions.DataGeneratorException;
import com.angel.io.descriptor.FileProcessorDescriptor;
import com.angel.io.processors.commands.impl.ExcelFileProcessorCommand;
import com.angel.io.processors.runners.imports.impl.ImportFileAnnotationProcessorRunner;
import com.angel.io.processors.runners.imports.impl.ImportFileProcessorRunner;


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
	protected void processPrepareInputStream(CodesGenerator generator, Class<?> domainClass) {	
		JavaDataMethod prepareImportFileTypeMethod = super.getDataType().createDataMethod("prepareInputStream");
		prepareImportFileTypeMethod.createReturnParameter(InputStream.class.getCanonicalName());
		
		prepareImportFileTypeMethod.createAnnotation(InputStreamBuilder.class.getCanonicalName());

		JavaCodeBlock prepareInputStreamMethodConent = (JavaCodeBlock) prepareImportFileTypeMethod.createCodeBlock();
		prepareInputStreamMethodConent.addLineCodeCommented("TODO Change file name.");
		prepareInputStreamMethodConent.addLineCodeCommented("TODO You can create a static class with files names.");
		
		String initialDataFile = "\"/initialData/" + domainClass.getSimpleName() + ".xls\"";
		List<String> parametersNames = new ArrayList<String>();
		parametersNames.add(initialDataFile);
		JavaCodeLine returnPrepareInputStream = 
			prepareInputStreamMethodConent
				.getLineCodeReturnCalledStaticMethod(FileHelper.class.getCanonicalName(), "findInputStreamInClasspath", parametersNames);
		
		String exceptionMessage ="File not found [\" + " + initialDataFile + "+ \"].";
		JavaCodeLine throwDataGeneratorException = prepareInputStreamMethodConent
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
	protected void processPrepareImportFileProcessorRunnerTypeMethod(CodesGenerator generator, Class<?> domainClass) {
		JavaParameter returnParameter = new JavaParameter(ImportFileProcessorRunner.class.getCanonicalName());
		
		List<DataParameter> methodParameters = new ArrayList<DataParameter>();
		methodParameters.add(new JavaParameter("fileProcessorDescriptor", FileProcessorDescriptor.class.getCanonicalName()));

		JavaDataMethod prepareImportFileTypeMethod = super.getDataType().createDataMethod("prepareImportFileProcessorRunner");
		prepareImportFileTypeMethod.setImplemented();
		prepareImportFileTypeMethod.setParameters(methodParameters);
		prepareImportFileTypeMethod.setReturnType(returnParameter);

		JavaAnnotation importFileProcessorBuilderAnnotation = prepareImportFileTypeMethod.createAnnotation(ImportFileProcessorRunnerBuilder.class.getCanonicalName());
		importFileProcessorBuilderAnnotation.createJavaAnnotationPropertyClass("fileProcessorDescriptor", FileProcessorDescriptor.class.getCanonicalName());
		importFileProcessorBuilderAnnotation.createJavaAnnotationPropertyString("name", "Importación de " + domainClass.getSimpleName());
		
		String newInstanceObjectType = ImportFileAnnotationProcessorRunner.class.getCanonicalName();
		JavaCodeBlock prepareImportFileContent = (JavaCodeBlock) prepareImportFileTypeMethod.getContent();
		JavaCodeLine createExcelVariable = prepareImportFileContent.getLineCodeCreateObject("excelFileProcessorCommand", ExcelFileProcessorCommand.class.getCanonicalName() );

		String domainObjectRowProcessorCanonicalType = generator.getImportForClassName( domainClass.getSimpleName() + "AnnotationRowProcessorCommand");
		JavaCodeLine createDomainObjectRowProcessorVariable = prepareImportFileContent.
					getLineCodeCreateObject("domainObjectRowProcessor", domainObjectRowProcessorCanonicalType );
		
		prepareImportFileContent.addCodeLine(createExcelVariable);
		prepareImportFileContent.addCodeLine(createDomainObjectRowProcessorVariable);
		
		List<String> parametersNames = new ArrayList<String>();
		parametersNames.add("fileProcessorDescriptor");
		parametersNames.add("excelFileProcessorCommand");
		parametersNames.add("domainObjectRowProcessor");
		JavaCodeLine newImportFileAnnotationProcessorRunner = 
				prepareImportFileContent.getLineCodeNewInstanceObject(newInstanceObjectType, parametersNames);
		prepareImportFileContent.addLineCodeReturn(newImportFileAnnotationProcessorRunner);
	}

	protected void processGeneratorAnnotation(CodesGenerator generator, Class<?> domainClass) {
		JavaAnnotation generatorAnnotation = super.getDataType().createDataAnnotation(Generator.class.getCanonicalName());
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
	protected DataType buildDataType() {
		return new JavaClassDataType();
	}	
}
