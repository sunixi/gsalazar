package com.angel.dao.generic.query.clauses.impl;

import com.angel.dao.generic.query.clauses.HQLClause;



/**
 * This class is used to build a query param. 
 *
 * @author Guille Salazar
 * @since 14/Jul/2009
 */
public class WhereClause implements HQLClause{

	public String createClause() {
		return null;
	}
	
	public WhereClause or(String propertyName, Object value){
		return this;
	}
	
	public WhereClause and(String propertyName, Object value){
		return this;
	}
}
