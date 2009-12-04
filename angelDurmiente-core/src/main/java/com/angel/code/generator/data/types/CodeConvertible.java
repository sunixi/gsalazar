/**
 * 
 */
package com.angel.code.generator.data.types;


/**
 * @author Guillermo D. Salazar
 * @since 26/Noviembre/2009.
 *
 */
public interface CodeConvertible {

	/**
	 * Convert an object type in a code representation to be created.
	 *  
	 * @return a code representation.
	 */
	public String convertCode();
}
