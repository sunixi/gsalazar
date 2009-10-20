/**
 * 
 */
package com.angel.architecture.persistence.beans;

import java.util.Date;

import javax.persistence.Entity;
import javax.servlet.http.HttpServletRequest;

import com.angel.architecture.persistence.base.PersistentObject;

/**
 * 
 *	@author Guillermo Daniel Salazar.
 *	@since 16/Semptiembre/2009.
 */
@Entity
public class ClickUser extends PersistentObject {

	private static final long serialVersionUID = -462238529934628758L;

	private String label;
	private String description;
	private Date clickDate;
	
	private String remoteAddress;
	private String remoteHost;
	private int remotePort;
	
	private String contextPath;
	private String pathInfo;
	
	private String localAddress;
	private int localPort;
	
	private String method;
	
	private String requestURI;
	private String requestURL;
	private String requestedSessionID;
	
	private int serverPort;
	private String serverPath;
	private String serverName;

	public ClickUser(HttpServletRequest request){
		this();
		this.setRemoteAddress(request.getRemoteAddr());
		this.setRemoteHost(request.getRemoteHost());
		this.setRemotePort(request.getRemotePort());
		this.setContextPath(request.getContextPath());
		this.setPathInfo(request.getPathInfo());
		this.setLocalAddress(request.getLocalAddr());
		this.setLocalPort(request.getLocalPort());
		this.setMethod(request.getMethod());
		this.setRequestURI(request.getRequestURI());
		this.setRequestURL(request.getRequestURL().toString());
		this.setRequestedSessionID(request.getRequestedSessionId());
		this.setServerPort(request.getServerPort());
		this.setServerPath(request.getServletPath());
		this.setServerName(request.getServerName());
	}
	
	public ClickUser(){
		super();
	}

	/**
	 * @return the remoteAddress
	 */
	public String getRemoteAddress() {
		return remoteAddress;
	}

	/**
	 * @param remoteAddress the remoteAddress to set
	 */
	public void setRemoteAddress(String remoteAddress) {
		this.remoteAddress = remoteAddress;
	}

	/**
	 * @return the remoteHost
	 */
	public String getRemoteHost() {
		return remoteHost;
	}

	/**
	 * @param remoteHost the remoteHost to set
	 */
	public void setRemoteHost(String remoteHost) {
		this.remoteHost = remoteHost;
	}

	/**
	 * @return the remotePort
	 */
	public int getRemotePort() {
		return remotePort;
	}

	/**
	 * @param remotePort the remotePort to set
	 */
	public void setRemotePort(int remotePort) {
		this.remotePort = remotePort;
	}

	/**
	 * @return the contextPath
	 */
	public String getContextPath() {
		return contextPath;
	}

	/**
	 * @param contextPath the contextPath to set
	 */
	public void setContextPath(String contextPath) {
		this.contextPath = contextPath;
	}

	/**
	 * @return the pathInfo
	 */
	public String getPathInfo() {
		return pathInfo;
	}

	/**
	 * @param pathInfo the pathInfo to set
	 */
	public void setPathInfo(String pathInfo) {
		this.pathInfo = pathInfo;
	}

	/**
	 * @return the localAddress
	 */
	public String getLocalAddress() {
		return localAddress;
	}

	/**
	 * @param localAddress the localAddress to set
	 */
	public void setLocalAddress(String localAddress) {
		this.localAddress = localAddress;
	}

	/**
	 * @return the localPort
	 */
	public int getLocalPort() {
		return localPort;
	}

	/**
	 * @param localPort the localPort to set
	 */
	public void setLocalPort(int localPort) {
		this.localPort = localPort;
	}

	/**
	 * @return the method
	 */
	public String getMethod() {
		return method;
	}

	/**
	 * @param method the method to set
	 */
	public void setMethod(String method) {
		this.method = method;
	}

	/**
	 * @return the requestURI
	 */
	public String getRequestURI() {
		return requestURI;
	}

	/**
	 * @param requestURI the requestURI to set
	 */
	public void setRequestURI(String requestURI) {
		this.requestURI = requestURI;
	}

	/**
	 * @return the requestURL
	 */
	public String getRequestURL() {
		return requestURL;
	}

	/**
	 * @param requestURL the requestURL to set
	 */
	public void setRequestURL(String requestURL) {
		this.requestURL = requestURL;
	}

	/**
	 * @return the requestedSessionID
	 */
	public String getRequestedSessionID() {
		return requestedSessionID;
	}

	/**
	 * @param requestedSessionID the requestedSessionID to set
	 */
	public void setRequestedSessionID(String requestedSessionID) {
		this.requestedSessionID = requestedSessionID;
	}

	/**
	 * @return the serverPort
	 */
	public int getServerPort() {
		return serverPort;
	}

	/**
	 * @param serverPort the serverPort to set
	 */
	public void setServerPort(int serverPort) {
		this.serverPort = serverPort;
	}

	/**
	 * @return the serverPath
	 */
	public String getServerPath() {
		return serverPath;
	}

	/**
	 * @param serverPath the serverPath to set
	 */
	public void setServerPath(String serverPath) {
		this.serverPath = serverPath;
	}

	/**
	 * @return the serverName
	 */
	public String getServerName() {
		return serverName;
	}

	/**
	 * @param serverName the serverName to set
	 */
	public void setServerName(String serverName) {
		this.serverName = serverName;
	}

	/**
	 * @return the label
	 */
	public String getLabel() {
		return label;
	}

	/**
	 * @param label the label to set
	 */
	public void setLabel(String label) {
		this.label = label;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the clickDate
	 */
	public Date getClickDate() {
		return clickDate;
	}

	/**
	 * @param clickDate the clickDate to set
	 */
	public void setClickDate(Date clickDate) {
		this.clickDate = clickDate;
	}
}
