/**
 * 
 */
package com.angel.code.generator.builders.method;

import com.angel.code.generator.CodesGenerator;
import com.angel.code.generator.data.DataType;

/**
 * @author Guillermo D. Salazar
 * @since 26/Noviembre/2009.
 *
 */
public interface MethodBuilder {
/*
	public <T> String buildMethodName(Class<T> domainClass, Field property);
	
	public <T, H extends CodeBlock> H buildMethodContent(Class<T> domainClass, Field property);
	
	public <T> List<JavaParameter> buildJavaParameters(Class<T> domainClass, Field property);
	
	public <T> JavaParameter buildReturnParameter(Class<T> domainClass, Field property);

	public <T> Annotation getAnnotation(Class<T> domainClass, Field property);
*/
	public void buildDataMethod(CodesGenerator generator, DataType dataType, Class<?> domainClass, Object owner);
}
