/**
 * 
 */
package com.angel.code.generator.data.impl.as3;

import com.angel.code.generator.data.enums.FlexTypeModifier;
import com.angel.code.generator.data.enums.FlexVisibility;
import com.angel.code.generator.data.impl.as3.annotations.FlexAnnotation;
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

	private FlexTypeModifier typeModifier;
	private FlexVisibility visibility;
	private boolean getter;
	private boolean setter;
	
	public FlexDataMethod(){
		super();
		this.setSetter(false);
		this.setGetter(false);
		this.setVisibility(FlexVisibility.PUBLIC);
		this.setTypeModifier(FlexTypeModifier.NONE);
	}

	/**
	 * @return the typeModifier
	 */
	public FlexTypeModifier getTypeModifier() {
		return typeModifier;
	}

	/**
	 * @param typeModifier the typeModifier to set
	 */
	public void setTypeModifier(FlexTypeModifier typeModifier) {
		this.typeModifier = typeModifier;
	}

	/**
	 * @return the visibility
	 */
	public FlexVisibility getVisibility() {
		return visibility;
	}

	/**
	 * @param visibility the visibility to set
	 */
	public void setVisibility(FlexVisibility visibility) {
		this.visibility = visibility;
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
		convertedCode += "\t" + this.convertVisibility();
		convertedCode += this.convertTypeModifier();
		convertedCode += (this.isGetter() ? "get" : "") + (this.isSetter() ? "set": "") + " function ";
		convertedCode += this.getMethodName();
		convertedCode += this.convertCodeDataParameters();
		convertedCode += ":" + (this.hasReturnType() ? " " + this.convertReturnType() + " ": " void ");
		convertedCode += this.convertCodeDataExceptions();
		return convertedCode;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public <T extends DataAnnotation> T createAnnotation(String canonicalName) {
		FlexAnnotation flexAnnotation = new FlexAnnotation();
		flexAnnotation.setName(canonicalName);
		super.addAnnotation(flexAnnotation);
		return (T) flexAnnotation;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T extends CodeBlock> T createCodeBlock() {
		CodeBlock codeBlock = new CodeBlock();
		super.setContent(codeBlock);
		return (T) codeBlock;
	}

	@SuppressWarnings("unchecked")
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

	/**
	 * @return the getter
	 */
	public boolean isGetter() {
		return getter;
	}

	/**
	 * @param getter the getter to set
	 */
	public void setGetter(boolean getter) {
		this.getter = getter;
	}

	/**
	 * @return the setter
	 */
	public boolean isSetter() {
		return setter;
	}

	/**
	 * @param setter the setter to set
	 */
	public void setSetter(boolean setter) {
		this.setter = setter;
	}
}
