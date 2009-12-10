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
public class XMLPropertyBeanValue extends XMLPropertyValue {

	@XStreamAsAttribute
	private XMLBean bean;
	
	/**
	 * 
	 */
	public XMLPropertyBeanValue() {
		super();
	}

	public XMLPropertyBeanValue(String name) {
		this();
		this.setName(name);
	}
	
	public XMLPropertyBeanValue(String name, XMLBean bean) {
		this();
		this.setName(name);
		this.setBean(bean);
	}

	/**
	 * @return the bean
	 */
	public XMLBean getBean() {
		return bean;
	}

	/**
	 * @param bean the bean to set
	 */
	public void setBean(XMLBean bean) {
		this.bean = bean;
	}
}
