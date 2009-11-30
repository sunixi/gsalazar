/**
 * 
 */
package com.angel.object.generator.classGenerator.impl;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import ar.com.angelDurmiente.AngelDurmienteBaseTestCase;

import com.angel.common.helpers.ReflectionHelper;
import com.angel.object.generator.ClassesGenerator;
import com.angel.object.generator.annotations.Accesor;
import com.angel.object.generator.classGenerator.ClassGenerator;
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
				String contentMethod = methodBuilder.buildMethodContent(domainClass, f);
				Accesor accesor = (Accesor) methodBuilder.getAnnotation(domainClass, f);
				if(!accesor.unique()){
					super.getJavaType().addImport("java.util.List");
				}
				super.getJavaType().addTypeMethodPublicVoidWithoutParametersImplemented(methodName, contentMethod);
			}
		}
	}

	protected void buildServiceProperty(ClassesGenerator generator, Class<?> domainClass) {
		String propertyName = domainClass.getSimpleName() + "Service";
		String propertyType = generator.getImportForClassName(propertyName);
		super.createJavaProperty(propertyName, propertyType);
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
