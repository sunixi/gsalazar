/**
 * 
 */
package com.angel.code.generator.data.impl.xml.web;

import com.thoughtworks.xstream.annotations.XStreamAlias;


/**
 * @author Guillermo D. Salazar
 *
 */
@XStreamAlias("servlet")
public class XMLServlet {

	@XStreamAlias("servlet-name")
	private String servletName;
	@XStreamAlias("servlet-class")
	private String servletClass;


	/**
	 * 
	 */
	public XMLServlet() {
		super();
	}

	public XMLServlet(String servletName, String servletClass) {
		this();
		this.setServletName(servletName);
		this.setServletClass(servletClass);
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
	 * @return the servletClass
	 */
	public String getServletClass() {
		return servletClass;
	}

	/**
	 * @param servletClass the servletClass to set
	 */
	public void setServletClass(String servletClass) {
		this.servletClass = servletClass;
	}	
}
