/**
 * 
 */
package com.angel.code.generator.data.types.codeLine;

import java.util.ArrayList;
import java.util.List;

import com.angel.common.helpers.StringHelper;

/**
 * @author Guillermo D. Salazar
 * @since 26/Noviembre/2009.
 *
 */
public class ExecutableCodeLine extends CodeLine {

	private String methodName;
	private String variableName;
	private List<String> parametersNames;

	/**
	 * Create a executable code line for "this" variable name.
	 * <pre>
	 * 	this.methodName()
	 * </pre>
	 * @param methodName to be executed.
	 */
	public ExecutableCodeLine(String methodName){
		super();
		this.setMethodName(methodName);
		this.setVariableName("this");
		this.setParametersNames(new ArrayList<String>());
	}

	/**
	 * Create a executable code line for "this" variable name.
	 * <pre>
	 * 	this.methodName(parameterNames)
	 * </pre>
	 * @param methodName to be executed.
	 * @param parameterNames to pass in the method to execute.
	 */
	public ExecutableCodeLine(String methodName, List<String> parameterNames){
		this(methodName);
		this.setParametersNames(parameterNames);
	}

	/**
	 * Create an executable code line for a variable name.
	 * <pre>
	 * 	variableName.methodName()
	 * </pre>
	 * @param methodName to be executed.
	 * @param variableName to execute its method.
	 */
	public ExecutableCodeLine(String methodName, String variableName){
		this(methodName);
		this.setVariableName(variableName);
	}

	/**
	 * Create an executable code line for a variable name.
	 * <pre>
	 * 	variableName.methodName(parameterNames)
	 * </pre>
	 * @param methodName to be executed.
	 * @param variableName to execute its method.
	 * @param parameterNames to be pass in the method.
	 */
	public ExecutableCodeLine(String methodName, String variableName, List<String> parameterNames){
		this(methodName, variableName);
		this.setParametersNames(parameterNames);
	}

	public String convertCode() {
		String convertedCode = "";
		convertedCode += this.getVariableName() + "." + this.getMethodName();
		if(this.hasParameters()){			
			convertedCode += "(" + StringHelper.convertToPlainString(this.getParametersNames().toArray(), ", ") + ")";
		} else {
			convertedCode += "()"; 
		}
		return convertedCode;
	}
	
	public boolean hasParameters(){
		return this.getParametersNames().size() > 0;
	}

	/**
	 * @return the methodName
	 */
	public String getMethodName() {
		return methodName;
	}

	/**
	 * @param methodName the methodName to set
	 */
	public void setMethodName(String methodName) {
		this.methodName = methodName;
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
	 * @return the parametersNames
	 */
	public List<String> getParametersNames() {
		return parametersNames;
	}

	/**
	 * @param parametersNames the parametersNames to set
	 */
	public void setParametersNames(List<String> parametersNames) {
		this.parametersNames = parametersNames;
	}

	@Override
	protected void completeImportsType(List<String> importsType) {
		//Do nothing.		
	}

	public ExecutableCodeLine addParameterName(String parameterName) {
		this.getParametersNames().add(parameterName);
		return this;
	}

	public ExecutableCodeLine addParameterNameString(String stringParameter) {
		return this.addParameterName("\"" + stringParameter + "\"");
	}

	public ExecutableCodeLine addParameterNameClass(String className) {
		return this.addParameterName(className + ".class");	
	}
}
