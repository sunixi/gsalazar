/**
 * 
 */
package com.angel.object.generator.classNameStrategies;

/**
 * @author Guillermo D. Salazar
 * @since 26/Noviembre/2009.
 *
 */
public interface ClassNameStrategy {

	public <T extends Object> String buildClassName(Class<T> domainClass);
}
