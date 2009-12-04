/**
 * 
 */
package com.angel.object.generator.methodBuilder.impl;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.angel.code.generator.annotations.Accesor;
import com.angel.common.helpers.StringHelper;
import com.angel.object.generator.java.JavaBlockCode;
import com.angel.object.generator.java.JavaLineCode;
import com.angel.object.generator.java.properties.JavaParameter;
import com.angel.object.generator.methodBuilder.MethodBuilder;


/**
 * @author Guillermo D. Salazar
 * @since 26/Noviembre/2009.
 *
 */
public class AccesorDAOImplAnnotationMethodBuilder implements MethodBuilder {


	public <T> List<JavaParameter> buildJavaParameters(Class<T> domainClass, Field property) {
		List<JavaParameter> parameters = new ArrayList<JavaParameter>();
		JavaParameter javaParameter = new JavaParameter(property.getName(), property.getType().getCanonicalName());
		parameters.add(javaParameter);
		return parameters;
	}

	@SuppressWarnings("unchecked")
	public <T> JavaBlockCode buildMethodContent(Class<T> domainClass, Field property) {
		Accesor accesor = (Accesor) this.getAnnotation(domainClass, property);
		JavaBlockCode contentMethod = new JavaBlockCode();
		String methodName = "";
		List<String> parametersNames = new ArrayList<String>();
		parametersNames.add("\"" + property.getName() + "\"");
		parametersNames.add(property.getName());
		if(accesor.unique()){
			if(accesor.optional()){
				methodName = "super.findUniqueOrNull";
			} else {
				methodName = "super.findUnique";
			}
		} else {
			methodName = "super.findAll";
		}
		JavaLineCode lineCode = contentMethod.getLineCodeCalledMethod(methodName, parametersNames);
		contentMethod.addLineCodeReturnVariableCollectionTypeCasted((Class<? extends Collection<?>>) List.class,
				domainClass.getCanonicalName(), lineCode);
		return contentMethod;
	}

	public <T> String buildMethodName(Class<T> domainClass, Field property) {
		Accesor accesor = (Accesor) this.getAnnotation(domainClass, property);
		String methodName = "buscar";
		methodName += accesor.unique() ? "Unico" : "Todos";
		methodName += accesor.optional() ? "ONulo" : "";
		methodName += "Por";
		methodName += StringHelper.capitalize(property.getName());
		return methodName;
	}

	public <T> JavaParameter buildReturnParameter(Class<T> domainClass, Field property) {
		Accesor accesor = (Accesor) this.getAnnotation(domainClass, property);
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
