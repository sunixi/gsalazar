/**
 * 
 */
package com.angel.object.generator.methodBuilder.impl;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import com.angel.common.helpers.ReflectionHelper;
import com.angel.common.helpers.StringHelper;
import com.angel.object.generator.annotations.Accesor;
import com.angel.object.generator.java.JavaBlockCode;
import com.angel.object.generator.java.JavaParameter;
import com.angel.object.generator.methodBuilder.MethodBuilder;


/**
 * @author Guillermo D. Salazar
 * @since 26/Noviembre/2009.
 *
 */
public class AccesorServiceImplAnnotationMethodBuilder implements MethodBuilder {


	public <T> List<JavaParameter> buildJavaParameters(Class<T> domainClass, Field property) {
		List<JavaParameter> parameters = new ArrayList<JavaParameter>();
		JavaParameter javaParameter = new JavaParameter(property.getName(), property.getType().getCanonicalName());
		parameters.add(javaParameter);
		return parameters;
	}

	public <T> JavaBlockCode buildMethodContent(Class<T> domainClass, Field property) {
		JavaBlockCode javaBlockCode = new JavaBlockCode();
		
		List<String> parameters = new ArrayList<String>();
		parameters.add(property.getName());
		String daoMethodName = this.buildMethodName(domainClass, property);//"get" + PackageHelper.getClassSimpleName(domainClass.getClass().getCanonicalName())+ "Service().";
		
		String contentMethod = "this." + 
		ReflectionHelper.getGetMethodName(domainClass.getSimpleName() + "DAO") + "()." +
		daoMethodName + "(" + StringHelper.convertToPlainString(parameters.toArray(), ",") + ")";

		javaBlockCode.addLineCodeReturnVariable(contentMethod);

		return javaBlockCode;
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

}
