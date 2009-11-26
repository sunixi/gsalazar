/**
 * 
 */
package com.angel.object.generator.java;



/**
 * @author Guillermo D. Salazar
 * @since 26/Noviembre/2009.
 *
 */
public class JavaParameter {

	private String parameterName;
	private Class<?> parameterType;
	
	public JavaParameter(String parameterName, Class<?> parameterType){
		super();
		this.setParameterName(parameterName);
		this.setParameterType(parameterType);
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

	/**
	 * @return the parameterType
	 */
	public Class<?> getParameterType() {
		return parameterType;
	}

	/**
	 * @param parameterType the parameterType to set
	 */
	public void setParameterType(Class<?> parameterType) {
		this.parameterType = parameterType;
	}
	
	public boolean equalsParameterType(JavaParameter javaParameter){
		return this.getParameterType().equals(javaParameter.getParameterType());
	}
}
