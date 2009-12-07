/**
 * 
 */
package com.angel.code.generator.data.types.codeLine;

import java.util.List;


/**
 * @author Guillermo D. Salazar
 * @since 26/Noviembre/2009.
 *
 */
public class HarcodedCodeLine extends CodeLine {

	private boolean endOfLine;
	private boolean returnType;
	private String code;

	public HarcodedCodeLine(){
		super();
		this.setEndOfLine(true);
		this.setReturnType(false);
	}

	public HarcodedCodeLine(String code){
		this();
		this.setCode(code);
	}

	public HarcodedCodeLine(String code, boolean returnType){
		this(code);
		this.setReturnType(returnType);
	}

	public HarcodedCodeLine(String code, boolean returnType, boolean endOfLine){
		this(code, returnType);
		this.setEndOfLine(endOfLine);
	}
	
	/**
	 * @return the returnType
	 */
	public boolean isReturnType() {
		return returnType;
	}

	/**
	 * @param returnType the returnType to set
	 */
	public void setReturnType(boolean returnType) {
		this.returnType = returnType;
	}

	/**
	 * @return the endOfLine
	 */
	public boolean isEndOfLine() {
		return endOfLine;
	}

	/**
	 * @param endOfLine the endOfLine to set
	 */
	public void setEndOfLine(boolean endOfLine) {
		this.endOfLine = endOfLine;
	}

	@Override
	public boolean isReturn(){
		return this.isReturnType();
	}

	@Override
	public boolean hasEndOfLine(){
		return true;
	}

	@Override
	protected void completeImportsType(List<String> importsType) {
		// Do nothing.		
	}

	public String convertCode() {
		return this.getCode();
	}

	/**
	 * @return the code
	 */
	public String getCode() {
		return code;
	}

	/**
	 * @param code the code to set
	 */
	public void setCode(String code) {
		this.code = code;
	}
}
