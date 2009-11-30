/**
 * 
 */
package com.angel.object.generator.classesGeneratorFactory;

import com.angel.object.generator.ClassesGenerator;

/**
 * @author Guillermo D. Salazar
 * @since 26/Noviembre/2009.
 *
 */
public interface ClassesGeneratorFactory {

	public ClassesGenerator createClassesGenerator(String baseProjectPackage);
	
}
