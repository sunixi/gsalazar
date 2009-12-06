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
import com.angel.code.generator.data.impl.java.JavaCodeBlock;
import com.angel.code.generator.data.impl.java.JavaCodeLine;
import com.angel.code.generator.data.impl.java.JavaConstructor;
import com.angel.code.generator.data.impl.java.properties.JavaParameter;
import com.angel.code.generator.data.types.DataMethod;
import com.angel.code.generator.data.types.DataParameter;
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
		DataMethod createDomainObjectEmptyTypeMethod = super.getDataType().createDataMethod(methodName);
		createDomainObjectEmptyTypeMethod.setMethodName(methodName);
		createDomainObjectEmptyTypeMethod.setReturnType(returnParameter);
		//addTypeMethodPublicWithoutParametersImplemented(methodName, returnParameter);
		createDomainObjectEmptyTypeMethod.setStaticTypeModifier();
		JavaCodeBlock blockCode = createDomainObjectEmptyTypeMethod.createCodeBlock();		
		JavaCodeLine createReturnLineCode = blockCode.getLineCodeNewInstanceObject(domainClass.getCanonicalName(), new ArrayList<String>());
		blockCode.addLineCodeReturn(createReturnLineCode);
	}
	
	protected void processCreateDomainObjectFor(CodesGenerator generator, Class<?> domainClass) {
		String methodName = "create" + domainClass.getSimpleName();
		
		List<DataParameter> parameters = new ArrayList<DataParameter>();
		parameters.add(new JavaParameter("quantity", Integer.class.getCanonicalName()));
		DataMethod createDomainObjectForTypeMethod = super.getDataType().createDataMethod(methodName, parameters);
		createDomainObjectForTypeMethod.setMethodName(methodName);
		createDomainObjectForTypeMethod.setReturnType(new JavaParameter(List.class.getCanonicalName()));
		createDomainObjectForTypeMethod.setStaticTypeModifier();

		JavaCodeBlock javaBlockCode = createDomainObjectForTypeMethod.createCodeBlock();
		
		JavaCodeLine collectionTypedCreate = javaBlockCode.getLineCodeCreateCollectionTyped(ArrayList.class.getCanonicalName(), domainClass.getCanonicalName());
		javaBlockCode.addLineCodeAssigmentCollectionTypedVariable(List.class.getCanonicalName(), domainClass.getCanonicalName(), "instances", collectionTypedCreate);
		
		
		List<String> parametersNames = new ArrayList<String>();
		parametersNames.add("create" + domainClass.getSimpleName() + "()");
		JavaCodeLine addInstancesToCollection = javaBlockCode.getLineCodeCalledVariableMethod("instances", "add", parametersNames);
		javaBlockCode.addLineCodeWithFor("int i = 0; i < quantity; i++", addInstancesToCollection);
		javaBlockCode.addLineCodeReturnVariable("instances");
	}
	
	protected void processCreateDomainObject(CodesGenerator generator, Class<?> domainClass) {
		String methodName = "create" + domainClass.getSimpleName();

		DataMethod createDomainObjectEmptyTypeMethod = super.getDataType().createDataMethod(methodName);
		createDomainObjectEmptyTypeMethod.setStaticTypeModifier();
		createDomainObjectEmptyTypeMethod.setReturnType(new JavaParameter(domainClass.getCanonicalName()));
		JavaCodeBlock javaBlockCode = createDomainObjectEmptyTypeMethod.createCodeBlock();
		JavaCodeLine createBeanEmptyLine = javaBlockCode.getLineCodeCalledMethod(methodName + "Empty", new ArrayList<String>());
		javaBlockCode.addLineCodeAssigmentTypedVariable(domainClass.getCanonicalName(), "bean", createBeanEmptyLine);
		
		Field[] fields = ReflectionHelper.getFieldsDeclaredFor(domainClass);
		for(Field field: fields){
			if(field.getModifiers() < Modifier.STATIC){
				this.processFieldForCreateDomainObject(field, generator, domainClass, createDomainObjectEmptyTypeMethod);
			}
		}
		javaBlockCode.addLineCodeReturnVariable("bean");
	}
	
	protected void processFieldForCreateDomainObject(Field field, CodesGenerator generator, Class<?> domainClass, DataMethod method){
		Class<?> classField = field.getType();
		String setterMethodName = ReflectionHelper.getSetMethodName(field.getName());
		boolean isBasicClass = ReflectionHelper.isABasicJavaClass(classField);
		JavaCodeBlock javaBlockCode = (JavaCodeBlock) method.getContent();
		if(isBasicClass){
			List<Object> parametersValues = new ArrayList<Object>();
			if(String.class.equals(classField)){
				parametersValues.add(10);
				JavaCodeLine javaLineCode = javaBlockCode.getLineCodeCalledStaticMethod(RandomStringUtils.class.getCanonicalName(), "randomAlphanumeric", parametersValues.toArray());
				javaBlockCode.addLineCodeVariableSetValue("bean", setterMethodName, javaLineCode);
			}
		} else {
			JavaCodeLine javaLineCode = javaBlockCode.getLineCodeNull();
			javaBlockCode.addLineCodeVariableSetValue("bean", setterMethodName, javaLineCode);
		}
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
