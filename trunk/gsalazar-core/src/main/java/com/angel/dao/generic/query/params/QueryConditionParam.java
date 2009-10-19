/**
 * 
 */
package com.angel.dao.generic.query.params;

import java.util.List;

import com.angel.dao.generic.query.condition.Condition;


/**
 * @author William.
 * @since 16/September/2009.
 *
 */
public interface QueryConditionParam {
	
	public String getName();

	public List<Object> getValues();

	public Condition getCondition();

	public boolean isOpenTag();

	public boolean isCloseTag();
	
	public String queryCondition();
}
