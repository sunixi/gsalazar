/**
 * 
 */
package com.angel.code.generator.data.impl.xml.spring;

import java.util.ArrayList;
import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;


/**
 * @author Guillermo D. Salazar
 *
 */
@XStreamAlias("property")
public class XMLPropertyList extends XMLPropertyValue{

	@XStreamAlias("list")
	private List<XMLPropertyListValue> values;

	/**
	 * 
	 */
	public XMLPropertyList() {
		super();
		this.setValues(new ArrayList<XMLPropertyListValue>());
	}

	/**
	 * @return the values
	 */
	public List<XMLPropertyListValue> getValues() {
		return values;
	}


	/**
	 * @param values the values to set
	 */
	public void setValues(List<XMLPropertyListValue> values) {
		this.values = values;
	}

	public void addValue(String value){
		this.getValues().add(new XMLPropertyListValue(value));
	}
}
