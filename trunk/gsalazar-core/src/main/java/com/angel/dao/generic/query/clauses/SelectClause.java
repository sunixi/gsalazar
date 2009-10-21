/**
 * 
 */
package com.angel.dao.generic.query.clauses;

import com.angel.dao.generic.query.builder.QueryBuilder;



/**
 * @author William.
 * @since 16/September/2009.
 *
 */
public interface SelectClause extends QueryClause {

	public boolean hasQuerySelectParams();
	
	public SelectClause addSubQuery(QueryBuilder queryBuilder);
	
	public SelectClause addSubQuery(QueryBuilder queryBuilder, String alias);
	
	public SelectClause add(String property);
	public SelectClause add(String propertyName, String alias);
	
	public SelectClause add(String prefix, String beanName, String alias);
	public SelectClause addAliased(String prefix, String beanName);
	
	public SelectClause addMin(String propertyName, String alias);
	public SelectClause addMin(String property);
	
	public SelectClause addMax(String propertyName, String alias);
	public SelectClause addMax(String property);
	
	public SelectClause addCount(String propertyName, String alias);
	public SelectClause addCount(String property);
	
	public SelectClause addAvg(String propertyName, String alias);
	public SelectClause addAvg(String property);
	
	public SelectClause addSum(String propertyName, String alias);
	public SelectClause addSum(String property);
	
	public SelectClause addDistinct(String propertyName, String alias);
	public SelectClause addDistinct(String property);
	
	public SelectClause addCountDistincts(String alias, String ...propertiesNames);
	public SelectClause addCountDistinct(String propertyName, String alias);
	public SelectClause addCountDistinct(String property);
	
	public Object[] getSubQueriesParams();
}
