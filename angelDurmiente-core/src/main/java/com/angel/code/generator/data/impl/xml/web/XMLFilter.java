/**
 * 
 */
package com.angel.code.generator.data.impl.xml.web;

import com.thoughtworks.xstream.annotations.XStreamAlias;


/**
 * @author Guillermo D. Salazar
 *
 */
@XStreamAlias("filter")
public class XMLFilter {

	@XStreamAlias("filter-name")
	private String filterName;
	@XStreamAlias("filter-class")
	private String filterClass;


	/**
	 * 
	 */
	public XMLFilter() {
		super();
	}

	public XMLFilter(String filterName, String filterClass) {
		this();
		this.setFilterName(filterName);
		this.setFilterClass(filterClass);
	}

	/**
	 * @return the filterName
	 */
	public String getFilterName() {
		return filterName;
	}

	/**
	 * @param filterName the filterName to set
	 */
	public void setFilterName(String filterName) {
		this.filterName = filterName;
	}

	/**
	 * @return the filterClass
	 */
	public String getFilterClass() {
		return filterClass;
	}

	/**
	 * @param filterClass the filterClass to set
	 */
	public void setFilterClass(String filterClass) {
		this.filterClass = filterClass;
	}
}
