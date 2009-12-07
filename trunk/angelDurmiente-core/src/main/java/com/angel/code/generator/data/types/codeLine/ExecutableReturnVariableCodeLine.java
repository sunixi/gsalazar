/**
 * 
 */
package com.angel.code.generator.data.types.codeLine;



/**
 * @author Guillermo D. Salazar
 * @since 26/Noviembre/2009.
 *
 */
public class ExecutableReturnVariableCodeLine extends ExecutableReturnCodeLine {

	public ExecutableReturnVariableCodeLine(String variableName, String returnCanonicalName){
		super(null, returnCanonicalName);
		this.setVariableName(variableName);
	}

	public ExecutableReturnVariableCodeLine(String variableName, String returnCanonicalName, String returnCollectionCanonicalName){
		this(variableName, returnCanonicalName);
		this.setReturnCollectionCanonicalName(returnCollectionCanonicalName);
	}

	@Override
	public String convertCode() {
		return this.getVariableName();
	}

	@Override
	public ExecutableReturnVariableCodeLine addParameterName(String parameterName) {
		return this;
	}
}
