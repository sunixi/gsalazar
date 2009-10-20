/**
 * 
 */
package com.angel.architecture.services;

import java.util.Date;
import java.util.List;

import com.angel.architecture.persistence.beans.ClickUser;
import com.angel.architecture.services.interfaces.GenericService;

/**
 * @author Guillermo Salazar
 *
 */
public interface ClickUserService extends GenericService {

	public List<ClickUser> buscarTodosPorRemoteHost(String remoteHost);
	
	public List<ClickUser> buscarTodosPorRemoteAddress(String remoteAddress);
	
	public List<ClickUser> buscarTodosPorRemotePort(int remotePort);
	
	public ClickUser createClick(String label, String description, Date clickDate);
	
}
