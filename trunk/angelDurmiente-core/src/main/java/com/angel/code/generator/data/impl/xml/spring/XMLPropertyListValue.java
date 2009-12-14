/**
 * 
 */
package com.angel.code.generator.data.impl.xml.spring;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;



/**
 * @author Guillermo D. Salazar
 *
 */
public class XMLPropertyListValue extends XMLPropertyValue{

	@XStreamAlias("value")
	private String listValue;

	/**
	 * 
	 */
	public XMLPropertyListValue(String listValue) {
		super();
		this.setListValue(listValue);
	}

	/**
	 * @return the listValue
	 */
	public String getListValue() {
		return listValue;
	}

	/**
	 * @param listValue the listValue to set
	 */
	public void setListValue(String listValue) {
		this.listValue = listValue;
	}
}
