/**
 * 
 */
package com.angel.code.generator.codeGenerator.impl.java;

import java.util.ArrayList;
import java.util.List;

import com.angel.code.generator.CodesGenerator;
import com.angel.code.generator.codeGenerator.GroupClassGenerator;
import com.angel.code.generator.data.DataType;
import com.angel.code.generator.data.impl.java.JavaClassDataType;
import com.angel.code.generator.data.impl.java.JavaCodeLine;
import com.angel.code.generator.data.impl.java.JavaDataMethod;
import com.angel.code.generator.data.types.CodeBlock;
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

		CodeBlock executeMethodContent = executeMethod.createCodeBlock();

		JavaCodeLine builderCreationJavaLine = executeMethodContent
				.getLineCodeCreateObject("builder", DataGeneratorAnnotationRunnerBuilder.class.getCanonicalName());
		executeMethodContent.addCodeLine(builderCreationJavaLine);
		
		for(Class<?> domainClass: domainClasses){
			String domainObjectDataGeneratorCanonicalType = generator.getImportForClassName(domainClass.getSimpleName() + "AnnotationDataGenerator");
			
			List<String> parametersValues = new ArrayList<String>();
			parametersValues.add(PackageHelper.getClassSimpleName(domainObjectDataGeneratorCanonicalType) + ".class");
			JavaCodeLine createInstanceAnnotationGeneratorJavaLine = executeMethodContent
				.getLineCodeCalledStaticClassMethod(domainObjectDataGeneratorCanonicalType);

			JavaCodeLine addGeneratorJavaLine = executeMethodContent.getLineCodeCalledVariableMethodWithTypeParameters(
					"builder", "addDataGeneratorClass", createInstanceAnnotationGeneratorJavaLine);
			executeMethodContent.addCodeLine(addGeneratorJavaLine);
		}
		
		JavaCodeLine callBuilderMethod = executeMethodContent.getLineCodeCalledVariableMethod("builder", "buildDataGeneratorRunner", new ArrayList<String>());
		executeMethodContent.addLineCodeAssigmentTypedVariable(DataGeneratorRunner.class.getCanonicalName(),
				"runner", callBuilderMethod);
		JavaCodeLine generateDataJavaLineCode = executeMethodContent.getLineCodeCalledVariableMethod("runner", "generateData", new ArrayList<String>());
		JavaCodeLine finalizeGeneratorJavaLineCode = executeMethodContent.getLineCodeCalledVariableMethod("runner", "finalizeGenerator", new ArrayList<String>());
		executeMethodContent.addCodeLine(generateDataJavaLineCode);
		executeMethodContent.addCodeLine(finalizeGeneratorJavaLineCode);
	}	
}
