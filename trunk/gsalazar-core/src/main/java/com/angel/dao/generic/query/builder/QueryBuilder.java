/**
 * 
 */
package com.angel.dao.generic.query.builder;

import com.angel.dao.generic.query.Query;
import com.angel.dao.generic.query.clauses.QueryClause;




/**
 * @author William.
 * @since 16/September/2009.
 *
 */
public interface QueryBuilder {

	public Query buildQuery();
	
	public QueryClause buildSelectClause();
	
	public QueryClause buildFromClause();
	
	public QueryClause buildWhereClause();
	
	public QueryClause buildGroupByClause();
	
	public QueryClause buildHavingClause();
	
	public QueryClause buildOrderByClause();
	
	public void setMaxResult(int maxResult);
	
	public void setFetchSize(int fetchSize);

}
