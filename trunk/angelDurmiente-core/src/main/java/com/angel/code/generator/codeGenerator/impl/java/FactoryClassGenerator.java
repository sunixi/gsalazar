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
import com.angel.code.generator.data.impl.java.ClassDataType;
import com.angel.code.generator.data.impl.java.JavaType;
import com.angel.common.helpers.ReflectionHelper;
import com.angel.object.generator.java.JavaBlockCode;
import com.angel.object.generator.java.JavaConstructor;
import com.angel.object.generator.java.JavaLineCode;
import com.angel.object.generator.java.TypeMethod;
import com.angel.object.generator.java.properties.JavaParameter;


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
		JavaConstructor privateConstructor = super.createJavaConstructor();
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
		TypeMethod createDomainObjectEmptyTypeMethod = super.getJavaType().addTypeMethodPublicWithoutParametersImplemented(methodName, returnParameter);
		createDomainObjectEmptyTypeMethod.setStaticTypeModifier();
		JavaBlockCode javaBlockCode = createDomainObjectEmptyTypeMethod.getContent();		
		JavaLineCode createReturnLineCode = javaBlockCode.getLineCodeNewInstanceObject(domainClass.getCanonicalName(), new ArrayList<String>());
		javaBlockCode.addLineCodeReturn(createReturnLineCode);
	}
	
	protected void processCreateDomainObjectFor(CodesGenerator generator, Class<?> domainClass) {
		String methodName = "create" + domainClass.getSimpleName();
		
		List<JavaParameter> parameters = new ArrayList<JavaParameter>();
		parameters.add(new JavaParameter("quantity", Integer.class.getCanonicalName()));
		TypeMethod createDomainObjectForTypeMethod = super.getJavaType().addTypeMethodPublicImplemented(methodName, parameters, new JavaParameter(List.class.getCanonicalName()));
		createDomainObjectForTypeMethod.setStaticTypeModifier();
		JavaBlockCode javaBlockCode = createDomainObjectForTypeMethod.getContent();
		
		JavaLineCode collectionTypedCreate = javaBlockCode.getLineCodeCreateCollectionTyped(ArrayList.class.getCanonicalName(), domainClass.getCanonicalName());
		javaBlockCode.addLineCodeAssigmentCollectionTypedVariable(List.class.getCanonicalName(), domainClass.getCanonicalName(), "instances", collectionTypedCreate);
		
		
		List<String> parametersNames = new ArrayList<String>();
		parametersNames.add("create" + domainClass.getSimpleName() + "()");
		JavaLineCode addInstancesToCollection = javaBlockCode.getLineCodeCalledVariableMethod("instances", "add", parametersNames);
		javaBlockCode.addLineCodeWithFor("int i = 0; i < quantity; i++", addInstancesToCollection);
		javaBlockCode.addLineCodeReturnVariable("instances");
	}
	
	protected void processCreateDomainObject(CodesGenerator generator, Class<?> domainClass) {
		String methodName = "create" + domainClass.getSimpleName();
		JavaParameter returnParameter = new JavaParameter(domainClass.getCanonicalName());
		TypeMethod createDomainObjectEmptyTypeMethod = super.getJavaType().addTypeMethodPublicWithoutParametersImplemented(methodName, returnParameter);
		createDomainObjectEmptyTypeMethod.setStaticTypeModifier();
		JavaBlockCode javaBlockCode = createDomainObjectEmptyTypeMethod.getContent();
		JavaLineCode createBeanEmptyLine = javaBlockCode.getLineCodeCalledMethod(methodName + "Empty", new ArrayList<String>());
		javaBlockCode.addLineCodeAssigmentTypedVariable(domainClass.getCanonicalName(), "bean", createBeanEmptyLine);
		
		Field[] fields = ReflectionHelper.getFieldsDeclaredFor(domainClass);
		for(Field field: fields){
			if(field.getModifiers() < Modifier.STATIC){
				this.processFieldForCreateDomainObject(field, generator, domainClass, createDomainObjectEmptyTypeMethod);
			}
		}
		javaBlockCode.addLineCodeReturnVariable("bean");
	}
	
	protected void processFieldForCreateDomainObject(Field field, CodesGenerator generator, Class<?> domainClass, TypeMethod method){
		Class<?> classField = field.getType();
		String setterMethodName = ReflectionHelper.getSetMethodName(field.getName());
		boolean isBasicClass = ReflectionHelper.isABasicJavaClass(classField);
		JavaBlockCode javaBlockCode = method.getContent();
		if(isBasicClass){
			List<Object> parametersValues = new ArrayList<Object>();
			if(String.class.equals(classField)){
				parametersValues.add(10);
				JavaLineCode javaLineCode = javaBlockCode.getLineCodeCalledStaticMethod(RandomStringUtils.class.getCanonicalName(), "randomAlphanumeric", parametersValues.toArray());
				javaBlockCode.addLineCodeVariableSetValue("bean", setterMethodName, javaLineCode);
			}
		} else {
			JavaLineCode javaLineCode = javaBlockCode.getLineCodeNull();
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
	protected JavaType buildJavaType() {
		return new ClassDataType();
	}	
}
