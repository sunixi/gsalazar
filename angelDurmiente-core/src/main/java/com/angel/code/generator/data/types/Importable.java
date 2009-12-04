/**
 * 
 */
package com.angel.code.generator.data.types;

import java.util.List;


/**
 * @author Guillermo D. Salazar
 * @since 26/Noviembre/2009.
 *
 */
public interface Importable {

	/**
	 * Get all imports type to generate code.
	 * 
	 * @return an imports type list with all imports.
	 */
	public List<String> getImportsType();
}
