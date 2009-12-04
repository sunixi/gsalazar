/**
 * 
 */
package com.angel.code.generator.codeGenerator.impl.java;

import java.util.ArrayList;
import java.util.List;

import com.angel.code.generator.CodesGenerator;
import com.angel.code.generator.codeGenerator.GroupClassGenerator;
import com.angel.code.generator.data.impl.java.ClassDataType;
import com.angel.code.generator.data.impl.java.InterfaceDataType;
import com.angel.code.generator.data.impl.java.JavaType;
import com.angel.code.generator.helpers.PackageHelper;
import com.angel.common.interfaces.Executable;
import com.angel.data.generator.base.DataGeneratorRunner;
import com.angel.data.generator.builders.impl.DataGeneratorAnnotationRunnerBuilder;
import com.angel.object.generator.java.JavaBlockCode;
import com.angel.object.generator.java.JavaLineCode;
import com.angel.object.generator.java.TypeMethod;


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
		InterfaceDataType serviceInterface = super.createJavaInterface();
		serviceInterface.setTypeName(Executable.class.getCanonicalName());
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
	protected JavaType buildJavaType() {
		return new ClassDataType();
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
		TypeMethod executeMethod = super.getJavaType().addTypeMethodPublicVoidWithoutParametersImplemented("execute");
		JavaBlockCode executeMethodContent = executeMethod.getContent();

		JavaLineCode builderCreationJavaLine = executeMethodContent
				.getLineCodeCreateObject("builder", DataGeneratorAnnotationRunnerBuilder.class.getCanonicalName());
		executeMethodContent.addJavaLineCode(builderCreationJavaLine);
		
		for(Class<?> domainClass: domainClasses){
			String domainObjectDataGeneratorCanonicalType = generator.getImportForClassName(domainClass.getSimpleName() + "AnnotationDataGenerator");
			
			List<String> parametersValues = new ArrayList<String>();
			parametersValues.add(PackageHelper.getClassSimpleName(domainObjectDataGeneratorCanonicalType) + ".class");
			JavaLineCode createInstanceAnnotationGeneratorJavaLine = executeMethodContent
				.getLineCodeCalledStaticClassMethod(domainObjectDataGeneratorCanonicalType);

			JavaLineCode addGeneratorJavaLine = executeMethodContent.getLineCodeCalledVariableMethodWithTypeParameters(
					"builder", "addDataGeneratorClass", createInstanceAnnotationGeneratorJavaLine);
			executeMethodContent.addJavaLineCode(addGeneratorJavaLine);
		}
		
		JavaLineCode callBuilderMethod = executeMethodContent.getLineCodeCalledVariableMethod("builder", "buildDataGeneratorRunner", new ArrayList<String>());
		executeMethodContent.addLineCodeAssigmentTypedVariable(DataGeneratorRunner.class.getCanonicalName(),
				"runner", callBuilderMethod);
		JavaLineCode generateDataJavaLineCode = executeMethodContent.getLineCodeCalledVariableMethod("runner", "generateData", new ArrayList<String>());
		JavaLineCode finalizeGeneratorJavaLineCode = executeMethodContent.getLineCodeCalledVariableMethod("runner", "finalizeGenerator", new ArrayList<String>());
		executeMethodContent.addJavaLineCode(generateDataJavaLineCode);
		executeMethodContent.addJavaLineCode(finalizeGeneratorJavaLineCode);
	}	
}