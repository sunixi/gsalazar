/**
 * 
 */
package com.angel.dao.generic.query.builder;

import com.angel.dao.generic.query.Query;
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
public interface QueryBuilder {

	public Query buildQuery();

	public SelectClause getSelectClause();
	
	public FromClause getFromClause();
	
	public WhereClause getWhereClause();
	
	public GroupByClause getGroupByClause();
	
	public HavingClause getHavingClause();
	
	public OrderByClause getOrderByClause();
	
	public void setMaxResult(int maxResult);
	
	public void setFetchSize(int fetchSize);

}
