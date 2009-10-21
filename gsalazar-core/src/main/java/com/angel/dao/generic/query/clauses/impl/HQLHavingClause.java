package com.angel.dao.generic.query.clauses.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.angel.common.helpers.StringHelper;
import com.angel.dao.generic.query.clauses.HavingClause;
import com.angel.dao.generic.query.condition.Condition;
import com.angel.dao.generic.query.params.QueryConditionParam;
import com.angel.dao.generic.query.params.impl.HQLWhereParam;



/**
 * This class is used to build a query param. 
 *
 * @author Guille Salazar
 * @since 14/Jul/2009
 */
public class HQLHavingClause implements HavingClause {
	
	private Condition condition;
	private boolean openGroup;
	private List<QueryConditionParam> conditions;
	
	public HQLHavingClause(){
		super();
		this.setConditions(new ArrayList<QueryConditionParam>());
	}
	
	public HQLHavingClause and(){
		this.setCondition(Condition.AND);
		return this;
	}
	
	public HQLHavingClause or(){
		this.setCondition(Condition.OR);
		return this;
	}
	
	public String createClause() {
		String clause = "";
		if(this.hasQueryConditionParams()){
			clause = " having ";
			for(QueryConditionParam qcp: this.getConditions()){
				clause += qcp.queryCondition();
			}
		}
		return StringHelper.replaceAllRecursively(clause, "  ", " ");
	}
	
	public boolean hasQueryConditionParams() {
		return this.getConditions().size() > 0;
	}

	public HQLHavingClause closeGroup(){
		this.setOpenGroup(false);
		return this;
	}
	
	public HQLHavingClause openGroup(){
		this.setOpenGroup(true);
		return this;
	}
	public HQLHavingClause grouped(HQLHavingClause havingClause){
		this.openGroup();
		for(QueryConditionParam qcp: havingClause.getConditions()){
			this.addQueryWhereParam(qcp);
		}
		this.closeGroup();
		return this;
	}
	
	protected HQLHavingClause addQueryWhereParam(String name, Object ...valuesParams){
		List<Object> values = new ArrayList<Object>();
		if(valuesParams != null){
			for(Object param: valuesParams){
				values.add(param);
			}
		}
		return this.addQueryWhereParam(new HQLWhereParam(this.getCondition(), name, this.isOpenGroup(), !this.isOpenGroup(), values));
	}
	
	protected HQLHavingClause addQueryWhereParam(QueryConditionParam queryConditionParam) {
		this.getConditions().add(queryConditionParam);
		if(this.getConditions().size() == 1){
			this.setCondition(Condition.AND);
		}
		return this;
	}

	public HQLHavingClause minGT(String alias, String property, long value){
		return this.addQueryWhereParam("min(" + alias + "." + property + ") > ?", value);
	}
	public HQLHavingClause minGT(String property, long value){
		return this.addQueryWhereParam("min(" + property + ") > ?", value);
	}
	public HQLHavingClause minST(String property, long value){
		return this.addQueryWhereParam("min(" + property + ") < ?", value);
	}
	public HQLHavingClause minST(String alias, String property, long value){
		return this.addQueryWhereParam("min(" + alias + "." + property + ") < ?", value);
	}
	public HQLHavingClause minEQ(String property, long value){
		return this.addQueryWhereParam("min(" + property + ") = ?", value);
	}
	public HQLHavingClause minEQ(String alias, String property, long value){
		return this.addQueryWhereParam("min(" + alias + "." + property + ") = ?", value);
	}

	public HQLHavingClause maxGT(String property, long value){
		return this.addQueryWhereParam("max(" + property + ") > ?", value);
	}
	public HQLHavingClause maxGT(String alias, String property, long value){
		return this.addQueryWhereParam("max(" + alias + "." + property + ") > ?", value);
	}
	public HQLHavingClause maxST(String property, long value){
		return this.addQueryWhereParam("min(" + property + ") < ?", value);
	}
	public HQLHavingClause maxST(String alias, String property, long value){
		return this.addQueryWhereParam("min(" + alias + "." + property + ") < ?", value);
	}
	public HQLHavingClause maxEQ(String property, long value){
		return this.addQueryWhereParam("min(" + property + ") = ?", value);
	}
	public HQLHavingClause maxEQ(String alias, String property, long value){
		return this.addQueryWhereParam("min(" + alias + "." + property + ") = ?", value);
	}
	
	public HQLHavingClause countGT(String property, long value){
		return this.addQueryWhereParam("count(" + property + ") > ?", value);
	}
	public HQLHavingClause countGT(String alias, String property, long value){
		return this.addQueryWhereParam("count(" + alias + "." + property + ") > ?", value);
	}
	public HQLHavingClause countST(String property, long value){
		return this.addQueryWhereParam("count(" + property + ") < ?", value);
	}
	public HQLHavingClause countST(String alias, String property, long value){
		return this.addQueryWhereParam("count(" + alias + "." + property + ") < ?", value);
	}
	public HQLHavingClause countEQ(String property, long value){
		return this.addQueryWhereParam("count(" + property + ") = ?", value);
	}
	public HQLHavingClause countEQ(String alias, String property, long value){
		return this.addQueryWhereParam("count(" + alias + "." + property + ") = ?", value);
	}
	
	public HQLHavingClause avgGT(String property, long value){
		return this.addQueryWhereParam("avg(" + property + ") > ?", value);
	}
	public HQLHavingClause avgGT(String alias, String property, long value){
		return this.addQueryWhereParam("avg(" + alias + "." + property + ") > ?", value);
	}
	public HQLHavingClause avgST(String property, long value){
		return this.addQueryWhereParam("avg(" + property + ") < ?", value);
	}
	public HQLHavingClause avgST(String alias, String property, long value){
		return this.addQueryWhereParam("avg(" + alias + "." + property + ") < ?", value);
	}
	public HQLHavingClause avgEQ(String property, long value){
		return this.addQueryWhereParam("avg(" + property + ") = ?", value);
	}
	public HQLHavingClause avgEQ(String alias, String property, long value){
		return this.addQueryWhereParam("avg(" + alias + "." + property + ") = ?", value);
	}
	
	public HQLHavingClause sumGT(String property, long value){
		return this.addQueryWhereParam("sum(" + property + ") > ?", value);
	}
	public HQLHavingClause sumGT(String alias, String property, long value){
		return this.addQueryWhereParam("sum(" + alias + "." + property + ") > ?", value);
	}
	public HQLHavingClause sumST(String property, long value){
		return this.addQueryWhereParam("sum(" + property + ") < ?", value);
	}
	public HQLHavingClause sumST(String alias, String property, long value){
		return this.addQueryWhereParam("sum(" + alias + "." + property + ") < ?", value);
	}
	public HQLHavingClause sumEQ(String property, long value){
		return this.addQueryWhereParam("sum(" + property + ") = ?", value);
	}
	public HQLHavingClause sumEQ(String alias, String property, long value){
		return this.addQueryWhereParam("sum(" + alias + "." + property + ") = ?", value);
	}
	
	public HQLHavingClause countDistinctGT(String property, long value){
		return this.addQueryWhereParam("count( distinct " + property + ") > ?", value);
	}
	public HQLHavingClause countDistinctGT(String alias, String property, long value){
		return this.addQueryWhereParam("count( distinct " + alias + "." + property + ") > ?", value);
	}
	public HQLHavingClause countDistinctST(String property, long value){
		return this.addQueryWhereParam("count( distinct " + property + ") < ?", value);
	}
	public HQLHavingClause countDistinctST(String alias, String property, long value){
		return this.addQueryWhereParam("count( distinct " + alias + "." + property + ") < ?", value);
	}
	public HQLHavingClause countDistinctEQ(String property, long value){
		return this.addQueryWhereParam("count( distinct " + property + ") = ?", value);
	}
	public HQLHavingClause countDistinctEQ(String alias, String property, long value){
		return this.addQueryWhereParam("count( distinct " + alias + "." + property + ") = ?", value);
	}

	/**
	 * @return the condition
	 */
	protected Condition getCondition() {
		return condition;
	}

	/**
	 * @param condition the condition to set
	 */
	protected void setCondition(Condition condition) {
		this.condition = condition;
	}

	public Collection<? extends Object> getParams() {
		List<Object> params = new ArrayList<Object>();
		for(QueryConditionParam qcp: this.getConditions()){
			params.addAll(qcp.getValues());
		}
		return params;
	}

	/**
	 * @return the conditions
	 */
	public List<QueryConditionParam> getConditions() {
		return conditions;
	}

	/**
	 * @return the openGroup
	 */
	public boolean isOpenGroup() {
		return openGroup;
	}

	/**
	 * @param openGroup the openGroup to set
	 */
	protected void setOpenGroup(boolean openGroup) {
		this.openGroup = openGroup;
	}

	/**
	 * @param conditions the conditions to set
	 */
	protected void setConditions(List<QueryConditionParam> conditions) {
		this.conditions = conditions;
	}	
}
