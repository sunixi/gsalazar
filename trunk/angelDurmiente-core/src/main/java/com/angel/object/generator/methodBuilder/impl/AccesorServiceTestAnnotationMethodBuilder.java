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
public class AccesorServiceTestAnnotationMethodBuilder implements MethodBuilder {


	public <T> List<JavaParameter> buildJavaParameters(Class<T> domainClass, Field property) {
		return new ArrayList<JavaParameter>();
	}

	@SuppressWarnings("unchecked")
	public <T> JavaBlockCode buildMethodContent(Class<T> domainClass, Field property) {
		String domainObjectSimpleName = domainClass.getSimpleName();
		JavaBlockCode javaBlockCode = new JavaBlockCode();
		javaBlockCode.addLineCodeCommented("TODO Must set a value to " + property.getName() + " property.");
		javaBlockCode.addLineCodeNullAssigment(property.getType().getCanonicalName(), property.getName());
		
		List<String> parameters = new ArrayList<String>();
		parameters.add(property.getName());
		String methodName = "this.get" + domainObjectSimpleName + "Service().";
		methodName +=  this.buildContentMethodName(domainClass, property);
		JavaLineCode calledMethodLine = javaBlockCode.getLineCodeCalledMethod(methodName, parameters);

		Accesor accesor = (Accesor) this.getAnnotation(domainClass, property);
		if(!accesor.unique()){
			javaBlockCode
				.addLineCodeCollectionVariableAssigment(
						(Class<? extends Collection<?>>) List.class, domainClass.getSimpleName(), "result", calledMethodLine);

		} else {
			javaBlockCode.addLineCodeAssigmentTypedVariable(property.getType().getCanonicalName(),
					property.getName(), calledMethodLine);
		}
		
		List<String> notNullAssertParameters = new ArrayList<String>();
		notNullAssertParameters.add("\"Result collection mustn't be null.\"");
		notNullAssertParameters.add("result");
		javaBlockCode.addLineCodeCallMethodWithParameters("assertNotNull", notNullAssertParameters);
		
		List<String> assertParameters = new ArrayList<String>();
		assertParameters.add("\"Result collection size must be more than one.\"");
		assertParameters.add("result.size() > 0");
		javaBlockCode.addLineCodeCallMethodWithParameters("assertTrue", assertParameters);

		return javaBlockCode;
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
