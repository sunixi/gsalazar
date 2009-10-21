/**
 * 
 */
package com.angel.dao.generic.query.factory;

import com.angel.dao.generic.query.clauses.FromClause;
import com.angel.dao.generic.query.clauses.GroupByClause;
import com.angel.dao.generic.query.clauses.HavingClause;
import com.angel.dao.generic.query.clauses.OrderByClause;
import com.angel.dao.generic.query.clauses.SelectClause;
import com.angel.dao.generic.query.clauses.WhereClause;


/**
 * @author William.
 * @since 16/September/2009.
 *
 */
public interface ClauseFactory {

	public SelectClause createSelectClause();
	
	public FromClause createFromClause();
	
	public WhereClause createWhereClause();
	
	public GroupByClause createGroupByClause();
	
	public HavingClause createHavingClause();
	
	public OrderByClause createOrderByClause();

}
