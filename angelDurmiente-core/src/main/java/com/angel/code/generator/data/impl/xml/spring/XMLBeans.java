/**
 * 
 */
package com.angel.code.generator.data.impl.xml.spring;

import java.util.ArrayList;
import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;


/**
 * @author Guillermo D. Salazar
 *
 */
@XStreamAlias("beans")
public class XMLBeans {
	
	@XStreamImplicit
	private List<XMLBean> beans;

	/**
	 * 
	 */
	public XMLBeans() {
		super();
		this.setBeans(new ArrayList<XMLBean>());
	}

	/**
	 * @return the beans
	 */
	public List<XMLBean> getBeans() {
		return beans;
	}

	/**
	 * @param beans the beans to set
	 */
	public void setBeans(List<XMLBean> beans) {
		this.beans = beans;
	}

	public void addBean(XMLBean bean){
		this.getBeans().add(bean);
	}
}
