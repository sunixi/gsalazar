/**
 * 
 */
package com.angel.object.generator.methodBuilder.impl;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;

import com.angel.common.helpers.StringHelper;
import com.angel.object.generator.java.JavaMethod;
import com.angel.object.generator.java.JavaParameter;
import com.angel.object.generator.methodBuilder.MethodBuilder;


/**
 * @author Guillermo D. Salazar
 * @since 26/Noviembre/2009.
 *
 */
public class ColumnAnnotationMethodBuilder implements MethodBuilder {

	public <T> JavaMethod buildJavaMethod(Class<T> domainClass, Field property, Annotation annotation) {
		Column column = (Column) annotation;
		String methodName = "buscar";
		methodName += column.unique() ? "Unico" : "Todos";
		methodName += column.nullable() ? "ONulo" : "";
		methodName += "Por";
		methodName += StringHelper.capitalize(property.getName());
		
		List<JavaParameter> parameters = new ArrayList<JavaParameter>();
		JavaParameter javaParameter = new JavaParameter(property.getName(), property.getDeclaringClass());
		parameters.add(javaParameter);
		
		Class<?> returnType = null;
		if(column.unique()){
			returnType = domainClass;
		} else {
			returnType = List.class;
		}

		return new JavaMethod(methodName, parameters, new JavaParameter("", returnType));
	}

	

}
