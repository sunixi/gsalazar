/**
 * 
 */
package com.angel.dao.generic.query.clauses;




/**
 * @author William.
 * @since 16/September/2009.
 *
 */
public interface FromClause extends QueryClause {
	
	
	public boolean hasQueryFromParams();
	public FromClause addQueryFromParam(String beanName, String alias);
	public FromClause addQueryJoinFromParam(String beanName, String alias);

	public <T extends Object> FromClause add(Class<T> objectBean, String alias) ;
	public FromClause add(String beanName, String alias);

	public <T extends Object> FromClause innerJoin(Class<T> objectBean);
	public <T extends Object> FromClause innerJoin(Class<T> objectBean, String alias);
	public FromClause innerJoin(String beanName);
	public FromClause innerJoin(String beanName, String alias);
	public FromClause innerJoin(String beanName, String propertyBeanName, String alias);
	public <T extends Object> FromClause leftJoin(Class<T> objectBean);
	public <T extends Object> FromClause leftJoin(Class<T> objectBean, String alias);
	public <T extends Object> FromClause leftJoin(String objectBean);
	public <T extends Object> FromClause leftJoin(String objectBean, String alias);
	public <T extends Object> FromClause rightJoin(String objectBean);
	public <T extends Object> FromClause rightJoin(String objectBean, String propertyBeanName, String alias);
	public <T extends Object> FromClause rightJoin(String objectBean, String alias);
	public <T extends Object> FromClause rightJoin(Class<T> objectBean);
	public <T extends Object> FromClause rightJoin(Class<T> objectBean, String alias);

}
