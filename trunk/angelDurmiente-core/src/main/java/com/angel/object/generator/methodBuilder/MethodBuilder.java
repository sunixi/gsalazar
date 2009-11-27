/**
 * 
 */
package com.angel.object.generator.methodBuilder;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

import com.angel.object.generator.java.JavaMethod;

/**
 * @author Guillermo D. Salazar
 * @since 26/Noviembre/2009.
 *
 */
public interface MethodBuilder {

	public <T> JavaMethod buildJavaMethod(Class<T> domainClass, Field property, Annotation annotation);
}
