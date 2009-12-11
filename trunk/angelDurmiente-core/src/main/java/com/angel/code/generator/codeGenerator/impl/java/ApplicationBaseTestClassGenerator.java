/**
 * 
 */
package com.angel.code.generator.codeGenerator.impl.java;

import java.util.List;

import org.junit.Test;

import com.angel.code.generator.CodesGenerator;
import com.angel.code.generator.codeGenerator.GroupClassGenerator;
import com.angel.code.generator.data.DataType;
import com.angel.code.generator.data.enums.Visibility;
import com.angel.code.generator.data.impl.java.JavaClassDataType;
import com.angel.code.generator.data.impl.java.JavaDataMethod;
import com.angel.code.generator.data.impl.java.properties.JavaParameter;
import com.angel.code.generator.data.impl.java.properties.JavaProperty;
import com.angel.code.generator.data.types.CodeBlock;
import com.angel.code.generator.data.types.DataMethod;
import com.angel.code.generator.data.types.codeLine.CommentCodeLine;
import com.angel.code.generator.data.types.codeLine.ExecutableReturnCodeLine;
import com.angel.code.generator.data.types.codeLine.ExecutableReturnVariableCodeLine;
import com.angel.code.generator.data.types.codeLine.ReturnableCodeLine;
import com.angel.code.generator.data.types.impl.DataCommentImpl;
import com.angel.test.GenericSpringTestCase;


/**
 * @author Guillermo Salazar.
 * @since 27/Noviembre/2009.
 *
 */
public class ApplicationBaseTestClassGenerator extends GroupClassGenerator {

	protected final static String DEFAULT_BASE_TEST_SOURCE_DIRECTORY = "\\src\\main\\test\\";

	public ApplicationBaseTestClassGenerator(String basePackage) {
		super(basePackage);
		super.setBaseSourcesDirectory(DEFAULT_BASE_TEST_SOURCE_DIRECTORY);
	}

	@Override
	public DataType buildSubClassForClassGenerator(DataType subDataType){
		subDataType.setCanonicalName(GenericSpringTestCase.class.getCanonicalName());
		return subDataType;
	}

	@Override
	protected DataType buildDataType() {
		return new JavaClassDataType();
	}

	@Override
	protected String buildClassName(List<Class<?>> domainClass) {
		return "ApplicationBaseTestCase";
	}

	@Override
	protected void generateContentClass(CodesGenerator generator, List<Class<?>> domainClasses) {
		String applicationInitials = generator.getApplicationInitials();
		
		JavaProperty dataProperty = super.getDataType().createDataProperty("testContexts");
		dataProperty.setFinalStaticTypeModifier();
		dataProperty.setCanonicalType(String.class.getCanonicalName());
		dataProperty.setArrayType();
		
		String[] applicationContexts = new String[]{
				"\"classpath:applicationContext-" + applicationInitials + "flex.xml\"",
				"\"classpath:applicationContext-" + applicationInitials + "daos.xml\"",
				"\"classpath:applicationContext-" + applicationInitials + "services.xml\"",
				"\"classpath:applicationContext-" + applicationInitials + "model.xml\""
		};
		dataProperty.setPropertyValueArray(applicationContexts);
		
		DataMethod mockTestMethod = super.getDataType().createDataMethod("testNothing");
		mockTestMethod.setComment(new DataCommentImpl("Test mock para toda la jerarquía de test cases de la aplicación."));
		mockTestMethod.createAnnotation(Test.class.getCanonicalName());
		CodeBlock mockTestCodeBlock = mockTestMethod.getContent();
		mockTestCodeBlock.addCodeLine(new CommentCodeLine("Do nothing."));
		
		JavaDataMethod dataMethod = super.getDataType().createDataMethod("getOtherContextsApplicationFiles");
		dataMethod.setVisibility(Visibility.PROTECTED);
		dataMethod.createAnnotation(Override.class.getCanonicalName());
		JavaParameter returnParameter = dataMethod.createReturnParameter(String.class.getCanonicalName());
		returnParameter.setArrayType();
		
		CodeBlock codeBlock = dataMethod.getContent();
		ExecutableReturnCodeLine executableReturnCodeLine = 
			new ExecutableReturnVariableCodeLine(
					"testContexts", String.class.getCanonicalName());
		ReturnableCodeLine returnableCodeLine = new ReturnableCodeLine(
				String.class.getCanonicalName(),
				executableReturnCodeLine);
		codeBlock.addCodeLine(returnableCodeLine);
		/*
		@Override
		protected String[] getOtherContextsApplicationFiles() {
			// TODO Auto-generated method stub
			return null;
		}*/
	}

	@Override
	protected void updateCurrentJavaType(CodesGenerator generator, List<Class<?>> domainClasses) {
		//Do nothing.
	}	
}
