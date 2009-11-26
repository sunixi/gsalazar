package com.angel.dao.generic.query.clauses.impl;

import java.util.ArrayList;
import java.util.List;

import com.angel.common.helpers.CollectionHelper;
import com.angel.common.helpers.StringHelper;
import com.angel.dao.generic.query.Query;
import com.angel.dao.generic.query.builder.QueryBuilder;
import com.angel.dao.generic.query.clauses.SelectClause;
import com.angel.dao.generic.query.params.QuerySelectParam;
import com.angel.dao.generic.query.params.impl.HQLSelectFromParam;



/**
 * This class is used to build a query param. 
 *
 * @author Guille Salazar
 * @since 14/Jul/2009
 */
public class HQLSelectClause implements SelectClause {
	
	private List<QuerySelectParam> selectParams;
	private List<Object> subQueriesParams;
	private Class<?> beanClass;

	public HQLSelectClause(){
		super();
		this.setSelectParams(new ArrayList<QuerySelectParam>());
		this.setSubQueriesParams(new ArrayList<Object>());
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
			clause = "select " + this.buildBeanClassQuery();
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
			clause += this.hasBeanClassAssigned() ? ") " : "";
		}
		return StringHelper.replaceAllRecursively(clause, "  ", " ");
	}
	
	protected String buildBeanClassQuery() {
		String beanClassQuery = "";
		if(this.hasBeanClassAssigned()){
			beanClassQuery = " new " + this.beanClass.getName() + "( ";
		}
		return beanClassQuery;
	}
	
	public boolean hasBeanClassAssigned() {
		return this.beanClass != null;
	}
	
	public boolean hasQuerySelectParams(){
		return this.getSelectParams().size() > 0;
	}
	
	public HQLSelectClause add(String property){
		return this.add(property, StringHelper.EMPTY_STRING);
	}
	public HQLSelectClause add(String propertyName, String alias){
		return this.addQuerySelectParam(propertyName, alias);
	}
	
	public HQLSelectClause add(String prefix, String beanName, String alias) {
		return this.addQuerySelectParam(prefix + "." + beanName, alias);
	}
	
	public HQLSelectClause addAliased(String prefix, String beanName) {
		return this.addQuerySelectParam(prefix + "." + beanName, StringHelper.EMPTY_STRING);
	}
	
	protected HQLSelectClause addQuerySelectParam(String propertyName, String alias){
		this.getSelectParams().add(new HQLSelectFromParam(propertyName, alias));
		return this;
	}

	public HQLSelectClause addMin(String propertyName, String alias){
		return this.addQuerySelectParam("min(" + propertyName + ")", alias);
	}
	public HQLSelectClause addMin(String property){
		return this.addMin(property, StringHelper.EMPTY_STRING);
	}
	
	public HQLSelectClause addMax(String propertyName, String alias){
		return this.addQuerySelectParam("max(" + propertyName + ")", alias);
	}
	public HQLSelectClause addMax(String property){
		return this.addMax(property, StringHelper.EMPTY_STRING);
	}
	
	public HQLSelectClause addCount(String propertyName, String alias){
		return this.addQuerySelectParam("count(" + propertyName + ")", alias);
	}
	public HQLSelectClause addCount(String property){
		return this.addCount(property, StringHelper.EMPTY_STRING);
	}
	
	public HQLSelectClause addAvg(String propertyName, String alias){
		return this.addQuerySelectParam("avg(" + propertyName + ")", alias);
	}
	public HQLSelectClause addAvg(String property){
		return this.addAvg(property, StringHelper.EMPTY_STRING);
	}
	
	public HQLSelectClause addSum(String propertyName, String alias){
		return this.addQuerySelectParam("sum(" + propertyName + ")", alias);
	}
	public HQLSelectClause addSum(String property){
		return this.addSum(property, StringHelper.EMPTY_STRING);
	}

	public HQLSelectClause addDistinct(String propertyName, String alias){
		return this.addQuerySelectParam("distinct " + propertyName, alias);
	}
	public HQLSelectClause addDistinct(String property){
		return this.addDistinct(property, StringHelper.EMPTY_STRING);
	}
	
	public HQLSelectClause addCountDistincts(String alias, String ...propertiesNames){
		String propertiesNamePlain = StringHelper.convertToPlainString(propertiesNames, ",");
		return this.addQuerySelectParam("count( distinct " + propertiesNamePlain + ")", alias);
	}
	public HQLSelectClause addCountDistinct(String propertyName, String alias){
		return this.addQuerySelectParam("count( distinct " + propertyName + ")", alias);
	}
	public HQLSelectClause addCountDistinct(String property){
		return this.addCountDistinct(property, StringHelper.EMPTY_STRING);
	}

	public SelectClause addSubQuery(QueryBuilder queryBuilder) {
		Query query = queryBuilder.buildQuery();
		String q = query.getQuery();
		List<?> params = CollectionHelper.convertTo(query.getParams());
		this.subQueriesParams.addAll(params);
		return this.addQuerySelectParam("( " + q + ")", StringHelper.EMPTY_STRING);
	}
	
	public SelectClause addSubQuery(QueryBuilder queryBuilder, String alias){
		
		return this;
	}

	public Object[] getSubQueriesParams() {
		return this.subQueriesParams.toArray();
	}

	/**
	 * @param subQueriesParams the subQueriesParams to set
	 */
	protected void setSubQueriesParams(List<Object> subQueriesParams) {
		this.subQueriesParams = subQueriesParams;
	}
	
	public boolean hasQuery(){
		return this.getSelectParams().size() > 0;
	}
	
	public SelectClause assignBeanClass(Class<?> beanClass){
		this.beanClass = beanClass;
		return this;
	}

}