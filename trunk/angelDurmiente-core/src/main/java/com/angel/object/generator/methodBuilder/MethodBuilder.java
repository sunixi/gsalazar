/**
 * 
 */
package com.angel.object.generator.methodBuilder;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.List;

import com.angel.object.generator.java.JavaBlockCode;
import com.angel.object.generator.java.properties.JavaParameter;

/**
 * @author Guillermo D. Salazar
 * @since 26/Noviembre/2009.
 *
 */
public interface MethodBuilder {

	public <T> String buildMethodName(Class<T> domainClass, Field property);
	
	public <T> JavaBlockCode buildMethodContent(Class<T> domainClass, Field property);
	
	public <T> List<JavaParameter> buildJavaParameters(Class<T> domainClass, Field property);
	
	public <T> JavaParameter buildReturnParameter(Class<T> domainClass, Field property);

	public <T> Annotation getAnnotation(Class<T> domainClass, Field property);
}
