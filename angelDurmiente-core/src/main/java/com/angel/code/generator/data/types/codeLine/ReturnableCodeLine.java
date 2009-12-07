/**
 * 
 */
package com.angel.code.generator.data.types.codeLine;

import java.util.List;

import com.angel.code.generator.helpers.PackageHelper;
import com.angel.common.helpers.StringHelper;

/**
 * @author Guillermo D. Salazar
 * @since 26/Noviembre/2009.
 *
 */
public class ReturnableCodeLine extends CodeLine {

	private String returnCanonicalName;
	private String returnCollectionCanonicalName;
	private ExecutableReturnCodeLine executableReturnCodeLine;

	/**
	 * Create a returnable code line with an executable return code line. This return canonical name must be equals
	 * to content code return canonical name. 
	 * <pre>
	 * 	return executableReturnCodeLine
	 * </pre>
	 * @param returnCanonicalName
	 * @param executableReturnCodeLine
	 */
	public ReturnableCodeLine(String returnCanonicalName, ExecutableReturnCodeLine executableReturnCodeLine){
		super();
		this.setReturnCanonicalName(returnCanonicalName);
		this.setExecutableReturnCodeLine(executableReturnCodeLine);
	}

	public ReturnableCodeLine(String returnCanonicalName, String returnCollectionCanonicalName, ExecutableReturnCodeLine executableReturnCodeLine){
		this(returnCanonicalName, executableReturnCodeLine);
		this.setReturnCollectionCanonicalName(returnCollectionCanonicalName);
	}
	
	@Override
	public boolean isReturn(){
		return true;
	}
	
	public String convertCode() {
		boolean needsCast = this.getExecutableReturnCodeLine().needsCast(this.getReturnCanonicalName(), this.getReturnCollectionCanonicalName());
		String convertedCode = "return ";
		if(needsCast){
			convertedCode += this.convertCodeCast();	
		}
		convertedCode += this.getExecutableReturnCodeLine().convertCode();
		return convertedCode;
	}


	protected String convertCodeCast() {
		String convertedCode = "(";
		convertedCode += this.convertCodeVariableType();
		convertedCode += ")";
		return convertedCode;
	}
	
	protected String convertCodeVariableType() {
		String convertedCode = "";
		if(this.hasCollectionCanonicalName()){
			convertedCode += this.getSimpleReturnCollectionCanonicalName() + "<" + this.getSimpleReturnCanonicalName() + ">";
		} else {
			convertedCode += this.getSimpleReturnCanonicalName();
		}
		return convertedCode;
	}

	protected String getSimpleReturnCollectionCanonicalName() {
		return StringHelper.isNotEmpty(this.getReturnCollectionCanonicalName()) ? PackageHelper.getClassSimpleName(this.getReturnCollectionCanonicalName()) : "";
	}

	protected String getSimpleReturnCanonicalName() {
		return PackageHelper.getClassSimpleName(this.getReturnCanonicalName());
	}
	
	/**
	 * @return the returnCanonicalName
	 */
	public String getReturnCanonicalName() {
		return returnCanonicalName;
	}

	/**
	 * @param returnCanonicalName the returnCanonicalName to set
	 */
	public void setReturnCanonicalName(String returnCanonicalName) {
		this.returnCanonicalName = returnCanonicalName;
	}

	/**
	 * @return the returnCollectionCanonicalName
	 */
	public String getReturnCollectionCanonicalName() {
		return returnCollectionCanonicalName;
	}

	/**
	 * @param returnCollectionCanonicalName the returnCollectionCanonicalName to set
	 */
	public void setReturnCollectionCanonicalName(
			String returnCollectionCanonicalName) {
		this.returnCollectionCanonicalName = returnCollectionCanonicalName;
	}

	public boolean hasCollectionCanonicalName(){
		return StringHelper.isNotEmpty(this.getReturnCollectionCanonicalName());
	}


	/**
	 * @return the executableReturnCodeLine
	 */
	public ExecutableReturnCodeLine getExecutableReturnCodeLine() {
		return executableReturnCodeLine;
	}


	/**
	 * @param executableReturnCodeLine the executableReturnCodeLine to set
	 */
	public void setExecutableReturnCodeLine(
			ExecutableReturnCodeLine executableReturnCodeLine) {
		this.executableReturnCodeLine = executableReturnCodeLine;
	}

	@Override
	protected void completeImportsType(List<String> importsType) {
		super.addImportType(importsType, this.getReturnCanonicalName());
		super.addImportType(importsType, this.getReturnCollectionCanonicalName());
		super.addImportsType(importsType, this.getExecutableReturnCodeLine().getImportsType());
	}
}
