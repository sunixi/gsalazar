/**
 * 
 */
package com.angel.object.generator.methodBuilder.impl;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import com.angel.common.helpers.StringHelper;
import com.angel.object.generator.annotations.Accesor;
import com.angel.object.generator.java.JavaParameter;
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

	public <T> String buildMethodContent(Class<T> domainClass, Field property) {
		Accesor accesor = (Accesor) this.getAnnotation(domainClass, property);
		String contentMethod = "";
		if(accesor.unique()){
			if(accesor.optional()){
				contentMethod = "return super.findUniqueOrNull(\"" + property.getName() + "\", " + property.getName() + ");";
			} else {
				contentMethod = "return super.findUnique(\"" + property.getName() + "\", " + property.getName() + ");";
			}
		} else {
			contentMethod = "return super.findAll(\"" + property.getName() + "\", " + property.getName() + ");";
		}
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