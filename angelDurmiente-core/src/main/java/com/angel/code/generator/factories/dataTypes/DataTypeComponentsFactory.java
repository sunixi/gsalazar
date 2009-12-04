/**
 * 
 */
package com.angel.code.generator.factories.dataTypes;

import com.angel.code.generator.data.types.CodeBlock;
import com.angel.code.generator.data.types.DataAnnotation;
import com.angel.code.generator.data.types.DataComment;
import com.angel.code.generator.data.types.DataConstructor;
import com.angel.code.generator.data.types.DataException;
import com.angel.code.generator.data.types.DataInterface;
import com.angel.code.generator.data.types.DataMethod;
import com.angel.code.generator.data.types.DataParameter;
import com.angel.code.generator.data.types.DataProperty;

/**
 * @author Guillermo D. Salazar
 * @since 26/Noviembre/2009.
 *
 */
public interface DataTypeComponentsFactory {

	public CodeBlock createCodeBlock();
	
	public DataAnnotation createDataAnnotation();
	
	public DataComment createDataComment();
	
	public DataConstructor createDataConstructor();
	
	public DataException createDataExcepcion();
	
	public DataInterface createDataInterface();
	
	public DataMethod createDataMethod();
	
	public DataParameter createDataParameter();
	
	public DataProperty createDataProperty();
	
}
