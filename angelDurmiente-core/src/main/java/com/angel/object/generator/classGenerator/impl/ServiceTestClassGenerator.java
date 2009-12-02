/**
 * 
 */
package com.angel.object.generator.classGenerator.impl;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import org.springframework.beans.factory.annotation.Autowired;

import ar.com.angelDurmiente.AngelDurmienteBaseTestCase;

import com.angel.common.helpers.ReflectionHelper;
import com.angel.object.generator.CodesGenerator;
import com.angel.object.generator.annotations.Accesor;
import com.angel.object.generator.classGenerator.ClassGenerator;
import com.angel.object.generator.java.JavaBlockCode;
import com.angel.object.generator.java.TypeMethod;
import com.angel.object.generator.java.properties.JavaProperty;
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

	protected final static String DEFAULT_BASE_TEST_SOURCE_DIRECTORY = "\\src\\main\\test\\";

	public ServiceTestClassGenerator(String basePackage) {
		super(basePackage);
		super.setBaseSourcesDirectory(DEFAULT_BASE_TEST_SOURCE_DIRECTORY);
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
	protected void generateContentClass(CodesGenerator generator, Class<?> domainClass) {
		this.buildServiceProperty(generator, domainClass);
		
		Field[] fields = ReflectionHelper.getFieldsDeclaredFor(domainClass);
		for(Field f : fields){
			if(f.getModifiers() < Modifier.STATIC){
				MethodBuilder methodBuilder = super.getMethodBuilderFor(f);
				String methodName = methodBuilder.buildMethodName(domainClass, f);
				JavaBlockCode contentMethod = methodBuilder.buildMethodContent(domainClass, f);
				TypeMethod typeMethod = super.getJavaType().addTypeMethodPublicVoidWithoutParametersImplemented(methodName);
				typeMethod.getContent().replaceBlockCode(contentMethod);
			}
		}
	}

	protected void buildServiceProperty(CodesGenerator generator, Class<?> domainClass) {
		String propertyName = domainClass.getSimpleName() + "Service";
		String propertyType = generator.getImportForClassName(propertyName);
		JavaProperty javaProperty = super.createJavaPropertyWithGetterAndSetter(propertyName, propertyType);
		javaProperty.addAnnotation(Autowired.class.getCanonicalName());
	}

	@Override
	protected String buildClassName(Class<?> domainClass) {
		return domainClass.getSimpleName() + "ServiceTest";
	}

	@Override
	protected void updateCurrentJavaType(CodesGenerator generator, Class<?> domainClass) {
		// TODO 
	}

	@Override
	protected JavaType buildJavaType() {
		return new JavaClass();
	}	
}
