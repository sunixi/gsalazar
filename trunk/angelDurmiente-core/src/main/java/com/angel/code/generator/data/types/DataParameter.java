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
public interface DataParameter extends CodeConvertible, Importable{

	public String getName();
	
	public String getCanonicalType();
	
	public String getSimpleType();
	
	public List<DataAnnotation> getAnnotations();
	
}
