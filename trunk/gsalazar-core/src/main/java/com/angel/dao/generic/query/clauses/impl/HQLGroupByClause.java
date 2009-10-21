package com.angel.dao.generic.query.clauses.impl;

import java.util.ArrayList;
import java.util.List;

import com.angel.common.helpers.StringHelper;
import com.angel.dao.generic.query.clauses.GroupByClause;



/**
 * This class is used to build a query param. 
 *
 * @author Guille Salazar
 * @since 14/Jul/2009
 */
public class HQLGroupByClause implements GroupByClause{
	
	private List<String> queryGroups;

	public HQLGroupByClause(){
		super();
		this.setQueryGroups(new ArrayList<String>());
	}
	public String createClause() {
		String clause = "";
		if(this.hasQueryGroupsParams()){
			clause = " group by ";
			int total = this.getQueryGroups().size();
			for(String ob: this.getQueryGroups()){
				if(total == 1){
					clause += ob;
				} else {
					clause += "," + ob;
				}
			}
		}
		return StringHelper.replaceAllRecursively(clause, "  ", " ");
		//return StringHelper.replaceAllRecursively(clause, "  ", " ").trim();
	}

	public boolean hasQueryGroupsParams() {
		return this.getQueryGroups().size() > 0;
	}
	public HQLGroupByClause add(String alias, String property){
		return this.addQueryGroupBy(alias + "." + property);
	}
	
	public HQLGroupByClause add(String property){
		return this.addQueryGroupBy(property);
	}
	
	protected HQLGroupByClause addQueryGroupBy(String property){
		this.getQueryGroups().add(property);
		return this;
	}
	/**
	 * @return the queryGroups
	 */
	public List<String> getQueryGroups() {
		return queryGroups;
	}
	/**
	 * @param queryGroups the queryGroups to set
	 */
	protected void setQueryGroups(List<String> queryGroups) {
		this.queryGroups = queryGroups;
	}
}
