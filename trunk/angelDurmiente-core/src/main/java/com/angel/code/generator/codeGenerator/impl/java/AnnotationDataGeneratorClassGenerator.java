/**
 * 
 */
package com.angel.code.generator.codeGenerator.impl.java;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import com.angel.code.generator.CodesGenerator;
import com.angel.code.generator.annotations.Accesor;
import com.angel.code.generator.builders.method.impl.AccesorServiceImplAnnotationMethodBuilder;
import com.angel.code.generator.codeGenerator.ClassGenerator;
import com.angel.code.generator.data.DataType;
import com.angel.code.generator.data.impl.java.JavaClassDataType;
import com.angel.code.generator.data.impl.java.JavaDataMethod;
import com.angel.code.generator.data.impl.java.annotations.JavaAnnotation;
import com.angel.code.generator.data.impl.java.properties.JavaParameter;
import com.angel.code.generator.data.types.CodeBlock;
import com.angel.code.generator.data.types.DataParameter;
import com.angel.code.generator.data.types.codeLine.AssignableCodeLine;
import com.angel.code.generator.data.types.codeLine.CommentCodeLine;
import com.angel.code.generator.data.types.codeLine.ControlStructureCodeLine;
import com.angel.code.generator.data.types.codeLine.ExecutableReturnCodeLine;
import com.angel.code.generator.data.types.codeLine.ExecutableReturnNewInstanceCodeLine;
import com.angel.code.generator.data.types.codeLine.ThrowExceptionCodeLine;
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

		CodeBlock prepareInputStreamMethodConent = (CodeBlock) prepareImportFileTypeMethod.createCodeBlock();
		CommentCodeLine commentCodeLine = new CommentCodeLine();
		commentCodeLine.setTODOComment("Change file name.").addNewLine().setTODOComment("Can create a static class with file names.");
		prepareInputStreamMethodConent.addCodeLine(commentCodeLine);
		
		String initialDataFile = "\"/initialData/" + domainClass.getSimpleName() + ".xls\"";
		List<String> parametersNames = new ArrayList<String>();
		parametersNames.add(initialDataFile);
		
		ExecutableReturnCodeLine executableReturnCodeLine = prepareInputStreamMethodConent.createExecutableReturnCodeLine("findInputStreamInClasspath", InputStream.class.getCanonicalName());
		executableReturnCodeLine.setParametersNames(parametersNames);
		prepareInputStreamMethodConent.createReturnableCodeLine(InputStream.class.getCanonicalName(), executableReturnCodeLine);

		ControlStructureCodeLine tryCodeLine = new ControlStructureCodeLine("try");
		tryCodeLine.addCodeLines(executableReturnCodeLine);

		ThrowExceptionCodeLine throwDataGeneratorException = new ThrowExceptionCodeLine(DataGeneratorException.class.getCanonicalName(),
				"File not found [\" + " + initialDataFile + "+ \"].", "e");
		
		ControlStructureCodeLine catchCodeLine = new ControlStructureCodeLine("catch", "FileNotFoundException e");
		catchCodeLine.addCodeLines(throwDataGeneratorException);

		prepareInputStreamMethodConent.addCodeLine(tryCodeLine);
		prepareInputStreamMethodConent.addCodeLine(catchCodeLine);
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

		CodeBlock prepareImportFileContent = prepareImportFileTypeMethod.createCodeBlock();
		
		ExecutableReturnNewInstanceCodeLine excelFileProcessorCommandInstance = new ExecutableReturnNewInstanceCodeLine(ExcelFileProcessorCommand.class.getCanonicalName());		
		AssignableCodeLine excelFileProcessorCommandCodeLine = new AssignableCodeLine("excelFileProcessorCommand", excelFileProcessorCommandInstance, ExcelFileProcessorCommand.class.getCanonicalName());
		prepareImportFileContent.addCodeLine(excelFileProcessorCommandCodeLine);

		String domainObjectRowProcessorCanonicalType = generator.getImportForClassName( domainClass.getSimpleName() + "AnnotationRowProcessorCommand");
		ExecutableReturnNewInstanceCodeLine domainObjectRowProcessorInstance = new ExecutableReturnNewInstanceCodeLine(ExcelFileProcessorCommand.class.getCanonicalName());		
		AssignableCodeLine domainObjectRowProcessorInstanceCodeLine = new AssignableCodeLine("domainObjectRowProcessor", domainObjectRowProcessorInstance, domainObjectRowProcessorCanonicalType);
		prepareImportFileContent.addCodeLine(domainObjectRowProcessorInstanceCodeLine);
		
		ExecutableReturnNewInstanceCodeLine createImportFileAnnotationProcessorRunner = new ExecutableReturnNewInstanceCodeLine(ImportFileAnnotationProcessorRunner.class.getCanonicalName());
		createImportFileAnnotationProcessorRunner.addParameterName("fileProcessorDescriptor").addParameterName("excelFileProcessorCommand").addParameterName("domainObjectRowProcessor");
		AssignableCodeLine assignableCodeLine = new AssignableCodeLine("importFile", createImportFileAnnotationProcessorRunner, ImportFileAnnotationProcessorRunner.class.getCanonicalName());

		prepareImportFileContent.addCodeLine(assignableCodeLine);
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
