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
import com.angel.object.generator.java.JavaMethod;
import com.angel.object.generator.java.JavaParameter;
import com.angel.object.generator.methodBuilder.MethodBuilder;


/**
 * @author Guillermo D. Salazar
 * @since 26/Noviembre/2009.
 *
 */
public class AccesorAnnotationMethodBuilder implements MethodBuilder {

	public <T> JavaMethod buildJavaMethod(Class<T> domainClass, Field property, Annotation annotation) {
		Accesor accesor = (Accesor) annotation;
		String methodName = "buscar";
		methodName += accesor.unique() ? "Unico" : "Todos";
		methodName += accesor.optional() ? "ONulo" : "";
		methodName += "Por";
		methodName += StringHelper.capitalize(property.getName());
		
		List<JavaParameter> parameters = new ArrayList<JavaParameter>();
		JavaParameter javaParameter = new JavaParameter(property.getName(), property.getDeclaringClass());
		parameters.add(javaParameter);
		
		Class<?> returnType = null;
		if(accesor.unique()){
			returnType = domainClass;
		} else {
			returnType = List.class;
		}

		return new JavaMethod(methodName, parameters, new JavaParameter("", returnType));
	}

	

}
