package com.angel.dao.generic.query.clauses.impl;

import java.util.ArrayList;
import java.util.List;

import com.angel.common.helpers.StringHelper;
import com.angel.dao.generic.query.clauses.QueryClause;
import com.angel.dao.generic.query.params.QuerySelectParam;
import com.angel.dao.generic.query.params.impl.HQLSelectFromParam;



/**
 * This class is used to build a query param. 
 *
 * @author Guille Salazar
 * @since 14/Jul/2009
 */
public class SelectClause implements QueryClause{
	
	private List<QuerySelectParam> selectParams;

	public SelectClause(){
		super();
		this.setSelectParams(new ArrayList<QuerySelectParam>());
	}
	
	/**
	 * @return the selectParams
	 */
	public List<QuerySelectParam> getSelectParams() {
		return selectParams;
	}

	/**
	 * @param selectParams the selectParams to set
	 */
	protected void setSelectParams(List<QuerySelectParam> selectParams) {
		this.selectParams = selectParams;
	}

	public String createClause() {
		String clause = "";
		if(this.hasQuerySelectParams()){
			clause = "select ";
			int current = 1;
			int total = this.getSelectParams().size();
			for(QuerySelectParam qsp: this.getSelectParams()){
				if(current != total){
					if(total != 1){
						clause += qsp.getSelection() + ", ";
					} else {
						clause += qsp.getSelection();
					}
				} else {
					clause += qsp.getSelection();
				}
				current++;
			}
		}
		return StringHelper.replaceAllRecursively(clause, "  ", " ").trim();
	}
	
	protected boolean hasQuerySelectParams(){
		return this.getSelectParams().size() > 0;
	}
	
	public SelectClause add(String property){
		return this.add(property, StringHelper.EMPTY_STRING);
	}
	public SelectClause add(String propertyName, String alias){
		return this.addQuerySelectParam(propertyName, alias);
	}
	
	protected SelectClause addQuerySelectParam(String propertyName, String alias){
		this.getSelectParams().add(new HQLSelectFromParam(propertyName, alias));
		return this;
	}

	public SelectClause addMin(String propertyName, String alias){
		return this.addQuerySelectParam("min(" + propertyName + ")", alias);
	}
	public SelectClause addMin(String property){
		return this.addMin(property, StringHelper.EMPTY_STRING);
	}
	
	public SelectClause addMax(String propertyName, String alias){
		return this.addQuerySelectParam("max(" + propertyName + ")", alias);
	}
	public SelectClause addMax(String property){
		return this.addMax(property, StringHelper.EMPTY_STRING);
	}
	
	public SelectClause addCount(String propertyName, String alias){
		return this.addQuerySelectParam("count(" + propertyName + ")", alias);
	}
	public SelectClause addCount(String property){
		return this.addCount(property, StringHelper.EMPTY_STRING);
	}
	
	public SelectClause addAvg(String propertyName, String alias){
		return this.addQuerySelectParam("avg(" + propertyName + ")", alias);
	}
	public SelectClause addAvg(String property){
		return this.addAvg(property, StringHelper.EMPTY_STRING);
	}
	
	public SelectClause addSum(String propertyName, String alias){
		return this.addQuerySelectParam("sum(" + propertyName + ")", alias);
	}
	public SelectClause addSum(String property){
		return this.addSum(property, StringHelper.EMPTY_STRING);
	}

	public SelectClause addDistinct(String propertyName, String alias){
		return this.addQuerySelectParam("distinct " + propertyName, alias);
	}
	public SelectClause addDistinct(String property){
		return this.addDistinct(property, StringHelper.EMPTY_STRING);
	}
	
	public SelectClause addCountDistincts(String alias, String ...propertiesNames){
		String propertiesNamePlain = StringHelper.convertToPlainString(propertiesNames, ",");
		return this.addQuerySelectParam("count( distinct " + propertiesNamePlain + ")", alias);
	}
	public SelectClause addCountDistinct(String propertyName, String alias){
		return this.addQuerySelectParam("count( distinct " + propertyName + ")", alias);
	}
	public SelectClause addCountDistinct(String property){
		return this.addCountDistinct(property, StringHelper.EMPTY_STRING);
	}
}
