/**
 * 
 */
package com.angel.dao.generic.query.params;

import com.angel.dao.generic.query.operators.QueryOperator;

/**
 * @author William.
 * @since 16/September/2009.
 *
 */
public interface QueryConditionParam {
	
	public String getName();

	public Object getValue();

	public String getCondition();

	public QueryOperator getOperator();

	public boolean isOpenTag();

	public boolean isCloseTag();
	
	public String getQueryCondition();
}
