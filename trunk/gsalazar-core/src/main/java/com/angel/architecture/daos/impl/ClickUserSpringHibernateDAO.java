/**
 * 
 */
package com.angel.architecture.daos.impl;

import java.util.List;

import com.angel.architecture.daos.ClickUserDAO;
import com.angel.architecture.persistence.beans.ClickUser;
import com.angel.architecture.persistence.ids.ObjectId;
import com.angel.dao.generic.impl.GenericSpringHibernateDAO;

/**
 * 
 * 
 * @author Guillermo Salazar
 * @since 28/Agosto/2009.
 *
 */
public class ClickUserSpringHibernateDAO extends GenericSpringHibernateDAO<ClickUser, ObjectId> implements ClickUserDAO {

	public ClickUserSpringHibernateDAO() {
		super(ClickUser.class, ObjectId.class);
	}

	public List<ClickUser> buscarTodosPorRemoteAddress(String remoteAddress) {
		return (List<ClickUser>) super.findAll("remoteAddress", remoteAddress);
	}

	public List<ClickUser> buscarTodosPorRemoteHost(String remoteHost) {
		return (List<ClickUser>) super.findAll("remoteHost", remoteHost);
	}

	public List<ClickUser> buscarTodosPorRemotePort(int remotePort) {
		return (List<ClickUser>) super.findAll("remotePort", remotePort);
	}
}
