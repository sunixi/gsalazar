/**
 * 
 */
package com.angel.object.generator.java;

import java.util.List;


/**
 * @author Guillermo D. Salazar
 * @since 26/Noviembre/2009.
 *
 */
public class JavaMethod {

	private String methodName;
	private List<JavaParameter> parameters;
	private JavaParameter returnType;
	private String contentMethod;
	private boolean implemented = true;

	public JavaMethod(String methodName){
		super();
		this.setMethodName(methodName);
	}
	
	public JavaMethod(String methodName, List<JavaParameter> parameters){
		this(methodName);
		this.setParameters(parameters);
	}
	
	public JavaMethod(String methodName, List<JavaParameter> parameters, JavaParameter returnType){
		this(methodName, parameters);
		this.setReturnType(returnType);
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
	 * @return the parameters
	 */
	public List<JavaParameter> getParameters() {
		return parameters;
	}

	/**
	 * @param parameters the parameters to set
	 */
	public void setParameters(List<JavaParameter> parameters) {
		this.parameters = parameters;
	}

	/**
	 * @return the returnType
	 */
	public JavaParameter getReturnType() {
		return returnType;
	}

	/**
	 * @param returnType the returnType to set
	 */
	public void setReturnType(JavaParameter returnType) {
		this.returnType = returnType;
	}

	/**
	 * @return the contentMethod
	 */
	public String getContentMethod() {
		return contentMethod;
	}

	/**
	 * @param contentMethod the contentMethod to set
	 */
	public void setContentMethod(String contentMethod) {
		this.contentMethod = contentMethod;
	}

	/**
	 * @return the implemented
	 */
	public boolean isImplemented() {
		return implemented;
	}

	/**
	 * @param implemented the implemented to set
	 */
	public void setImplemented(boolean implemented) {
		this.implemented = implemented;
	}
}