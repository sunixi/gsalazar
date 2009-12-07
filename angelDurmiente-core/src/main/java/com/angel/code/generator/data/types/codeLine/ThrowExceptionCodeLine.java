/**
 * 
 */
package com.angel.code.generator.data.types.codeLine;

import com.angel.common.helpers.StringHelper;


/**
 * @author Guillermo D. Salazar
 * @since 26/Noviembre/2009.
 *
 */
public class ThrowExceptionCodeLine extends ReturnableCodeLine {

	private String message;
	private String catchExceptionVariableName;

	public ThrowExceptionCodeLine(String returnCanonicalName, String message){
		super(returnCanonicalName, null);
		this.setMessage(message);
	}

	public ThrowExceptionCodeLine(String returnCanonicalName, String message, String catchExceptionVariableName){
		this(returnCanonicalName, message);
		this.setCatchExceptionVariableName(catchExceptionVariableName);
	}

	@Override
	public String convertCode() {
		String convertedCode = "throw new " + this.getSimpleReturnCanonicalName();
		convertedCode += "(\"" + this.getMessage() + "\"" ;
		convertedCode += this.hasCatchExceptionvariableName() ? ", " + this.getCatchExceptionVariableName() : "" ;
		return convertedCode;
	}
	
	public boolean hasCatchExceptionvariableName(){
		return StringHelper.isNotEmpty(this.getCatchExceptionVariableName());
	}

	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * @param message the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * @return the catchExceptionVariableName
	 */
	public String getCatchExceptionVariableName() {
		return catchExceptionVariableName;
	}

	/**
	 * @param catchExceptionVariableName the catchExceptionVariableName to set
	 */
	public void setCatchExceptionVariableName(String catchExceptionVariableName) {
		this.catchExceptionVariableName = catchExceptionVariableName;
	}
}
