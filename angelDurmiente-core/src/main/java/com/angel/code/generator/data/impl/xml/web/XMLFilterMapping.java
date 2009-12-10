/**
 * 
 */
package com.angel.code.generator.data.impl.xml.web;

import com.thoughtworks.xstream.annotations.XStreamAlias;


/**
 * @author Guillermo D. Salazar
 *
 */
@XStreamAlias("filter-mapping")
public class XMLFilterMapping {

	@XStreamAlias("filter-name")
	private String filterName;

	@XStreamAlias("url-mapping")
	private String urlMapping;

	/**
	 * 
	 */
	public XMLFilterMapping() {
		super();
	}

	public XMLFilterMapping(String filterName, String urlMapping) {
		this();
		this.setFilterName(filterName);
		this.setUrlMapping(urlMapping);
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
	 * @return the urlMapping
	 */
	public String getUrlMapping() {
		return urlMapping;
	}

	/**
	 * @param urlMapping the urlMapping to set
	 */
	public void setUrlMapping(String urlMapping) {
		this.urlMapping = urlMapping;
	}
}
