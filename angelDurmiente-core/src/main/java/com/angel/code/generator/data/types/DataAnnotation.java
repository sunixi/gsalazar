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
public interface DataAnnotation extends CodeConvertible, Importable {
	
	public Object getParameterValue(String name);
	
	public String getCanonicalType();
	
	public String getName();
	
	public int getQuantityParameters();
	
	public List<String> getParametersNames();

	public boolean hasName(String name);

	public boolean isCanonicalType(String canonicalType);

}
