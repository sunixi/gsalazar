/**
 * 
 */
package com.angel.code.generator.data.impl.as3;

import java.util.List;

import com.angel.code.generator.data.ClassDataType;
import com.angel.code.generator.data.DataType;
import com.angel.code.generator.data.types.DataAnnotation;
import com.angel.code.generator.data.types.DataConstructor;
import com.angel.code.generator.data.types.DataInterface;
import com.angel.code.generator.data.types.DataMethod;
import com.angel.code.generator.data.types.DataParameter;
import com.angel.code.generator.data.types.DataProperty;
import com.angel.code.generator.data.types.impl.DataCommentImpl;


/**
 * @author Guillermo D. Salazar
 * @since 26/Noviembre/2009.
 *
 */
public class FlexClassDataType extends ClassDataType {
	
	protected final static String ACTION_SCRIPT_FILE_EXTENSION = ".as";
	
	public FlexClassDataType(){
		super();

	}

	@SuppressWarnings("unchecked")
	@Override
	public DataConstructor createDataConstructor() {
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T extends DataAnnotation> T createDataAnnotation(String canonicalName) {
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T extends DataInterface> T createDataInterface(String canonicalName) {
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T extends DataMethod> T createDataMethod(String name) {
		FlexDataMethod flexDataMethod = new FlexDataMethod();
		flexDataMethod.setMethodName(name);
		super.addMethod(flexDataMethod);
		return (T) flexDataMethod;
	}

	@SuppressWarnings("unchecked")
	public <T extends DataMethod> T createDataMethod(String name, List<DataParameter> methodParameters){
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T extends DataProperty> T createDataProperty(String name) {
		return null;
	}

	@Override
	public String getDataTypeFileName() {
		return super.getSimpleName() + ACTION_SCRIPT_FILE_EXTENSION;
	}

	/**
	 * Convert data type sign to a representation code.
	 * 
	 * @return a code representation of data type sign.
	 */
	@Override
	protected String convertCodeDataTypeSign() {
		return "\npublic " + this.getDataTypeIdentifierName() + " " + this.getDataTypeSign();
	}

	@Override
	protected String convertCodeInheritSubDataType() {
		String codeConverted = "";
		if (this.hasSubDataType()) {
			FlexClassDataType flexClassDataType = (FlexClassDataType) this.getSubDataType();
			codeConverted += " extends " + flexClassDataType.getSimpleName();
		}
		return codeConverted;
	}
	
	/**
	 * Add all sub data type import types.
	 * 
	 * @see {@link DataType#addImport(List, List)} add a list imports types.
	 * @see {@link DataType#addImport(List, String)} add a import type.
	 * @param typesImports
	 *            where imports must be added.
	 */
	@Override
	protected void addSubDataTypeImportsType(List<String> typesImports) {
		super.addSubDataTypeImportsType(typesImports);
		if (this.hasSubDataType()) {
			//Do Nothing.
		}
	}

	@Override
	protected void addTypeImports(List<String> typesImports) {
		//Do Nothing.
	}
}
