/**
 * 
 */
package com.angel.code.generator.codeGenerator.impl.java;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import org.springframework.beans.factory.annotation.Autowired;

import com.angel.code.generator.CodesGenerator;
import com.angel.code.generator.annotations.Accesor;
import com.angel.code.generator.builders.method.MethodBuilder;
import com.angel.code.generator.builders.method.impl.AccesorServiceTestAnnotationMethodBuilder;
import com.angel.code.generator.codeGenerator.ClassGenerator;
import com.angel.code.generator.data.DataType;
import com.angel.code.generator.data.impl.java.JavaClassDataType;
import com.angel.code.generator.data.impl.java.properties.JavaProperty;
import com.angel.common.helpers.ReflectionHelper;
import com.angel.test.GenericSpringTestCase;


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
	public DataType buildSubClassForClassGenerator(DataType subDataType){
		subDataType.setCanonicalName(GenericSpringTestCase.class.getCanonicalName());
		return subDataType;
	}
	
	@Override
	protected void generateContentClass(CodesGenerator generator, Class<?> domainClass) {
		this.buildServiceProperty(generator, domainClass);
		
		Field[] fields = ReflectionHelper.getFieldsDeclaredFor(domainClass);
		for(Field f : fields){
			if(f.getModifiers() < Modifier.STATIC){
				MethodBuilder methodBuilder = super.getMethodBuilderFor(f);
				methodBuilder.buildDataMethod(generator, super.getDataType(), domainClass, f);
			}
		}
	}

	protected void buildServiceProperty(CodesGenerator generator, Class<?> domainClass) {
		String propertyName = domainClass.getSimpleName() + "Service";
		String propertyType = generator.getImportForClassName(propertyName);
		JavaProperty javaProperty = super.getDataType().createDataProperty(propertyName);
		javaProperty.setCanonicalType(propertyType);
		super.getDataType().createDataMethodGetterSetter(javaProperty);
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
	protected DataType buildDataType() {
		return new JavaClassDataType();
	}	
}