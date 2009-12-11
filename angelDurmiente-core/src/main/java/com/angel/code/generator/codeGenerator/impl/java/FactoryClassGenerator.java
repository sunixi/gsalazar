/**
 * 
 */
package com.angel.code.generator.codeGenerator.impl.java;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.RandomStringUtils;

import com.angel.code.generator.CodesGenerator;
import com.angel.code.generator.codeGenerator.ClassGenerator;
import com.angel.code.generator.data.DataType;
import com.angel.code.generator.data.impl.java.JavaClassDataType;
import com.angel.code.generator.data.impl.java.JavaConstructor;
import com.angel.code.generator.data.impl.java.JavaDataMethod;
import com.angel.code.generator.data.impl.java.properties.JavaParameter;
import com.angel.code.generator.data.types.CodeBlock;
import com.angel.code.generator.data.types.DataMethod;
import com.angel.code.generator.data.types.DataParameter;
import com.angel.code.generator.data.types.codeLine.AssignableCodeLine;
import com.angel.code.generator.data.types.codeLine.ControlStructureCodeLine;
import com.angel.code.generator.data.types.codeLine.ExecutableCodeLine;
import com.angel.code.generator.data.types.codeLine.ExecutableReturnCodeLine;
import com.angel.code.generator.data.types.codeLine.ExecutableReturnNewInstanceCodeLine;
import com.angel.code.generator.data.types.codeLine.ExecutableReturnVariableCodeLine;
import com.angel.code.generator.data.types.codeLine.ReturnableCodeLine;
import com.angel.code.generator.helpers.PackageHelper;
import com.angel.common.helpers.ReflectionHelper;


/**
 * @author Guillermo Salazar.
 * @since 27/Noviembre/2009.
 *
 */
public class FactoryClassGenerator extends ClassGenerator {

	public FactoryClassGenerator(String basePackage) {
		super(basePackage);
	}

	@Override
	protected void generateContentClass(CodesGenerator generator, Class<?> domainClass) {
		this.processPrivateConstructor();
		this.processCreateDomainObjectEmpty(generator, domainClass);
		this.processCreateDomainObject(generator, domainClass);
		this.processCreateDomainObjectFor(generator, domainClass);
	}

	/**
	 * Add a private constructor to use only static factory methods.
	 * <pre>
	 *	private DomainObject(){
	 *		super();
	 *	}
	 * </pre>
	 */
	protected void processPrivateConstructor() {
		JavaConstructor privateConstructor = (JavaConstructor) ((JavaClassDataType)super.getDataType()).createDataConstructor();
		privateConstructor.setPrivateVisibility();
	}

	/**
	 * Add a static method for create a domain object empty. Domain object is created with default constructor, so domain object 
	 * should have a constructor without parameters. This method created is something like this example:
	 * 
	 * <pre>
	 * 	public static DomainObject createDomainObjectEmpty(){
	 * 		return new DomainObject();
	 * 	}
	 * </pre>
	 *  
	 * @param generator classes with all class generator to use.
	 * @param domainClass to add this static factory method.
	 */
	protected void processCreateDomainObjectEmpty(CodesGenerator generator,
			Class<?> domainClass) {
		String methodName = "create" + domainClass.getSimpleName() + "Empty";
		JavaParameter returnParameter = new JavaParameter(domainClass.getCanonicalName());
		JavaDataMethod createDomainObjectEmptyTypeMethod = super.getDataType().createDataMethod(methodName);
		createDomainObjectEmptyTypeMethod.setMethodName(methodName);
		createDomainObjectEmptyTypeMethod.setReturnType(returnParameter);
		createDomainObjectEmptyTypeMethod.setStaticTypeModifier();
		CodeBlock blockCode = createDomainObjectEmptyTypeMethod.createCodeBlock();
		blockCode.addCodeLine(new ReturnableCodeLine(domainClass.getCanonicalName(), new ExecutableReturnNewInstanceCodeLine(domainClass.getCanonicalName())));
	}

	protected void processCreateDomainObjectFor(CodesGenerator generator, Class<?> domainClass) {
		String methodName = "create" + domainClass.getSimpleName();
		
		List<DataParameter> parameters = new ArrayList<DataParameter>();
		parameters.add(new JavaParameter("quantity", Integer.class.getCanonicalName()));
		JavaDataMethod createDomainObjectForTypeMethod = super.getDataType().createDataMethod(methodName, parameters);
		createDomainObjectForTypeMethod.setMethodName(methodName);
		createDomainObjectForTypeMethod.setReturnType(new JavaParameter(List.class.getCanonicalName()));
		createDomainObjectForTypeMethod.setStaticTypeModifier();

		CodeBlock codeBlock = createDomainObjectForTypeMethod.createCodeBlock();

		AssignableCodeLine instancesVariable = 
			new AssignableCodeLine("instances", new ExecutableReturnNewInstanceCodeLine(domainClass.getCanonicalName(), ArrayList.class.getCanonicalName()),
					domainClass.getCanonicalName(), ArrayList.class.getCanonicalName());
		codeBlock.addCodeLine(instancesVariable);
		ControlStructureCodeLine forControlStructure = new ControlStructureCodeLine("for", "int i = 0; i < quantity; i++");
		
		ExecutableCodeLine addToCollectionDomainObject = new ExecutableReturnCodeLine("add", "");
		addToCollectionDomainObject.setVariableName("instances");
		addToCollectionDomainObject.addParameterName("create" + domainClass.getSimpleName() + "()");
		forControlStructure.addCodeLines(addToCollectionDomainObject);
		codeBlock.addCodeLine(forControlStructure);
		
		codeBlock.addCodeLine(
				new ReturnableCodeLine(
						domainClass.getCanonicalName(),
						List.class.getCanonicalName(),
						new ExecutableReturnVariableCodeLine(
								"instances",
								domainClass.getCanonicalName(), List.class.getCanonicalName()
								)
						)
						
				);
		
	}
	
	protected void processCreateDomainObject(CodesGenerator generator, Class<?> domainClass) {
		String methodName = "create" + domainClass.getSimpleName();

		JavaDataMethod createDomainObjectEmptyTypeMethod = super.getDataType().createDataMethod(methodName);
		createDomainObjectEmptyTypeMethod.setStaticTypeModifier();
		createDomainObjectEmptyTypeMethod.setReturnType(new JavaParameter(domainClass.getCanonicalName()));
		CodeBlock codeBlock = createDomainObjectEmptyTypeMethod.createCodeBlock();
		
		AssignableCodeLine assignableCodeLine = new AssignableCodeLine(domainClass.getCanonicalName(), 
				new ExecutableReturnNewInstanceCodeLine(domainClass.getCanonicalName()));
		codeBlock.addCodeLine(assignableCodeLine);
		
		Field[] fields = ReflectionHelper.getFieldsDeclaredFor(domainClass);
		for(Field field: fields){
			if(field.getModifiers() < Modifier.STATIC){
				this.processFieldForCreateDomainObject(field, generator, domainClass, createDomainObjectEmptyTypeMethod);
			}
		}

		codeBlock.addCodeLine(
				new ReturnableCodeLine(domainClass.getCanonicalName(), 
				new ExecutableReturnVariableCodeLine(
						PackageHelper.getClassSimpleVariableName(domainClass.getCanonicalName()), 
						domainClass.getCanonicalName())
				)
		);
	}

	protected void processFieldForCreateDomainObject(Field field, CodesGenerator generator, Class<?> domainClass, DataMethod method){
		Class<?> classField = field.getType();
		//String simpleClassField = PackageHelper.getClassSimpleName(classField.getCanonicalName());
		String setterMethodName = ReflectionHelper.getSetMethodName(field.getName());
		//boolean isBasicClass = ReflectionHelper.isABasicJavaClass(classField);
		//boolean isPrimitive = ImportsHelper.isJavaPrimitiveType(field.getType().getCanonicalName());
		CodeBlock codeBlock = method.getContent();
		ExecutableCodeLine executableCodeLine = null;
		if(String.class.equals(classField)){
			executableCodeLine = new ExecutableCodeLine(setterMethodName, PackageHelper.getClassSimpleVariableName(domainClass.getSimpleName()));  
			executableCodeLine.addParameterName("RandomStringUtils.randomAlphabetic(10)")
				.addGlobalImport(RandomStringUtils.class.getCanonicalName());
		} else if(Integer.class.equals(classField) || int.class.equals(classField)
				|| Double.class.equals(classField) || double.class.equals(classField)
				|| Float.class.equals(classField) || float.class.equals(classField)){
			executableCodeLine = new ExecutableCodeLine(setterMethodName, PackageHelper.getClassSimpleVariableName(domainClass.getSimpleName()));  
			executableCodeLine.addParameterName(
					"Integer.valueOf(RandomStringUtils.randomNumeric(5))")
					.addGlobalImport(RandomStringUtils.class.getCanonicalName());				
		} else if(Character.class.equals(classField) || char.class.equals(classField)) {
			executableCodeLine = new ExecutableCodeLine(setterMethodName, PackageHelper.getClassSimpleVariableName(domainClass.getSimpleName()));
			executableCodeLine.addParameterName("(char) RandomStringUtils.randomNumeric(1)");
		} else {
			executableCodeLine = new ExecutableCodeLine(setterMethodName, PackageHelper.getClassSimpleVariableName(domainClass.getSimpleName()));
			executableCodeLine.addParameterName("null");
		}
		codeBlock.addCodeLine(executableCodeLine);
	}

	@Override
	protected String buildClassName(Class<?> domainClass) {
		return domainClass.getSimpleName() + "Factory";
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
