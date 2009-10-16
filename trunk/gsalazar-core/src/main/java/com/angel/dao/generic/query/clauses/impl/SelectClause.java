package com.angel.dao.generic.query.clauses.impl;

import java.util.ArrayList;
import java.util.List;

import com.angel.dao.generic.query.clauses.HQLClause;
import com.angel.dao.generic.query.params.QuerySelectionParam;



/**
 * This class is used to build a query param. 
 *
 * @author Guille Salazar
 * @since 14/Jul/2009
 */
public class SelectClause implements HQLClause{
	
	private List<QuerySelectionParam> selectParams;

	public SelectClause(){
		super();
		this.setSelectParams(new ArrayList<QuerySelectionParam>());
	}
	
	/**
	 * @return the selectParams
	 */
	public List<QuerySelectionParam> getSelectParams() {
		return selectParams;
	}

	/**
	 * @param selectParams the selectParams to set
	 */
	protected void setSelectParams(List<QuerySelectionParam> selectParams) {
		this.selectParams = selectParams;
	}

	public String createClause() {
		return null;
	}
	
	
	
}
