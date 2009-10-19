package com.angel.dao.generic.query.clauses.impl;

import java.util.List;

import com.angel.common.helpers.StringHelper;
import com.angel.dao.generic.query.clauses.QueryClause;



/**
 * This class is used to build a query param. 
 *
 * @author Guille Salazar
 * @since 14/Jul/2009
 */
public class OrderByClause implements QueryClause{
	
	private List<String> ordersBy;
	
	public OrderByClause(){
		super();
	}

	public String createClause() {
		String clause = "";
		if(this.hasOrderByParams()){
			clause = " order by ";
			int total = this.getOrdersBy().size();
			for(String ob: this.getOrdersBy()){
				if(total == 1){
					clause += ob;
				} else {
					clause += "," + ob;
				}
			}
		}
		return StringHelper.replaceAllRecursively(clause, "  ", " ").trim();
	}
	
	protected boolean hasOrderByParams(){
		return this.getOrdersBy().size() > 0;
	}
	
	protected OrderByClause addOrderByClause(String property) {
		this.getOrdersBy().add(property);
		return this;
	}
	
	public OrderByClause desc(String property) {
		return this.addOrderByClause(" desc " + property);
	}
	
	public OrderByClause desc(String alias, String property) {
		return this.addOrderByClause(" desc " + alias + "." + property);
	}

	public OrderByClause asc(String property) {
		return this.addOrderByClause(" asc " + property);
	}
	
	public OrderByClause asc(String alias, String property) {
		return this.addOrderByClause(" asc " + alias + "." + property);
	}

	/**
	 * @return the ordersBy
	 */
	protected List<String> getOrdersBy() {
		return ordersBy;
	}

	/**
	 * @param ordersBy the ordersBy to set
	 */
	protected void setOrdersBy(List<String> ordersBy) {
		this.ordersBy = ordersBy;
	}	
}
