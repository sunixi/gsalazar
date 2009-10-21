/**
 * 
 */
package com.angel.dao.generic.query.clauses;



/**
 * @author William.
 * @since 16/September/2009.
 *
 */
public interface OrderByClause extends QueryClause{

	public boolean hasOrderByParams();
	
	public OrderByClause addOrderByClause(String property);
	
	public OrderByClause desc(String property);
	
	public OrderByClause desc(String alias, String property);
	
	public OrderByClause asc(String property);
	
	public OrderByClause asc(String alias, String property);

}
