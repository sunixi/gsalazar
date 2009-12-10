/**
 * 
 */
package com.angel.code.generator.data.impl.xml.web;

import com.thoughtworks.xstream.annotations.XStreamAlias;


/**
 * @author Guillermo D. Salazar
 *
 */
@XStreamAlias("listener")
public class XMLListener {

	@XStreamAlias("listener-class")
	private String listenerClass;


	/**
	 * 
	 */
	public XMLListener() {
		super();
	}

	public XMLListener(String listenerClass) {
		this();
		this.setListenerClass(listenerClass);
	}

	/**
	 * @return the listenerClass
	 */
	public String getListenerClass() {
		return listenerClass;
	}

	/**
	 * @param listenerClass the listenerClass to set
	 */
	public void setListenerClass(String listenerClass) {
		this.listenerClass = listenerClass;
	}
}
