/**
 * 
 */
package com.angel.code.generator.data.impl.java;

import com.angel.code.generator.data.DataType;




/**
 * @author Guillermo D. Salazar
 * @since 26/Noviembre/2009.
 *
 */
public class InterfaceDataType extends DataType {

	public InterfaceDataType(){
		super();
	}

	public InterfaceDataType(String typeName) {
		super(typeName);
	}

	@Override
	public boolean isAnImplementationType() {
		return false;
	}

	@Override
	public String getDataTypeFileName() {
		return this.getSimpleName() + ".java";
	}

	@Override
	public String getDataTypeIdentifierName() {
		return "interface";
	}

}
