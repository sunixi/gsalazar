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
public interface DataConstructor extends CodeConvertible, Importable {
	
	public List<DataParameter> getParameters();
	
	public int getQuantityParameters();
	
	public CodeBlock getContent();

}
