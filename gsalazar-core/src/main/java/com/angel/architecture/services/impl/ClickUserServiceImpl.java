/**
 * 
 */
package com.angel.architecture.services.impl;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.angel.architecture.daos.ClickUserDAO;
import com.angel.architecture.persistence.beans.ClickUser;
import com.angel.architecture.services.ClickUserService;

import flex.messaging.FlexContext;

/**
 * @author Guillermo Salazar
 *
 */
public class ClickUserServiceImpl extends GenericServiceImpl implements ClickUserService{

	protected ClickUserDAO getClickUserDAO(){
		return (ClickUserDAO) super.getGenericDAO();
	}
	
	public List<ClickUser> buscarTodosPorRemoteAddress(String remoteAddress) {
		return this.getClickUserDAO().buscarTodosPorRemoteAddress(remoteAddress);
	}

	public List<ClickUser> buscarTodosPorRemoteHost(String remoteHost) {
		return this.getClickUserDAO().buscarTodosPorRemoteHost(remoteHost);
	}

	public List<ClickUser> buscarTodosPorRemotePort(int remotePort) {
		return this.getClickUserDAO().buscarTodosPorRemotePort(remotePort);
	}

	public ClickUser createClick(String label, String description, Date clickDate) {
		HttpServletRequest request = FlexContext.getHttpRequest();
		ClickUser clickUser = new ClickUser(request);
		clickUser.setLabel(label);
		clickUser.setDescription(description);
		clickUser.setClickDate(clickDate);
		clickUser = (ClickUser) super.create(clickUser);
		return clickUser;
	}
}
