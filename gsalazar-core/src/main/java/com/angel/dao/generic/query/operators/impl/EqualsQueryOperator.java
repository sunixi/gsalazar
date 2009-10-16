package com.angel.dao.generic.query.operators.impl;

import com.angel.dao.generic.query.operators.QueryOperator;
import com.angel.dao.generic.query.params.QueryConditionParam;



/**
 * This class is used to build a query param. 
 *
 * @author Guille Salazar
 * @since 14/Jul/2009
 */
public class EqualsQueryOperator implements QueryOperator{
	
	private boolean stringParam = true;

	public EqualsQueryOperator(){
		super();
	}
	
	public EqualsQueryOperator(boolean stringParam){
		this();
		this.setStringParam(stringParam);
	}

	public String createOperatorQuery(QueryConditionParam queryConditionParam) {
		if(this.isStringParam()){
			return queryConditionParam.getName() + " = '" + queryConditionParam.getValue() + "' ";
		} else {
			return queryConditionParam.getName() + " = " + queryConditionParam.getValue() + " ";
		}
	}

	/**
	 * @return the stringParam
	 */
	public boolean isStringParam() {
		return stringParam;
	}

	/**
	 * @param stringParam the stringParam to set
	 */
	public void setStringParam(boolean stringParam) {
		this.stringParam = stringParam;
	}
}
