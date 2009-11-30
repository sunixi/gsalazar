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
public class AccesorServiceTestAnnotationMethodBuilder implements MethodBuilder {


	public <T> List<JavaParameter> buildJavaParameters(Class<T> domainClass, Field property) {
		return new ArrayList<JavaParameter>();
	}

	public <T> String buildMethodContent(Class<T> domainClass, Field property) {
		String domainObjectSimpleName = domainClass.getSimpleName();
		String contentMethod = property.getType().getSimpleName() + " " + property.getName() + " = null;\n\t\t";
		
		Accesor accesor = (Accesor) this.getAnnotation(domainClass, property);
		if(!accesor.unique()){
			contentMethod += "List<" + domainClass.getSimpleName() + "> result";
		} else {
			contentMethod += domainObjectSimpleName + " " + property.getName();
		}
		contentMethod += " = this.get" + domainObjectSimpleName + "Service().";
		contentMethod += this.buildContentMethodName(domainClass, property);
		contentMethod += "(" + property.getName() + ");\n";
		return contentMethod;
	}

	public <T> String buildContentMethodName(Class<T> domainClass, Field property) {
		Accesor accesor = (Accesor) this.getAnnotation(domainClass, property);
		String methodName = "buscar";
		methodName += accesor.unique() ? "Unico" : "Todos";
		methodName += accesor.optional() ? "ONulo" : "";
		methodName += "Por";
		methodName += StringHelper.capitalize(property.getName());
		return methodName;
	}
	
	public <T> String buildMethodName(Class<T> domainClass, Field property) {
		return "test" + this.buildContentMethodName(domainClass, property);
	}

	public <T> JavaParameter buildReturnParameter(Class<T> domainClass, Field property) {
		return null;
	}

	public <T> Annotation getAnnotation(Class<T> domainClass, Field property) {
		return property.getAnnotation(Accesor.class);
	}
	

}
