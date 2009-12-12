/**
 * 
 */
package com.angel.code.generator.codeGenerator.impl.as3;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import com.angel.code.generator.CodesGenerator;
import com.angel.code.generator.codeGenerator.ClassGenerator;
import com.angel.code.generator.data.DataType;
import com.angel.code.generator.data.impl.as3.FlexClassDataType;
import com.angel.code.generator.data.impl.as3.annotations.FlexAnnotation;
import com.angel.common.helpers.ReflectionHelper;

/**
 * @author Guillermo Salazar.
 * @since 27/Noviembre/2009.
 *
 */
public class AS3ClassGenerator extends ClassGenerator {

	public AS3ClassGenerator(String basePackage) {
		super(basePackage);
	}

	@Override
	protected void generateContentClass(CodesGenerator generator, Class<?> domainClass) {
		super.getDataType().createDataAnnotation("Bindable");
		FlexAnnotation remoteClassAnnotation = super.getDataType().createDataAnnotation("RemoteClass");
		remoteClassAnnotation.createAnnotationPropertyString("alias", domainClass.getCanonicalName());

		Field[] fields = ReflectionHelper.getFieldsDeclaredFor(domainClass);
		for(Field f : fields){
			if(f.getModifiers() < Modifier.STATIC){
				super.getDataType().createDataProperty(f.getName(), f.getType().getCanonicalName());
			}
		}
	}

	@Override
	protected String buildClassName(Class<?> domainClass) {
		return domainClass.getSimpleName();
	}

	@Override
	protected void updateCurrentJavaType(CodesGenerator generator, Class<?> domainClass) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected DataType buildDataType() {
		return new FlexClassDataType();
	}
}
