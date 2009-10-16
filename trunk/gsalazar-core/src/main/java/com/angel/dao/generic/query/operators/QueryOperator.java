/**
 * 
 */
package com.angel.dao.generic.query.operators;

import com.angel.dao.generic.query.params.QueryConditionParam;

/**
 * @author William.
 * @since 16/September/2009.
 *
 */
public interface QueryOperator {

	public String createOperatorQuery(QueryConditionParam queryConditionParam);

}
