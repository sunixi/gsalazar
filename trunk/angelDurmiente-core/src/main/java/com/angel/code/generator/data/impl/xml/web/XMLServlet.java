/**
 * 
 */
package com.angel.code.generator.data.impl.xml.web;

import java.util.ArrayList;
import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;


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

	@XStreamAlias("load-on-startup")
	private String loadOnStartup;

	@XStreamImplicit
	private List<XMLSertvletInitParameter> initParameters;

	/**
	 * 
	 */
	public XMLServlet() {
		super();
		this.setInitParameters(new ArrayList<XMLSertvletInitParameter>());
	}

	public XMLServlet(String servletName, String servletClass) {
		this();
		this.setServletName(servletName);
		this.setServletClass(servletClass);
	}

	public XMLServlet(String servletName, String servletClass, String loadOnStartup) {
		this(servletName, servletClass);
		this.setLoadOnStartup(loadOnStartup);
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

	/**
	 * @return the initParameters
	 */
	public List<XMLSertvletInitParameter> getInitParameters() {
		return initParameters;
	}

	/**
	 * @param initParameters the initParameters to set
	 */
	public void setInitParameters(List<XMLSertvletInitParameter> initParameters) {
		this.initParameters = initParameters;
	}
	
	public void addInitParameter(String paramName, String paramValue){
		this.addInitParameter(new XMLSertvletInitParameter(paramName, paramValue));
	}

	public void addInitParameter(XMLSertvletInitParameter initParameter){
		this.getInitParameters().add(initParameter);
	}

	/**
	 * @return the loadOnStartup
	 */
	public String getLoadOnStartup() {
		return loadOnStartup;
	}

	/**
	 * @param loadOnStartup the loadOnStartup to set
	 */
	public void setLoadOnStartup(String loadOnStartup) {
		this.loadOnStartup = loadOnStartup;
	}
}
