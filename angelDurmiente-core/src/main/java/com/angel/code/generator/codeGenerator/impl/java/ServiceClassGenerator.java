/**
 * 
 */
package com.angel.code.generator.codeGenerator.impl.java;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.List;

import javax.persistence.Column;

import com.angel.architecture.services.interfaces.GenericService;
import com.angel.code.generator.CodesGenerator;
import com.angel.code.generator.annotations.Accesor;
import com.angel.code.generator.codeGenerator.ClassGenerator;
import com.angel.code.generator.data.impl.java.InterfaceDataType;
import com.angel.code.generator.data.impl.java.JavaType;
import com.angel.common.helpers.ReflectionHelper;
import com.angel.object.generator.java.properties.JavaParameter;
import com.angel.object.generator.methodBuilder.MethodBuilder;
import com.angel.object.generator.methodBuilder.impl.AccesorAnnotationMethodBuilder;
import com.angel.object.generator.methodBuilder.impl.ColumnAnnotationMethodBuilder;


/**
 * @author Guillermo Salazar.
 * @since 27/Noviembre/2009.
 *
 */
public class ServiceClassGenerator extends ClassGenerator {

	public ServiceClassGenerator(String basePackage) {
		super(basePackage);
		this.getMethodBuilderStrategies().put(Accesor.class, new AccesorAnnotationMethodBuilder());
		this.getMethodBuilderStrategies().put(Column.class, new ColumnAnnotationMethodBuilder());
	}

	public ServiceClassGenerator(String basePackage, ClassGenerator interfaceClassGenerator) {
		this(basePackage);
		this.setInterfaceClassGenerator(interfaceClassGenerator);
	}

	@Override
	public JavaType buildSubClassForClassGenerator(JavaType subjavaType){
		subjavaType.setTypeName(GenericService.class.getCanonicalName());
		return subjavaType;
	}


	@Override
	protected void generateContentClass(CodesGenerator generator, Class<?> domainClass) {
		Field[] fields = ReflectionHelper.getFieldsDeclaredFor(domainClass);
		for(Field f : fields){
			if(f.getModifiers() < Modifier.STATIC){
				MethodBuilder methodBuilder = super.getMethodBuilderFor(f);

				String methodName = methodBuilder.buildMethodName(domainClass, f);
				List<JavaParameter> javaParameters = methodBuilder.buildJavaParameters(domainClass, f);
				JavaParameter returnParameter = methodBuilder.buildReturnParameter(domainClass, f);

				super.getJavaType().addTypeMethodPublicNotImplemented(methodName, javaParameters, returnParameter);
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
	protected JavaType buildJavaType() {
		return new InterfaceDataType();
	}
}