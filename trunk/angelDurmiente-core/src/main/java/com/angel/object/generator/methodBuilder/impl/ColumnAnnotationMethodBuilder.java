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
import com.angel.object.generator.java.JavaParameter;
import com.angel.object.generator.methodBuilder.MethodBuilder;


/**
 * @author Guillermo D. Salazar
 * @since 26/Noviembre/2009.
 *
 */
public class ColumnAnnotationMethodBuilder implements MethodBuilder {

	public <T> List<JavaParameter> buildJavaParameters(Class<T> domainClass, Field property) {
		List<JavaParameter> parameters = new ArrayList<JavaParameter>();
		JavaParameter javaParameter = new JavaParameter(property.getName(), property.getType().getCanonicalName());
		parameters.add(javaParameter);
		return parameters;
	}

	public <T> String buildMethodContent(Class<T> domainClass, Field property) {
		// TODO Auto-generated method stub
		return null;
	}

	public <T> String buildMethodName(Class<T> domainClass, Field property) {
		Column column = (Column) property.getAnnotation(Column.class);
		String methodName = "buscar";
		methodName += column.unique() ? "Unico" : "Todos";
		methodName += column.unique() && column.nullable() ? "ONulo" : "";
		methodName += "Por";
		methodName += StringHelper.capitalize(property.getName());
		return methodName;
	}

	public <T> JavaParameter buildReturnParameter(Class<T> domainClass, Field property) {
		Column column = (Column) property.getAnnotation(Column.class);
		
		Class<?> returnType = null;
		if(column.unique()){
			returnType = domainClass;
		} else {
			returnType = List.class;
		}
		return new JavaParameter(returnType.getCanonicalName());
	}

	
	public <T> Annotation getAnnotation(Class<T> domainClass, Field property) {
		return property.getAnnotation(Column.class);
	}
}
