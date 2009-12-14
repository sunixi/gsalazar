/**
 * 
 */
package com.angel.code.generator.data.impl.xml.spring;

import com.thoughtworks.xstream.annotations.XStreamAlias;


/**
 * @author Guillermo D. Salazar
 *
 */
@XStreamAlias("property")
public class XMLPropertyList extends XMLPropertyValue{

	@XStreamAlias("list")
	private XMLPropertyListValue values;

	/**
	 * 
	 */
	public XMLPropertyList() {
		super();
		this.setValues(new XMLPropertyListValue());
	}

	/**
	 * @return the values
	 */
	public XMLPropertyListValue getValues() {
		return values;
	}


	/**
	 * @param values the values to set
	 */
	public void setValues(XMLPropertyListValue values) {
		this.values = values;
	}

	public void addValue(String canonicalName) {
		this.getValues().addValue(canonicalName);
	}

}
