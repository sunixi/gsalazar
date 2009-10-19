/**
 * 
 */
package com.angel.dao.generic.query.params;

/**
 * @author William.
 * @since 16/September/2009.
 *
 */
public interface QueryFromParam {

	public String getName();
	
	public String getAlias();
	
	public String getFrom();

	public boolean isJoin();
}
