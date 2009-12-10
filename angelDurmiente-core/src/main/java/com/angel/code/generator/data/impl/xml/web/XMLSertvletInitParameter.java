/**
 * 
 */
package com.angel.code.generator.data.impl.xml.web;

import com.thoughtworks.xstream.annotations.XStreamAlias;


/**
 * @author Guillermo D. Salazar
 *
 */
@XStreamAlias("init-param")
public class XMLSertvletInitParameter {

	@XStreamAlias("param-name")
	private String paramName;

	@XStreamAlias("param-value")
	private String paramValue;

	/**
	 * 
	 */
	public XMLSertvletInitParameter() {
		super();
	}

	public XMLSertvletInitParameter(String paramName, String paramValue) {
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
