/**
 * 
 */
package com.angel.object.generator.java;

import java.util.ArrayList;
import java.util.List;

import com.angel.common.helpers.StringHelper;


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
	private JavaMethodVisibility visibility;

	public JavaMethod(String methodName){
		super();
		this.setMethodName(methodName);
		this.setVisibility(JavaMethodVisibility.PUBLIC);
	}
	
	public JavaMethod(String methodName, List<JavaParameter> parameters){
		this(methodName);
		this.setParameters(parameters);
	}

	public JavaMethod(String methodName, List<JavaParameter> parameters, boolean implemented){
		this(methodName, parameters);
		this.setReturnType(returnType);
		this.setImplemented(implemented);
	}

	public JavaMethod(String methodName, List<JavaParameter> parameters, JavaParameter returnType, String contentMethod){
		this(methodName, parameters);
		this.setReturnType(returnType);
		this.setContentMethod(contentMethod);
	}
	
	public JavaMethod(String methodName, List<JavaParameter> parameters, JavaParameter returnType){
		this(methodName, parameters);
		this.setReturnType(returnType);
	}
	
	public JavaMethod(String methodName, List<JavaParameter> parameters, JavaParameter returnType, boolean implemented){
		this(methodName, parameters);
		this.setReturnType(returnType);
		this.setImplemented(implemented);
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
	
	public void setNotImplemented(){
		this.setImplemented(false);
	}
	
	public void setImplemented(){
		this.setImplemented(true);
	}

	public String getMethod(){
		String method = "";
		method += this.getVisibility().getVisibility() + " ";
		method += this.hasReturnType() ? this.getReturnType().getSimpleReturnTypeName() + " " : " void ";
		method += this.getMethodName() + "(";
		method += this.hasJavaParameters() ? this.getPlainJavaParametersNames() + ")" : "";
		method += this.getMethodName() + ")";
		if(this.isImplemented()){
			method += " {\n";
			method += this.getContentMethod();
			method += " }\n\n";
		} else {
			method += ";";
		}
		return method;
	}
	
	public boolean hasJavaParameters(){
		return this.getParameters().size() > 0;
	}
	
	public boolean hasReturnType(){
		return this.getReturnType() != null;
	}
	
	public List<String> getJavaParametersNames(){
		List<String> parametersNames = new ArrayList<String>();
		for(JavaParameter jp: this.getParameters()){
			parametersNames.add(jp.getParameterName());
		}
		return parametersNames;
	}
	
	public String getPlainJavaParametersNames(){
		List<String> parametersNames = this.getJavaParametersNames();
		return StringHelper.convertToPlainString(parametersNames.toArray(), ",");
	}

	/**
	 * @return the visibility
	 */
	public JavaMethodVisibility getVisibility() {
		return visibility;
	}

	/**
	 * @param visibility the visibility to set
	 */
	public void setVisibility(JavaMethodVisibility visibility) {
		this.visibility = visibility;
	}



	public enum JavaMethodVisibility {
		PROTECTED("protected"),
		PUBLIC("public"),
		PRIVATE("private");
		
		private String visibility;

		private JavaMethodVisibility(String visibility){
			this.visibility = visibility;
		}

		public String getVisibility() {
			return visibility;
		}	
	}
}
