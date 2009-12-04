/**
 * 
 */
package com.angel.code.generator.data.types;

import java.util.List;

import com.angel.code.generator.exceptions.CodeGeneratorException;


/**
 * @author Guillermo D. Salazar
 * @since 26/Noviembre/2009.
 *
 */
public interface DataInterface extends CodeConvertible, Importable {
	
	public String getName();
	
	public List<DataAnnotation> getAnnotations();
	
	public List<DataMethod> getMethods();
	
	public List<String> getMethodsSign();

	/**
	 * 
	 * @param name
	 * @param totalParameters
	 * @return
	 * @throws CodeGeneratorException when there are more than one method with the same method name and total quantity parameters. 
	 */
	public DataMethod getMethod(String name, int totalParameters) throws CodeGeneratorException;

	/**
	 * Get a method with a name. Parameters types (canonical types) must match with canonical parameters types.
	 * 
	 * @param name method to find.
	 * @param canonicalParamtersTypes to match in all data method.
	 * @return a method with a name, and all canonical parameters types. Or return null if method doesn't exist.
	 */
	public DataMethod getMethod(String name, List<String> canonicalParamtersTypes);

	/**
	 * Get a method with a name, and without parameters.
	 * 
	 * @param name method to find.
	 * @return a method with a name and without parameters.
	 * @throws CodeGeneratorException when there are more than one method with the same method name and total quantity parameters.
	 */
	public DataMethod getMethod(String name) throws CodeGeneratorException;

	/**
	 * Get an simple interface name.
	 * 
	 * @return interface sign. It is a simple interface name.
	 */
	public String getSign();

}
