/**
 * 
 */
package com.angel.code.generator.builders.method.impl;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import com.angel.code.generator.CodesGenerator;
import com.angel.code.generator.annotations.Accesor;
import com.angel.code.generator.builders.method.MethodBuilder;
import com.angel.code.generator.data.DataType;
import com.angel.code.generator.data.impl.java.properties.JavaParameter;
import com.angel.code.generator.data.types.CodeBlock;
import com.angel.code.generator.data.types.DataMethod;
import com.angel.code.generator.data.types.DataParameter;
import com.angel.common.helpers.StringHelper;


/**
 * @author Guillermo D. Salazar
 * @since 26/Noviembre/2009.
 *
 */
public class AccesorJavaAnnotationMethodBuilder implements MethodBuilder {


	public <T> List<DataParameter> buildDataParameters(Class<T> domainClass, Field property) {
		List<DataParameter> parameters = new ArrayList<DataParameter>();
		JavaParameter javaParameter = new JavaParameter(property.getName(), property.getType().getCanonicalName());
		parameters.add(javaParameter);
		return parameters;
	}

	public <T> CodeBlock buildMethodContent(Class<T> domainClass, Field property) {
		// TODO Auto-generated method stub
		return null;
	}

	public <T> String buildMethodName(Class<T> domainClass, Field property) {
		Accesor accesor = (Accesor) property.getAnnotation(Accesor.class);
		String methodName = "buscar";
		methodName += accesor.unique() ? "Unico" : "Todos";
		methodName += accesor.optional() ? "ONulo" : "";
		methodName += "Por";
		methodName += StringHelper.capitalize(property.getName());
		return methodName;
	}

	public <T> JavaParameter buildReturnParameter(Class<T> domainClass, Field property) {
		Accesor accesor = (Accesor) property.getAnnotation(Accesor.class);
		Class<?> returnType = null;
		if(accesor.unique()){
			returnType = domainClass;
		} else {
			returnType = List.class;
		}
		return new JavaParameter(returnType.getCanonicalName());
	}

	public <T> Annotation getAnnotation(Class<T> domainClass, Field property) {
		return property.getAnnotation(Accesor.class);
	}	

	public void buildDataMethod(CodesGenerator generator, DataType dataType, Class<?> domainClass, Object owner) {
		String methodName = this.buildMethodName(domainClass, (Field) owner);
		CodeBlock codeBlock = this.buildMethodContent(domainClass, (Field) owner);
		List<DataParameter> methodParameters = this.buildDataParameters(domainClass, (Field) owner);
		DataParameter returnParamter = this.buildReturnParameter(domainClass, (Field) owner);

		DataMethod dataMethod = dataType.createDataMethod(methodName);
		dataMethod.setParameters(methodParameters);
		dataMethod.setContent(codeBlock);
		dataMethod.setReturnType(returnParamter);		
	}
}
