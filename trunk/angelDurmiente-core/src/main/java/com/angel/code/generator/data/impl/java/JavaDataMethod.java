/**
 * 
 */
package com.angel.code.generator.data.impl.java;

import com.angel.code.generator.data.impl.java.annotations.JavaAnnotation;
import com.angel.code.generator.data.impl.java.properties.JavaParameter;
import com.angel.code.generator.data.types.CodeBlock;
import com.angel.code.generator.data.types.DataAnnotation;
import com.angel.code.generator.data.types.DataException;
import com.angel.code.generator.data.types.DataMethod;
import com.angel.code.generator.data.types.DataParameter;
import com.angel.common.helpers.StringHelper;


/**
 * @author Guillermo D. Salazar
 * @since 26/Noviembre/2009.
 *
 */
public class JavaDataMethod extends DataMethod {

	public JavaDataMethod(){
		super();
		this.setContent(new JavaCodeBlock());
		this.setComment(new JavaDataComment());
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T extends DataAnnotation> T createAnnotation(String canonicalName) {
		DataAnnotation dataAnnotation = new JavaAnnotation(canonicalName);
		super.addAnnotation(dataAnnotation);
		return (T) dataAnnotation;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T extends CodeBlock> T createCodeBlock() {
		CodeBlock codeBlock = new JavaCodeBlock();
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
		DataParameter dataParameter = new JavaParameter(name, StringHelper.EMPTY_STRING);
		super.addParameter(dataParameter);
		return (T) dataParameter;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T extends DataParameter> T createReturnParameter(String canonicalName) {
		DataParameter dataParameter = new JavaParameter(canonicalName);
		super.setReturnType(dataParameter);
		return (T) dataParameter;
	}
}
