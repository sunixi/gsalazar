package com.angel.dao.generic.query.clauses.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.angel.common.helpers.StringHelper;
import com.angel.dao.generic.query.clauses.QueryClause;
import com.angel.dao.generic.query.condition.Condition;
import com.angel.dao.generic.query.params.QueryConditionParam;
import com.angel.dao.generic.query.params.impl.HQLWhereParam;



/**
 * This class is used to build a query param. 
 *
 * @author Guille Salazar
 * @since 14/Jul/2009
 */
public class HavingClause implements QueryClause{
	
	private Condition condition;
	private boolean openGroup;
	private List<QueryConditionParam> conditions;
	
	public HavingClause(){
		super();
		this.setConditions(new ArrayList<QueryConditionParam>());
	}
	
	public HavingClause and(){
		this.setCondition(Condition.AND);
		return this;
	}
	
	public HavingClause or(){
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
		return StringHelper.replaceAllRecursively(clause, "  ", " ").trim();
	}
	
	private boolean hasQueryConditionParams() {
		return this.getConditions().size() > 0;
	}

	public HavingClause closeGroup(){
		this.setOpenGroup(false);
		return this;
	}
	
	public HavingClause openGroup(){
		this.setOpenGroup(true);
		return this;
	}
	public HavingClause grouped(HavingClause havingClause){
		this.openGroup();
		for(QueryConditionParam qcp: havingClause.getConditions()){
			this.addQueryWhereParam(qcp);
		}
		this.closeGroup();
		return this;
	}
	
	protected HavingClause addQueryWhereParam(String name, Object ...valuesParams){
		List<Object> values = new ArrayList<Object>();
		if(valuesParams != null){
			for(Object param: valuesParams){
				values.add(param);
			}
		}
		return this.addQueryWhereParam(new HQLWhereParam(this.getCondition(), name, this.isOpenGroup(), !this.isOpenGroup(), values));
	}
	
	protected HavingClause addQueryWhereParam(QueryConditionParam queryConditionParam) {
		this.getConditions().add(queryConditionParam);
		if(this.getConditions().size() == 1){
			this.setCondition(Condition.AND);
		}
		return this;
	}

	public HavingClause minGT(String alias, String property, long value){
		return this.addQueryWhereParam("min(" + alias + "." + property + ") > ?", value);
	}
	public HavingClause minGT(String property, long value){
		return this.addQueryWhereParam("min(" + property + ") > ?", value);
	}
	public HavingClause minST(String property, long value){
		return this.addQueryWhereParam("min(" + property + ") < ?", value);
	}
	public HavingClause minST(String alias, String property, long value){
		return this.addQueryWhereParam("min(" + alias + "." + property + ") < ?", value);
	}
	public HavingClause minEQ(String property, long value){
		return this.addQueryWhereParam("min(" + property + ") = ?", value);
	}
	public HavingClause minEQ(String alias, String property, long value){
		return this.addQueryWhereParam("min(" + alias + "." + property + ") = ?", value);
	}

	public HavingClause maxGT(String property, long value){
		return this.addQueryWhereParam("max(" + property + ") > ?", value);
	}
	public HavingClause maxGT(String alias, String property, long value){
		return this.addQueryWhereParam("max(" + alias + "." + property + ") > ?", value);
	}
	public HavingClause maxST(String property, long value){
		return this.addQueryWhereParam("min(" + property + ") < ?", value);
	}
	public HavingClause maxST(String alias, String property, long value){
		return this.addQueryWhereParam("min(" + alias + "." + property + ") < ?", value);
	}
	public HavingClause maxEQ(String property, long value){
		return this.addQueryWhereParam("min(" + property + ") = ?", value);
	}
	public HavingClause maxEQ(String alias, String property, long value){
		return this.addQueryWhereParam("min(" + alias + "." + property + ") = ?", value);
	}
	
	public HavingClause countGT(String property, long value){
		return this.addQueryWhereParam("count(" + property + ") > ?", value);
	}
	public HavingClause countGT(String alias, String property, long value){
		return this.addQueryWhereParam("count(" + alias + "." + property + ") > ?", value);
	}
	public HavingClause countST(String property, long value){
		return this.addQueryWhereParam("count(" + property + ") < ?", value);
	}
	public HavingClause countST(String alias, String property, long value){
		return this.addQueryWhereParam("count(" + alias + "." + property + ") < ?", value);
	}
	public HavingClause countEQ(String property, long value){
		return this.addQueryWhereParam("count(" + property + ") = ?", value);
	}
	public HavingClause countEQ(String alias, String property, long value){
		return this.addQueryWhereParam("count(" + alias + "." + property + ") = ?", value);
	}
	
	public HavingClause avgGT(String property, long value){
		return this.addQueryWhereParam("avg(" + property + ") > ?", value);
	}
	public HavingClause avgGT(String alias, String property, long value){
		return this.addQueryWhereParam("avg(" + alias + "." + property + ") > ?", value);
	}
	public HavingClause avgST(String property, long value){
		return this.addQueryWhereParam("avg(" + property + ") < ?", value);
	}
	public HavingClause avgST(String alias, String property, long value){
		return this.addQueryWhereParam("avg(" + alias + "." + property + ") < ?", value);
	}
	public HavingClause avgEQ(String property, long value){
		return this.addQueryWhereParam("avg(" + property + ") = ?", value);
	}
	public HavingClause avgEQ(String alias, String property, long value){
		return this.addQueryWhereParam("avg(" + alias + "." + property + ") = ?", value);
	}
	
	public HavingClause sumGT(String property, long value){
		return this.addQueryWhereParam("sum(" + property + ") > ?", value);
	}
	public HavingClause sumGT(String alias, String property, long value){
		return this.addQueryWhereParam("sum(" + alias + "." + property + ") > ?", value);
	}
	public HavingClause sumST(String property, long value){
		return this.addQueryWhereParam("sum(" + property + ") < ?", value);
	}
	public HavingClause sumST(String alias, String property, long value){
		return this.addQueryWhereParam("sum(" + alias + "." + property + ") < ?", value);
	}
	public HavingClause sumEQ(String property, long value){
		return this.addQueryWhereParam("sum(" + property + ") = ?", value);
	}
	public HavingClause sumEQ(String alias, String property, long value){
		return this.addQueryWhereParam("sum(" + alias + "." + property + ") = ?", value);
	}
	
	public HavingClause countDistinctGT(String property, long value){
		return this.addQueryWhereParam("count( distinct " + property + ") > ?", value);
	}
	public HavingClause countDistinctGT(String alias, String property, long value){
		return this.addQueryWhereParam("count( distinct " + alias + "." + property + ") > ?", value);
	}
	public HavingClause countDistinctST(String property, long value){
		return this.addQueryWhereParam("count( distinct " + property + ") < ?", value);
	}
	public HavingClause countDistinctST(String alias, String property, long value){
		return this.addQueryWhereParam("count( distinct " + alias + "." + property + ") < ?", value);
	}
	public HavingClause countDistinctEQ(String property, long value){
		return this.addQueryWhereParam("count( distinct " + property + ") = ?", value);
	}
	public HavingClause countDistinctEQ(String alias, String property, long value){
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
	protected boolean isOpenGroup() {
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
