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
public interface DataMethod extends CodeConvertible, Importable {

	public String getName();
	
	public CodeBlock getContent();
	
	public String getSign();
	
	public List<DataParameter> getParameters();
	
	public DataParameter getReturnParameter();
	
	public boolean isVoid();
	
	public boolean isImplemented();
	
	public List<DataAnnotation> getAnnotations();
}
