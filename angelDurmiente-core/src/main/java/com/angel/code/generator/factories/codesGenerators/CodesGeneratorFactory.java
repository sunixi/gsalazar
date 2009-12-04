/**
 * 
 */
package com.angel.code.generator.factories.codesGenerators;

import com.angel.code.generator.CodesGenerator;

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
