/**
 * 
 */
package com.angel.dao.generic.query.clauses.factory;

import com.angel.dao.generic.query.clauses.QueryClause;


/**
 * @author William.
 * @since 16/September/2009.
 *
 */
public interface ClauseFactory {

	public QueryClause createSelectClause();
	
	public QueryClause createFromClause();
	
	public QueryClause createWhereClause();
	
	public QueryClause createGroupByClause();
	
	public QueryClause createHavingClause();
	
	public QueryClause createOrderByClause();

}
