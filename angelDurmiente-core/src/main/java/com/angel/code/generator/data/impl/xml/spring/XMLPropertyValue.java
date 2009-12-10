/**
 * 
 */
package com.angel.code.generator.data.impl.xml.spring;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;


/**
 * @author Guillermo D. Salazar
 *
 */
@XStreamAlias("property")
public class XMLPropertyValue {

	@XStreamAsAttribute
	private String name;
	@XStreamAsAttribute
	private String value;
	
	/**
	 * 
	 */
	public XMLPropertyValue() {
		super();
	}

	public XMLPropertyValue(String name) {
		this();
		this.setName(name);
	}
	
	public XMLPropertyValue(String name, String value) {
		this();
		this.setName(name);
		this.setValue(value);
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the value
	 */
	public String getValue() {
		return value;
	}

	/**
	 * @param value the value to set
	 */
	public void setValue(String value) {
		this.value = value;
	}
}
