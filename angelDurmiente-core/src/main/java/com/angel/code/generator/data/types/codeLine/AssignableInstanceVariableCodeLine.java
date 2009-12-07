/**
 * 
 */
package com.angel.code.generator.data.types.codeLine;

import com.angel.code.generator.helpers.PackageHelper;

/**
 * @author Guillermo D. Salazar
 * @since 26/Noviembre/2009.
 *
 */
public class AssignableInstanceVariableCodeLine extends AssignableCodeLine {

	/**
	 * Create a null assignable code line for a variable instance type.
	 * <pre>
	 * 	this.variableName = null
	 * </pre>
	 * @param variableCanonicalName to be assignable type.
	 */
	public AssignableInstanceVariableCodeLine(String variableCanonicalName){
		super(PackageHelper.getClassSimpleVariableName(variableCanonicalName), variableCanonicalName);
	}

	/**
	 * Create a assignable code line for a variable instance with a executable return code line.
	 * <pre>
	 * 	this.variableName = executableReturnCodeLine
	 * </pre>
	 * @param variableCanonicalName
	 * @param executableReturnCodeLine
	 */
	public AssignableInstanceVariableCodeLine(String variableCanonicalName, ExecutableReturnCodeLine executableReturnCodeLine){
		super(PackageHelper.getClassSimpleVariableName(variableCanonicalName), executableReturnCodeLine, variableCanonicalName);
	}
	
	public AssignableInstanceVariableCodeLine(String variableName, String variableCanonicalName, ExecutableReturnCodeLine executableReturnCodeLine){
		super(PackageHelper.getClassSimpleVariableName(variableName), executableReturnCodeLine, variableCanonicalName);
	}

	public AssignableInstanceVariableCodeLine(String variableName, String variableCanonicalName, ExecutableReturnCodeLine executableReturnCodeLine, String collectionVariableCanonicalName){
		super(PackageHelper.getClassSimpleVariableName(variableName), executableReturnCodeLine, variableCanonicalName, collectionVariableCanonicalName);
	}
	
	@Override
	public String convertCode() {
		String convertedCode = "this." + this.getVariableName() + " = ";
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
}
