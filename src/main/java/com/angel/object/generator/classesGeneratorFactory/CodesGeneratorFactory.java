/**
 * 
 */
package com.angel.object.generator.classesGeneratorFactory;

import com.angel.object.generator.CodesGenerator;

/**
 * @author Guillermo D. Salazar
 * @since 26/Noviembre/2009.
 *
 */
public interface CodesGeneratorFactory {

	/**
	 * Create a codes generator instances with all code generators added.
	 * 
	 * @param baseProjectPackage where resources will be generated.
	 * @return a new codes generator instance.
	 */
	public CodesGenerator createClassesGenerator(String baseProjectPackage);
	
}
