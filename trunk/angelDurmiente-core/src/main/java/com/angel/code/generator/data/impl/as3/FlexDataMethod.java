/**
 * 
 */
package com.angel.code.generator.data.impl.as3;

import com.angel.code.generator.data.types.CodeBlock;
import com.angel.code.generator.data.types.DataAnnotation;
import com.angel.code.generator.data.types.DataException;
import com.angel.code.generator.data.types.DataMethod;
import com.angel.code.generator.data.types.DataParameter;


/**
 * @author Guillermo D. Salazar
 * @since 26/Noviembre/2009.
 *
 */
public class FlexDataMethod extends DataMethod {

	public FlexDataMethod(){
		super();
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T extends DataAnnotation> T createAnnotation(String canonicalName) {
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T extends CodeBlock> T createCodeBlock() {
		CodeBlock codeBlock = new CodeBlock();
		super.setContent(codeBlock);
		return (T) codeBlock;
	}

	@Override
	public <T extends DataException> T createException(String canonicalName) {
		//TODO Implementar.
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T extends DataParameter> T createParameter(String name) {
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T extends DataParameter> T createReturnParameter(String canonicalName) {
		return null;
	}
}
