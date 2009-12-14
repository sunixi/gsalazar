/**
 * 
 */
package com.angel.code.generator.codeGenerator.impl.java;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import com.angel.architecture.services.interfaces.GenericService;
import com.angel.code.generator.CodesGenerator;
import com.angel.code.generator.annotations.Accesor;
import com.angel.code.generator.builders.method.impl.AccesorJavaAnnotationMethodBuilder;
import com.angel.code.generator.codeGenerator.ClassGenerator;
import com.angel.code.generator.data.DataType;
import com.angel.code.generator.data.impl.java.JavaInterfaceDataType;
import com.angel.common.helpers.ReflectionHelper;

/**
 * @author Guillermo Salazar.
 * @since 27/Noviembre/2009.
 *
 */
public class ServiceClassGenerator extends ClassGenerator {

	public ServiceClassGenerator(String basePackage) {
		super(basePackage);
		//TODO Hacer algun applies method builder.
		this.getMethodBuilderStrategies().put(Accesor.class, new AccesorJavaAnnotationMethodBuilder());
	}

	@Override
	public DataType buildSubClassForClassGenerator(CodesGenerator generator, DataType subDataType){
		subDataType.setCanonicalName(GenericService.class.getCanonicalName());
		return subDataType;
	}


	@Override
	protected void generateContentClass(CodesGenerator generator, Class<?> domainClass) {
		Field[] fields = ReflectionHelper.getFieldsDeclaredFor(domainClass);
		for(Field f : fields){
			if(f.getModifiers() < Modifier.STATIC){
				super.buildMethodFor(f, generator, domainClass);
			}
		}
	}

	@Override
	protected String buildClassName(Class<?> domainClass) {
		return domainClass.getSimpleName() + "Service";
	}

	@Override
	protected void updateCurrentJavaType(CodesGenerator generator, Class<?> domainClass) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected DataType buildDataType() {
		return new JavaInterfaceDataType();
	}
}
