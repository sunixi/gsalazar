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
public class AssignableCodeLine extends CodeLine {

	private String returnCanonicalName;
	private String returnCollectionCanonicalName;
	private String variableName;
	private ExecutableReturnCodeLine executableReturnCodeLine;

	/**
	 * Create a null assignable code line with a variable type, and a variable name with de simple variable type.
	 * <pre>
	 * 	Type type = null
	 * </pre>
	 * @param variableCanonicalName to be assignable type.
	 */
	public AssignableCodeLine(String variableCanonicalName){
		this(PackageHelper.getClassSimpleVariableName(variableCanonicalName), variableCanonicalName);
	}

	/**
	 * Create a assingable code line with a variable type, and a variable name with a simple variable type.
	 * <pre>
	 * 	Type type = executableReturnCodeLine
	 * </pre>
	 * @param variableCanonicalName to be assignable.
	 * @param executableReturnCodeLine to assign variable name.
	 */
	public AssignableCodeLine(String variableCanonicalName, ExecutableReturnCodeLine executableReturnCodeLine){
		this(PackageHelper.getClassSimpleVariableName(variableCanonicalName), executableReturnCodeLine, variableCanonicalName);
	}
	
	/**
	 * Create a null assignable code line with a variable type.
	 * <pre>
	 * 	Type variableName = null
	 * </pre>
	 * @param variableName to be assignable.
	 * @param variableCanonicalName type to be create to variable.
	 */
	public AssignableCodeLine(String variableName, String variableCanonicalName){
		super();
		this.setVariableName(variableName);
		this.setReturnCanonicalName(variableCanonicalName);
		this.setExecutableReturnCodeLine(executableReturnCodeLine);
	}
	
	public AssignableCodeLine(String variableName, ExecutableReturnCodeLine executableReturnCodeLine, String variableCanonicalName){
		super();
		this.setVariableName(variableName);
		this.setReturnCanonicalName(variableCanonicalName);
		this.setExecutableReturnCodeLine(executableReturnCodeLine);
	}

	/**
	 * Create an assignable code line for a variable name with a typed collection. This code line is going to be assignable by a 
	 * executable return code line.
	 * <pre>
	 * 	CollectionType&lt;Type&gt; variableName = executableReturnCodeLine.
	 * </pre>
	 * @param variableName to be assigned.
	 * @param executableReturnCodeLine to be executed to assigned value to variable in this code line.
	 * @param variableCanonicalName variable's canonical name type. 
	 * @param collectionCanonicalName variable's collection canonical name type.
	 */
	public AssignableCodeLine(String variableName, ExecutableReturnCodeLine executableReturnCodeLine, String variableCanonicalName, String collectionCanonicalName){
		this(variableName, executableReturnCodeLine, variableCanonicalName);
		this.setReturnCollectionCanonicalName(collectionCanonicalName);
	}
	
	
	public String convertCode() {
		String convertedCode = this.convertCodeVariableType();
		convertedCode += " = ";
		if(this.hasExecutableReturnCodeLine()){
			boolean needsCast = this.getExecutableReturnCodeLine().needsCast(this.getReturnCanonicalName(), this.getReturnCollectionCanonicalName());
			if(needsCast){
				convertedCode += this.convertCodeCast();	
			}
			convertedCode += this.getExecutableReturnCodeLine().convertCode();
		} else {
			convertedCode += "null";
		}
		return convertedCode;
	}

	protected boolean hasExecutableReturnCodeLine() {
		return this.getExecutableReturnCodeLine() != null;
	}

	protected String getSimpleReturnCollectionCanonicalName() {
		return PackageHelper.getClassSimpleName(this.getReturnCollectionCanonicalName());
	}

	protected String getSimpleReturnCanonicalName() {
		return PackageHelper.getClassSimpleName(this.getReturnCanonicalName());
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
		convertedCode += " " + this.getVariableName();
		return convertedCode;
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
	 * @return the variableName
	 */
	public String getVariableName() {
		return variableName;
	}

	/**
	 * @param variableName the variableName to set
	 */
	public void setVariableName(String variableName) {
		this.variableName = variableName;
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
		this.addImportType(importsType, this.getReturnCanonicalName());
		this.addImportType(importsType, this.getReturnCollectionCanonicalName());
		this.completeImportsTypeExecutableReturnCodeLine(importsType);
	}
	
	protected void completeImportsTypeExecutableReturnCodeLine(List<String> importsType){
		if(this.hasExecutableReturnCodeLine()){
			this.addImportsType(importsType, this.getExecutableReturnCodeLine().getImportsType());
		}		
	}
}
