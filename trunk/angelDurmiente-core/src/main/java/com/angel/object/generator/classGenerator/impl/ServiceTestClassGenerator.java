/**
 * 
 */
package com.angel.object.generator.classGenerator.impl;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import org.springframework.beans.factory.annotation.Autowired;

import ar.com.angelDurmiente.AngelDurmienteBaseTestCase;

import com.angel.common.helpers.ReflectionHelper;
import com.angel.object.generator.ClassesGenerator;
import com.angel.object.generator.annotations.Accesor;
import com.angel.object.generator.classGenerator.ClassGenerator;
import com.angel.object.generator.java.JavaBlockCode;
import com.angel.object.generator.java.JavaProperty;
import com.angel.object.generator.java.types.JavaClass;
import com.angel.object.generator.java.types.JavaType;
import com.angel.object.generator.methodBuilder.MethodBuilder;
import com.angel.object.generator.methodBuilder.impl.AccesorServiceTestAnnotationMethodBuilder;


/**
 * @author Guillermo Salazar.
 * @since 27/Noviembre/2009.
 *
 */
public class ServiceTestClassGenerator extends ClassGenerator {

	public ServiceTestClassGenerator(String basePackage) {
		super(basePackage);
		this.getMethodBuilderStrategies().put(Accesor.class, new AccesorServiceTestAnnotationMethodBuilder());
	}

	@Override
	public JavaType buildSubClassForClassGenerator(JavaType subjavaType){
		subjavaType.setTypeName(AngelDurmienteBaseTestCase.class.getCanonicalName());
		return subjavaType;
	}

	protected void buildInterfacesClasses(){
		//Do nothing.
	}
	
	@Override
	protected void generateContentClass(ClassesGenerator generator, Class<?> domainClass) {
		this.buildServiceProperty(generator, domainClass);
		
		Field[] fields = ReflectionHelper.getFieldsDeclaredFor(domainClass);
		for(Field f : fields){
			if(f.getModifiers() < Modifier.STATIC){
				MethodBuilder methodBuilder = super.getMethodBuilderFor(f);
				String methodName = methodBuilder.buildMethodName(domainClass, f);
				JavaBlockCode contentMethod = methodBuilder.buildMethodContent(domainClass, f);
				JavaBlockCode methodBlockCode = super.getJavaType().addTypeMethodPublicVoidWithoutParametersImplemented(methodName);
				methodBlockCode.replaceBlockCode(contentMethod);
			}
		}
	}

	protected void buildServiceProperty(ClassesGenerator generator, Class<?> domainClass) {
		String propertyName = domainClass.getSimpleName() + "Service";
		String propertyType = generator.getImportForClassName(propertyName);
		JavaProperty javaProperty = super.createJavaProperty(propertyName, propertyType);
		javaProperty.addAnnotation(Autowired.class.getCanonicalName());
	}

	@Override
	protected String buildClassName(Class<?> domainClass) {
		return domainClass.getSimpleName() + "ServiceTest";
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
