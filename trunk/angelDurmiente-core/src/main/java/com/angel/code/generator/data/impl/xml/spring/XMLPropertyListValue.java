/**
 * 
 */
package com.angel.code.generator.data.impl.xml.spring;

import java.util.ArrayList;
import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamImplicit;



/**
 * @author Guillermo D. Salazar
 *
 */
public class XMLPropertyListValue extends XMLPropertyValue{

	@XStreamImplicit(itemFieldName = "value")
	private List<String> listValue;

	/**
	 * 
	 */
	public XMLPropertyListValue() {
		super();
		this.setListValue(new ArrayList<String>());
	}

	/**
	 * @return the listValue
	 */
	public List<String> getListValue() {
		return listValue;
	}

	/**
	 * @param listValue the listValue to set
	 */
	public void setListValue(List<String> listValue) {
		this.listValue = listValue;
	}

	public void addValue(String value){
		this.getListValue().add(value);
	}

}
