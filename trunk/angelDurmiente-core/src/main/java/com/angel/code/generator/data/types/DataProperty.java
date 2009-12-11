/**
 * 
 */
package com.angel.code.generator.data.types;



/**
 * 
 * @author Guillermo D. Salazar
 * @since 26/Noviembre/2009.
 *
 */
public interface DataProperty extends CodeConvertible, Importable {

	public String getName();
	
	//public TypeModifier getTypeModifier();
	
	public boolean isVisibility(Object visibility);
	
	public String getCanonicalType();
	
	public String getSimpleType();

	public boolean hasName(String name);

	public boolean isPrivate();
	
	
}
