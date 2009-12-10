/**
 * 
 */
package com.angel.code.generator.data.impl.xml.web;

import com.thoughtworks.xstream.annotations.XStreamAlias;


/**
 * @author Guillermo D. Salazar
 *
 */
@XStreamAlias("context-param")
public class XMLContextParameter {

	private String paramName;
	private String paramValue;

	/**
	 * 
	 */
	public XMLContextParameter() {
		super();
	}

	public XMLContextParameter(String paramName, String paramValue) {
		this();
		this.setParamName(paramName);
		this.setParamValue(paramValue);
	}

	/**
	 * @return the paramName
	 */
	public String getParamName() {
		return paramName;
	}

	/**
	 * @param paramName the paramName to set
	 */
	public void setParamName(String paramName) {
		this.paramName = paramName;
	}

	/**
	 * @return the paramValue
	 */
	public String getParamValue() {
		return paramValue;
	}

	/**
	 * @param paramValue the paramValue to set
	 */
	public void setParamValue(String paramValue) {
		this.paramValue = paramValue;
	}
}
