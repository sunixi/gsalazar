/**
 * 
 */
package com.angel.code.generator.data.impl.xml.web;

import java.util.ArrayList;
import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import com.thoughtworks.xstream.annotations.XStreamImplicit;


/**
 * @author Guillermo D. Salazar
 *
 */
@XStreamAlias("web-app")
public class XMLWebapp {
	
	private String displayName;

	@XStreamImplicit
	private List<XMLContextParameter> contextParameters;

	@XStreamImplicit
	private List<XMLListener> listeners;
	
	@XStreamImplicit
	private List<XMLFilter> filters;
	
	@XStreamImplicit
	private List<XMLFilterMapping> filterMappings;

	@XStreamImplicit
	private List<XMLServlet> servlets;
	
	@XStreamImplicit
	private List<XMLServletMapping> servletMappings;
	
	@XStreamAlias("xmlns")
	@XStreamAsAttribute
	private String xmlns;
	
	@XStreamAsAttribute
	@XStreamAlias("xmlns:xsi")
	private String xmlnsXsi;

	@XStreamAsAttribute
	@XStreamAlias("xsi:schemaLocation")
	private String xsiSchemaLotation;

	@XStreamAsAttribute
	private String version;

	/*
xmlns="http://java.sun.com/xml/ns/javaee"
           xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
           xsi:schemaLocation="http://java.sun.com/xml/ns/javaee
		  http://java.sun.com/xml/ns/javaee/web-app_2_5.xsd"
           version="2.5"
	 */

	/**
	 * 
	 */
	public XMLWebapp() {
		super();
		this.setContextParameters(new ArrayList<XMLContextParameter>());
		this.setListeners(new ArrayList<XMLListener>());
		this.setFilters(new ArrayList<XMLFilter>());
		this.setFilterMappings(new ArrayList<XMLFilterMapping>());
		this.setServletMappings(new ArrayList<XMLServletMapping>());
		this.setServlets(new ArrayList<XMLServlet>());
		this.setXmlns("http://java.sun.com/xml/ns/javaee");
		this.setXmlnsXsi("http://www.w3.org/2001/XMLSchema-instance");
		this.setXsiSchemaLotation("http://java.sun.com/xml/ns/javaee\nhttp://java.sun.com/xml/ns/javaee/web-app_2_5.xsd");
		this.setVersion("2.5");
	}

	/**
	 * @return the displayName
	 */
	public String getDisplayName() {
		return displayName;
	}

	/**
	 * @param displayName the displayName to set
	 */
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	/**
	 * @return the contextParameters
	 */
	public List<XMLContextParameter> getContextParameters() {
		return contextParameters;
	}

	/**
	 * @param contextParameters the contextParameters to set
	 */
	public void setContextParameters(List<XMLContextParameter> contextParameters) {
		this.contextParameters = contextParameters;
	}

	public void addContextParam(XMLContextParameter contextParameter) {
		this.getContextParameters().add(contextParameter);
	}

	/**
	 * @return the xmlns
	 */
	protected String getXmlns() {
		return xmlns;
	}

	/**
	 * @param xmlns the xmlns to set
	 */
	protected void setXmlns(String xmlns) {
		this.xmlns = xmlns;
	}

	/**
	 * @return the xmlnsXsi
	 */
	protected String getXmlnsXsi() {
		return xmlnsXsi;
	}

	/**
	 * @param xmlnsXsi the xmlnsXsi to set
	 */
	protected void setXmlnsXsi(String xmlnsXsi) {
		this.xmlnsXsi = xmlnsXsi;
	}

	/**
	 * @return the xsiSchemaLotation
	 */
	protected String getXsiSchemaLotation() {
		return xsiSchemaLotation;
	}

	/**
	 * @param xsiSchemaLotation the xsiSchemaLotation to set
	 */
	protected void setXsiSchemaLotation(String xsiSchemaLotation) {
		this.xsiSchemaLotation = xsiSchemaLotation;
	}

	/**
	 * @return the version
	 */
	protected String getVersion() {
		return version;
	}

	/**
	 * @param version the version to set
	 */
	protected void setVersion(String version) {
		this.version = version;
	}

	/**
	 * @return the listeners
	 */
	public List<XMLListener> getListeners() {
		return listeners;
	}

	/**
	 * @param listeners the listeners to set
	 */
	public void setListeners(List<XMLListener> listeners) {
		this.listeners = listeners;
	}

	public void addListener(XMLListener listener){
		this.getListeners().add(listener);
	}

	public void addFilter(String filterName, String filterClass, String urlMapping){
		this.getFilters().add(new XMLFilter(filterName, filterClass));
		this.getFilterMappings().add(new XMLFilterMapping(filterName, urlMapping));
	}

	public void addFilter(String filterName, String filterClass){
		this.addFilter(filterName, filterClass, "/*");
	}
	
	public void addServlet(String servletName, String servletClass, String urlPattern){
		this.getServlets().add(new XMLServlet(servletName, servletClass));
		this.getServletMappings().add(new XMLServletMapping(servletName, urlPattern));
	}

	/**
	 * @return the filters
	 */
	public List<XMLFilter> getFilters() {
		return filters;
	}

	/**
	 * @param filters the filters to set
	 */
	public void setFilters(List<XMLFilter> filters) {
		this.filters = filters;
	}

	/**
	 * @return the filterMappings
	 */
	public List<XMLFilterMapping> getFilterMappings() {
		return filterMappings;
	}

	/**
	 * @param filterMappings the filterMappings to set
	 */
	public void setFilterMappings(List<XMLFilterMapping> filterMappings) {
		this.filterMappings = filterMappings;
	}

	/**
	 * @return the servlets
	 */
	public List<XMLServlet> getServlets() {
		return servlets;
	}

	/**
	 * @param servlets the servlets to set
	 */
	public void setServlets(List<XMLServlet> servlets) {
		this.servlets = servlets;
	}

	/**
	 * @return the servletMappings
	 */
	public List<XMLServletMapping> getServletMappings() {
		return servletMappings;
	}

	/**
	 * @param servletMappings the servletMappings to set
	 */
	public void setServletMappings(List<XMLServletMapping> servletMappings) {
		this.servletMappings = servletMappings;
	}
}
