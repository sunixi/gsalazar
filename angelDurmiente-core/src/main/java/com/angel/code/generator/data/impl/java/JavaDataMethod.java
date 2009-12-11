/**
 * 
 */
package com.angel.code.generator.data.impl.java;

import com.angel.code.generator.data.enums.TypeModifier;
import com.angel.code.generator.data.enums.Visibility;
import com.angel.code.generator.data.impl.java.annotations.JavaAnnotation;
import com.angel.code.generator.data.impl.java.properties.JavaParameter;
import com.angel.code.generator.data.types.CodeBlock;
import com.angel.code.generator.data.types.DataAnnotation;
import com.angel.code.generator.data.types.DataException;
import com.angel.code.generator.data.types.DataMethod;
import com.angel.code.generator.data.types.DataParameter;
import com.angel.code.generator.data.types.impl.DataCommentImpl;
import com.angel.common.helpers.StringHelper;


/**
 * @author Guillermo D. Salazar
 * @since 26/Noviembre/2009.
 *
 */
public class JavaDataMethod extends DataMethod {

	private TypeModifier typeModifier;
	private Visibility visibility;

	public JavaDataMethod(){
		super();
		this.setTypeModifier(TypeModifier.NONE);
		this.setVisibility(Visibility.PUBLIC);
		this.setContent(new CodeBlock());
		this.setComment(new DataCommentImpl());
	}
	
	/**
	 * @return the visibility
	 */
	public Visibility getVisibility() {
		return visibility;
	}

	/**
	 * @param visibility the visibility to set
	 */
	public void setVisibility(Visibility visibility) {
		this.visibility = visibility;
	}

	/**
	 * @return the typeModifier
	 */
	public TypeModifier getTypeModifier() {
		return typeModifier;
	}

	/**
	 * @param typeModifier the typeModifier to set
	 */
	public void setTypeModifier(TypeModifier typeModifier) {
		this.typeModifier = typeModifier;
	}

	public void setStaticTypeModifier(){
		this.setTypeModifier(TypeModifier.STATIC);
	}
	
	public void setFinalStaticTypeModifier(){
		this.setTypeModifier(TypeModifier.FINAL_STATIC);
	}

	public void setNoneModifier(){
		this.setTypeModifier(TypeModifier.NONE);
	}

	public void setFinalTypeModifier(){
		this.setTypeModifier(TypeModifier.FINAL);
	}

	protected String convertTypeModifier(){
		return this.getTypeModifier().getTypeModifier();
	}

	
	protected String convertVisibility(){
		return this.getVisibility().getVisibility();
	}
	
	@Override
	protected String convertCodeDataMethodSign() {
		String convertedCode = this.hasAnnotations() ? "\n": "";
		convertedCode += "\t" + this.convertVisibility() + " " + this.convertTypeModifier();
		convertedCode += this.hasReturnType() ? " " + this.convertReturnType() + " ": " void ";
		convertedCode += this.getMethodName();
		convertedCode += this.convertCodeDataParameters();
		convertedCode += this.convertCodeDataExceptions();
		return convertedCode;
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
