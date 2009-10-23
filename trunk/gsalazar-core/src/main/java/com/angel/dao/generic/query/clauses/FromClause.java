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

	/**
	 * Add a bean class with an alias name. This get object bean class name to add its name to from clause. 
	 * 
	 * @param <T> some object.
	 * @param objectBean class to add to this from clause.
	 * @param alias associated to object bean to be referenced in the query.
	 * @return this from clause.
	 * @see FromClause#add(String, String).
	 */
	public <T extends Object> FromClause add(Class<T> objectBean, String alias) ;
	
	/**
	 * Add a bean name to from clause with an alias to be referenced in other part of query.
	 * 
	 * @param beanName to add to select clause.
	 * @param alias name to associated to bean name.
	 * @return this from clause.
	 */
	public FromClause add(String beanName, String alias);
	
	/**
	 * Add a bean name without an alias name associated.
	 * 
	 * @param beanName to add to this from clause.
	 * @return this from clause.
	 * @see FromClause#add(Class, String)
	 * @see	FromClause#add(String, String)
	 * @see	FromClause#add(Class)
	 */
	public FromClause add(String beanName);
	
	public <T extends Object> FromClause add(Class<T> objectBean);

	public <T extends Object> FromClause innerJoin(Class<T> objectBean);
	public <T extends Object> FromClause innerJoin(Class<T> objectBean, String alias);
	public FromClause innerJoin(String beanName);
	public FromClause innerJoin(String beanName, String alias);
	public FromClause innerJoin(String beanName, String propertyBeanName, String alias);
	public <T extends Object> FromClause leftJoin(Class<T> objectBean);
	public <T extends Object> FromClause leftJoin(Class<T> objectBean, String alias);
	public FromClause leftJoin(String objectBean);
	public FromClause leftJoin(String objectBean, String alias);
	public FromClause rightJoin(String objectBean);
	public FromClause rightJoin(String objectBean, String propertyBeanName, String alias);
	public FromClause rightJoin(String objectBean, String alias);
	public <T extends Object> FromClause rightJoin(Class<T> objectBean);
	public <T extends Object> FromClause rightJoin(Class<T> objectBean, String alias);
	
	public FromClause fullJoin(String objectBean, String alias);
	public <T extends Object> FromClause fullJoin(Class<T> objectBean, String alias);

}
