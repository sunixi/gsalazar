/**
 * 
 */
package com.angel.architecture.daos;

import java.util.List;

import com.angel.architecture.persistence.beans.ClickUser;
import com.angel.architecture.persistence.ids.ObjectId;
import com.angel.dao.generic.interfaces.GenericDAO;

/**
 * @author Guillermo Salazar.
 * @since 27/Agosto/2009.
 *
 */
public interface ClickUserDAO extends GenericDAO<ClickUser, ObjectId>{

	public List<ClickUser> buscarTodosPorRemoteHost(String remoteHost);
	
	public List<ClickUser> buscarTodosPorRemoteAddress(String remoteAddress);
	
	public List<ClickUser> buscarTodosPorRemotePort(int remotePort);
	

}
