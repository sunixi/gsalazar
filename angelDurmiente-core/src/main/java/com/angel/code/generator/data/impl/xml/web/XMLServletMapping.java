/**
 * 
 */
package com.angel.code.generator.data.impl.xml.web;

import com.thoughtworks.xstream.annotations.XStreamAlias;


/**
 * @author Guillermo D. Salazar
 *
 */
@XStreamAlias("servlet-mapping")
public class XMLServletMapping {

	@XStreamAlias("servlet-name")
	private String servletName;
	@XStreamAlias("urlPattern")
	private String urlPattern;


	/**
	 * 
	 */
	public XMLServletMapping() {
		super();
	}

	public XMLServletMapping(String servletName, String urlPattern) {
		this();
		this.setServletName(servletName);
		this.setUrlPattern(urlPattern);
	}

	/**
	 * @return the servletName
	 */
	public String getServletName() {
		return servletName;
	}

	/**
	 * @param servletName the servletName to set
	 */
	public void setServletName(String servletName) {
		this.servletName = servletName;
	}

	/**
	 * @return the urlPattern
	 */
	public String getUrlPattern() {
		return urlPattern;
	}

	/**
	 * @param urlPattern the urlPattern to set
	 */
	public void setUrlPattern(String urlPattern) {
		this.urlPattern = urlPattern;
	}
}
