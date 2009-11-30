/**
 * 
 */
package com.angel.object.generator.classGenerator.impl;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.List;

import javax.persistence.Column;

import com.angel.architecture.services.impl.GenericServiceImpl;
import com.angel.common.helpers.ReflectionHelper;
import com.angel.common.helpers.StringHelper;
import com.angel.object.generator.ClassesGenerator;
import com.angel.object.generator.annotations.Accesor;
import com.angel.object.generator.classGenerator.ClassGenerator;
import com.angel.object.generator.java.JavaParameter;
import com.angel.object.generator.java.types.JavaClass;
import com.angel.object.generator.java.types.JavaInterface;
import com.angel.object.generator.java.types.JavaType;
import com.angel.object.generator.methodBuilder.MethodBuilder;
import com.angel.object.generator.methodBuilder.impl.AccesorAnnotationMethodBuilder;
import com.angel.object.generator.methodBuilder.impl.ColumnAnnotationMethodBuilder;


/**
 * @author Guillermo Salazar.
 * @since 27/Noviembre/2009.
 *
 */
public class ServiceImplClassGenerator extends ClassGenerator {

	public ServiceImplClassGenerator(String basePackage) {
		super(basePackage);
		this.getMethodBuilderStrategies().put(Accesor.class, new AccesorAnnotationMethodBuilder());
		this.getMethodBuilderStrategies().put(Column.class, new ColumnAnnotationMethodBuilder());
	}
	
	public ServiceImplClassGenerator(String basePackage, ClassGenerator interfaceClassGenerator) {
		this(basePackage);
		this.setInterfaceClassGenerator(interfaceClassGenerator);
	}

	@Override
	public JavaType buildSubClassForClassGenerator(JavaType subjavaType){
		subjavaType.setTypeName(GenericServiceImpl.class.getCanonicalName());
		return subjavaType;
	}

	protected void buildInterfacesClasses(){
		JavaInterface serviceInterface = super.createJavaInterface();
		serviceInterface.setTypeName(super.getDomainObjectSimpleName() + "Service");
	}
	
	@Override
	protected void generateContentClass(ClassesGenerator generator, Class<?> domainClass) {
		this.buildGenericDAOGetter(generator, domainClass);
		
		Field[] fields = ReflectionHelper.getFieldsDeclaredFor(domainClass);
		for(Field f : fields){
			if(f.getModifiers() < Modifier.STATIC){
				MethodBuilder methodBuilder = super.getMethodBuilderFor(f);

				String methodName = methodBuilder.buildMethodName(domainClass, f);
				List<JavaParameter> javaParameters = methodBuilder.buildJavaParameters(domainClass, f);
				JavaParameter returnParameter = methodBuilder.buildReturnParameter(domainClass, f);

				String contentMethod = "return this." + 
					ReflectionHelper.getGetMethodName(domainClass.getSimpleName() + "DAO") + "()." +
					methodName + "(" +
					StringHelper.convertToPlainString(javaParameters.toArray(), ",") + ");";

				super.getJavaType().addTypeMethodPublicImplemented(methodName, javaParameters, returnParameter, contentMethod);
			}
		}
	}

	protected void buildGenericDAOGetter(ClassesGenerator generator, Class<?> domainClass) {
		String contentMethod = "return (" + domainClass.getSimpleName() + "DAO" + ") super.getGenericDAO();";
		String domainClassDAO = domainClass.getSimpleName() + "DAO";
		String methodName = ReflectionHelper.getGetMethodName(domainClassDAO);
		JavaParameter returnParameter = super.getJavaType().buildReturnJavaParameter(domainClassDAO);
		returnParameter.notImportType();
		super.getJavaType().addTypeMethodProtectedImplementedWithoutParameters(methodName, returnParameter, contentMethod);
		
		String importClassName = generator.getImportForClassName(domainClassDAO);
		super.getJavaType().addImport(importClassName);
	}

	@Override
	protected String buildClassName(Class<?> domainClass) {
		return domainClass.getSimpleName() + "ServiceImpl";
	}

	@Override
	protected void updateCurrentJavaType(ClassesGenerator generator, Class<?> domainClass) {
		// TODO 
	}

	@Override
	protected JavaType buildJavaType() {
		return new JavaClass();
	}	
}
