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

	public void buildDataMethod(CodesGenerator generator, DataType dataType, Class<?> domainClass, Object owner);

}
