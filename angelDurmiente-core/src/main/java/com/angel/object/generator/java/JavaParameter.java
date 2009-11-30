/**
 * 
 */
package com.angel.object.generator.java;

import com.angel.common.helpers.StringHelper;
import com.angel.object.generator.helper.PackageHelper;



/**
 * @author Guillermo D. Salazar
 * @since 26/Noviembre/2009.
 *
 */
public class JavaParameter {

	private String parameterName;
	private String parameterType;
	private boolean importType;
	
	public JavaParameter(String parameterName, String parameterType){
		super();
		this.setParameterName(parameterName);
		this.setParameterType(parameterType);
		this.setImportType(true);
	}
	
	public JavaParameter(String parameterType){
		this(StringHelper.EMPTY_STRING, parameterType);
	}
	
	/**
	 * @return the parameterType
	 */
	public String getParameterType() {
		return parameterType;
	}

	/**
	 * @param parameterType the parameterType to set
	 */
	public void setParameterType(String parameterType) {
		this.parameterType = parameterType;
	}

	/**
	 * @return the parameterName
	 */
	public String getParameterName() {
		return parameterName;
	}

	/**
	 * @param parameterName the parameterName to set
	 */
	public void setParameterName(String parameterName) {
		this.parameterName = parameterName;
	}
	
	public boolean equalsParameterType(JavaParameter javaParameter){
		return this.getParameterType().equals(javaParameter.getParameterType());
	}
	
	public String getSimpleReturnTypeName(){
		return PackageHelper.getClassSimpleName(this.getParameterType());
	}

	public String getCanonicalReturnTypeName() {
		return this.getParameterType();
	}

	public String getParameter() {
		return this.getSimpleReturnTypeName() + " " + (this.hasParameterName() ? this.getParameterName() : "");
	}

	public boolean hasParameterName(){
		return StringHelper.isNotEmpty(this.getParameterName());
	}
	
	@Override
	public String toString(){
		return this.getParameterName();
	}

	/**
	 * @return the importType
	 */
	public boolean isImportType() {
		return importType;
	}

	/**
	 * @param importType the importType to set
	 */
	public void setImportType(boolean importType) {
		this.importType = importType;
	}

	public void notImportType() {
		this.setImportType(false);
	}
	
	public void importType() {
		this.setImportType(true);
	}
}
