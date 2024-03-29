/**
 * 
 */
package com.angel.code.generator.codeGenerator.impl.java;

import java.util.List;

import com.angel.code.generator.CodesGenerator;
import com.angel.code.generator.codeGenerator.GroupClassGenerator;
import com.angel.code.generator.data.DataType;
import com.angel.code.generator.data.impl.java.JavaClassDataType;
import com.angel.code.generator.data.impl.java.JavaDataMethod;
import com.angel.code.generator.data.types.CodeBlock;
import com.angel.code.generator.data.types.codeLine.AssignableCodeLine;
import com.angel.code.generator.data.types.codeLine.ExecutableCodeLine;
import com.angel.code.generator.data.types.codeLine.ExecutableReturnCodeLine;
import com.angel.code.generator.data.types.codeLine.ExecutableReturnNewInstanceCodeLine;
import com.angel.code.generator.helpers.PackageHelper;
import com.angel.common.interfaces.Executable;
import com.angel.data.generator.base.DataGeneratorRunner;
import com.angel.data.generator.builders.impl.DataGeneratorAnnotationRunnerBuilder;


/**
 * @author Guillermo Salazar.
 * @since 27/Noviembre/2009.
 *
 */
public class ApplicationAnnotationGeneratorExecutableClassGenerator extends GroupClassGenerator {

	public ApplicationAnnotationGeneratorExecutableClassGenerator(String basePackage) {
		super(basePackage);
	}

	@Override
	protected void processJavaTypeInterfaces(CodesGenerator generator){
		super.getDataType().createDataInterface(Executable.class.getCanonicalName());
	}
	

	@Override
	protected String buildClassName(List<Class<?>> domainClasses) {
		return "ApplicationDataAnnotationGeneratorExecutable";
	}

	@Override
	protected void updateCurrentJavaType(CodesGenerator generator, List<Class<?>> domainClasses) {
		// Do nothing.
	}

	@Override
	protected DataType buildDataType() {
		return new JavaClassDataType();
	}

	/*
		DataGeneratorAnnotationRunnerBuilder builder = new DataGeneratorAnnotationRunnerBuilder();
		builder.addDataGeneratorClass(UsuarioAnnotationDataGenerator.class);
		builder.addDataGeneratorClass(AlbumAnnotationDataGenerator.class);
		DataGeneratorRunner runner = builder.buildDataGeneratorRunner();
		runner.generateData();
		runner.finalizeGenerator();
	 */
	@Override
	protected void generateContentClass(CodesGenerator generator, List<Class<?>> domainClasses) {
		JavaDataMethod executeMethod = super.getDataType().createDataMethod("execute");
		CodeBlock executeCodeBlock = executeMethod.createCodeBlock();
		
		AssignableCodeLine builderAssignable = new AssignableCodeLine(
				DataGeneratorAnnotationRunnerBuilder.class.getCanonicalName(),
				new ExecutableReturnNewInstanceCodeLine(DataGeneratorAnnotationRunnerBuilder.class.getCanonicalName()));
		executeCodeBlock.addCodeLine(builderAssignable);

		for(Class<?> domainClass: domainClasses){
			ExecutableCodeLine addDataGeneratorClassCodeLine = new ExecutableCodeLine("addDataGeneratorClass", "dataGeneratorAnnotationRunnerBuilder");
			String annotationDataGeneratorCanonicalName = generator.getImportForClassName(domainClass.getSimpleName() + "AnnotationDataGenerator");
			addDataGeneratorClassCodeLine.addParameterNameClass(PackageHelper.getClassSimpleName(annotationDataGeneratorCanonicalName));
			addDataGeneratorClassCodeLine.addGlobalImport(annotationDataGeneratorCanonicalName);
			executeCodeBlock.addCodeLine(addDataGeneratorClassCodeLine);
		}

		ExecutableReturnCodeLine executableBuildDataGeneratorRunner = new ExecutableReturnCodeLine("buildDataGeneratorRunner", DataGeneratorRunner.class.getCanonicalName());
		executableBuildDataGeneratorRunner.setVariableName("dataGeneratorAnnotationRunnerBuilder");
		AssignableCodeLine runnerAssignable = new AssignableCodeLine(
				DataGeneratorRunner.class.getCanonicalName(), executableBuildDataGeneratorRunner );
		
		executeCodeBlock.addCodeLine(runnerAssignable);
		
		executeCodeBlock.addCodeLine(new ExecutableCodeLine("generateData", "dataGeneratorRunner"));
		executeCodeBlock.addCodeLine(new ExecutableCodeLine("finalizeGenerator", "dataGeneratorRunner"));
	}	
}
