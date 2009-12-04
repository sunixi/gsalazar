/**
 * 
 */
package com.angel.code.generator.data.types;

import com.angel.code.generator.data.enums.TypeModifier;
import com.angel.code.generator.data.enums.Visibility;


/**
 * @author Guillermo D. Salazar
 * @since 26/Noviembre/2009.
 *
 */
public interface DataProperty extends CodeConvertible, Importable {

	public String getName();
	
	public TypeModifier getTypeModifier();
	
	public Visibility getVisibility();
	
	public String getCanonicalType();
	
	public String getSimpleType();
	
	
}
