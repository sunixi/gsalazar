/**
 * 
 */
package com.angel.object.generator.fileGenerator;

/**
 * @author Guillermo D. Salazar
 * @since 26/Noviembre/2009.
 *
 */
public interface FileGenerator {

	public <T> String generateFile(Class<T> domainClass);
}
